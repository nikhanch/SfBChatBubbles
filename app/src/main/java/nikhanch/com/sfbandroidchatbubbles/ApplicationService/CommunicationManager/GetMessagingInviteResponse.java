package nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nikhanch on 10/1/2015.
 */
public class GetMessagingInviteResponse {

    @SerializedName("direction")
    public String direction;
    @SerializedName("importance")
    public String importance;
    @SerializedName("threadId")
    public String threadId;
    @SerializedName("state")
    public String state;
    @SerializedName("operationId")
    public String operationId;
    @SerializedName("subject")
    public String subject;
    @SerializedName("_links")

    public nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager.GetMessagingInviteResponse.Links Links;
    @SerializedName("rel")

    public String rel;

    public GetMessagingInviteResponse(String direction, String importance, String threadId, String state, String operationId, String subject, nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager.GetMessagingInviteResponse.Links Links, String rel) {
        this.direction = direction;
        this.importance = importance;
        this.threadId = threadId;
        this.state = state;
        this.operationId = operationId;
        this.subject = subject;
        this.Links = Links;
        this.rel = rel;
    }

    public class Cancel {
        @SerializedName("href")
        public String href;
        public Cancel(String href) {
            this.href = href;
        }

    }

    public class Conversation {
        @SerializedName("href")
        public String href;
        public Conversation(String href) {
            this.href = href;
        }

    }

    public class From {
        @SerializedName("href")
        public String href;
        @SerializedName("title")
        public String title;
        public From(String href, String title) {
            this.href = href;
            this.title = title;
        }

    }

    public class Links {

        @SerializedName("self")
        public Self self;
        @SerializedName("from")
        public From from;
        @SerializedName("to")
        public To to;
        @SerializedName("cancel")
        public Cancel cancel;
        @SerializedName("conversation")
        public Conversation conversation;
        @SerializedName("messaging")

        public Messaging messaging;
        public Links(Self self, From from, To to, Cancel cancel, Conversation conversation, Messaging messaging) {
            this.self = self;
            this.from = from;
            this.to = to;
            this.cancel = cancel;
            this.conversation = conversation;
            this.messaging = messaging;
        }

    }
    public class Messaging {
        @SerializedName("href")
        public String href;
        public Messaging(String href) {
            this.href = href;
        }
    }

    public class Self {
        @SerializedName("href")
        public String href;
        public Self(String href) {
            this.href = href;
        }
    }

    public class To {
        @SerializedName("href")
        public String href;
        public To(String href) {
            this.href = href;
        }
    }
}
