package nikhanch.com.sfbandroidchatbubbles.ViewModels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import nikhanch.com.sfbandroidchatbubbles.Models.ContactModel;
import nikhanch.com.sfbandroidchatbubbles.R;

/**
 * Created by sumitc on 10/1/2015.
 */
public class MeetingAdapter extends ArrayAdapter<MeetingInstance> {

    private Activity myActivity;

    public MeetingAdapter(Context context, Activity activity, ArrayList < MeetingInstance > data) {
        super(context, R.layout.item_meeting, data);

        myActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_meeting, parent, false);

        MeetingInstance meeting = getItem(position);
        TextView textView = (TextView) convertView.findViewById(R.id.subject);
        textView.setText(getItem(position).getMeetingSubject());

        TextView joinUrl = (TextView) convertView.findViewById(R.id.join_url);
        final String joinUrlString = getItem(position).getJoinUrl();
        //joinUrl.setText();

        joinUrl.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(joinUrlString));
                myActivity.startActivity(browserIntent);
            }
        });

        return convertView;
    }

}
