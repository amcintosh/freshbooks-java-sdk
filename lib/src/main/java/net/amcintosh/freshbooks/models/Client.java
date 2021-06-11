package net.amcintosh.freshbooks.models;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.Util;
import net.amcintosh.freshbooks.models.api.ConvertibleContent;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * A client in the new FreshBooks is a resource representing an entity you send invoices to.
 *
 * @see <a href="https://www.freshbooks.com/api/clients">FreshBooks API - Clients</a>
 */
public class Client extends GenericJson implements ConvertibleContent {

    @Key Long id;
    @Key("accounting_systemid") String accountingSystemId;
    @Key("bus_phone") String businessPhone;
    @Key("company_industry") String companyIndustry;
    @Key("company_size") String companySize;
    @Key("currency_code") String currencyCode;
    @Key String email;
    @Key String fax;
    @Key("fname") String firstName;
    @Key("home_phone") String homePhone;
    @Key String language;
    @Key("last_activity") String lastActivity;
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
    @Key("userid") Long userId;
    @Key("vat_name") String vatName;
    @Key("vat_number") String vatNumber;
    @Key("vis_state") int visState;

    /**
     * Get the unique identifier of this client within this business.
     *
     * @return Client Id
     */
    public long getId() {
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
     * @return The industry
     */
    public String getCompanyIndustry() {
        return companyIndustry;
    }

    /**
     * Description of industry client is in.
     *
     * @param companyIndustry The industry
     */
    public void setCompanyIndustry(String companyIndustry) {
        this.companyIndustry = companyIndustry;
    }

    /**
     * Size of client's company
     *
     * @return Eg. "10"
     */
    public String getCompanySize() {
        return companySize;
    }

    /**
     * Size of client's company
     *
     * @param companySize Eg. "10"
     */
    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    /**
     * 3-letter shortcode for client's preferred currency. Eg. USD, CAD, EUR
     *
     * @return Eg. "USD"
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * 3-letter shortcode for client's preferred currency. Eg. USD, CAD, EUR
     *
     * @param currencyCode Eg. "USD"
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * Client's email.
     *
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Client's email.
     *
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Client's fax number.
     *
     * @return Eg. "416-444-4444"
     */
    public String getFax() {
        return fax;
    }

    /**
     * Client's fax number.
     *
     * @param fax Eg. 416-444-4444
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * Client's first name.
     *
     * @return Eg. "Gordon"
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Client's first name.
     *
     * @param firstName Eg. "Gordon"
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Client's home phone number.
     *
     * @return Eg. 416-444-4444
     */
    public String getHomePhone() {
        return homePhone;
    }

    /**
     * Client's home phone number.
     *
     * @param homePhone Eg. 416-444-4444
     */
    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    /**
     * Shortcode indicating user language e.g. "en"
     *
     * @return Eg. "en"
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Shortcode indicating user language e.g. "en"
     *
     * @param language Eg. "en"
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * The last client activity action.
     * <br><br>
     * <i>Note:</i> This returns as "null" in all calls unless a "last_activity"
     * include parameter is provided.
     *
     * @return eg. "Client Updated"
     */
    public String getLastActivity() {
        return lastActivity;
    }

    /**
     * Client's last name.
     *
     * @return Eg. "Shumway"
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Client's last name.
     *
     * @param lastName Eg. "Shumway"
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Client's mobile phone number.
     *
     * @return Eg. "416-444-4444"
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * Client's mobile phone number.
     *
     * @param mobilePhone Eg. "416-444-4444"
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * Notes kept by admin about client.
     *
     * @return The notes
     */
    public String getNote() {
        return note;
    }

    /**
     * Notes kept by admin about client.
     *
     * @param note The notes
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Name for client's business.
     *
     * @return Organization name
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * Name for client's business.
     *
     * @param organization Organization name
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * Billing address city.
     *
     * @return The city
     */
    public String getBillingCity() {
        return billingCity;
    }

    /**
     * Billing address city.
     *
     * @param billingCity The city
     */
    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    /**
     * Billing address postal code.
     *
     * @return The postal code
     */
    public String getBillingCode() {
        return billingCode;
    }

    /**
     * Billing address postal code.
     *
     * @param billingCode The postal code
     */
    public void setBillingCode(String billingCode) {
        this.billingCode = billingCode;
    }

    /**
     * Billing address country.
     *
     * @return The country
     */
    public String getBillingCountry() {
        return billingCountry;
    }

    /**
     * Billing address country.
     *
     * @param billingCountry The country
     */
    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    /**
     * Short form of province for billing address.
     *
     * @return The province/state
     */
    public String getBillingProvince() {
        return billingProvince;
    }

    /**
     * Short form of province for billing address.
     *
     * @param billingProvince The province/state
     */
    public void setBillingProvince(String billingProvince) {
        this.billingProvince = billingProvince;
    }

    /**
     * Billing address street.
     *
     * @return The street address.
     */
    public String getBillingStreet() {
        return billingStreet;
    }

    /**
     * Billing address street.
     *
     * @param billingStreet The street address.
     */
    public void setBillingStreet(String billingStreet) {
        this.billingStreet = billingStreet;
    }

    /**
     * Billing address, additional street info.
     *
     * @return The street address.
     */
    public String getBillingStreet2() {
        return billingStreet2;
    }

    /**
     * Billing address, second street info.
     *
     * @param billingStreet2 The street address.
     */
    public void setBillingStreet2(String billingStreet2) {
        this.billingStreet2 = billingStreet2;
    }

    /**
     * Shipping address city.
     *
     * @return The city
     */
    public String getShippingCity() {
        return shippingCity;
    }

    /**
     * Shipping address city.
     *
     * @param shippingCity The city
     */
    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    /**
     * Shipping address postal code.
     *
     * @return The postal code
     */
    public String getShippingCode() {
        return shippingCode;
    }

    /**
     * Shipping address postal code.
     *
     * @param shippingCode The postal code
     */
    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    /**
     * Shipping address country.
     *
     * @return The country
     */
    public String getShippingCountry() {
        return shippingCountry;
    }

    /**
     * Shipping address country.
     *
     * @param shippingCountry The country
     */
    public void setShippingCountry(String shippingCountry) {
        this.shippingCountry = shippingCountry;
    }

    /**
     * Short form of province for shipping address.
     *
     * @return The province/state
     */
    public String getShippingProvince() {
        return shippingProvince;
    }

    /**
     * Short form of province for shipping address.
     *
     * @param shippingProvince The province/state
     */
    public void setShippingProvince(String shippingProvince) {
        this.shippingProvince = shippingProvince;
    }

    /**
     * Shipping address street.
     *
     * @return The address
     */
    public String getShippingStreet() {
        return shippingStreet;
    }

    /**
     * Shipping address street.
     *
     * @param shippingStreet The address
     */
    public void setShippingStreet(String shippingStreet) {
        this.shippingStreet = shippingStreet;
    }

    /**
     * Shipping address, second street info.
     *
     * @return The address
     */
    public String getShippingStreet2() {
        return shippingStreet2;
    }

    /**
     * Shipping address, second street info.
     *
     * @param shippingStreet2 The address
     */
    public void setShippingStreet2(String shippingStreet2) {
        this.shippingStreet2 = shippingStreet2;
    }

    /**
     * Duplicate of id
     *
     * @return The id
     */
    public long getUserId() {
        return userId;
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
     * <br><br>
     * <i>Note:</i> The API returns this data in UTC.
     *
     * @return Signup time in UTC
     */
    public ZonedDateTime getSignupDate() {
        LocalDateTime accountingLocalTime = LocalDateTime.parse(signupDate, Util.getAccountingDateTimeFormatter());
        return accountingLocalTime.atZone(Util.UTC_ZONE);
    }

    /**
     * Get the time of last modification to the client.
     * <br><br>
     * <i>Note:</i> The API returns this data in "US/Eastern", but it is converted to UTC.
     *
     * @return Updated time in UTC
     */
    public ZonedDateTime getUpdated() {
        return Util.getZonedDateTimeFromAccountingLocalTime(this.updated);
    }

    /**
     * Get the visibility state: active, deleted, or archived
     * <br><br>
     * @see <a href="https://www.freshbooks.com/api/active_deleted">FreshBooks API - Active and Deleted Objects</a>
     *
     * @return Enum of the visibility state.
     */
    public VisState getVisState() {
        return VisState.valueOf(this.visState);
    }

    /**
     * Set the visibility state of the client.
     * <br><br>
     * @see <a href="https://www.freshbooks.com/api/active_deleted">FreshBooks API - Active and Deleted Objects</a>
     *
     * @param visState VisState value
     */
    public void setVisState(VisState visState) {
        this.visState = visState.getValue();
    }

    public Map<String, Object> getContent() {
        Map<String, Object> content = new HashMap<>();
        Util.convertContent(content, "bus_phone", this.businessPhone);
        Util.convertContent(content, "company_industry", this.companyIndustry);
        Util.convertContent(content, "company_size", this.companySize);
        Util.convertContent(content, "currency_code", this.currencyCode);
        Util.convertContent(content, "email", this.email);
        Util.convertContent(content, "fax", this.fax);
        Util.convertContent(content, "fname", this.firstName);
        Util.convertContent(content, "home_phone", this.homePhone);
        Util.convertContent(content, "language", this.language);
        Util.convertContent(content, "lname", this.lastName);
        Util.convertContent(content, "note", this.note);
        Util.convertContent(content, "organization", this.organization);
        Util.convertContent(content, "p_city", this.billingCity);
        Util.convertContent(content, "p_code", this.billingCode);
        Util.convertContent(content, "p_country", this.billingCountry);
        Util.convertContent(content, "p_province", this.billingProvince);
        Util.convertContent(content, "p_street", this.billingStreet);
        Util.convertContent(content, "p_street2", this.billingStreet2);
        //@Key("pref_email") pref_email	bool	prefers email over ground mail
        //@Key("pref_gmail") pref_gmail	bool	prefers ground mail over email
        Util.convertContent(content, "s_city", this.shippingCity);
        Util.convertContent(content, "s_code", this.shippingCode);
        Util.convertContent(content, "s_country", this.shippingCountry);
        Util.convertContent(content, "s_province", this.shippingProvince);
        Util.convertContent(content, "s_street", this.shippingStreet);
        Util.convertContent(content, "s_street2", this.shippingStreet2);
        Util.convertContent(content, "vat_name", this.vatName);
        Util.convertContent(content, "vat_number", this.vatNumber);
        return content;
    }
}
