package nikhanch.com.sfbandroidchatbubbles.ViewModels;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import nikhanch.com.sfbandroidchatbubbles.R;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import android.view.LayoutInflater;

import com.txusballesteros.bubbles.BubblesManager;
import com.txusballesteros.bubbles.BubbleLayout;
import com.txusballesteros.bubbles.OnInitializedCallback;


    public class LoginActivity extends AppCompatActivity {

    private com.txusballesteros.bubbles.BubblesManager bubblesManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addNewView();
            }
        });

        final Button navButton = (Button) findViewById(R.id.chatButton);
        navButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent listView = new Intent(getApplicationContext(), BuddyActivity.class);
                startActivity(listView);
            }
        });

        final Button navButton2 = (Button) findViewById(R.id.meetingsButton);
        navButton2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MeetingActivity.class);
                startActivity(intent);
            }
        });

        initializeBubblesManager();
    }


    private void addNewBubble() {
        BubbleLayout bubbleView = (BubbleLayout)LayoutInflater.from(LoginActivity.this).inflate(R.layout.bubble_layout, null);
        bubbleView.setOnBubbleRemoveListener(new BubbleLayout.OnBubbleRemoveListener() {
            @Override
            public void onBubbleRemoved(BubbleLayout bubble) { }
        });
        bubbleView.setOnBubbleClickListener(new BubbleLayout.OnBubbleClickListener() {

            @Override
            public void onBubbleClick(BubbleLayout bubble) {
                Toast.makeText(getApplicationContext(), "Clicked !",
                        Toast.LENGTH_SHORT).show();
            }
        });

        initializeBubblesManager();
    }

    public void addNewView()
    {
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                800,
                600,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                /*WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |*/ WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT);

        WindowManager wm = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);

        ViewGroup mTopView = (ViewGroup) LayoutInflater.from(LoginActivity.this).inflate(R.layout.activity_chat, null);

        wm.addView(mTopView, params);
    }

    private void initializeBubblesManager() {
        bubblesManager = new BubblesManager.Builder(this)
                //.setTrashLayout(R.layout.bubble_trash_layout)
                .setInitializationCallback(new OnInitializedCallback() {
                    @Override
                    public void onInitialized() {
                        // addNewBubble();
                    }
                })
                .build();
        bubblesManager.initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
