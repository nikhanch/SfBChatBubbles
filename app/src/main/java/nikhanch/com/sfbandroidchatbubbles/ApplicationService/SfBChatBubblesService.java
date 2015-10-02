package nikhanch.com.sfbandroidchatbubbles.ApplicationService;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.squareup.otto.Produce;

import nikhanch.com.sfbandroidchatbubbles.ApplicationService.BuddylistManager.BuddylistManager;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.BuddylistManager.IBuddylistManager;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager.CommunicationManager;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager.ICommunicationManager;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.MeetingsManager.MeetingsManager;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.PopUpManager.PopUpManager;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.WebTicket.CWTTokenProvider;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.WebTicket.ICWTTokenProvider;

/**
 * Created by nikhanch on 9/27/2015.
 */
public class  SfBChatBubblesService extends Service{

    public static SfBChatBubblesService GetInstance = null;
    private final IBinder mBinder = new SfBChatBubblesServiceBinder();
    // Declare Service Members/Components here

    private LyncSignIn mLyncSignIn;
    private ICWTTokenProvider mCWTTokenProvider;
    private MeetingsManager mMeetingsManager;
    private IBuddylistManager mBuddylistManager;
    private ICommunicationManager mCommunicationManager;
    private PopUpManager mPopUpManager;

    private boolean serviceStarted = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        StartServiceIfRequired();

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        this.GetInstance = null;
        this.serviceStarted = false;
    }


    private void StartServiceIfRequired(){
        if (this.isServiceStarted()){
            return;
        }
        this.GetInstance = this;
        this.mCWTTokenProvider = new CWTTokenProvider(this);
        this.mLyncSignIn = new LyncSignIn(this);
        this.mBuddylistManager = new BuddylistManager(this);
        this.mCommunicationManager = new CommunicationManager(this);
        this.mMeetingsManager = new MeetingsManager(this);
        this.mPopUpManager = new PopUpManager(this);

        // Init Service Members/Components here
        this.serviceStarted = true;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    public boolean isServiceStarted() {
        return serviceStarted;
    }

    public LyncSignIn getLyncSignIn(){
        return this.mLyncSignIn;
    }

    public ICWTTokenProvider getCWTTokenProvider(){
        return this.mCWTTokenProvider;
    }

    public IBuddylistManager getBuddylistManager(){
        return this.mBuddylistManager;
    }

    public ICommunicationManager getCommunicationManager(){
        return this.mCommunicationManager;
    }

    public MeetingsManager getMeetingsManager() { return this.mMeetingsManager; }

    public class SfBChatBubblesServiceBinder extends Binder {
        public SfBChatBubblesService getSfBChatBubblesService(){
            return SfBChatBubblesService.this;
        }
    }
}
