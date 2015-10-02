package nikhanch.com.sfbandroidchatbubbles.ApplicationService.CommunicationManager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitsh on 10/1/2015.
 */
public class PutCommuncationResourceRequest {

    @SerializedName("audioPreference")
    @Expose
    public String audioPreference;
    @SerializedName("conversationHistory")
    @Expose
    public String conversationHistory;
    @SerializedName("simultaneousRingNumberMatch")
    @Expose
    public String simultaneousRingNumberMatch;
    @SerializedName("supportedMessageFormats")
    @Expose
    public List<String> supportedMessageFormats = new ArrayList<String>();
    @SerializedName("supportedModalities")
    @Expose
    public List<String> supportedModalities = new ArrayList<String>();
    @SerializedName("voipFallbackToPhoneAudioTimeOut")
    @Expose
    public String voipFallbackToPhoneAudioTimeOut;
    @SerializedName("0fbfa8d4-2315-409d-b817-01a9bb69c1e7")
    @Expose
    public String bullShitId;
    @SerializedName("etag")
    @Expose
    public String etag;

    public PutCommuncationResourceRequest(String audioPreference, String conversationHistory, String simultaneousRingNumberMatch, List<String> supportedMessageFormats, List<String> supportedModalities, String voipFallbackToPhoneAudioTimeOut, String bullShitId, String etag) {
        this.audioPreference = audioPreference;
        this.conversationHistory = conversationHistory;
        this.simultaneousRingNumberMatch = simultaneousRingNumberMatch;
        this.supportedMessageFormats = supportedMessageFormats;
        this.supportedModalities = supportedModalities;
        this.voipFallbackToPhoneAudioTimeOut = voipFallbackToPhoneAudioTimeOut;
        this.bullShitId = bullShitId;
        this.etag = etag;
    }

}




