package nikhanch.com.sfbandroidchatbubbles.ApplicationService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dmitsh on 9/29/2015.
 */
public class SignInLinksResponse {
    @SerializedName("_links")

    public SignInLinksResponse.Links Links;

    public SignInLinksResponse(SignInLinksResponse.Links Links) {
        this.Links=Links;
    }

    public class Links {
        public final User user;
        public final Applications applications;
        public Links(User user) {
            this.user = user;
            this.applications = null;
        }

        public Links(Applications applications){
            this.applications = applications;
            this.user = null;
        }
    }

    public class User {
        public final String href;

        public User(String href) {
            this.href = href;
        }
    }

    public class Applications{
        public final String href;
        public Applications(String href){
            this.href = href;
        }
    }
}
