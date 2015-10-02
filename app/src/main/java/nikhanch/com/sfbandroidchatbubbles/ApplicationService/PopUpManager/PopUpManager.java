package nikhanch.com.sfbandroidchatbubbles.ApplicationService.PopUpManager;


import android.content.Context;
import android.graphics.PixelFormat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
import com.txusballesteros.bubbles.BubbleLayout;
import com.txusballesteros.bubbles.BubblesManager;
import com.txusballesteros.bubbles.OnInitializedCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.Queue;

import nikhanch.com.sfbandroidchatbubbles.Application;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager.MessageResponseEvent;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.SfBChatBubblesService;
import nikhanch.com.sfbandroidchatbubbles.Models.ContactModel;
import nikhanch.com.sfbandroidchatbubbles.Models.PhotoDownloadCallback;
import nikhanch.com.sfbandroidchatbubbles.R;
import nikhanch.com.sfbandroidchatbubbles.ViewModels.ChatMessage;
import nikhanch.com.sfbandroidchatbubbles.ViewModels.ChatMessageAdapter;
import nikhanch.com.sfbandroidchatbubbles.ViewModels.CircleImageTransform;

public class PopUpManager {
    private com.txusballesteros.bubbles.BubblesManager bubblesManager;
    private SfBChatBubblesService mApplicationService;
    private int mBubbleCount = 0;
    private BubbleLayout mBubbleView = null;
    private ViewGroup mTopView = null;
    private ArrayList<String> messages = new ArrayList<>();


    // Chat window UI
    private ListView mListView;
    private Button mButtonSend;
    private EditText mEditTextMessage;
    private ImageView mImageView;

    private ChatMessageAdapter mAdapter;

    public PopUpManager(SfBChatBubblesService service) {
        mApplicationService = service;
        initializeBubblesManager();

        Application.getServiceEventBus().register(this);
    }

    private void initializeBubblesManager() {
        bubblesManager = new BubblesManager.Builder(mApplicationService)
                .setTrashLayout(R.layout.bubble_trash_layout)
                .setInitializationCallback(new OnInitializedCallback() {
                    @Override
                    public void onInitialized() {
                    //    addNewBubble();
                    }
                })
                .build();
        bubblesManager.initialize();
    }


    public void addNewView()
    {
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                800,
                600,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                /* WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | */WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS ,
                PixelFormat.TRANSLUCENT);

        WindowManager wm = (WindowManager) mApplicationService
                .getSystemService(Context.WINDOW_SERVICE);

        // initialize chat window

        mTopView = (ViewGroup) LayoutInflater.from(mApplicationService).inflate(R.layout.activity_chat, null);

        ImageView closeImageView = (ImageView)mTopView.findViewById(R.id.closeImageView);
        closeImageView.setVisibility(View.VISIBLE);
        closeImageView.setClickable(true);

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView();
            }
        });

        // TODO: this is very very gross/identical to code in ChatActivity

        mListView = (ListView) mTopView.findViewById(R.id.listView);
        mButtonSend = (Button) mTopView.findViewById(R.id.btn_send);
        mEditTextMessage = (EditText) mTopView.findViewById(R.id.et_message);
        mImageView = (ImageView) mTopView.findViewById(R.id.iv_image);

        mAdapter = new ChatMessageAdapter(mTopView.getContext(), new ArrayList<ChatMessage>());
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

        for(String message : messages)
        {
            addChatMessage(new ChatMessage(message, false, false));
        }

        wm.addView(mTopView, params);

        messages.clear();
    }

    private void sendMessage(String message) {
        //if (this.conversation != null){
        //    conversation.SendMessageInConversation(message);
        //}
        ChatMessage chatMessage = new ChatMessage(message, true, false);
        addChatMessage(chatMessage);

        //Application.getServiceEventBus().post(new MessageResponseEvent(message, mSipUri));
    }

    private void addChatMessage(ChatMessage message)
    {
        mAdapter.add(message);
        mListView.setSelection(mAdapter.getCount() - 1);
    }

    public void removeView()
    {
        WindowManager wm = (WindowManager) mApplicationService
                .getSystemService(Context.WINDOW_SERVICE);

        wm.removeView(mTopView);

        mTopView = null;

        TextView textCount = (TextView)mBubbleView.findViewById(R.id.unreadCount);
        textCount.setVisibility(View.INVISIBLE);

        mBubbleCount = 0;
    }

    private void addNewBubble(String uri) {
        mBubbleView = (BubbleLayout) LayoutInflater.from(mApplicationService).inflate(R.layout.bubble_layout, null);
        mBubbleView.setOnBubbleRemoveListener(new BubbleLayout.OnBubbleRemoveListener() {
            @Override
            public void onBubbleRemoved(BubbleLayout bubble) { mBubbleView = null; }
        });
        mBubbleView.setOnBubbleClickListener(new BubbleLayout.OnBubbleClickListener() {

            @Override
            public void onBubbleClick(BubbleLayout bubble) {
                HandleClick();
            }
        });


        ImageView imageView = (ImageView) mBubbleView.findViewById(R.id.avatar);

        ContactModel model = Application.getServiceTalker().getSfbChatBubbleService().getBuddylistManager().GetContactModel(uri);
        model.getPhoto(new photoCallback(imageView));

        mBubbleView.setShouldStickToWall(true);
        bubblesManager.addBubble(mBubbleView, 60, 20);

        mBubbleCount = 1;
    }

    class photoCallback implements PhotoDownloadCallback {
        ImageView tobindto;
        photoCallback(ImageView v){
            this.tobindto = v;
        }
        @Override
        public void OnPhotoDownloaded(File photo) {
            if (photo != null){
                Picasso.with(tobindto.getContext()).load(photo).transform(new CircleImageTransform()).into(this.tobindto);
            }
        }
    }

    private void HandleClick() {
        if (mTopView == null)
        {
            addNewView();
        }
        else
        {
            removeView();
        }
    }

    @Subscribe
    public void onMessageReceived(MessageResponseEvent event) {

        if (mBubbleView != null)
        {
            TextView textView = (TextView) mBubbleView.findViewById(R.id.unreadCount);

            if (mBubbleCount == 0)
            {
                textView.setVisibility(View.VISIBLE);
            }
            mBubbleCount++;
            textView.setText(Integer.toString(mBubbleCount));
        }
        else
        {
            // TODO: check url to see if it matches the current bubble
            addNewBubble(event.sipUrl);
        }

        messages.add(event.message);
    }

    //TODO: destroy bubble manager on close
}