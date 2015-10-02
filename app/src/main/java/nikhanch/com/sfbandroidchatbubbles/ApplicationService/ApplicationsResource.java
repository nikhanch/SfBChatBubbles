package nikhanch.com.sfbandroidchatbubbles.ApplicationService;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhanch on 9/29/2015.
 */
public class ApplicationsResource {

        public String culture;

        public String userAgent;

        @SerializedName("_links")
        public nikhanch.com.sfbandroidchatbubbles.ApplicationService.ApplicationsResource.Links Links;

        @SerializedName("_embedded")
        public nikhanch.com.sfbandroidchatbubbles.ApplicationService.ApplicationsResource.Embedded Embedded;

        public String rel;
        public String etag;

        public ApplicationsResource(
                String culture, 
                String userAgent, 
                nikhanch.com.sfbandroidchatbubbles.ApplicationService.ApplicationsResource.Links Links, 
                nikhanch.com.sfbandroidchatbubbles.ApplicationService.ApplicationsResource.Embedded Embedded, 
                String rel, 
                String etag) {
            this.culture = culture;
            this.userAgent = userAgent;
            this.Links = Links;
            this.Embedded = Embedded;
            this.rel = rel;
            this.etag = etag;
        }


    public class Batch {
        public final String href;
        public Batch(String href) {
            this.href = href;
        }
    }

    public class CallForwardingSettings {
        public final String href;
        public CallForwardingSettings(String href) {
            this.href = href;
        }
    }
   
    public class Communication {


        @SerializedName("0fbfa8d4-2315-409d-b817-01a9bb69c1e7")
        public String bullShitId;
        @SerializedName("supportedModalities")
        public List<Object> supportedModalities = new ArrayList<Object>();
        @SerializedName("supportedMessageFormats")
        public List<String> supportedMessageFormats = new ArrayList<String>();
        @SerializedName("conversationHistory")
        public String conversationHistory;
        @SerializedName("_links")
        public Links____ Links;
        @SerializedName("rel")
        public String rel;
        @SerializedName("etag")
        public String etag;

        public Communication(String bullShitId, List<Object> supportedModalities, List<String> supportedMessageFormats, String conversationHistory, Links____ Links, String rel, String etag) {
            this.bullShitId = bullShitId;
            this.supportedModalities = supportedModalities;
            this.supportedMessageFormats = supportedMessageFormats;
            this.conversationHistory = conversationHistory;
            this.Links = Links;
            this.rel = rel;
            this.etag = etag;
        }

    }

    public class Conversations {

        @SerializedName("href")
        @Expose
        public String href;

        public Conversations(String href) {
            this.href = href;
        }

    }
    public class ConversationLogs {

        @SerializedName("href")
        @Expose
        public String href;

        public ConversationLogs(String href) {
            this.href = href;
        }

    }
    public class Embedded {

        @SerializedName("me")
        @Expose
        public Me me;
        @SerializedName("people")
        @Expose
        public People people;
        @SerializedName("onlineMeetings")
        @Expose
        public OnlineMeetings onlineMeetings;
        @SerializedName("communication")
        @Expose
        public Communication communication;

        public Embedded(Me me, People people, OnlineMeetings onlineMeetings, Communication communication) {
            this.me = me;
            this.people = people;
            this.onlineMeetings = onlineMeetings;
            this.communication = communication;
        }

    }

    public class Events {

        @SerializedName("href")
        @Expose
        public String href;

        public Events(String href) {
            this.href = href;
        }

    }

    public class JoinOnlineMeeting {

        @SerializedName("href")
        @Expose
        public String href;

        public JoinOnlineMeeting(String href) {
            this.href = href;
        }

    }

    public class Links {

        @SerializedName("self")
        @Expose
        public Self self;
        @SerializedName("policies")
        @Expose
        public Policies policies;
        @SerializedName("batch")
        @Expose
        public Batch batch;
        @SerializedName("events")
        @Expose
        public Events events;

        public Links(Self self, Policies policies, Batch batch, Events events) {
            this.self = self;
            this.policies = policies;
            this.batch = batch;
            this.events = events;
        }

    }

    public class Links_ {

