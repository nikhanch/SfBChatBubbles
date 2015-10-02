package nikhanch.com.sfbandroidchatbubbles.ApplicationService.BuddylistManager;

import android.widget.Toast;

import com.squareup.okhttp.Interceptor;
import com.squareup.otto.Subscribe;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
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
    //endregion

    //region WebService methods
    private void getMyContacts(){

        URL myContactsUrl = UriUtils.GetAbsoluteUrl(this.mApplicationsResource.Embedded.people.Links.myContacts.href);
        mRetrofit.client().interceptors().add(new RetrofitInterceptor(true, true));

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
        CustomRetrofitCallback(BuddylistManager manager){
            this.buddylistManager = manager;
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
