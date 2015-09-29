package nikhanch.com.sfbandroidchatbubbles.ApplicationService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dmitsh on 9/29/2015.
 */
public class LyncDiscoverResponse {
    @SerializedName("_links")

    public nikhanch.com.sfbandroidchatbubbles.ApplicationService.LyncDiscoverResponse.Links Links;

    public LyncDiscoverResponse(nikhanch.com.sfbandroidchatbubbles.ApplicationService.LyncDiscoverResponse.Links Links) {
        this.Links=Links;
    }

    public class Links {
        public final User user;

        public Links(User user) {
              this.user = user;
        }
    }

    public class User {
        public final String href;

        public User(String href) {
            this.href = href;
        }
    }
}
