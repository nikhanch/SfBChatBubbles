package nikhanch.com.sfbandroidchatbubbles.ApplicationService;

import java.net.URL;

import nikhanch.com.sfbandroidchatbubbles.ApplicationService.WebTicket.OAuthToken;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Url;

/**
 * Created by dmitsh on 9/29/2015.
 */
public interface LyncService {
    @GET
    Call<SignInLinksResponse> GetAutoDResponse(@Url String url);

    @GET
    Call<SignInLinksResponse> GetUserResponse(@Url String url, @Header("Authorization") String token);

    @Headers("Accept: application/json")
    //@Headers("Content-Type: application/x-www-form-urlencoded;charset='utf-8'")
    @FormUrlEncoded
    @POST
    Call<OAuthToken> GetOAuthToken(
            @Url String url,
            @Field("grant_type") String grantType,
            @Field("username") String userName,
            @Field("password") String password
            );

    @Headers("X-MS-Namespace: internal")
    @POST
    Call<ApplicationsResource> CreateApplicationResource(@Url String url, @Header("Authorization") String token, @Body ApplicationResourceRequest request);

    @Headers("X-MS-Namespace: internal")
    @POST
    Call<Object> MakeMeAvailable(@Url String url, @Header("Authorization") String token, @Body MakeMeAvailableRequest request);
}
