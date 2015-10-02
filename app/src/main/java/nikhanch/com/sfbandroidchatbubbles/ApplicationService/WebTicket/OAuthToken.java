package nikhanch.com.sfbandroidchatbubbles.ApplicationService.WebTicket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by dmitsh on 9/29/2015.
 */
public class OAuthToken {
        @SerializedName("access_token")
        public final String accessToken;

        @SerializedName("expires_in")
        public final int expiresIn;

        @SerializedName("ms_rtc_identityscope")
        public final String msRtcIdentityscope;

        @SerializedName("token_type")
        public final String tokenType;

        private long timeCreated;
        public OAuthToken(String accessToken, int expiresIn, String msRtcIdentityscope, String tokenType) {
            this.accessToken = accessToken;
            this.expiresIn = expiresIn;
            this.msRtcIdentityscope = msRtcIdentityscope;
            this.tokenType = tokenType;
            timeCreated = System.currentTimeMillis();
        }

        void initTime(){
            this.timeCreated = System.currentTimeMillis();
        }
        boolean isValid(){
            long currentTime = System.currentTimeMillis();
            long expiresInMs = expiresIn * 1000;
            boolean isValid = currentTime - timeCreated < expiresInMs;
            return isValid;
        }
    }
