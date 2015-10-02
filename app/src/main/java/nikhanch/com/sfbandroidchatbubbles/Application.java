package nikhanch.com.sfbandroidchatbubbles;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.squareup.otto.ThreadEnforcer;

import io.fabric.sdk.android.Fabric;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.SfBChatBubblesService;
import nikhanch.com.sfbandroidchatbubbles.Utils.ApplicationServiceTalker;
import nikhanch.com.sfbandroidchatbubbles.Utils.EventBus;
import timber.log.Timber;

/**
 * Created by nikhanch on 9/27/2015.
 */
public class Application extends android.app.Application {

    private static EventBus serviceEventBus;
    public static EventBus getServiceEventBus() {
        return serviceEventBus;
    }

    private static ApplicationServiceTalker serviceTalker;
    public static ApplicationServiceTalker getServiceTalker(){
        return serviceTalker;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        else{
            Fabric.with(this, new Crashlytics());
            Timber.plant(new CrashlyticsTree());
        }

        this.serviceEventBus = new EventBus(ThreadEnforcer.MAIN);

        startService(new Intent(this, SfBChatBubblesService.class));
        this.serviceTalker = new ApplicationServiceTalker(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    public class CrashlyticsTree extends Timber.Tree {
        private static final String CRASHLYTICS_KEY_PRIORITY = "priority";
        private static final String CRASHLYTICS_KEY_TAG = "tag";
        private static final String CRASHLYTICS_KEY_MESSAGE = "message";

        @Override
        protected void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
                return;
            }

            Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority);
            Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag);
            Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message);

            if (t == null) {
                Crashlytics.logException(new Exception(message));
            } else {
                Crashlytics.logException(t);
            }
        }
    }
}

