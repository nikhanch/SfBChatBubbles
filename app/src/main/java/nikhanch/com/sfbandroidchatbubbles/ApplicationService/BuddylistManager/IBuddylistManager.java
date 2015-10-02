package nikhanch.com.sfbandroidchatbubbles.ApplicationService.BuddylistManager;

import java.net.URL;
import java.util.Map;

import nikhanch.com.sfbandroidchatbubbles.Models.ContactModel;
import nikhanch.com.sfbandroidchatbubbles.Models.PhotoDownloadCallback;

/**
 * Created by nikhanch on 9/30/2015.
 */
public interface IBuddylistManager {
    Map<String, ContactModel> GetContactModelsMap();

    ContactModel GetContactModel(String sipUri);

    void GetPhoto(URL url, PhotoDownloadCallback callback);
}
