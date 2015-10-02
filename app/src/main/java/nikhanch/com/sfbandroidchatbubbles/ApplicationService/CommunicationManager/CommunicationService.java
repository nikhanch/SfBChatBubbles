package nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Url;

/**
 * Created by nikhanch on 9/30/2015.
 */
public interface CommunicationService {

    @Headers("X-MS-Namespace: internal")
    @GET
    Call<Object> GetConversationLogs(@Url String url, @Header("Authorization") String token);

    @Headers("X-MS-Namespace: internal")
    @GET
    Call<Object> GetConversationHistory(@Url String url, @Header("Authorization") String token);


    @Headers("X-MS-Namespace: internal")
    @GET
    Call<Object> GetOnGoingCoversations(@Url String url, @Header("Authorization") String token);

    @Headers("X-MS-Namespace: internal")
    @POST
    Call<Object> StartConversation(@Url String url, @Header("Authorization") String token,@Body MessagingInviteRequest request);

    @Headers("X-MS-Namespace: internal")
    @PUT
    Call<Object> PutCommunicationResource(@Url String url, @Header("Authorization") String token,@Body PutCommuncationResourceRequest request);
}
