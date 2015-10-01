package nikhanch.com.sfbandroidchatbubbles.ApplicationService.MeetingsManager;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Url;

/**
 * Created by dmitsh on 9/30/2015.
 */
public interface MeetingsManagementService {
    @GET
    Call<MyMeetingsResponse> GetMyMeetings(@Url String url, @Header("Authorization") String token);

    @GET
    Call<MyOnlineMeetingsResponse> GetMyOnlineMeeting(@Url String url, @Header("Authorization") String token);
}