        @SerializedName("self")
        @Expose
        public Self_ self;
        @SerializedName("makeMeAvailable")
        @Expose
        public MakeMeAvailable makeMeAvailable;
        @SerializedName("callForwardingSettings")
        @Expose
        public CallForwardingSettings callForwardingSettings;
        @SerializedName("phones")
        @Expose
        public Phones phones;
        @SerializedName("photo")
        @Expose
        public Photo photo;

        public Links_(Self_ self, MakeMeAvailable makeMeAvailable, CallForwardingSettings callForwardingSettings, Phones phones, Photo photo) {
            this.self = self;
            this.makeMeAvailable = makeMeAvailable;
            this.callForwardingSettings = callForwardingSettings;
            this.phones = phones;
            this.photo = photo;
        }

    }

    public class Links__ {

        @SerializedName("self")
        @Expose
        public Self__ self;
        @SerializedName("presenceSubscriptions")
        @Expose
        public PresenceSubscriptions presenceSubscriptions;
        @SerializedName("subscribedContacts")
        @Expose
        public SubscribedContacts subscribedContacts;
        @SerializedName("presenceSubscriptionMemberships")
        @Expose
        public PresenceSubscriptionMemberships presenceSubscriptionMemberships;
        @SerializedName("myGroups")
        @Expose
        public MyGroups myGroups;
        @SerializedName("myGroupMemberships")
        @Expose
        public MyGroupMemberships myGroupMemberships;
        @SerializedName("myContacts")
        @Expose
        public MyContacts myContacts;
        @SerializedName("myPrivacyRelationships")
        @Expose
        public MyPrivacyRelationships myPrivacyRelationships;
        @SerializedName("myContactsAndGroupsSubscription")
        @Expose
        public MyContactsAndGroupsSubscription myContactsAndGroupsSubscription;
        @SerializedName("search")
        @Expose
        public Search search;

        public Links__(Self__ self, PresenceSubscriptions presenceSubscriptions, SubscribedContacts subscribedContacts, PresenceSubscriptionMemberships presenceSubscriptionMemberships, MyGroups myGroups, MyGroupMemberships myGroupMemberships, MyContacts myContacts, MyPrivacyRelationships myPrivacyRelationships, MyContactsAndGroupsSubscription myContactsAndGroupsSubscription, Search search) {
            this.self = self;
            this.presenceSubscriptions = presenceSubscriptions;
            this.subscribedContacts = subscribedContacts;
            this.presenceSubscriptionMemberships = presenceSubscriptionMemberships;
            this.myGroups = myGroups;
            this.myGroupMemberships = myGroupMemberships;
            this.myContacts = myContacts;
            this.myPrivacyRelationships = myPrivacyRelationships;
            this.myContactsAndGroupsSubscription = myContactsAndGroupsSubscription;
            this.search = search;
        }

    }

    public class Links___ {

        @SerializedName("self")
        @Expose
        public Self___ self;
        @SerializedName("myOnlineMeetings")
        @Expose
        public MyOnlineMeetings myOnlineMeetings;
        @SerializedName("onlineMeetingDefaultValues")
        @Expose
        public OnlineMeetingDefaultValues onlineMeetingDefaultValues;
        @SerializedName("onlineMeetingEligibleValues")
        @Expose
        public OnlineMeetingEligibleValues onlineMeetingEligibleValues;
        @SerializedName("onlineMeetingInvitationCustomization")
        @Expose
        public OnlineMeetingInvitationCustomization onlineMeetingInvitationCustomization;
        @SerializedName("onlineMeetingPolicies")
        @Expose
        public OnlineMeetingPolicies onlineMeetingPolicies;
        @SerializedName("phoneDialInInformation")
        @Expose
        public PhoneDialInInformation phoneDialInInformation;
        @SerializedName("myAssignedOnlineMeeting")
        @Expose
        public MyAssignedOnlineMeeting myAssignedOnlineMeeting;

