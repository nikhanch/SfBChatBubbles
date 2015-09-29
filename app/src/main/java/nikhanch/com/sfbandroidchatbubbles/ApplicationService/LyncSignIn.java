package nikhanch.com.sfbandroidchatbubbles.ApplicationService;

import android.content.Context;
import android.widget.Toast;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by dmitsh on 9/29/2015.
 */


public class LyncSignIn {

    public static final String LYNC_SERVER_API_URL = "https://LyncDiscover.microsoft.com/";

    public final Context mContext;

    public LyncSignIn(Context context)
    {
        mContext = context;
        PerformLyncAutoD();
    }

    public void OnDiscoverCompleted(boolean isSuccess, String url){

    }


    private void PerformLyncAutoD() {
        try {
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(LYNC_SERVER_API_URL).build();
            LyncService service = retrofit.create(LyncService.class);
            Call<LyncDiscoverResponse> call = service.GetAutoDResponse(LYNC_SERVER_API_URL);
            Callback<LyncDiscoverResponse> cb = new CustomCallBack<LyncDiscoverResponse>(this) {
                @Override
                public void onResponse(Response<LyncDiscoverResponse> response, Retrofit retrofit){
                    String str = response.body().Links.user.href;
                    this.component.OnDiscoverCompleted(response.isSuccess(), str);
                    Toast.makeText(mContext, str, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            };
            call.enqueue(cb);


        }
        catch (Exception e){
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    abstract class CustomCallBack<T> implements Callback<T>{
            protected LyncSignIn component = null;
            public CustomCallBack(LyncSignIn component ) {
                    this.component = component ;
                }
    }

}
