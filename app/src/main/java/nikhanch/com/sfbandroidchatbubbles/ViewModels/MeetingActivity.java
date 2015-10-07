package nikhanch.com.sfbandroidchatbubbles.ViewModels;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nikhanch.com.sfbandroidchatbubbles.Application;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.MeetingsManager.MyMeetingsEvent;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.MeetingsManager.MyMeetingsResponse;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.MeetingsManager.MyOnlineMeetingsResponse;
import nikhanch.com.sfbandroidchatbubbles.Models.ContactModel;
import nikhanch.com.sfbandroidchatbubbles.R;

/**
 * Created by sumitc on 10/1/2015.
 */
public class MeetingActivity extends AppCompatActivity {

    private ListView mListView;
    private Button mButtonSend;
    private EditText mEditTextMessage;
    private ImageView mImageView;

    private MeetingAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        mListView = (ListView) findViewById(R.id.listView);
        mButtonSend = (Button) findViewById(R.id.btn_send);
        mEditTextMessage = (EditText) findViewById(R.id.et_message);

        mAdapter = new MeetingAdapter(this, this, new ArrayList<MeetingInstance>());
        mListView.setAdapter(mAdapter);

        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = mEditTextMessage.getText().toString();
                if (TextUtils.isEmpty(message)) {
                    return;
                }
                sendMessage(message);
                mEditTextMessage.setText("");
            }
        });

        MyMeetingsEvent meetingList = Application.getServiceTalker().getSfbChatBubbleService().getMeetingsManager().mMeetingsEvent;

        if (meetingList != null && (meetingList.size() == meetingList.capacity())) {
            List<MyOnlineMeetingsResponse> meetings = meetingList.getAll();

            for (int i = 0; i < meetings.size(); i++) {
                MyOnlineMeetingsResponse meetingData = meetings.get(i);

                String subject = meetingData.subject;
                if (subject == "") subject = "(Empty Subject)";
                addMeeting(subject, false, meetingData.joinUrl);
            }
        }
    }

    private void addMeeting(String subject, boolean mine, String joinUrl) {
        MeetingInstance meeting = new MeetingInstance(subject, mine, joinUrl);
        mAdapter.add(meeting);
    }

    private void sendMessage(String message) {

        if (mListView != null) {
            Object o = mListView.getSelectedItem();
            MeetingInstance meetingInstance = (MeetingInstance) (mListView.getSelectedItem());
            if (meetingInstance != null) {
                // TODO: actuall send message
            }
        }
    }
}
