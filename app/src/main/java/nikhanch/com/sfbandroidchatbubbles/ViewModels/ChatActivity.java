package nikhanch.com.sfbandroidchatbubbles.ViewModels;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
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
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import nikhanch.com.sfbandroidchatbubbles.Application;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.ApplicationsResource;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager.AutoReply;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager.ConversationEvent;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager.MessageResponseEvent;
import nikhanch.com.sfbandroidchatbubbles.Models.ContactModel;
import nikhanch.com.sfbandroidchatbubbles.R;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private ListView mListView;
    private Button mButtonSend;
    private EditText mEditTextMessage;
    private ImageView mImageView;

    private String mSipUri;
    private ContactModel mContactModel;
    private boolean signInComplete = false;
    private ChatMessageAdapter mAdapter;

    private nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager.Conversation conversation = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        // Get ContactModel passed via sipUri via Intent
        Intent i = getIntent();
        mSipUri = i.getStringExtra("sipUri");

        mContactModel = Application.getServiceTalker().getSfbChatBubbleService().getBuddylistManager().GetContactModel(mSipUri);
        setTitle("Chat with " + mContactModel.getName());

        mListView = (ListView) findViewById(R.id.listView);
        mButtonSend = (Button) findViewById(R.id.btn_send);
        mEditTextMessage = (EditText) findViewById(R.id.et_message);
        mImageView = (ImageView) findViewById(R.id.iv_image);

        mAdapter = new ChatMessageAdapter(this, new ArrayList<ChatMessage>());
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

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        if (Application.getServiceTalker().getSfbChatBubbleService().getLyncSignIn().getApplicationResource() != null){
            this.signInComplete = true;
        }
        Application.getServiceTalker().getSfbChatBubbleService().getCommunicationManager().StartConversation(mContactModel.getSipUri(), "");
        Application.getServiceEventBus().register(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Application.getServiceEventBus().unregister(this);
    }

    @Subscribe
    public void OnConversationCreated(ConversationEvent ev){
        if (ev != null && ev.recepientUrl == mContactModel.getSipUri()){
            this.conversation = ev.conversation;
            Toast.makeText(this, "Conversation created", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void OnSignInComplete(ApplicationsResource resource){
        if (resource != null){
            this.signInComplete = true;
        }
    }

    @Subscribe
    public void onResponseMessage(MessageResponseEvent ev){
        if (ev != null && ev.sipUrl == mContactModel.getSipUri()){
            mimicOtherMessage(ev.message);
        }
    }
    private void sendMessage(String message) {
        if (this.conversation != null){
            conversation.SendMessageInConversation(message);
        }
        ChatMessage chatMessage = new ChatMessage(message, true, false);
        addChatMessage(chatMessage);

        Application.getServiceEventBus().post(new MessageResponseEvent(message, mSipUri));
    }

    private void mimicOtherMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, false, false);
        addChatMessage(chatMessage);
    }

    private void sendMessage() {
        ChatMessage chatMessage = new ChatMessage(null, true, true);
        addChatMessage(chatMessage);

        mimicOtherMessage();
    }

    private void mimicOtherMessage() {
        ChatMessage chatMessage = new ChatMessage(null, false, true);
        addChatMessage(chatMessage);
    }

    private void addChatMessage(ChatMessage message)
    {
        mAdapter.add(message);
        mListView.setSelection(mAdapter.getCount() - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
