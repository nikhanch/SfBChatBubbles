package nikhanch.com.sfbandroidchatbubbles.ApplicationService.WebTicket;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.HttpUrl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nikhanch.com.sfbandroidchatbubbles.ApplicationService.LyncSignIn;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.SignInLinksResponse;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Url;

/**
 * Created by nikhanch on 9/29/2015.
 */

public class CWTTokenProvider implements ICWTTokenProvider{
    public final Context mContext;

    private Map<String, OAuthToken> mValidTokens;
    private Map<String, List<TokenRequestFromUser>> mPendingTokenRequests;
    private Retrofit mRetrofit;
    private WebTicketService mWebTicketService;

    public CWTTokenProvider(Context context){
        this.mContext = context;
        mValidTokens = new HashMap<>();
        mPendingTokenRequests = new HashMap<>();
        mRetrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(LyncSignIn.LYNC_SERVER_API_URL).build();
        mWebTicketService = mRetrofit.create(WebTicketService.class);
    }

    public void GetToken(URL url, Object userContext, GetTokenCallback callback){
        final String cachedToken = getCachedToken(url);
        if (cachedToken != ""){
            this.invokeCallback(cachedToken, userContext, callback, true);
            return;
        }
        addCallbackToPendingList(url, userContext, callback);
        getWebTicketUrl(url, callback);
    }

    private String getCachedToken(URL url){
        String host = url.getHost();
        if (mValidTokens.containsKey(host)){
            OAuthToken token = mValidTokens.get(host);
            if (token.isValid()){
                return token.accessToken;
            }
        }
        return "";
    }

    private void invokeCallback(final String token, final Object userContext, final GetTokenCallback userCallback, final boolean succeeded){
        Handler h = new Handler();
        h.post(new Runnable() {
            @Override
            public void run() {
                if (succeeded) {
                    userCallback.OnTokenRetrieved(userContext, "Bearer " + token);
                } else {
                    userCallback.OnTokenRetrievalFailed(userContext);
                }
            }
        });
    }

    public void OnWebTicketEndpointUrlFound(URL webTicketEndpointUrl, String tokenForHost) {
        getTokenFromService(webTicketEndpointUrl, tokenForHost);
    }

    private void getWebTicketUrl(URL url, GetTokenCallback callback){
        String tokenForHost = url.getHost();
        try {
            Call<Object> call = mWebTicketService.GetWebTicketUrl(url.toString());
            Callback<Object> cb = new CustomCallBack<Object>(this, tokenForHost) {
                @Override
                public void onResponse(Response<Object> response, Retrofit retrofit){
                    String webTicketEndpointUrl = "";
                    String grantType = "";

                    if(!response.isSuccess() && response.code() == 401) {
                        Headers headers = response.headers();
                        Map<String, List<String>> map = headers.toMultimap();
                        List<String> list = map.get("WWW-Authenticate");
                        Iterator it = list.iterator();
                        while(it.hasNext()) {
                            String data = (String) it.next();

                            if (data.contains("MsRtcOAuth")) {
                                int indx1, indx2;
                                indx1 = data.indexOf("href=\"");
                                if (indx1 != -1 && indx1 + 6 < data.length()) {
                                    indx2 = data.indexOf('"', indx1 + 6);
                                    if (indx2 != -1)
                                        webTicketEndpointUrl = data.substring(indx1 + 6, indx2);
                                }
                                indx1 = data.indexOf("grant_type=\"");
                                if (indx1 != -1 && indx1 + 12 < data.length()) {
                                    indx2 = data.indexOf('"', indx1 + 12);
                                    if (indx2 != -1)
                                        grantType = data.substring(indx1 + 12, indx2);
                                }
                                break;
                            }
                        }
                        //Toast.makeText(mContext, webTicketEndpointUrl, Toast.LENGTH_LONG).show();
                        try{
                            URL webTicketUrl = new URL(webTicketEndpointUrl);
                            this.component.OnWebTicketEndpointUrlFound(webTicketUrl, this.tokenForHost);

                        }
                        catch (MalformedURLException e){
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                            this.component.OnTokenRetrievedFromService(null, this.tokenForHost);
                        }
                    }
                }
                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            };
            call.enqueue(cb);
        }
        catch (Exception e){
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    public void OnTokenRetrievedFromService(OAuthToken token, String tokenForHost){
        if (this.mPendingTokenRequests.containsKey(tokenForHost)){
            List<TokenRequestFromUser> callbacks = this.mPendingTokenRequests.get(tokenForHost);
            Iterator<TokenRequestFromUser> iter = callbacks.iterator();
            while(iter.hasNext()){
                TokenRequestFromUser context = iter.next();
                GetTokenCallback callback = context.callback;
                String accessToken = "";
                boolean succeeded = false;
                if (token != null){
                    accessToken = token.accessToken;
                    succeeded = true;
                }
                this.invokeCallback(accessToken, context.userContext, callback, succeeded);
            }
        }
        this.mPendingTokenRequests.remove(tokenForHost);
        this.mValidTokens.put(tokenForHost, token);
    }

    private void getTokenFromService(URL url, String tokenForHost){

        try{

            Call<OAuthToken> call = mWebTicketService.GetOAuthToken(url.toString(), "password", "lmtest3@microsoft.com", "Pass@sept2015");
            Callback<OAuthToken> cb = new CustomCallBack<OAuthToken>(this, tokenForHost) {
                @Override
                public void onResponse(Response<OAuthToken> response, Retrofit retrofit){
                    OAuthToken responseToken = response.body();
                    String token = "";

                    if  (response.code() == 200){
                        token = responseToken.accessToken;
                    }

                    //Toast.makeText(mContext, token, Toast.LENGTH_LONG).show();
                    this.component.OnTokenRetrievedFromService(responseToken, this.tokenForHost);
                }
                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            };
            call.enqueue(cb);
        }
        catch (Exception e){
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void addCallbackToPendingList(URL url, Object userContext, GetTokenCallback callback){
        TokenRequestFromUser contextToSave = new TokenRequestFromUser(userContext, callback);
        String key = url.getHost();
        if (mPendingTokenRequests.containsKey(key)){
            List<TokenRequestFromUser> callbacks = mPendingTokenRequests.get(key);
            callbacks.add(contextToSave);
        }
        else{
            ArrayList<TokenRequestFromUser> pendingCallback = new ArrayList<>();
            pendingCallback.add(contextToSave);
            mPendingTokenRequests.put(url.getHost(), pendingCallback);
        }
    }

    abstract class CustomCallBack<T> implements Callback<T>{
        protected CWTTokenProvider component = null;
        protected String tokenForHost = null;
        public CustomCallBack(CWTTokenProvider component, String tokenForHost ) {
            this.component = component;
            this.tokenForHost = tokenForHost;
        }
    }

    class TokenRequestFromUser{
        public final Object userContext;
        public final GetTokenCallback callback;
        TokenRequestFromUser(Object userContext, GetTokenCallback callback){
            this.userContext = userContext;
            this.callback = callback;
        }
    }
}
