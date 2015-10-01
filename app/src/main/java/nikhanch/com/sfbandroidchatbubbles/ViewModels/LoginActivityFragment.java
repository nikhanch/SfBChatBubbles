package nikhanch.com.sfbandroidchatbubbles.ViewModels;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.squareup.otto.Subscribe;

import nikhanch.com.sfbandroidchatbubbles.Application;
import nikhanch.com.sfbandroidchatbubbles.R;
import nikhanch.com.sfbandroidchatbubbles.Utils.ApplicationServiceTalker;


/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {

    ServiceEventBusListener busEventListener = null;
    ApplicationServiceTalker serviceTalker = null;
    public LoginActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (busEventListener == null || !busEventListener.isRegisteredWithBus()){
            busEventListener = new ServiceEventBusListener();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        busEventListener = null;
    }

    private class ServiceEventBusListener{

        boolean mRegistered;

        public ServiceEventBusListener() {
            this.mRegistered = false;
            Application.getServiceEventBus().register(this);
        }



        public boolean isRegisteredWithBus() {
            return mRegistered;
        }

        @Subscribe
        public void OnServiceConnectionStateChanged(ApplicationServiceTalker.ServiceConnectionState connectionState){
            if (connectionState != null){
                if (connectionState.isConnected()){
                    if (!isRegisteredWithBus()) {
                        mRegistered = true;
                    }
                }
                else{
                    mRegistered = false;
                }
            }
        }

        public void StopListeningToServiceBus() {
            Application.getServiceEventBus().unregister(this);
            this.mRegistered = false;
        }

        // Hook up events from service here
    }
}
