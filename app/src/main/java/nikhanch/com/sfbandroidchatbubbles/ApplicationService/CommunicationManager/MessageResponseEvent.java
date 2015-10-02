package nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager;

/**
 * Created by dmitsh on 10/1/2015.
 */
public class MessageResponseEvent {

    public MessageResponseEvent(String msg, String sipUrl)
    {
        this.sipUrl = sipUrl;
        message = msg;
    }

    public final String sipUrl;
    public final String message;
}
