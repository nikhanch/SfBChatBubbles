package nikhanch.com.sfbandroidchatbubbles.ApplicationService.WebTicket;

import java.net.URL;

/**
 * Created by nikhanch on 9/29/2015.
 */
public interface ICWTTokenProvider {
    void GetToken(URL url, Object context, GetTokenCallback callback);
}
