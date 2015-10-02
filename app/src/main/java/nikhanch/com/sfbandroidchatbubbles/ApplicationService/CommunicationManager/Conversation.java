package nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager;

import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nikhanch.com.sfbandroidchatbubbles.ApplicationService.LyncSignIn;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.SfBChatBubblesService;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.WebTicket.GetTokenCallback;
import nikhanch.com.sfbandroidchatbubbles.ApplicationServiceUtils.RetrofitInterceptor;
import nikhanch.com.sfbandroidchatbubbles.ApplicationServiceUtils.UriUtils;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by nikhanch on 10/1/2015.
 */
public class Conversation {

    private String mRecepient;
    private String mMessagingInviteUrl;
    private String mConversationUrl;
    private String mMessagingUrl;
    private String sendMessageUrl;
    private CommunicationManager mCommunicationManager;
    private Retrofit mRetrofit;
    GetMessagingInviteResponse messagingInviteResponse;
    GetConversationResponse conversationResponse;
List<String> pendingMessages = new ArrayList();
    private SfBChatBubblesService mApplicationService;
    private ConversationService mConversationService = null;
    Conversation(CommunicationManager manager, String recepient, String messagingInviteUrl){
        this.mCommunicationManager = manager;
        this.mMessagingInviteUrl = messagingInviteUrl;
        this.mRecepient = recepient;
        mApplicationService = SfBChatBubblesService.GetInstance;
        mRetrofit = new Retrofit.Builder().baseUrl(LyncSignIn.LYNC_SERVER_API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        mRetrofit.client().interceptors().add(new RetrofitInterceptor(true,true));
        mConversationService = mRetrofit.create(ConversationService.class);
    }

    public void SendMessageInConversation(String message){
        if (!this.mMessagingUrl.isEmpty()){
           // sendMessageWebMethod(message);
        }
        else{
            pendingMessages.add(message);
        }
    }


    public void GetConversationUrls(){
        if (this.mConversationUrl == null){
            getConversationUrlFromInviteUrl();
        }
        else if (this.mMessagingUrl == null || this.mMessagingUrl.isEmpty()){
            getMessagingUrl();
        }
        else if (this.sendMessageUrl == null || this.sendMessageUrl.isEmpty()){
            getSendMessageUrl();
        }
    }

    public void onConversationUrlFound(GetMessagingInviteResponse response){
        this.messagingInviteResponse = response;
        if (response != null && response.Links != null && response.Links.conversation != null) {
            this.mConversationUrl = response.Links.conversation.href;
        }
        GetConversationUrls();
    }

    public void onMessagingUrlFound(GetConversationResponse response){
        this.conversationResponse = response;
        if (response != null && response.Links != null && response.Links.messaging != null) {
            this.mMessagingUrl = response.Links.messaging.href;
        }
        GetConversationUrls();
    }

    public void onSendMessageUrlFound(GetMessagingResourceResponse response){
        if (response != null && response.Links != null && response.Links.sendMessage != null) {
            this.sendMessageUrl = response.Links.sendMessage.href;
        }
        else {
            GetConversationUrls();
        }

        if (pendingMessages.size() > 0) {
            for (String message : pendingMessages) {
                sendMessageWebMethod(message);
            }
            pendingMessages.clear();
        }
        else{
            sendMessageWebMethod("Testing 1 2 3");
        }
    }

    private void getSendMessageUrl(){
        URL urlToGet = UriUtils.GetAbsoluteUrl(this.mMessagingUrl);
        try {
            GetTokenRequestContext context = new GetTokenRequestContext(this, urlToGet);
            mApplicationService.getCWTTokenProvider().GetToken(urlToGet, context, new CustomGetTokenCallback() {
                @Override
                public void OnTokenRetrieved(Object userContext, String token) {
                    GetTokenRequestContext requestContext = (GetTokenRequestContext) userContext;
                    Call<GetMessagingResourceResponse> call = mConversationService.GetMessagingResource(requestContext.urlToGet.toString(), token);
                    Callback<GetMessagingResourceResponse> cb = new CustomRetrofitCallback<GetMessagingResourceResponse>(requestContext.manager) {
                        @Override
                        public void onResponse(Response<GetMessagingResourceResponse> response, Retrofit retrofit) {
                            int code = response.code();
                            String headers = response.headers().toString();
                            GetMessagingResourceResponse response1 = response.body();

                            this.manager.onSendMessageUrlFound(response1);
                        }
                    };
                    call.enqueue(cb);
                }
            });
        } catch (Exception e){
            onOperationFailure("Create Application Resource", e.getMessage());
        }
    }

    private void getConversationUrlFromInviteUrl(){
        URL urlToGet = UriUtils.GetAbsoluteUrl(this.mMessagingInviteUrl);
        try {
            GetTokenRequestContext context = new GetTokenRequestContext(this, urlToGet);
            mApplicationService.getCWTTokenProvider().GetToken(urlToGet, context, new CustomGetTokenCallback() {
                @Override
                public void OnTokenRetrieved(Object userContext, String token) {
                    GetTokenRequestContext requestContext = (GetTokenRequestContext) userContext;
                    Call<GetMessagingInviteResponse> call = mConversationService.GetConversationUrlFromInviteUrl(requestContext.urlToGet.toString(), token);
                    Callback<GetMessagingInviteResponse> cb = new CustomRetrofitCallback<GetMessagingInviteResponse>(requestContext.manager) {
                        @Override
                        public void onResponse(Response<GetMessagingInviteResponse> response, Retrofit retrofit) {
                            int code = response.code();
                            String headers = response.headers().toString();
                            GetMessagingInviteResponse response1 = response.body();

                            this.manager.onConversationUrlFound(response1);
                        }
                    };
                    call.enqueue(cb);
                }
            });
        } catch (Exception e){
            onOperationFailure("Create Application Resource", e.getMessage());
        }
    }

    private void getMessagingUrl(){
        URL urlToGet = UriUtils.GetAbsoluteUrl(this.mConversationUrl);
        try {
            GetTokenRequestContext context = new GetTokenRequestContext(this, urlToGet);
            mApplicationService.getCWTTokenProvider().GetToken(urlToGet, context, new CustomGetTokenCallback() {
                @Override
                public void OnTokenRetrieved(Object userContext, String token) {
                    GetTokenRequestContext requestContext = (GetTokenRequestContext) userContext;
                    Call<GetConversationResponse> call = mConversationService.GetConversation(requestContext.urlToGet.toString(), token);
                    Callback<GetConversationResponse> cb = new CustomRetrofitCallback<GetConversationResponse>(requestContext.manager) {
                        @Override
                        public void onResponse(Response<GetConversationResponse> response, Retrofit retrofit) {
                            int code = response.code();
                            String headers = response.headers().toString();
                            GetConversationResponse response1 = response.body();
                            this.manager.onMessagingUrlFound(response1);
                        }
                    };
                    call.enqueue(cb);
                }
            });
        } catch (Exception e){
            onOperationFailure("Create Application Resource", e.getMessage());
        }
    }

    private void sendMessageWebMethod(String message){
        URL urlToGet = UriUtils.GetAbsoluteUrl(this.sendMessageUrl);
        try {
            GetTokenRequestContext context = new GetTokenRequestContext(this, urlToGet, message);
            mApplicationService.getCWTTokenProvider().GetToken(urlToGet, context, new CustomGetTokenCallback() {
                @Override
                public void OnTokenRetrieved(Object userContext, String token) {
                    GetTokenRequestContext requestContext = (GetTokenRequestContext) userContext;
                    RequestBody body = RequestBody.create(MediaType.parse("text/plain"), requestContext.messageToSend);
                    Call<Object> call = mConversationService.SendMessage(requestContext.urlToGet.toString(), token, body);

                    Callback<Object> cb = new CustomRetrofitCallback<Object>(requestContext.manager) {
                        @Override
                        public void onResponse(Response<Object> response, Retrofit retrofit) {
                            int code = response.code();
                            String headers = response.headers().toString();
                            Object response1 = response.body();
                        }
                    };
                    call.enqueue(cb);
                }
            });
        } catch (Exception e){
            onOperationFailure("Create Application Resource", e.getMessage());
        }
    }

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
        public final Conversation manager;
        public final Object context;
        CustomRetrofitCallback(Conversation manager){
            this.manager = manager;
            this.context = null;
        }

        CustomRetrofitCallback(Conversation manager, Object context){
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
        public final Conversation manager;
        public final URL urlToGet;
        public final String messageToSend;
        public GetTokenRequestContext(Conversation manager, URL url){
            this.manager = manager;
            this.urlToGet = url;
            this.messageToSend = null;
        }

        public GetTokenRequestContext(Conversation manager, URL url, String messageToSend){
            this.manager = manager;
            this.urlToGet = url;
            this.messageToSend = messageToSend;
        }


    }


    //endregion
}
