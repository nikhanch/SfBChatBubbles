package nikhanch.com.sfbandroidchatbubbles.ApplicationService.BuddylistManager;

import android.os.MemoryFile;
import android.widget.Toast;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.ResponseBody;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nikhanch.com.sfbandroidchatbubbles.Application;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.ApplicationResourceRequest;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.ApplicationsResource;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.LyncSignIn;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.SfBChatBubblesService;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.WebTicket.GetTokenCallback;
import nikhanch.com.sfbandroidchatbubbles.ApplicationServiceUtils.RetrofitInterceptor;
import nikhanch.com.sfbandroidchatbubbles.ApplicationServiceUtils.UriUtils;
import nikhanch.com.sfbandroidchatbubbles.Models.ContactModel;
import nikhanch.com.sfbandroidchatbubbles.Models.PhotoDownloadCallback;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by nikhanch on 9/30/2015.
 */
public class BuddylistManager implements IBuddylistManager{

    SfBChatBubblesService mApplicationService;
    ApplicationsResource mApplicationsResource = null;

    Retrofit mRetrofit = null;
    LyncContactManagementService mContactManagementService = null;

    Map<String, ContactModel> sipToContactModelMap = new HashMap<>();
Map<String, List<PhotoDownloadCallback>> pendingPhotoDownloadCallbacks = new HashMap<>();
    public BuddylistManager(SfBChatBubblesService service){
        this.mApplicationService = service;
        this.mRetrofit = new Retrofit.Builder().baseUrl(LyncSignIn.LYNC_SERVER_API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        this.mContactManagementService = mRetrofit.create(LyncContactManagementService.class);

        Application.getServiceEventBus().register(this);
    }

    //region IBuddylistManager methods
    @Override
    public Map<String, ContactModel> GetContactModelsMap() {
        return this.sipToContactModelMap;
    }

    @Override
    public ContactModel GetContactModel(String sipUri) {
        if (this.sipToContactModelMap.containsKey(sipUri)){
            return this.sipToContactModelMap.get(sipUri);
        }
        return null;
    }

    @Override
    public void GetPhoto(URL url, PhotoDownloadCallback callback) {
        if (this.pendingPhotoDownloadCallbacks.containsKey(url.toString())){
            this.pendingPhotoDownloadCallbacks.get(url.toString()).add(callback);
        }
        else{
            List<PhotoDownloadCallback> callbacks = new ArrayList<>();
            callbacks.add(callback);
            this.pendingPhotoDownloadCallbacks.put(url.toString(), callbacks);
        }

        getPhotoWebMethod(url);
    }


    //endregion

    //region EventListeners
    @Subscribe
    public void OnApplicationResourceUpdated(ApplicationsResource resource){
        if (resource != null && updateNeeded()){
            this.mApplicationsResource = resource;
            getMyContacts();
            //getMyGroups();
        }
    }
    //endregion

    //region Callbacks from Webservice Methods
    public void onMyContactsFound(MyContactsResponse response){
        Map<String, ContactModel> modelsUpdated = new HashMap<>();
        if (response != null && response.Embedded != null && response.Embedded.contact != null){
            for (MyContactsResponse.Contact contactRecord : response.Embedded.contact){
                if (contactRecord != null){
                    ContactModel model = new ContactModel(contactRecord);
                    modelsUpdated.put(model.getSipUri(), model);
                    this.sipToContactModelMap.put(model.getSipUri(), model);
                }
            }
        }

        ContactsUpdatedEvent event = new ContactsUpdatedEvent(modelsUpdated);
        Application.getServiceEventBus().post(event);
    }

    public void onPhotoDownloaded(File photo, URL photoUrl){
        if (this.pendingPhotoDownloadCallbacks.containsKey(photoUrl.toString())){
            List<PhotoDownloadCallback> callbacks = this.pendingPhotoDownloadCallbacks.get(photoUrl.toString());
            for(PhotoDownloadCallback c : callbacks){
                c.OnPhotoDownloaded(photo);
            }
        }
        this.pendingPhotoDownloadCallbacks.remove(photoUrl.toString());
    }
    //endregion

    //region WebService methods
    private void getPhotoWebMethod(URL photoUrl){
        try {
            GetTokenRequestContext context = new GetTokenRequestContext(this, photoUrl);
            mApplicationService.getCWTTokenProvider().GetToken(photoUrl, context, new CustomGetTokenCallback() {
                @Override
                public void OnTokenRetrieved(Object userContext, String token) {
                    GetTokenRequestContext requestContext = (GetTokenRequestContext) userContext;
                    Call<ResponseBody> call = mContactManagementService.GetPhoto(requestContext.urlToGet.toString(), token);
                    Callback<ResponseBody> cb = new CustomRetrofitCallback<ResponseBody>(requestContext.buddylistManager, requestContext.urlToGet) {
                        @Override
                        public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                            int code = response.code();
                            File photo = null;
                            URL urlToget = (URL)this.context;
                            if (response.isSuccess()){

                                try {
                                    File outputDir = SfBChatBubblesService.GetInstance.getCacheDir(); // context being the Activity pointer
                                    boolean created = outputDir.mkdirs();
                                    String fileName = urlToget.getPath().toString();
                                    fileName = fileName.replace("/", "_");
                                    fileName = fileName.replace("\\", "_");
                                    photo = File.createTempFile(fileName, "jpg");
                                    BufferedSink sink = Okio.buffer(Okio.sink(photo));
                                    sink.writeAll(response.body().source());
                                    sink.close();
                                }
                                catch (IOException e){
                                    onOperationFailure("get photo bytes", e.getMessage());
                                }
                            }
                            this.buddylistManager.onPhotoDownloaded(photo, urlToget);
                        }
                    };
                    call.enqueue(cb);
                }
            });
        } catch (Exception e){
            onOperationFailure("Create Application Resource", e.getMessage());
        }
    }



