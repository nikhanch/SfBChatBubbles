package nikhanch.com.sfbandroidchatbubbles.ApplicationService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dmitsh on 9/29/2015.
 */
public class ApplicationResourceRequest {
    @SerializedName("culture")
    public String culture;

    @SerializedName("endpointId")
    public String endpointId;

    @SerializedName("instanceId")
    public String instanceId;

    @SerializedName("userAgent")
    public String userAgent;

    ApplicationResourceRequest(){

    }

    ApplicationResourceRequest(String culture, String endpointId, String instanceId, String userAgent){
        this.culture = culture;
        this.endpointId = endpointId;
        this.instanceId = instanceId;
        this.userAgent = userAgent;
    }
}