        public Links___(Self___ self, MyOnlineMeetings myOnlineMeetings, OnlineMeetingDefaultValues onlineMeetingDefaultValues, OnlineMeetingEligibleValues onlineMeetingEligibleValues, OnlineMeetingInvitationCustomization onlineMeetingInvitationCustomization, OnlineMeetingPolicies onlineMeetingPolicies, PhoneDialInInformation phoneDialInInformation, MyAssignedOnlineMeeting myAssignedOnlineMeeting) {
            this.self = self;
            this.myOnlineMeetings = myOnlineMeetings;
            this.onlineMeetingDefaultValues = onlineMeetingDefaultValues;
            this.onlineMeetingEligibleValues = onlineMeetingEligibleValues;
            this.onlineMeetingInvitationCustomization = onlineMeetingInvitationCustomization;
            this.onlineMeetingPolicies = onlineMeetingPolicies;
            this.phoneDialInInformation = phoneDialInInformation;
            this.myAssignedOnlineMeeting = myAssignedOnlineMeeting;
        }

    }

    public class Links____ {

        @SerializedName("self")
        @Expose
        public Self____ self;
        @SerializedName("startPhoneAudio")
        @Expose
        public StartPhoneAudio startPhoneAudio;
        @SerializedName("conversations")
        @Expose
        public Conversations conversations;
        @SerializedName("conversationLogs")
        @Expose
        public ConversationLogs conversationLogs;

        @SerializedName("startMessaging")
        @Expose
        public StartMessaging startMessaging;
        @SerializedName("startOnlineMeeting")
        @Expose
        public StartOnlineMeeting startOnlineMeeting;
        @SerializedName("joinOnlineMeeting")
        @Expose
        public JoinOnlineMeeting joinOnlineMeeting;
        @SerializedName("missedItems")
        @Expose
        public MissedItems missedItems;

        public Links____(Self____ self, StartPhoneAudio startPhoneAudio, Conversations conversations, StartMessaging startMessaging, StartOnlineMeeting startOnlineMeeting, JoinOnlineMeeting joinOnlineMeeting, MissedItems missedItems) {
            this.self = self;
            this.startPhoneAudio = startPhoneAudio;
            this.conversations = conversations;
            this.startMessaging = startMessaging;
            this.startOnlineMeeting = startOnlineMeeting;
            this.joinOnlineMeeting = joinOnlineMeeting;
            this.missedItems = missedItems;
        }

    }

    public class MakeMeAvailable {

        @SerializedName("href")
        @Expose
        public String href;

        public MakeMeAvailable(String href) {
            this.href = href;
        }

    }

    public class Me {

        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("uri")
        @Expose
        public String uri;
        @SerializedName("emailAddresses")
        @Expose
        public List<String> emailAddresses = new ArrayList<String>();
        @SerializedName("_links")
        @Expose
        public Links_ Links;
        @SerializedName("rel")
        @Expose
        public String rel;

        public Me(String name, String uri, List<String> emailAddresses, Links_ Links, String rel) {
            this.name = name;
            this.uri = uri;
            this.emailAddresses = emailAddresses;
            this.Links = Links;
            this.rel = rel;
        }

    }

    public class MissedItems {

        @SerializedName("href")
        @Expose
        public String href;

        public MissedItems(String href) {
            this.href = href;
        }

    }

    public class MyAssignedOnlineMeeting {

        @SerializedName("href")
        @Expose
        public String href;

        public MyAssignedOnlineMeeting(String href) {
            this.href = href;
        }

    }

    public class MyContacts {

        @SerializedName("href")
        @Expose
        public String href;

        public MyContacts(String href) {
            this.href = href;
        }

    }

    public class MyContactsAndGroupsSubscription {

        @SerializedName("href")
        @Expose
        public String href;

        public MyContactsAndGroupsSubscription(String href) {
            this.href = href;
        }

    }

    public class MyGroupMemberships {

        @SerializedName("href")
        @Expose
        public String href;
        @SerializedName("revision")
        @Expose
        public String revision;

        public MyGroupMemberships(String href, String revision) {
            this.href = href;
            this.revision = revision;
        }

    }

    public class MyGroups {

        @SerializedName("href")
        @Expose
        public String href;
        @SerializedName("revision")
        @Expose
        public String revision;

        public MyGroups(String href, String revision) {
            this.href = href;
            this.revision = revision;
        }

    }

    public class MyOnlineMeetings {

        @SerializedName("href")
        @Expose
        public String href;

        public MyOnlineMeetings(String href) {
            this.href = href;
        }

    }

    public class MyPrivacyRelationships {

        @SerializedName("href")
        @Expose
        public String href;

