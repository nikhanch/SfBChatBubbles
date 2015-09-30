package nikhanch.com.sfbandroidchatbubbles.Models;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import nikhanch.com.sfbandroidchatbubbles.ApplicationService.BuddylistManager.MyContactsResponse;
import nikhanch.com.sfbandroidchatbubbles.ApplicationServiceUtils.UriUtils;

/**
 * Created by nikhanch on 9/30/2015.
 */
public class ContactModel {

    private String sipUri;
    private String name;
    private String company;
    private String department;
    private String title;
    private String office;
    private List<String> emails = new ArrayList<>();
    private String homePhoneNumber;
    private String workPhoneNumber;
    private String mobilePhoneNumber;
    private String otherPhoneNumber;
    private URL photoUrl;
    private URL selfUcwaUrl;

    public ContactModel(MyContactsResponse.Contact contactRecord){

        this.setSipUri(contactRecord.uri);
        this.setName(contactRecord.name);
        this.setCompany(contactRecord.company);
        this.setDepartment(contactRecord.department);
        this.setTitle(contactRecord.title);
        this.setOffice(contactRecord.office);
        this.setEmails(contactRecord.emailAddresses);
        this.setHomePhoneNumber(contactRecord.homePhoneNumber);
        this.setWorkPhoneNumber(contactRecord.workPhoneNumber);
        this.setMobilePhoneNumber(contactRecord.mobilePhoneNumber);
        this.setOtherPhoneNumber(contactRecord.otherPhoneNumber);
        this.setPhotoUrl(UriUtils.GetAbsoluteUrl(contactRecord.Links.contactPhoto.href));
        this.setSelfUcwaUrl(UriUtils.GetAbsoluteUrl(contactRecord.Links.self.href));
    }

    public String getSipUri() {
        return sipUri;
    }

    public void setSipUri(String sipUri) {
        this.sipUri = sipUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getOtherPhoneNumber() {
        return otherPhoneNumber;
    }

    public void setOtherPhoneNumber(String otherPhoneNumber) {
        this.otherPhoneNumber = otherPhoneNumber;
    }

    public URL getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(URL photoUrl) {
        this.photoUrl = photoUrl;
    }

    public URL getSelfUcwaUrl() {
        return selfUcwaUrl;
    }

    public void setSelfUcwaUrl(URL selfUcwaUrl) {
        this.selfUcwaUrl = selfUcwaUrl;
    }
}
