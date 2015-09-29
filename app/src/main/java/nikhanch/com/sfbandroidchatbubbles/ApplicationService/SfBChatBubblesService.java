package nikhanch.com.sfbandroidchatbubbles.ApplicationService;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.squareup.otto.Produce;

/**
 * Created by nikhanch on 9/27/2015.
 */
public class SfBChatBubblesService extends Service{

    private final IBinder mBinder = new SfBChatBubblesServiceBinder();
    // Declare Service Members/Components here

    private LyncSignIn mLyncSignIn;

    private boolean serviceStarted = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        StartServiceIfRequired();

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        this.serviceStarted = false;
    }


    private void StartServiceIfRequired(){
        if (this.isServiceStarted()){
            return;
        }

        this.mLyncSignIn = new LyncSignIn(this);

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

    public class SfBChatBubblesServiceBinder extends Binder {
        public SfBChatBubblesService getSfBChatBubblesService(){
            return SfBChatBubblesService.this;
        }
    }
}
