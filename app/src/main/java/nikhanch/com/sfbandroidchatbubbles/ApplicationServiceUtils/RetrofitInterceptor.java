package nikhanch.com.sfbandroidchatbubbles.ApplicationServiceUtils;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

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
        Request request = chain.request();

        if (this.printRequest) {
            Buffer buffer = new Buffer();
            request.body().writeTo(buffer);
            String requestSent = "Request to +" + request.urlString() + "\n Body is " + buffer.readUtf8();
            Timber.v(requestSent);
            String headers = request.headers().toString();
        }
        com.squareup.okhttp.Response response = chain.proceed(request);

        String msg = response.body().string();
        if (this.printResponse) {
            String responseHeaders = response.headers().toString();
            Timber.v("response Headers = " + responseHeaders);
            String headersFromRequestResponse = response.request().headers().toString();
            Timber.v("request Headers sent" + headersFromRequestResponse );
            String responseSentStr = String.format("Response from %s in %s", response.request().urlString(), msg);
            Timber.v(responseSentStr);
        }
        return response.newBuilder().body(ResponseBody.create(response.body().contentType(), msg)).build();
    }
}
