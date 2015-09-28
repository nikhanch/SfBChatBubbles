package nikhanch.com.sfbandroidchatbubbles.Utils;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by nikhanch on 9/27/2015.
 */
public class EventBus extends Bus {
    private final Handler mainThread = new Handler(Looper.getMainLooper());

    public EventBus(ThreadEnforcer enforcer){
        super(enforcer);
    }
}
