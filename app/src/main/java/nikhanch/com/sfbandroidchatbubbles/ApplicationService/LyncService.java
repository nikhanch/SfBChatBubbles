package nikhanch.com.sfbandroidchatbubbles.ApplicationService;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Url;

/**
 * Created by dmitsh on 9/29/2015.
 */
public interface LyncService {
    @GET
    Call<LyncDiscoverResponse> GetAutoDResponse(@Url String url);

    @GET
    Call<Object> GetUserResponse(@Url String url);
}
