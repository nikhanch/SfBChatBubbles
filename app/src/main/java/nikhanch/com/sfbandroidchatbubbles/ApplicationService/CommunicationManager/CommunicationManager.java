package nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager;

import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import nikhanch.com.sfbandroidchatbubbles.Application;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.ApplicationsResource;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.BuddylistManager.ContactsUpdatedEvent;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.BuddylistManager.LyncContactManagementService;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.BuddylistManager.MyContactsResponse;
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
public class CommunicationManager implements ICommunicationManager{

    SfBChatBubblesService mApplicationService;
    ApplicationsResource mApplicationsResource = null;

    Retrofit mRetrofit = null;
    CommunicationService mCommunicationService = null;

    public CommunicationManager(SfBChatBubblesService service){
        this.mApplicationService = service;
        this.mRetrofit = new Retrofit.Builder().baseUrl(LyncSignIn.LYNC_SERVER_API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        this.mCommunicationService = mRetrofit.create(CommunicationService.class);
        //this.mRetrofit.client().interceptors().add(new RetrofitInterceptor(true, true));
        Application.getServiceEventBus().register(this);
    }

    //region ICommunicationManager
    @Override
    public void StartConversation(String recepientSipurl, String subject){
        MessagingInviteRequest req = new MessagingInviteRequest("Normal", subject, recepientSipurl, UUID.randomUUID().toString());
        this.startConversationWebMethods(req);
    }
    //endregion

    //region EventListeners
    @Subscribe
    public void OnApplicationResourceUpdated(ApplicationsResource resource){
        if (resource != null && updateNeeded()){
            this.mApplicationsResource = resource;
            //getConversations();
            //getConversationHistory();
            OnConversationHistoryRetrieved(400);
        }
    }
    //endregion

    //region Callbacks from Webservice Methods
    public void onConversationsUpdated(Object foo){
        /*
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
        */
    }

    public void OnConversationHistoryRetrieved(int code){
        //this.StartConversation("sip:nikhanch@microsoft.com", "Testing 123");
    }

    public void OnConversationCreated(String location, MessagingInviteRequest request){
        Conversation c = new Conversation(this, request.to, location);
        c.GetConversationUrls();
        ConversationEvent ev = new ConversationEvent(request.to, c);
        Application.getServiceEventBus().post(ev);
    }
    //endregion

    //region WebService methods
    private void getConversationHistory(){

        URL urlToGet = getConversationLogsUrl();
        //UriUtils.GetAbsoluteUrl(this.mApplicationsResource.Embedded.communication.Links.conversations.href);

        try {
            GetTokenRequestContext context = new GetTokenRequestContext(this, urlToGet);
            mApplicationService.getCWTTokenProvider().GetToken(urlToGet, context, new CustomGetTokenCallback() {
                @Override
                public void OnTokenRetrieved(Object userContext, String token) {
                    GetTokenRequestContext requestContext = (GetTokenRequestContext) userContext;
                    Call<Object> call = mCommunicationService.GetConversationHistory(requestContext.urlToGet.toString(), token);
                    Callback<Object> cb = new CustomRetrofitCallback<Object>(requestContext.manager) {
                        @Override
                        public void onResponse(Response<Object> response, Retrofit retrofit) {
                            int code = response.code();
                            if (response.isSuccess()) {
                                Object resource = response.body();
                            }
                            this.manager.OnConversationHistoryRetrieved(code);
                        }
                    };
                    call.enqueue(cb);
                }
            });
        } catch (Exception e){
            onOperationFailure("Create Application Resource", e.getMessage());
        }
    }

    private void startConversationWebMethods(MessagingInviteRequest req){

        URL urlToGet = UriUtils.GetAbsoluteUrl(this.mApplicationsResource.Embedded.communication.Links.startMessaging.href);
        try {
            StartMessagingGetTokenRequestContext context = new StartMessagingGetTokenRequestContext(this, urlToGet, req);
            mApplicationService.getCWTTokenProvider().GetToken(urlToGet, context, new CustomGetTokenCallback() {
                @Override
                public void OnTokenRetrieved(Object userContext, String token) {
                    StartMessagingGetTokenRequestContext requestContext = (StartMessagingGetTokenRequestContext) userContext;
                    Call<Object> call = mCommunicationService.StartConversation(requestContext.urlToGet.toString(), token, requestContext.request);
                    Callback<Object> cb = new CustomRetrofitCallback<Object>(requestContext.manager, requestContext.request) {
                        @Override
                        public void onResponse(Response<Object> response, Retrofit retrofit) {
                            int code = response.code();
                            String headers = response.headers().toString();
                            String location = response.headers().get("Location");
                            if (response.isSuccess()) {
                                Object resource = response.body();
                            }
                            MessagingInviteRequest requestContext = (MessagingInviteRequest) this.context;
                            this.manager.OnConversationCreated(location, requestContext);
                        }
                    };
                    call.enqueue(cb);
                }
            });
        } catch (Exception e){
            onOperationFailure("Create Application Resource", e.getMessage());
        }
    }


    private void getConversations(){

        URL urlToGet = UriUtils.GetAbsoluteUrl(this.mApplicationsResource.Embedded.communication.Links.conversations.href);

        try {
            GetTokenRequestContext context = new GetTokenRequestContext(this, urlToGet);
            mApplicationService.getCWTTokenProvider().GetToken(urlToGet, context, new CustomGetTokenCallback() {
                @Override
                public void OnTokenRetrieved(Object userContext, String token) {
                    GetTokenRequestContext requestContext = (GetTokenRequestContext) userContext;
                    Call<Object> call = mCommunicationService.GetOnGoingCoversations(requestContext.urlToGet.toString(), token);
                    Callback<Object> cb = new CustomRetrofitCallback<Object>(requestContext.manager) {
                        @Override
                        public void onResponse(Response<Object> response, Retrofit retrofit) {
                            int code = response.code();
                            if (response.isSuccess()) {
                                Object resource = response.body();
                            }
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

    private URL getConversationLogsUrl(){
        URL conversationLogsUrl = null;
        if (this.mApplicationsResource.Embedded.communication.Links.conversationLogs != null) {
            conversationLogsUrl = UriUtils.GetAbsoluteUrl(this.mApplicationsResource.Embedded.communication.Links.conversationLogs.href);
        }
        else{
            String conversationUrl = this.mApplicationsResource.Embedded.communication.Links.conversations.href;
            if (conversationUrl != null && !conversationUrl.isEmpty()){
                int idx1 = conversationUrl.indexOf("conversations");
                String newUrl = conversationUrl.substring(0, idx1);
                newUrl += "conversationLogs?limit=5";
                conversationLogsUrl = UriUtils.GetAbsoluteUrl(newUrl);
            }
        }

        return conversationLogsUrl;
    }
    //endregion

    //region Helper classes
    abstract class CustomRetrofitCallback<T> implements Callback<T>{
        public final CommunicationManager manager;
        public final Object context;
        CustomRetrofitCallback(CommunicationManager manager){
            this.manager = manager;
            this.context = null;
        }

        CustomRetrofitCallback(CommunicationManager manager, Object context){
            this.manager = manager;
            this.context = context;
        }

        public void onFailure(Throwable t) {
            this.manager.onOperationFailure("send authenticated request", t.getMessage());
        }
    }

    abstract class CustomGetTokenCallback implements GetTokenCallback {
        @Override
        public void OnTokenRetrievalFailed(Object userContext) {
            GetTokenRequestContext requestContext = (GetTokenRequestContext)userContext;
            requestContext.manager.onOperationFailure("get token failed", "");
        }
    }

    class GetTokenRequestContext {
        public final CommunicationManager manager;
        public final URL urlToGet;
        public GetTokenRequestContext(CommunicationManager manager, URL url){
            this.manager = manager;
            this.urlToGet = url;
        }
    }

    class StartMessagingGetTokenRequestContext {
        public final CommunicationManager manager;
        public final URL urlToGet;
        public final MessagingInviteRequest request;
        public StartMessagingGetTokenRequestContext (CommunicationManager manager, URL url, MessagingInviteRequest req){
            this.manager = manager;
            this.urlToGet = url;
            this.request = req;
        }
    }
    //endregion

}
