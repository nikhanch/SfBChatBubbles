package nikhanch.com.sfbandroidchatbubbles.ApplicationService;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    public final Context mContext;

    public LyncSignIn(Context context)
    {
        mContext = context;
        GetLyncAutoDiscoveryUrl();
    }

    public void OnDiscoverResponse(boolean isSuccess, String url){
        if(isSuccess && !url.isEmpty())
        {
            GetWebTicketEndpointUrl(url);

        }
    }

    public void OnWebTicketEndpointUrl(String url)
    {
        if(!url.isEmpty())
            PostOAuthUrl(url);
    }

    private void GetLyncAutoDiscoveryUrl() {
        try {
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(LYNC_SERVER_API_URL).build();
            LyncService service = retrofit.create(LyncService.class);
            Call<LyncDiscoverResponse> call = service.GetAutoDResponse(LYNC_SERVER_API_URL);
            Callback<LyncDiscoverResponse> cb = new CustomCallBack<LyncDiscoverResponse>(this) {
                @Override
                public void onResponse(Response<LyncDiscoverResponse> response, Retrofit retrofit){
                    String str = response.body().Links.user.href;
                    this.component.OnDiscoverResponse(response.isSuccess(), str);
                    Toast.makeText(mContext, str, Toast.LENGTH_LONG).show();
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

    private void GetWebTicketEndpointUrl(String url)
    {
        try {
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(LYNC_SERVER_API_URL).build();
            LyncService service = retrofit.create(LyncService.class);
            Call<Object> call = service.GetUserResponse(url);
            Callback<Object> cb = new CustomCallBack<Object>(this) {
                @Override
                public void onResponse(Response<Object> response, Retrofit retrofit){
                    String webTicketEndpointUrl = "";
                    String grantType = "";

                    if (!response.isSuccess() && response.code() == 401) {
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
                        Toast.makeText(mContext, webTicketEndpointUrl, Toast.LENGTH_LONG).show();
                        this.component.OnWebTicketEndpointUrl(webTicketEndpointUrl);
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

    public void PostOAuthUrl(String url)
    {
        try {
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(LYNC_SERVER_API_URL).build();

            retrofit.client().interceptors().add(new Interceptor(){
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
                });



            LyncService service = retrofit.create(LyncService.class);

            String foo = "grant_type=password&username=lmtest5@microsoft.com&password=Pass@sept2015";
            Call<Object> call = service.GetOAuthToken(url, "password", "lmtest5@microsoft.com", "Pass@sept2015");
            Callback<Object> cb = new CustomCallBack<Object>(this) {
                @Override
                public void onResponse(Response<Object> response, Retrofit retrofit){
                    boolean status = response.isSuccess();
                    int code = response.code();
                    String str = response.headers().toString();
                    Toast.makeText(mContext, str, Toast.LENGTH_LONG).show();
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

    abstract class CustomCallBack<T> implements Callback<T>{
            protected LyncSignIn component = null;
            public CustomCallBack(LyncSignIn component ) {
                    this.component = component ;
                }
    }

}
