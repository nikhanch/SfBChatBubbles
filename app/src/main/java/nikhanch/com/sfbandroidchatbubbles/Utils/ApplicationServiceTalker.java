package nikhanch.com.sfbandroidchatbubbles.Utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.squareup.otto.Produce;

import nikhanch.com.sfbandroidchatbubbles.Application;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.SfBChatBubblesService;

/**
 * Created by nikhanch on 9/27/2015.
 */

// TODO: consider making singleton
public class ApplicationServiceTalker {

    private SfBChatBubblesService mSfbChatBubbleService;
    private android.app.Application mApplication;
    private ServiceConnectionState mServiceConnectionState;
    private ServiceConnection mConnection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            SfBChatBubblesService.SfBChatBubblesServiceBinder serviceBinder = (SfBChatBubblesService.SfBChatBubblesServiceBinder) binder;
            mSfbChatBubbleService = serviceBinder.getSfBChatBubblesService();
            mServiceConnectionState = new ServiceConnectionState(true);
            Application.getServiceEventBus().post(mServiceConnectionState);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mSfbChatBubbleService = null;
            mServiceConnectionState = new ServiceConnectionState(true);
            Application.getServiceEventBus().post(mServiceConnectionState);
        }
    };

    public ApplicationServiceTalker(android.app.Application application){
        this.mApplication = application;
        Application.getServiceEventBus().register(this);
        this.mServiceConnectionState = new ServiceConnectionState(false);

        Intent i = new Intent(this.mApplication, SfBChatBubblesService.class);
        this.mApplication.bindService(i, mConnection, Context.BIND_AUTO_CREATE);
    }


    public SfBChatBubblesService getSfbChatBubbleService() {
        return mSfbChatBubbleService;
    }
    @Produce
    public ServiceConnectionState getServiceConnectionState(){
        return this.mServiceConnectionState;
    }
    public class ServiceConnectionState{

        private boolean mConnected;
        public boolean isConnected() {
            return mConnected;
        }

        ServiceConnectionState(boolean connected){
            this.mConnected = connected;
        }
    }
}
