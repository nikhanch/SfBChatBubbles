package nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nikhanch on 10/1/2015.
 */
public class MessagingInviteRequest {

    public final String importance;
    public final String subject;
    public final String to;
    public final String operationId;

    MessagingInviteRequest(String importance, String subject, String to, String operationId){
        this.importance = importance;
        this.subject = subject;
        this.to = to;
        this.operationId = operationId;
    }


}
