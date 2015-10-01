package nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Url;

/**
 * Created by nikhanch on 10/1/2015.
 */
public interface ConversationService {
    @Headers("X-MS-Namespace: internal")
    @GET
    Call<GetMessagingInviteResponse> GetConversationUrlFromInviteUrl(@Url String url, @Header("Authorization") String token);


    @Headers("X-MS-Namespace: internal")
    @GET
    Call<GetConversationResponse> GetConversation(@Url String url, @Header("Authorization") String token);

    @Headers("X-MS-Namespace: internal")
    @GET
    Call<GetMessagingResourceResponse> GetMessagingResource(@Url String url, @Header("Authorization") String token);

}
