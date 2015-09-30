package nikhanch.com.sfbandroidchatbubbles.ApplicationService.BuddylistManager;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MyContactsResponse {

    @SerializedName("_links")
    public nikhanch.com.sfbandroidchatbubbles.ApplicationService.BuddylistManager.MyContactsResponse.Links Links;
    @SerializedName("_embedded")
    public nikhanch.com.sfbandroidchatbubbles.ApplicationService.BuddylistManager.MyContactsResponse.Embedded Embedded;
    @SerializedName("rel")
    public String rel;

    public MyContactsResponse(
            nikhanch.com.sfbandroidchatbubbles.ApplicationService.BuddylistManager.MyContactsResponse.Links Links,
            nikhanch.com.sfbandroidchatbubbles.ApplicationService.BuddylistManager.MyContactsResponse.Embedded Embedded,
            String rel) {
        this.Links = Links;
        this.Embedded = Embedded;
        this.rel = rel;
    }

    public class Contact {

        @SerializedName("uri")
        public String uri;
        @SerializedName("sourceNetwork")
        public String sourceNetwork;
        @SerializedName("company")
        public String company;
        @SerializedName("department")
        public String department;
        @SerializedName("office")
        public String office;
        @SerializedName("emailAddresses")
        public List<String> emailAddresses = new ArrayList<>();
        @SerializedName("homePhoneNumber")
        public String homePhoneNumber;
        @SerializedName("workPhoneNumber")
        public String workPhoneNumber;
        @SerializedName("mobilePhoneNumber")
        public String mobilePhoneNumber;
        @SerializedName("otherPhoneNumber")
        public String otherPhoneNumber;
        @SerializedName("type")
        public String type;
        @SerializedName("name")
        public String name;
        @SerializedName("_links")
        public Links_ Links;
        @SerializedName("rel")
        public String rel;
        @SerializedName("etag")
        public String etag;
        @SerializedName("title")
        public String title;
        @SerializedName("sourceNetworkIconUrl")
        public String sourceNetworkIconUrl;

        public Contact(String uri, String sourceNetwork, String company, String department, String office, List<String> emailAddresses, String homePhoneNumber, String workPhoneNumber, String mobilePhoneNumber, String otherPhoneNumber, String type, String name, Links_ Links, String rel, String etag, String title, String sourceNetworkIconUrl) {
            this.uri = uri;
            this.sourceNetwork = sourceNetwork;
            this.company = company;
            this.department = department;
            this.office = office;
            this.emailAddresses = emailAddresses;
            this.homePhoneNumber = homePhoneNumber;
            this.workPhoneNumber = workPhoneNumber;
            this.mobilePhoneNumber = mobilePhoneNumber;
            this.otherPhoneNumber = otherPhoneNumber;
            this.type = type;
            this.name = name;
            this.Links = Links;
            this.rel = rel;
            this.etag = etag;
            this.title = title;
            this.sourceNetworkIconUrl = sourceNetworkIconUrl;
        }

    }

    public class ContactLocation {
        @SerializedName("href")
        public final String href;
        public ContactLocation(String href) {
            this.href = href;
        }

    }

    public class ContactNote {
        @SerializedName("href")
        public final String href;
        public ContactNote(String href) {
            this.href = href;
        }
    }

    public class ContactPhoto {
        @SerializedName("href")
        public String href;
        public ContactPhoto(String href) {
            this.href = href;
        }
    }

    public class ContactPresence {
        @SerializedName("href")
        public final String href;
        public ContactPresence(String href) {
            this.href = href;
        }
    }

    public class ContactPrivacyRelationship {
        @SerializedName("href")
        public final String href;
        @SerializedName("revision")
        public final String revision;
        public ContactPrivacyRelationship(String href, String revision) {
            this.href = href;
            this.revision = revision;
        }
    }

    public class ContactSupportedModalities {
        @SerializedName("href")
        public String href;
        public ContactSupportedModalities(String href) {
            this.href = href;
        }
    }

    public class Embedded {
        @SerializedName("contact")
        public List<Contact> contact = new ArrayList<>();
        public Embedded(List<Contact> contact) {
            this.contact = contact;
        }
    }

    public class Links {
        @SerializedName("self")
        public final Self self;
        public Links(Self self) {
            this.self = self;
        }
    }

    public class Links_ {

        @SerializedName("self")
        public Self_ self;
        @SerializedName("contactPhoto")
        public ContactPhoto contactPhoto;
        @SerializedName("contactPresence")
        public ContactPresence contactPresence;
        @SerializedName("contactLocation")
        public ContactLocation contactLocation;
        @SerializedName("contactNote")
        public ContactNote contactNote;
        @SerializedName("contactSupportedModalities")
        public ContactSupportedModalities contactSupportedModalities;
        @SerializedName("contactPrivacyRelationship")
        public ContactPrivacyRelationship contactPrivacyRelationship;

        public Links_(Self_ self, ContactPhoto contactPhoto, ContactPresence contactPresence, ContactLocation contactLocation, ContactNote contactNote, ContactSupportedModalities contactSupportedModalities, ContactPrivacyRelationship contactPrivacyRelationship) {
            this.self = self;
            this.contactPhoto = contactPhoto;
            this.contactPresence = contactPresence;
            this.contactLocation = contactLocation;
            this.contactNote = contactNote;
            this.contactSupportedModalities = contactSupportedModalities;
            this.contactPrivacyRelationship = contactPrivacyRelationship;
        }
    }

    public class Self {
        @SerializedName("href")
        public final String href;
        public Self(String href) {
            this.href = href;
        }
    }

    public class Self_ {
        @SerializedName("href")
        public final String href;
        public Self_(String href) {
            this.href = href;
        }
    }
}
