package nikhanch.com.sfbandroidchatbubbles.ApplicationService;

import android.widget.Toast;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.ResponseBody;
import com.squareup.otto.Produce;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import nikhanch.com.sfbandroidchatbubbles.Application;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.WebTicket.GetTokenCallback;
import nikhanch.com.sfbandroidchatbubbles.ApplicationServiceUtils.RetrofitInterceptor;
import nikhanch.com.sfbandroidchatbubbles.ApplicationServiceUtils.UriUtils;
import okio.Buffer;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by dmitsh on 9/29/2015.
 */


public class LyncSignIn {

    public static final String LYNC_SERVER_API_URL = "https://LyncDiscover.microsoft.com/";

    public final SfBChatBubblesService mApplicationService;
    private String mUserUrl;
    private String mToken;
    private URL mApplicationsResourceUrl;

    private ApplicationsResource mApplicationResource;
    private Retrofit mRetrofit;
    private LyncService mLyncService;
    public LyncSignIn(SfBChatBubblesService context)
    {
        mApplicationService = context;
        mRetrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(LYNC_SERVER_API_URL).build();
        //region Interceptor to read and write web traffic sent
        /*mRetrofit.client().interceptors().add(new Interceptor(){
            @Override public com.squareup.okhttp.Response intercept(Interceptor.Chain chain) throws IOException {
                Request request = chain.request();
                Buffer buffer = new Buffer();
                request.body().writeTo(buffer);
                String requestSent = "Request to +" + request.urlString() + "\n Body is " + buffer.readUtf8();
                String headers = request.headers().toString();
                long t1 = System.nanoTime();
                com.squareup.okhttp.Response response = chain.proceed(request);
                long t2 = System.nanoTime();
                String msg = response.body().string();
                String headersFromRequestResponse = response.request().headers().toString();
                String responseSentStr = String.format("Response from %s in %.1fms%n\n%s", response.request().urlString(), (t2 - t1) / 1e6d, msg);
                String responseHeaders = response.headers().toString();

                return response.newBuilder().body(ResponseBody.create(response.body().contentType(), msg)).build();
            }
        });*/
        //endregion
        mLyncService = mRetrofit.create(LyncService.class);
        Application.getServiceEventBus().register(this);
        GetLyncAutoDiscoveryUrl();
    }

    public URL getApplicationResourceUrl(){
       return  this.mApplicationsResourceUrl;
    }

    //region Event Firing
    @Produce
    public ApplicationsResource getApplicationResource(){
        return this.mApplicationResource;
    }

    private void onApplicationResourceUpdated(){
        Application.getServiceEventBus().post(this.mApplicationResource);
    }
    //endregion

    //region Callbacks From Web Services
    public void OnDiscoverResponse(boolean isSuccess, String url){
        if(isSuccess && !url.isEmpty())
        {
            this.mUserUrl = url;
            getUserUrl();
        }
    }

    public void OnApplicationsResourceFound(String resource){
        try {
            this.mApplicationsResourceUrl = new URL(resource);
            CreateApplicationResource();
        }catch (MalformedURLException e){
            onOperationFailure("Create app resource url from string", e.getMessage());
        }
    }

    public void OnApplicationsResouceCreated(ApplicationsResource resource){
        this.mApplicationResource = resource;

        if (mApplicationResource.Embedded.me.Links.makeMeAvailable != null) {
            makeMeAvailable();
        }
        else{
            OnMadeAvailable();
        }

    }

    public void OnMadeAvailable(){
        this.onApplicationResourceUpdated();
    }
    //endregion

