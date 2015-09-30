package nikhanch.com.sfbandroidchatbubbles.ApplicationService.WebTicket;

import com.squareup.okhttp.HttpUrl;

import java.net.URL;

import nikhanch.com.sfbandroidchatbubbles.ApplicationService.WebTicket.OAuthToken;
import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Url;

/**
 * Created by nikhanch on 9/29/2015.
 */
public interface WebTicketService {
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

    @GET
    Call<Object> GetWebTicketUrl(@Url String url);
}
