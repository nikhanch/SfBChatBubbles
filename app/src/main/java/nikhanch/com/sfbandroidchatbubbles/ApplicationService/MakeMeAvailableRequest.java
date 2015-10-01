package nikhanch.com.sfbandroidchatbubbles.ApplicationService;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhanch on 9/30/2015.
 */
public class MakeMeAvailableRequest {

    @SerializedName("phoneNumber")
    @Expose
    public String phoneNumber;
    @SerializedName("signInAs")
    @Expose
    public String signInAs;
    @SerializedName("supportedMessageFormats")
    @Expose
    public List<String> supportedMessageFormats = new ArrayList<String>();
    @SerializedName("supportedModalities")
    @Expose
    public List<String> supportedModalities = new ArrayList<String>();

    public MakeMeAvailableRequest() {
    }

    public MakeMeAvailableRequest(String phoneNumber, String signInAs, List<String> supportedMessageFormats, List<String> supportedModalities) {
        this.phoneNumber = phoneNumber;
        this.signInAs = signInAs;
        this.supportedMessageFormats = supportedMessageFormats;
        this.supportedModalities = supportedModalities;
    }
}
