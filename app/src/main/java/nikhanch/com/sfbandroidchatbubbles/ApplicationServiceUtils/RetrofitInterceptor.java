package nikhanch.com.sfbandroidchatbubbles.ApplicationServiceUtils;

import android.widget.Toast;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import nikhanch.com.sfbandroidchatbubbles.ApplicationService.SfBChatBubblesService;
import okio.Buffer;
import timber.log.Timber;

/**
 * Created by nikhanch on 9/30/2015.
 */
public class RetrofitInterceptor implements Interceptor {

    public final boolean printRequest;
    public final boolean printResponse;
    public RetrofitInterceptor(boolean printRequest, boolean printResponse){
        this.printRequest = printRequest;
        this.printResponse = printResponse;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        try {
            Request request = chain.request();

            if (this.printRequest && request.method() != "GET") {
                Buffer buffer = new Buffer();
                request.body().writeTo(buffer);
                String requestSent = "Request to +" + request.urlString() + "\n Body is " + buffer.readUtf8();
                Timber.i(requestSent);
                String headers = request.headers().toString();
            }
            com.squareup.okhttp.Response response = chain.proceed(request);

            String msg = response.body().string();
            if (this.printResponse) {
                String responseHeaders = response.headers().toString();
                Timber.i("response Headers = " + responseHeaders);
                String headersFromRequestResponse = response.request().headers().toString();
                Timber.i("request Headers sent" + headersFromRequestResponse);
                String responseSentStr = String.format("Response from %s in %s", response.request().urlString(), msg);
                Timber.i(responseSentStr);
            }
            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), msg)).build();
        }
        catch (Exception e){
            Toast.makeText(SfBChatBubblesService.GetInstance, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return null;
    }
}