    //region Methods to perform sign in and application creation on UCWA
    private void GetLyncAutoDiscoveryUrl() {
        try {
            Call<SignInLinksResponse> call = mLyncService.GetAutoDResponse(LYNC_SERVER_API_URL);
            Callback<SignInLinksResponse> cb = new CustomCallBack<SignInLinksResponse>(this) {
                @Override
                public void onResponse(Response<SignInLinksResponse> response, Retrofit retrofit){
                    String str = response.body().Links.user.href;
                    this.component.OnDiscoverResponse(response.isSuccess(), str);
                    Toast.makeText(mApplicationService, str, Toast.LENGTH_LONG).show();
                }
            };
            call.enqueue(cb);
        }
        catch (Exception e){
            Toast.makeText(mApplicationService, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void getUserUrl() {
        try {
            URL url = new URL(this.mUserUrl);
            GetTokenRequestContext context = new GetTokenRequestContext(this, this.mUserUrl);
            mApplicationService.getCWTTokenProvider().GetToken(url, context, new CustomGetTokenCallback() {
                @Override
                public void OnTokenRetrieved(Object userContext, String token) {
                    GetTokenRequestContext requestContext = (GetTokenRequestContext) userContext;
                    Call<SignInLinksResponse> call = mLyncService.GetUserResponse(requestContext.urlToRequest, token);
                    Callback<SignInLinksResponse> cb = new CustomCallBack<SignInLinksResponse>(requestContext.signIn) {
                        @Override
                        public void onResponse(Response<SignInLinksResponse> response, Retrofit retrofit) {
                            if (response.isSuccess()) {
                                String applicationsResource = response.body().Links.applications.href;
                                Toast.makeText(mApplicationService, applicationsResource, Toast.LENGTH_LONG).show();
                                this.component.OnApplicationsResourceFound(applicationsResource);
                            }
                        }
                    };
                    call.enqueue(cb);
                }
            });
        }
        catch (Exception e){
            this.onOperationFailure("getUserUrl", e.getMessage());
        }
    }

    private void CreateApplicationResource() {
        try {
            GetTokenRequestContext context = new GetTokenRequestContext(this, this.mApplicationsResourceUrl.toString());

            mApplicationService.getCWTTokenProvider().GetToken(this.mApplicationsResourceUrl, context, new CustomGetTokenCallback() {
                @Override
                public void OnTokenRetrieved(Object userContext, String token) {
                    GetTokenRequestContext requestContext = (GetTokenRequestContext) userContext;
                    Call<ApplicationsResource> call = mLyncService.CreateApplicationResource(requestContext.urlToRequest, token, new ApplicationResourceRequest("en-US", "131313", "262626", "abcdtesttest", "phone"));
                    Callback<ApplicationsResource> cb = new CustomCallBack<ApplicationsResource>(requestContext.signIn) {
                        @Override
                        public void onResponse(Response<ApplicationsResource> response, Retrofit retrofit) {
                            int code = response.code();
                            if (response.isSuccess()) {
                                ApplicationsResource resource = response.body();
                                String myPhotoUrl = resource.Embedded.me.Links.photo.href;
                                this.component.OnApplicationsResouceCreated(resource);
                            }
                        }
                    };
                    call.enqueue(cb);
                }
            });
        } catch (Exception e) {
            onOperationFailure("Create Application Resource", e.getMessage());
        }
    }

    private void makeMeAvailable() {
        try {
            String partialUrlStr = this.mApplicationResource.Embedded.me.Links.makeMeAvailable.href;

            URL urlToGet = UriUtils.GetAbsoluteUrl(partialUrlStr);
            GetTokenRequestContext context = new GetTokenRequestContext(this, urlToGet.toString());
            mRetrofit.client().interceptors().add(new RetrofitInterceptor(true, true));
            mApplicationService.getCWTTokenProvider().GetToken(urlToGet, context, new CustomGetTokenCallback() {
                @Override
                public void OnTokenRetrieved(Object userContext, String token) {
                    GetTokenRequestContext requestContext = (GetTokenRequestContext) userContext;
                    List<String> supportedMessageFormats = new ArrayList<String>();
                    supportedMessageFormats.add("Plain");
                    List<String> supportedModalities = new ArrayList<String>();
                    supportedModalities.add("Messaging");
                    MakeMeAvailableRequest req = new MakeMeAvailableRequest("+14125761138", "busy", supportedMessageFormats, supportedModalities);
                    Call<Object> call = mLyncService.MakeMeAvailable(requestContext.urlToRequest, token, req);
                    Callback<Object> cb = new CustomCallBack<Object>(requestContext.signIn) {
                        @Override
                        public void onResponse(Response<Object> response, Retrofit retrofit) {
                            int code = response.code();
                            if (response.isSuccess()) {
                                this.component.OnMadeAvailable();
                            }
                        }
                    };
                    call.enqueue(cb);
                }
            });
        } catch (Exception e) {
            onOperationFailure("Create Application Resource", e.getMessage());
        }
    }
    //endregion

    //region Helper Methods
    public void onOperationFailure(String component, String message){
        Toast.makeText(mApplicationService, "Component = " + component + " message = " + message, Toast.LENGTH_LONG).show();
    }
    //endregion

    //region Helper Classes
    abstract class CustomCallBack<T> implements Callback<T>{
        protected LyncSignIn component = null;
        public CustomCallBack(LyncSignIn component ) {
                this.component = component ;
            }

        @Override
        public void onFailure(Throwable t) {
            this.component.onOperationFailure("send authenticated request", t.getMessage());
        }
    }

    abstract class CustomGetTokenCallback implements GetTokenCallback{
        @Override
        public void OnTokenRetrievalFailed(Object userContext) {
            GetTokenRequestContext requestContext = (GetTokenRequestContext)userContext;
            requestContext.signIn.onOperationFailure("get token failed", "");
        }
    }

    class GetTokenRequestContext{
        public final LyncSignIn signIn;
        public final String urlToRequest;

        public GetTokenRequestContext(LyncSignIn signIn, String url){
            this.signIn = signIn;
            this.urlToRequest = url;
        }
    }
    //endregion
}
