package nikhanch.com.sfbandroidchatbubbles.ApplicationService.BuddylistManager;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Url;

/**
 * Created by nikhanch on 9/30/2015.
 */

public interface LyncContactManagementService {

    @GET
    Call<MyContactsResponse> GetMyContacts(@Url String url, @Header("Authorization") String token);
}
