package nikhanch.com.sfbandroidchatbubbles.ApplicationService;

import java.util.HashMap;

import io.fabric.sdk.android.services.concurrency.Task;
import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Url;

/**
 * Created by dmitsh on 9/29/2015.
 */
public interface LyncService {
    @GET
    Call<LyncDiscoverResponse> GetAutoDResponse(@Url String url);

    @GET
    Call<Object> GetUserResponse(@Url String url);

    @Headers("Accept: application/json")
    //@Headers("Content-Type: application/x-www-form-urlencoded;charset='utf-8'")
    @FormUrlEncoded
    @POST
    Call<Object> GetOAuthToken(
            @Url String url,
            @Field("grant_type") String grantType,
            @Field("username") String userName,
            @Field("password") String password
            );
}
