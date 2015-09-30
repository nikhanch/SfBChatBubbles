package nikhanch.com.sfbandroidchatbubbles.ApplicationService.WebTicket;

import java.net.URL;

/**
 * Created by nikhanch on 9/29/2015.
 */
public interface GetTokenCallback {
    void OnTokenRetrieved(Object userContext, String token);
    void OnTokenRetrievalFailed(Object userContext);
}
