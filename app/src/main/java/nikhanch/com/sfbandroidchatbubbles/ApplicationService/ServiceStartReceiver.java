package nikhanch.com.sfbandroidchatbubbles.ApplicationService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by nikhanch on 9/27/2015.
 */

public class ServiceStartReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        startDataSyncService(context);

    }

    public static void startDataSyncService(Context context){
        // Toast.makeText(context, "service start request recd", Toast.LENGTH_LONG).show();
        Intent service = new Intent(context, SfBChatBubblesService.class);
        context.startService(service);

    }
}

