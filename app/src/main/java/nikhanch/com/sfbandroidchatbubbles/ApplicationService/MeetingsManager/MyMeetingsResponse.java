package nikhanch.com.sfbandroidchatbubbles.ApplicationService.MeetingsManager;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MyMeetingsResponse {
    @SerializedName("_links")
    @Expose
    public nikhanch.com.sfbandroidchatbubbles.ApplicationService.MeetingsManager.MyMeetingsResponse.Links Links;
    @SerializedName("_embedded")
    @Expose
    public nikhanch.com.sfbandroidchatbubbles.ApplicationService.MeetingsManager.MyMeetingsResponse.Embedded Embedded;
    @SerializedName("rel")
    @Expose
    public String rel;

    public MyMeetingsResponse() {
    }

    public MyMeetingsResponse(
            nikhanch.com.sfbandroidchatbubbles.ApplicationService.MeetingsManager.MyMeetingsResponse.Links Links,
            nikhanch.com.sfbandroidchatbubbles.ApplicationService.MeetingsManager.MyMeetingsResponse.Embedded Embedded,
            String rel) {
        this.Links = Links;
        this.Embedded = Embedded;
        this.rel = rel;
    }

    public class Embedded {

        @SerializedName("myAssignedOnlineMeeting")
        @Expose
        public List<MyAssignedOnlineMeeting> myAssignedOnlineMeeting = new ArrayList<MyAssignedOnlineMeeting>();
        @SerializedName("myOnlineMeeting")
        @Expose
        public List<MyOnlineMeeting> myOnlineMeeting = new ArrayList<MyOnlineMeeting>();

        public Embedded() {
        }

        public Embedded(List<MyAssignedOnlineMeeting> myAssignedOnlineMeeting, List<MyOnlineMeeting> myOnlineMeeting) {
            this.myAssignedOnlineMeeting = myAssignedOnlineMeeting;
            this.myOnlineMeeting = myOnlineMeeting;
        }
    }

    public class Links {

        @SerializedName("self")
        @Expose
        public Self self;

        public Links() {
        }

        public Links(Self self) {
            this.self = self;
        }
    }

    public class Links_ {

        @SerializedName("self")
        @Expose
        public Self_ self;

        public Links_() {
        }

        public Links_(Self_ self) {
            this.self = self;
        }
    }

    public class Links__ {

        @SerializedName("self")
        @Expose
        public Self__ self;

        public Links__() {
        }

        public Links__(Self__ self) {
            this.self = self;
        }
    }

    public class MyAssignedOnlineMeeting {

        @SerializedName("onlineMeetingId")
        @Expose
        public String onlineMeetingId;
        @SerializedName("subject")
        @Expose
        public String subject;
        @SerializedName("_links")
        @Expose
        public Links_ Links;
        @SerializedName("rel")
        @Expose
        public String rel;
        @SerializedName("etag")
        @Expose
        public String etag;

        public MyAssignedOnlineMeeting() {
        }

        public MyAssignedOnlineMeeting(String onlineMeetingId, String subject, Links_ Links, String rel, String etag) {
            this.onlineMeetingId = onlineMeetingId;
            this.subject = subject;
            this.Links = Links;
            this.rel = rel;
            this.etag = etag;
        }
    }

    public class MyOnlineMeeting {

        @SerializedName("onlineMeetingId")
        @Expose
        public String onlineMeetingId;
        @SerializedName("subject")
        @Expose
        public String subject;
        @SerializedName("_links")
        @Expose
        public Links__ Links;
        @SerializedName("rel")
        @Expose
        public String rel;
        @SerializedName("etag")
        @Expose
        public String etag;

        public MyOnlineMeeting() {
        }

        public MyOnlineMeeting(String onlineMeetingId, String subject, Links__ Links, String rel, String etag) {
            this.onlineMeetingId = onlineMeetingId;
            this.subject = subject;
            this.Links = Links;
            this.rel = rel;
            this.etag = etag;
        }
    }

    public class Self {

        @SerializedName("href")
        @Expose
        public String href;

        public Self() {
        }

        public Self(String href) {
            this.href = href;
        }
    }

    public class Self_ {

        @SerializedName("href")
        @Expose
        public String href;

        public Self_() {
        }

        public Self_(String href) {
            this.href = href;
        }
    }

    public class Self__ {

        @SerializedName("href")
        @Expose
        public String href;

        public Self__() {
        }

        public Self__(String href) {
            this.href = href;
        }
    }
}