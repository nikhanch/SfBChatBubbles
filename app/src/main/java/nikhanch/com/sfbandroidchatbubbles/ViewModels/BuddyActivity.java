package nikhanch.com.sfbandroidchatbubbles.ViewModels;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

import nikhanch.com.sfbandroidchatbubbles.Application;
import nikhanch.com.sfbandroidchatbubbles.Models.ContactModel;

/**
 * Created by nickbi on 10/1/2015.
 */

public class BuddyActivity extends ListActivity {

    // This is the Adapter being used to display the list's data
    BuddyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We pass null for the cursor, then update it in onLoadFinished()
        mAdapter = new BuddyAdapter(this, new ArrayList<ContactModel>());
        setListAdapter(mAdapter);

        Map<String, ContactModel> map = Application.getServiceTalker().getSfbChatBubbleService().getBuddylistManager().GetContactModelsMap();

        for(ContactModel model : map.values()) {
            mAdapter.add(model);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent chatScreen = new Intent(getApplicationContext(), ChatActivity.class);
        chatScreen.putExtra("sipUri", mAdapter.getItem(position).getSipUri());
        startActivity(chatScreen);
    }
}

