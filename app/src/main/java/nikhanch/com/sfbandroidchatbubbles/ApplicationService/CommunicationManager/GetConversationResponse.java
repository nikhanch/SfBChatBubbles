package nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhanch on 10/1/2015.
 */
public class GetConversationResponse {


    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("threadId")
    @Expose
    public String threadId;
    @SerializedName("subject")
    @Expose
    public String subject;
    @SerializedName("activeModalities")

    public List<String> activeModalities = new ArrayList<String>();
    @SerializedName("importance")

    public String importance;
    @SerializedName("recording")

    public Boolean recording;
    @SerializedName("_links")

    public nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager.GetConversationResponse.Links Links;
    @SerializedName("rel")

    public String rel;

    public GetConversationResponse(String state, String threadId, String subject, List<String> activeModalities, String importance, Boolean recording, nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager.GetConversationResponse.Links Links, String rel) {
        this.state = state;
        this.threadId = threadId;
        this.subject = subject;
        this.activeModalities = activeModalities;
        this.importance = importance;
        this.recording = recording;
        this.Links = Links;
        this.rel = rel;
    }

    public class AddParticipant {

        @SerializedName("href")
        @Expose
        public String href;

        public AddParticipant(String href) {
            this.href = href;
        }

    }

    public class ApplicationSharing {

        @SerializedName("href")
        @Expose
        public String href;

        public ApplicationSharing(String href) {
            this.href = href;
        }

    }

    public class AudioVideo {

        @SerializedName("href")
        @Expose
        public String href;
        public AudioVideo(String href) {
            this.href = href;
        }

    }

    public class DataCollaboration {

        @SerializedName("href")
        public String href;
        public DataCollaboration(String href) {
            this.href = href;
        }

    }





    public class Links {

        @SerializedName("self")
        public Self self;
        @SerializedName("applicationSharing")
        public ApplicationSharing applicationSharing;
        @SerializedName("audioVideo")
        public AudioVideo audioVideo;
        @SerializedName("dataCollaboration")
        public DataCollaboration dataCollaboration;
        @SerializedName("messaging")
        public Messaging messaging;
        @SerializedName("phoneAudio")
        public PhoneAudio phoneAudio;
        @SerializedName("localParticipant")
        public LocalParticipant localParticipant;
        @SerializedName("participants")
        public Participants participants;
        @SerializedName("addParticipant")
        public AddParticipant addParticipant;
        public Links(Self self, ApplicationSharing applicationSharing, AudioVideo audioVideo, DataCollaboration dataCollaboration, Messaging messaging, PhoneAudio phoneAudio, LocalParticipant localParticipant, Participants participants, AddParticipant addParticipant) {
            this.self = self;
            this.applicationSharing = applicationSharing;
            this.audioVideo = audioVideo;
            this.dataCollaboration = dataCollaboration;
            this.messaging = messaging;
            this.phoneAudio = phoneAudio;
            this.localParticipant = localParticipant;
            this.participants = participants;
            this.addParticipant = addParticipant;
        }
    }

    public class LocalParticipant {
        @SerializedName("href")
        public String href;
        @SerializedName("title")
        public String title;
        public LocalParticipant(String href, String title) {
            this.href = href;
            this.title = title;
        }
    }

    public class Messaging {
        @SerializedName("href")
        public String href;
        public Messaging(String href) {
            this.href = href;
        }
    }

    public class Participants {
        @SerializedName("href")
        public String href;
        public Participants(String href) {
            this.href = href;
        }
    }

    public class PhoneAudio {
        @SerializedName("href")
        public String href;
        public PhoneAudio(String href) {
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




}
