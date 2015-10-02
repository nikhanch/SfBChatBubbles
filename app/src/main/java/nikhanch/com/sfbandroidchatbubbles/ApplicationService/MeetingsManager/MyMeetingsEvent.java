package nikhanch.com.sfbandroidchatbubbles.ApplicationService.MeetingsManager;

import java.util.List;

/**
 * Created by dmitsh on 10/2/2015.
 */

public class MyMeetingsEvent {

    private int mCapacity;

    private List<MyOnlineMeetingsResponse> mMeetings;

    public MyMeetingsEvent(int capacity) {
        mCapacity = capacity;
    }

    public int capacity() {
        return mCapacity;
    }

    public void add(MyOnlineMeetingsResponse meeting) {
        mMeetings.add(meeting);
    }

    public List<MyOnlineMeetingsResponse> getAll() {
        return mMeetings;
    }

    public int size() {
        return mMeetings.size();
    }
}
