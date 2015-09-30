package nikhanch.com.sfbandroidchatbubbles.ApplicationServiceUtils;

import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

import nikhanch.com.sfbandroidchatbubbles.ApplicationService.SfBChatBubblesService;

/**
 * Created by nikhanch on 9/30/2015.
 */
public class UriUtils {
    public static URL GetAbsoluteUrl(String relUrl){
        SfBChatBubblesService applicationService = SfBChatBubblesService.GetInstance;
        URL appReousourceUrl = applicationService.getLyncSignIn().getApplicationResourceUrl();
        try {
            URL url = new URL(appReousourceUrl.getProtocol(), appReousourceUrl.getHost(), relUrl);
            return url;
        }
        catch (MalformedURLException e){
            Toast.makeText(applicationService, "Failed to create myContactsUrl", Toast.LENGTH_LONG).show();
            return  null;
        }
    }
}
