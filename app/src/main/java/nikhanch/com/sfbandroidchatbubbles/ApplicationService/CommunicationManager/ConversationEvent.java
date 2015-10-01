package nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager;

/**
 * Created by nikhanch on 10/1/2015.
 */
public class ConversationEvent {
    public final String recepientUrl;
    public final Conversation conversation;

    public ConversationEvent(String recepientUrl, Conversation c){
        this.recepientUrl = recepientUrl;
        this.conversation = c;
    }
}
