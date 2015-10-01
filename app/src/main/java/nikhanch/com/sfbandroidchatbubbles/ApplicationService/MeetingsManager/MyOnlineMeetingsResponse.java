package nikhanch.com.sfbandroidchatbubbles.ApplicationService.MeetingsManager;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MyOnlineMeetingsResponse {

    @SerializedName("accessLevel")
    @Expose
    public String accessLevel;
    @SerializedName("entryExitAnnouncement")
    @Expose
    public String entryExitAnnouncement;
    @SerializedName("attendees")
    @Expose
    public List<Object> attendees = new ArrayList<Object>();
    @SerializedName("automaticLeaderAssignment")
    @Expose
    public String automaticLeaderAssignment;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("leaders")
    @Expose
    public List<Object> leaders = new ArrayList<Object>();
    @SerializedName("onlineMeetingId")
    @Expose
    public String onlineMeetingId;
    @SerializedName("onlineMeetingUri")
    @Expose
    public String onlineMeetingUri;
    @SerializedName("onlineMeetingRel")
    @Expose
    public String onlineMeetingRel;
    @SerializedName("organizerUri")
    @Expose
    public String organizerUri;
    @SerializedName("conferenceId")
    @Expose
    public String conferenceId;
    @SerializedName("phoneUserAdmission")
    @Expose
    public String phoneUserAdmission;
    @SerializedName("lobbyBypassForPhoneUsers")
    @Expose
    public String lobbyBypassForPhoneUsers;
    @SerializedName("subject")
    @Expose
    public String subject;
    @SerializedName("joinUrl")
    @Expose
    public String joinUrl;
    @SerializedName("aux")
    @Expose
    public String aux;
    @SerializedName("_links")
    @Expose
    public nikhanch.com.sfbandroidchatbubbles.ApplicationService.MeetingsManager.MyOnlineMeetingsResponse.Links Links;
    @SerializedName("rel")
    @Expose
    public String rel;
    @SerializedName("etag")
    @Expose
    public String etag;

    public MyOnlineMeetingsResponse() {
    }

    public MyOnlineMeetingsResponse(
            String accessLevel,
            String entryExitAnnouncement,
            List<Object> attendees,
            String automaticLeaderAssignment,
            String description,
            List<Object> leaders,
            String onlineMeetingId,
            String onlineMeetingUri,
            String onlineMeetingRel,
            String organizerUri,
            String conferenceId,
            String phoneUserAdmission,
            String lobbyBypassForPhoneUsers,
            String subject,
            String joinUrl,
            String aux,
            nikhanch.com.sfbandroidchatbubbles.ApplicationService.MeetingsManager.MyOnlineMeetingsResponse.Links Links,
            String rel,
            String etag) {
        this.accessLevel = accessLevel;
        this.entryExitAnnouncement = entryExitAnnouncement;
        this.attendees = attendees;
        this.automaticLeaderAssignment = automaticLeaderAssignment;
        this.description = description;
        this.leaders = leaders;
        this.onlineMeetingId = onlineMeetingId;
        this.onlineMeetingUri = onlineMeetingUri;
        this.onlineMeetingRel = onlineMeetingRel;
        this.organizerUri = organizerUri;
        this.conferenceId = conferenceId;
        this.phoneUserAdmission = phoneUserAdmission;
        this.lobbyBypassForPhoneUsers = lobbyBypassForPhoneUsers;
        this.subject = subject;
        this.joinUrl = joinUrl;
        this.aux = aux;
        this.Links = Links;
        this.rel = rel;
        this.etag = etag;
    }

    public class Links {

        @SerializedName("self")
        @Expose
        public Self self;
        @SerializedName("onlineMeetingExtensions")
        @Expose
        public OnlineMeetingExtensions onlineMeetingExtensions;

        public Links() {
        }

        public Links(Self self, OnlineMeetingExtensions onlineMeetingExtensions) {
            this.self = self;
            this.onlineMeetingExtensions = onlineMeetingExtensions;
        }
    }

    public class OnlineMeetingExtensions {

        @SerializedName("href")
        @Expose
        private String href;

        public OnlineMeetingExtensions() {
        }

        public OnlineMeetingExtensions(String href) {
            this.href = href;
        }
    }

    public class Self {

        @SerializedName("href")
        @Expose
        private String href;

        public Self() {
        }

        public Self(String href) {
            this.href = href;
        }
    }
}
