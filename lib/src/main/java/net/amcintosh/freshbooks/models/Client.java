package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.Util;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class Client {

    @Key Long id;
    @Key String accountingSystemId;
    @Key("bus_phone") String businessPhone;
    @Key("company_industry") String companyIndustry;
    @Key("company_size") String companySize;
    @Key("currency_code") String currencyCode;
    @Key String email;
    @Key String fax;
    @Key("fname") String firstName;
    @Key("home_phone") String homePhone;
    @Key String language;
    //@Key("last_activity") last_activity	datetime	//time of last client activity
    @Key("lname") String lastName;
    @Key("mob_phone") String mobilePhone;
    @Key String note;
    @Key String organization;
    @Key("p_city") String billingCity;
    @Key("p_code") String billingCode;
    @Key("p_country") String billingCountry;
    @Key("p_province") String billingProvince;
    @Key("p_street") String billingStreet;
    @Key("p_street2") String billingStreet2;
    //@Key("pref_email") pref_email	bool	prefers email over ground mail
    //@Key("pref_gmail") pref_gmail	bool	prefers ground mail over email
    @Key("s_city") String shippingCity;
    @Key("s_code") String shippingCode;
    @Key("s_country") String shippingCountry;
    @Key("s_province") String shippingProvince;
    @Key("s_street") String shippingStreet;
    @Key("s_street2") String shippingStreet2;
    @Key("signup_date") String signupDate;
    @Key String updated;
    @Key Long userid;
    @Key("vat_name") String vatName;
    @Key("vat_number") String vatNumber;
    @Key("vis_state") int visState;

    /**
     * Get unique to this business id for client
     *
     * @return Client Id
     */
    public Long getId() {
        return id;
    }

    /**
     * Get unique identifier of business client exists on.
     *
     * @return Accounting System Id
     */
    public String getAccountingSystemId() {
        return accountingSystemId;
    }

    private void setAccountingSystemId(String accountingSystemId) {
        this.accountingSystemId = accountingSystemId;
    }

    /**
     * Business phone number.
     *
     * @return String of the phone number
     */
    public String getBusinessPhone() {
        return businessPhone;
    }

    /**
     * Business phone number.
     *
     * @param businessPhone String of the phone number
     */
    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    /**
     * Description of industry client is in.
     *
     * @return
     */
    public String getCompanyIndustry() {
        return companyIndustry;
    }

    /**
     * Description of industry client is in.
     *
     * @param companyIndustry
     */
    public void setCompanyIndustry(String companyIndustry) {
        this.companyIndustry = companyIndustry;
    }

    /**
     * Size of client's company
     *
     * @return
     */
    public String getCompanySize() {
        return companySize;
    }

    /**
     * Size of client's company
     *
     * @param companySize
     */
    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    /**
     * 3-letter shortcode for client's preferred currency. Eg. USD, CAD, EUR
     *
     * @return
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * 3-letter shortcode for client's preferred currency. Eg. USD, CAD, EUR
     *
     * @param currencyCode
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * Client's email.
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Client's email.
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Client's fax number.
     *
     * @return
     */
    public String getFax() {
        return fax;
    }

    /**
     * Client's fax number.
     *
     * @param fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * Client's first name.
     *
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Client's first name.
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Client's home phone number.
     *
     * @return
     */
    public String getHomePhone() {
        return homePhone;
    }

    /**
     * Client's home phone number.
     *
     * @param homePhone
     */
    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    /**
     * Shortcode indicating user language e.g. "en"
     *
     * @return
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Shortcode indicating user language e.g. "en"
     *
     * @param language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Client's last name.
     *
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Client's last name.
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Client's mobile phone number.
     *
     * @return
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * Client's mobile phone number.
     *
     * @param mobilePhone
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * Notes kept by admin about client.
     *
     * @return
     */
    public String getNote() {
        return note;
    }

    /**
     * Notes kept by admin about client.
     *
     * @param note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Name for client's business.
     *
     * @return
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * Name for client's business.
     *
     * @param organization
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * Billing address city.
     *
     * @return
     */
    public String getBillingCity() {
        return billingCity;
    }

    /**
     * Billing address city.
     *
     * @param billingCity
     */
    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    /**
     * Billing address postal code.
     *
     * @return
     */
    public String getBillingCode() {
        return billingCode;
    }

    /**
     * Billing address postal code.
     *
     * @param billingCode
     */
    public void setBillingCode(String billingCode) {
        this.billingCode = billingCode;
    }

    /**
     * Billing address country.
     *
     * @return
     */
    public String getBillingCountry() {
        return billingCountry;
    }

    /**
     * Billing address country.
     *
     * @param billingCountry
     */
    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    /**
     * Short form of province for billing address.
     *
     * @return
     */
    public String getBillingProvince() {
        return billingProvince;
    }

    /**
     * Short form of province for billing address.
     *
     * @param billingProvince
     */
    public void setBillingProvince(String billingProvince) {
        this.billingProvince = billingProvince;
    }

    /**
     * Billing address street.
     *
     * @return
     */
    public String getBillingStreet() {
        return billingStreet;
    }

    /**
     * Billing address street.
     *
     * @param billingStreet
     */
    public void setBillingStreet(String billingStreet) {
        this.billingStreet = billingStreet;
    }

    /**
     * Billing address, second street info.
     *
     * @return
     */
    public String getBillingStreet2() {
        return billingStreet2;
    }

    /**
     * Billing address, second street info.
     *
     * @param billingStreet2
     */
    public void setBillingStreet2(String billingStreet2) {
        this.billingStreet2 = billingStreet2;
    }

    /**
     * Shipping address city.
     *
     * @return
     */
    public String getShippingCity() {
        return shippingCity;
    }

    /**
     * Shipping address city.
     *
     * @param shippingCity
     */
    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    /**
     * Shipping address postal code.
     *
     * @return
     */
    public String getShippingCode() {
        return shippingCode;
    }

    /**
     * Shipping address postal code.
     *
     * @param shippingCode
     */
    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    /**
     * Shipping address country.
     *
     * @return
     */
    public String getShippingCountry() {
        return shippingCountry;
    }

    /**
     * Shipping address country.
     *
     * @param shippingCountry
     */
    public void setShippingCountry(String shippingCountry) {
        this.shippingCountry = shippingCountry;
    }

    /**
     * Short form of province for shipping address.
     *
     * @return
     */
    public String getShippingProvince() {
        return shippingProvince;
    }

    /**
     * Short form of province for shipping address.
     *
     * @param shippingProvince
     */
    public void setShippingProvince(String shippingProvince) {
        this.shippingProvince = shippingProvince;
    }

    /**
     * Shipping address street.
     *
     * @return
     */
    public String getShippingStreet() {
        return shippingStreet;
    }

    /**
     * Shipping address street.
     *
     * @param shippingStreet
     */
    public void setShippingStreet(String shippingStreet) {
        this.shippingStreet = shippingStreet;
    }

    /**
     * Shipping address, second street info.
     *
     * @return
     */
    public String getShippingStreet2() {
        return shippingStreet2;
    }

    /**
     * Shipping address, second street info.
     *
     * @param shippingStreet2
     */
    public void setShippingStreet2(String shippingStreet2) {
        this.shippingStreet2 = shippingStreet2;
    }

    /**
     * Duplicate of id
     *
     * @return
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * Get the "Value Added Tax" name
     *
     * @return VAT name
     */
    public String getVatName() {
        return vatName;
    }

    /**
     * Set the "Value Added Tax" name
     *
     * @param vatName VAT name
     */
    public void setVatName(String vatName) {
        this.vatName = vatName;
    }

    /**
     * Get the "Value Added Tax" number
     *
     * @return VAT number
     */
    public String getVatNumber() {
        return vatNumber;
    }

    /**
     * Set the "Value Added Tax" number
     *
     * @param vatNumber VAT number
     */
    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    /**
     * Get the signup time of the client.
     *
     * Note: The API returns this data in UTC.
     *
     * @return Signup time in UTC
     */
    public ZonedDateTime getSignupDate() {
        return Util.getZonedDateTimeFromAccountingLocalTime(this.signupDate);
    }

    private void setSignupDate(ZonedDateTime signupDate) {
        this.signupDate = Util.getAccountingLocalTimeFromZonedDateTime(signupDate);
    }

    /**
     * Get the time of last modification to the clinet.
     *
     * Note: The API returns this data in "US/Eastern", but it is converted to UTC.
     *
     * @return Updated time in UTC
     */
    public ZonedDateTime getUpdated() {
        return Util.getZonedDateTimeFromAccountingLocalTime(this.updated);
    }

    private void setUpdated(ZonedDateTime updated) {
        this.updated = Util.getAccountingLocalTimeFromZonedDateTime(updated);
    }

    /**
     * Get the visibility state: active, deleted, or archived
     *
     * @return Enum of the visibility state.
     */
    public VisState getVisState() {
        return VisState.valueOf(this.visState);
    }

    /**
     * Set the visibility state of the client.
     *
     * @param visState
     */
    public void setVisState(VisState visState) {
        this.visState = visState.getValue();
    }

    public Map<String, Object> getContent() {
        Map<String, Object> content = new HashMap<>();
        Util.putIfNotNull(content, "bus_phone", this.businessPhone);
        Util.putIfNotNull(content, "company_industry", this.companyIndustry);
        Util.putIfNotNull(content, "company_size", this.companySize);
        Util.putIfNotNull(content, "currency_code", this.currencyCode);
        Util.putIfNotNull(content, "email", this.email);
        Util.putIfNotNull(content, "fax", this.fax);
        Util.putIfNotNull(content, "fname", this.firstName);
        Util.putIfNotNull(content, "home_phone", this.homePhone);
        Util.putIfNotNull(content, "language", this.language);
        //Util.putIfNotNull(content, "last_activity", this.last_activity);
        Util.putIfNotNull(content, "lname", this.lastName);
        Util.putIfNotNull(content, "note", this.note);
        Util.putIfNotNull(content, "organization", this.organization);
        Util.putIfNotNull(content, "p_city", this.billingCity);
        Util.putIfNotNull(content, "p_code", this.billingCode);
        Util.putIfNotNull(content, "p_country", this.billingCountry);
        Util.putIfNotNull(content, "p_province", this.billingProvince);
        Util.putIfNotNull(content, "p_street", this.billingStreet);
        Util.putIfNotNull(content, "p_street2", this.billingStreet2);
        //@Key("pref_email") pref_email	bool	prefers email over ground mail
        //@Key("pref_gmail") pref_gmail	bool	prefers ground mail over email
        Util.putIfNotNull(content, "s_city", this.shippingCity);
        Util.putIfNotNull(content, "s_code", this.shippingCode);
        Util.putIfNotNull(content, "s_country", this.shippingCountry);
        Util.putIfNotNull(content, "s_province", this.shippingProvince);
        Util.putIfNotNull(content, "s_street", this.shippingStreet);
        Util.putIfNotNull(content, "s_street2", this.shippingStreet2);
        Util.putIfNotNull(content, "vat_name", this.vatName);
        Util.putIfNotNull(content, "vat_number", this.vatNumber);
        return content;
    }
}
