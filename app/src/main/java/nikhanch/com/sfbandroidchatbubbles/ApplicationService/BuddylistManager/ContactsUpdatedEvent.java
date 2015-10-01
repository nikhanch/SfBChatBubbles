package nikhanch.com.sfbandroidchatbubbles.ApplicationService.BuddylistManager;

import java.util.Map;

import nikhanch.com.sfbandroidchatbubbles.Models.ContactModel;

/**
 * Created by nikhanch on 9/30/2015.
 */
public class ContactsUpdatedEvent {
    public final Map<String, ContactModel> modelsUpdated;
    ContactsUpdatedEvent(Map<String, ContactModel> modelsUpdated){
        this.modelsUpdated = modelsUpdated;
    }
}
