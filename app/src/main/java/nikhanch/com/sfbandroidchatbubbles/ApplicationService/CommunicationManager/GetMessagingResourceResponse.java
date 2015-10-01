package nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhanch on 10/1/2015.
 */
public class GetMessagingResourceResponse {

    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("negotiatedMessageFormats")
    @Expose
    public List<String> negotiatedMessageFormats = new ArrayList<String>();
    @SerializedName("_links")
    @Expose
    public nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager.GetMessagingResourceResponse.Links Links;
    @SerializedName("rel")
    @Expose
    public String rel;

    public GetMessagingResourceResponse(String state, List<String> negotiatedMessageFormats, nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager.GetMessagingResourceResponse.Links Links, String rel) {
        this.state = state;
        this.negotiatedMessageFormats = negotiatedMessageFormats;
        this.Links = Links;
        this.rel = rel;
    }

    public class Conversation {

        @SerializedName("href")
        @Expose
        public String href;
        public Conversation(String href) {
            this.href = href;
        }

    }

    public class Links {

        @SerializedName("self")
        @Expose
        public Self self;
        @SerializedName("conversation")
        @Expose
        public Conversation conversation;
        @SerializedName("stopMessaging")
        @Expose
        public StopMessaging stopMessaging;
        @SerializedName("sendMessage")
        @Expose
        public SendMessage sendMessage;
        @SerializedName("setIsTyping")
        @Expose
        public SetIsTyping setIsTyping;
        @SerializedName("typingParticipants")
        @Expose
        public TypingParticipants typingParticipants;

        public Links(Self self, Conversation conversation, StopMessaging stopMessaging, SendMessage sendMessage, SetIsTyping setIsTyping, TypingParticipants typingParticipants) {
            this.self = self;
            this.conversation = conversation;
            this.stopMessaging = stopMessaging;
            this.sendMessage = sendMessage;
            this.setIsTyping = setIsTyping;
            this.typingParticipants = typingParticipants;
        }

    }
    public class Self {

        @SerializedName("href")
        @Expose
        public String href;
        public Self(String href) {
            this.href = href;
        }

    }
    public class SendMessage {

        @SerializedName("href")
        @Expose
        public String href;
        public SendMessage(String href) {
            this.href = href;
        }

    }

    public class SetIsTyping {
        @SerializedName("href")
        public String href;
        public SetIsTyping(String href) {
            this.href = href;
        }
    }

    public class StopMessaging {

        @SerializedName("href")
        public String href;
        public StopMessaging(String href) {
            this.href = href;
        }
    }

    public class TypingParticipants {
        @SerializedName("href")
        public String href;
        public TypingParticipants(String href) {
            this.href = href;
        }
    }
}