        public MyPrivacyRelationships(String href) {
            this.href = href;
        }

    }

    public class OnlineMeetingDefaultValues {

        @SerializedName("href")
        @Expose
        public String href;

        public OnlineMeetingDefaultValues(String href) {
            this.href = href;
        }

    }

    public class OnlineMeetingEligibleValues {

        @SerializedName("href")
        @Expose
        public String href;

        public OnlineMeetingEligibleValues(String href) {
            this.href = href;
        }

    }

    public class OnlineMeetingInvitationCustomization {

        @SerializedName("href")
        @Expose
        public String href;

        public OnlineMeetingInvitationCustomization(String href) {
            this.href = href;
        }

    }

    public class OnlineMeetingPolicies {

        @SerializedName("href")
        @Expose
        public String href;

        public OnlineMeetingPolicies(String href) {
            this.href = href;
        }

    }

    public class OnlineMeetings {

        @SerializedName("_links")
        @Expose
        public Links___ Links;
        @SerializedName("rel")
        @Expose
        public String rel;

        public OnlineMeetings(Links___ Links, String rel) {
            this.Links = Links;
            this.rel = rel;
        }

    }

    public class People {

        @SerializedName("_links")
        @Expose
        public Links__ Links;
        @SerializedName("rel")
        @Expose
        public String rel;

        public People(Links__ Links, String rel) {
            this.Links = Links;
            this.rel = rel;
        }

    }

    public class PhoneDialInInformation {

        @SerializedName("href")
        @Expose
        public String href;

        public PhoneDialInInformation(String href) {
            this.href = href;
        }

    }

    public class Phones {

        @SerializedName("href")
        @Expose
        public String href;

        public Phones(String href) {
            this.href = href;
        }

    }

    public class Photo {

        @SerializedName("href")
        @Expose
        public String href;

        public Photo(String href) {
            this.href = href;
        }

    }

    public class Policies {

        @SerializedName("href")
        @Expose
        public String href;

        public Policies(String href) {
            this.href = href;
        }

    }

    public class PresenceSubscriptionMemberships {

        @SerializedName("href")
        @Expose
        public String href;

        public PresenceSubscriptionMemberships(String href) {
            this.href = href;
        }

    }

    public class PresenceSubscriptions {

        @SerializedName("href")
        @Expose
        public String href;

        public PresenceSubscriptions(String href) {
            this.href = href;
        }

    }

    public class Search {

        @SerializedName("href")
        @Expose
        public String href;
        @SerializedName("revision")
        @Expose
        public String revision;

        public Search(String href, String revision) {
            this.href = href;
            this.revision = revision;
        }

    }

    public class Self {

        @SerializedName("href")
        @Expose
        public String href;

        public Self(String href) {
            this.href = href;
        }

    }

    public class Self_ {

        @SerializedName("href")
        @Expose
        public String href;

        public Self_(String href) {
            this.href = href;
        }

    }

    public class Self__ {

        @SerializedName("href")
        @Expose
        public String href;

        public Self__(String href) {
            this.href = href;
        }

    }

    public class Self___ {

        @SerializedName("href")
        @Expose
        public String href;

        public Self___(String href) {
            this.href = href;
        }

    }

    public class Self____ {

        @SerializedName("href")
        @Expose
        public String href;

        public Self____(String href) {
            this.href = href;
        }

    }

    public class StartMessaging {

        @SerializedName("href")
        @Expose
        public String href;
        @SerializedName("revision")
        @Expose
        public String revision;

        public StartMessaging(String href, String revision) {
            this.href = href;
            this.revision = revision;
        }

    }

    public class StartOnlineMeeting {

        @SerializedName("href")
        @Expose
        public String href;

        public StartOnlineMeeting(String href) {
            this.href = href;
        }

    }

    public class StartPhoneAudio {

        @SerializedName("href")
        @Expose
        public String href;
        @SerializedName("revision")
        @Expose
        public String revision;

        public StartPhoneAudio(String href, String revision) {
            this.href = href;
            this.revision = revision;
        }

    }
    public class SubscribedContacts {

        @SerializedName("href")
        @Expose
        public String href;

        public SubscribedContacts(String href) {
            this.href = href;
        }

    }
}
