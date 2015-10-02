package nikhanch.com.sfbandroidchatbubbles.ViewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

import nikhanch.com.sfbandroidchatbubbles.Models.ContactModel;
import nikhanch.com.sfbandroidchatbubbles.R;
import retrofit.http.Url;

/**
 * Created by nickbi on 10/1/2015.
 */
public class BuddyAdapter extends ArrayAdapter<ContactModel> {
    private final int BUDDY = 0;

    public BuddyAdapter(Context context, ArrayList<ContactModel> data) {
        super(context, R.layout.item_buddy, data);
    }

    @Override
    public int getViewTypeCount() {
        // my message, other message, my image, other image
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_buddy, parent, false);

        TextView textView = (TextView) convertView.findViewById(R.id.text);
        textView.setText(getItem(position).getName());


        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);

        //Won't use picasso right now, need creds in order to download
        /*
        URL url = getItem(position).getPhotoUrl();
        if(url.toString() != null) {
            Picasso.with(getContext().getApplicationContext()).load(url.toString()).into(imageView);
        }*/

        return convertView;
    }
}