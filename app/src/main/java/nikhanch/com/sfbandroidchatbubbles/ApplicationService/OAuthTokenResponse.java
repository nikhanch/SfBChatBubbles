package nikhanch.com.sfbandroidchatbubbles.ApplicationService;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmitsh on 9/29/2015.
 */
public class OAuthTokenResponse {
        @SerializedName("access_token")
        public final String accessToken;

        @SerializedName("expires_in")
        public final int expiresIn;

        @SerializedName("ms_rtc_identityscope")
        public final String msRtcIdentityscope;

        @SerializedName("token_type")
        public final String tokenType;

        public OAuthTokenResponse(String accessToken, int expiresIn, String msRtcIdentityscope, String tokenType) {
            this.accessToken = accessToken;
            this.expiresIn = expiresIn;
            this.msRtcIdentityscope = msRtcIdentityscope;
            this.tokenType = tokenType;
        }
    }