    private void getMyContacts(){

        URL myContactsUrl = UriUtils.GetAbsoluteUrl(this.mApplicationsResource.Embedded.people.Links.myContacts.href);
        //mRetrofit.client().interceptors().add(new RetrofitInterceptor(true, true));

        try {
            GetTokenRequestContext context = new GetTokenRequestContext(this, myContactsUrl);
            mApplicationService.getCWTTokenProvider().GetToken(myContactsUrl, context, new CustomGetTokenCallback() {
                @Override
                public void OnTokenRetrieved(Object userContext, String token) {
                    GetTokenRequestContext requestContext = (GetTokenRequestContext) userContext;
                    Call<MyContactsResponse> call = mContactManagementService.GetMyContacts(requestContext.urlToGet.toString(), token);
                    Callback<MyContactsResponse> cb = new CustomRetrofitCallback<MyContactsResponse>(requestContext.buddylistManager) {
                        @Override
                        public void onResponse(Response<MyContactsResponse> response, Retrofit retrofit) {
                            int code = response.code();
                            this.buddylistManager.onMyContactsFound(response.body());
                        }
                    };
                    call.enqueue(cb);
                }
            });
        } catch (Exception e){
            onOperationFailure("Create Application Resource", e.getMessage());
        }
    }

    private void getMyGroups(){
        URL myGroups = UriUtils.GetAbsoluteUrl(this.mApplicationsResource.Embedded.people.Links.myGroups.href);
    }
    //endregion

    //region Helper Methods
    public void onOperationFailure(String component, String message){
        Toast.makeText(mApplicationService, "Component = " + component + " message = " + message, Toast.LENGTH_LONG).show();
    }

    private boolean updateNeeded(){
        return  true;
    }
    //endregion

    //region Helper classes
    abstract class CustomRetrofitCallback<T> implements Callback<T>{
        public final BuddylistManager buddylistManager;
        public final Object context;
        CustomRetrofitCallback(BuddylistManager manager){
            this.buddylistManager = manager;
            this.context = null;
        }

        CustomRetrofitCallback(BuddylistManager manager, Object context){
            this.buddylistManager = manager;
            this.context = context;
        }

        public void onFailure(Throwable t) {
            this.buddylistManager.onOperationFailure("send authenticated request", t.getMessage());
        }
    }

    abstract class CustomGetTokenCallback implements GetTokenCallback {
        @Override
        public void OnTokenRetrievalFailed(Object userContext) {
            GetTokenRequestContext requestContext = (GetTokenRequestContext)userContext;
            requestContext.buddylistManager.onOperationFailure("get token failed", "");
        }
    }

    class GetTokenRequestContext {
        public final BuddylistManager buddylistManager;
        public final URL urlToGet;
        public GetTokenRequestContext(BuddylistManager manager, URL url){
            this.buddylistManager = manager;
            this.urlToGet = url;
        }
    }
    //endregion

}
