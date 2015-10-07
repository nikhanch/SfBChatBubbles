package nikhanch.com.sfbandroidchatbubbles.ViewModels;

/**
 * Created by sumitc on 10/1/2015.
 */
public class MeetingInstance {
    private boolean isMine;
    private String meetingSubject;
    private String joinUrl;

    public MeetingInstance(String subject, boolean mine, String url) {
        meetingSubject = subject;
        isMine = mine;
        joinUrl = url;
    }

    public String getMeetingSubject() {
        return meetingSubject;
    }

    public void setMeetingSubject(String subject) {
        this.meetingSubject = subject;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setIsMine(boolean isMine) {
        this.isMine = isMine;
    }

    public String getJoinUrl() {
        return joinUrl;
    }

    public void setJoinUrl(String url) {
        this.joinUrl = url;
    }

}
