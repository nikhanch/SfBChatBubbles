package nikhanch.com.sfbandroidchatbubbles.ApplicationService.MeetingsManager;

import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nikhanch.com.sfbandroidchatbubbles.Application;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.ApplicationsResource;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.LyncSignIn;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.SfBChatBubblesService;
import nikhanch.com.sfbandroidchatbubbles.ApplicationService.WebTicket.GetTokenCallback;
import nikhanch.com.sfbandroidchatbubbles.ApplicationServiceUtils.RetrofitInterceptor;
import nikhanch.com.sfbandroidchatbubbles.ApplicationServiceUtils.UriUtils;
import nikhanch.com.sfbandroidchatbubbles.Models.ContactModel;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by dmitsh on 9/30/2015.
 */
public class MeetingsManager {

    SfBChatBubblesService mApplicationService;
    ApplicationsResource mApplicationsResource = null;

    Retrofit mRetrofit = null;
    MeetingsManagementService mMeetingsManagementService = null;

    public MyMeetingsEvent mMeetingsEvent = null;

    public MeetingsManager(SfBChatBubblesService service){
        this.mApplicationService = service;
        this.mRetrofit = new Retrofit.Builder().baseUrl(LyncSignIn.LYNC_SERVER_API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        this.mMeetingsManagementService = mRetrofit.create(MeetingsManagementService.class);
        Application.getServiceEventBus().register(this);
    }

    @Subscribe
        public void OnApplicationResourceUpdated(ApplicationsResource resource){
            if (resource != null && updateNeeded()) {
                this.mApplicationsResource = resource;
                getMyMeetings();
            }
    }

    private boolean updateNeeded(){
        return  true;
    }

    private void getMyMeetings()
    {
        URL myMeetingsUrl = UriUtils.GetAbsoluteUrl(this.mApplicationsResource.Embedded.onlineMeetings.Links.myOnlineMeetings.href);
        try {
            GetTokenRequestContext context = new GetTokenRequestContext(this, myMeetingsUrl);
            mApplicationService.getCWTTokenProvider().GetToken(myMeetingsUrl, context, new CustomGetTokenCallback() {
                @Override
                public void OnTokenRetrieved(Object userContext, String token) {
                    GetTokenRequestContext requestContext = (GetTokenRequestContext) userContext;
                    Call<MyMeetingsResponse> call = mMeetingsManagementService.GetMyMeetings(requestContext.urlToGet.toString(), token);
                    Callback<MyMeetingsResponse> cb = new CustomRetrofitCallback<MyMeetingsResponse>(requestContext.meetingsManager) {
                        @Override
                        public void onResponse(Response<MyMeetingsResponse> response, Retrofit retrofit) {
                            int code = response.code();
                            if (response.isSuccess()) {
                                MyMeetingsResponse resource = response.body();

                                List<MyMeetingsResponse.MyOnlineMeeting> myOnlineMeetings = resource.Embedded.myOnlineMeeting;
                                mMeetingsEvent = new MyMeetingsEvent(myOnlineMeetings.size());
                                for(int i=0; i<myOnlineMeetings.size(); i++)
                                {
                                    this.meetingsManager.OnOnlineMeeting(myOnlineMeetings.get(i));
                                }
                            }
                        }
                    };
                    call.enqueue(cb);
                }
            });
        } catch (Exception e){
            onOperationFailure("Create Application Resource", e.getMessage());
        }
    }

    public void OnOnlineMeeting(MyMeetingsResponse.MyOnlineMeeting myOnlineMeeting) {
        if(myOnlineMeeting.Links.self.href == null)
            return;

        try {
            URL myOnlineMeetingsUrl = UriUtils.GetAbsoluteUrl(myOnlineMeeting.Links.self.href);
            GetTokenRequestContext context = new GetTokenRequestContext(this, myOnlineMeetingsUrl);
            mApplicationService.getCWTTokenProvider().GetToken(myOnlineMeetingsUrl, context, new CustomGetTokenCallback() {
                @Override
                public void OnTokenRetrieved(Object userContext, String token) {
                    GetTokenRequestContext requestContext = (GetTokenRequestContext) userContext;
                    Call<MyOnlineMeetingsResponse> call = mMeetingsManagementService.GetMyOnlineMeeting(requestContext.urlToGet.toString(), token);
                    Callback<MyOnlineMeetingsResponse> cb = new CustomRetrofitCallback<MyOnlineMeetingsResponse>(requestContext.meetingsManager) {
                        @Override
                        public void onResponse(Response<MyOnlineMeetingsResponse> response, Retrofit retrofit) {
                            int code = response.code();
                            if (response.isSuccess()) {
                                MyOnlineMeetingsResponse resource = response.body();
                                Toast.makeText(mApplicationService, "joinUrl = " + resource.joinUrl, Toast.LENGTH_LONG).show();

                                if(mMeetingsEvent != null) {
                                    mMeetingsEvent.add(resource);
                                    if(mMeetingsEvent.size() == mMeetingsEvent.capacity()) {
                                        Application.getServiceEventBus().post(mMeetingsEvent);
                                    }
                                }
                            }
                        }
                    };
                    call.enqueue(cb);
                }
            });
        } catch (Exception e){
            onOperationFailure("Create Application Resource", e.getMessage());
        }
    }

    public void onOperationFailure(String component, String message){
        Toast.makeText(mApplicationService, "Component = " + component + " message = " + message, Toast.LENGTH_LONG).show();
    }

    abstract class CustomRetrofitCallback<T> implements Callback<T>{
        public final MeetingsManager meetingsManager;
        CustomRetrofitCallback(MeetingsManager manager){
            this.meetingsManager = manager;
        }

        public void onFailure(Throwable t) {
            this.meetingsManager.onOperationFailure("send authenticated request", t.getMessage());
        }
    }

    abstract class CustomGetTokenCallback implements GetTokenCallback {
        @Override
        public void OnTokenRetrievalFailed(Object userContext) {
            GetTokenRequestContext requestContext = (GetTokenRequestContext)userContext;
            requestContext.meetingsManager.onOperationFailure("get token failed", "");
        }
    }

    class GetTokenRequestContext {
        public final MeetingsManager meetingsManager;
        public final URL urlToGet;
        public GetTokenRequestContext(MeetingsManager manager, URL url){
            this.meetingsManager = manager;
            this.urlToGet = url;
        }
    }
}
