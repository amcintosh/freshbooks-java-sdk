package net.amcintosh.freshbooks.models;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.google.api.client.util.NullValue;
import com.google.api.client.util.Value;
import net.amcintosh.freshbooks.Util;
import net.amcintosh.freshbooks.models.api.ConvertibleContent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Invoices in FreshBooks are what gets sent to Clients, detailing specific goods or
 * services performed or provided by the Administrator of their System, and the amount
 * that Client owes to the Admin.
 *
 * @see <a href="https://www.freshbooks.com/api/invoices">FreshBooks API - Invoices</a>
 */
public class Invoice extends GenericJson implements ConvertibleContent {

    @Key Long id;
    @Key("invoiceid") Long invoiceId;
    @Key("accounting_systemid") String accountingSystemId;
    @Key("accountid") String accountId;
    @Key String address;
    @Key Money amount;
    @Key("auto_bill") boolean autoBill;
    @Key("autobill_status") InvoiceAutoBillStatus autoBillStatus;
    @Key String city;
    @Key String code;
    @Key String country;
    @Key("create_date") String createDate;
    @Key("created_at") String createdAt;
    @Key("currency_code") String currencyCode;
    @Key("current_organization") String currentOrganization;
    @Key("customerid") Long customerId;
    @Key("date_paid") String datePaid;
    @Key("deposit_amount") Money depositAmount;
    @Key("deposit_percentage") String depositPercentage;
    @Key("deposit_status") InvoiceDepositStatus depositStatus;
    @Key String description;
    @Key("discount_description") String discountDescription;
    @Key("discount_total") Money discountTotal;
    @Key("discount_value") String discountValue;
    @Key("display_status") InvoiceDisplayStatus displayStatus;
    @Key("due_date") String dueDate;
    @Key("due_offset_days") int dueOffsetDays;
    @Key("estimateid") Long estimateId;
    @Key String fname;
    @Key("generation_date") String generationDate;
    @Key("gmail") boolean groundMail;
    @Key("invoice_number") String invoiceNumber;
    @Key String language;
    @Key("last_order_status") InvoiceOrderStatus lastOrderStatus;
    @Key String lname;
    @Key String notes;
    @Key String organization;
    @Key Money outstanding;
    @Key("ownerid") Long ownerId;
    @Key Money paid;
    @Key Long parent;
    @Key("payment_status") InvoicePaymentStatus paymentStatus;
    @Key("po_number") String PONumber;
    @Key String province;
    @Key("sentid") Long sentId;
    @Key("show_attachments") boolean showAttachments;
    @Key String street;
    @Key String street2;
    @Key Integer status;
    @Key String terms;
    @Key String updated;
    @Key("use_default_presentation") Boolean useDefaultPresentation;
    @Key("v3_status") InvoiceV3Status v3Status;
    @Key("vat_name") String VATName;
    @Key("vat_number") String VATNumber;
    @Key("vis_state") int visState;

    // Includes
    @Key List<LineItem> lines;
    @Key InvoicePresentation presentation;

    /**
     * Get the unique identifier of this invoice within this business.
     *
     * @return The id of the invoice
     */
    public long getId() {
        return id;
    }

    /**
     * Get unique to this business id for invoice.
     *
     * <i>Note:</i> This is the same as <code>getId</code>.
     *
     * @return The id of the invoice
     */
    public long getInvoiceId() {
        return invoiceId;
    }

    /**
     * Get unique identifier of the account the invoice exists on.
     *
     * @return Accounting System Id
     */
    public String getAccountingSystemId() {
        return accountingSystemId;
    }

    /**
     * Get unique identifier of the account the invoice exists on.
     * The same as <code>getAccountingSystemId()</code>.
     *
     * @return Accounting System Id
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * First line of address on invoice.
     *
     * @return String of the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * First line of address on invoice.
     *
     * @param address String of the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Total amount of invoice
     *
     * @return Object containing amount and currency code.
     */
    public Money getAmount() {
        return amount;
    }

    /**
     * Whether this invoice has a credit card saved.
     *
     * @return boolean if invoice has autobill enabled.
     */
    public boolean isAutoBill() {
        return autoBill;
    }

    /**
     * Whether this invoice has a credit card saved.
     * <br><br>
     * <i>Note:</i> This is only writeable on creation.
     *
     * @param autoBill
     */
    public void setAutoBill(boolean autoBill) {
        this.autoBill = autoBill;
    }

    /**
     * One of retry, failed, or success.
     *
     * @return
     */
    public InvoiceAutoBillStatus getAutoBillStatus() {
        return autoBillStatus;
    }

    /**
     * One of retry, failed, or success.
     * <br><br>
     * <i>Note:</i> This is only writeable on creation.
     *
     * @param autoBillStatus
     */
    public void setAutoBillStatus(InvoiceAutoBillStatus autoBillStatus) {
        this.autoBillStatus = autoBillStatus;
    }

    /**
     * City for address on invoice.
     *
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     * City for address on invoice.
     *
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Postal/ZIP code for address on invoice.
     *
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * Postal/ZIP code for address on invoice.
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Country for address on invoice.
     *
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     * Country for address on invoice.
     *
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Get the id of the client the invoice is for.
     * <br><br>
     * <i>Note:</i> The API request/response uses <code>customerid</code> rather than
     * <code>clientid</code>, so this method is identical to <code>getCustomerId()</code>.
     *
     * @return Id of client
     */
    public long getClientId() {
        return customerId;
    }

    /**
     * Set the id of the client the invoice is for.
     * <br><br>
     * <i>Note:</i> The API request/response uses <code>customerid</code> rather than
     * <code>clientid</code>, so this method is identical to <code>setCustomerId()</code>.
     *
     * @param clientId Id of client
     */
    public void setClientId(long clientId) {
        this.customerId = clientId;
    }

    /**
     * Date the invoice was created.
     * <br>
     * The API returns this in YYYY-MM-DD format. It is converted to a LocalDate.
     *
     * @return converted LocalDate object
     */
    public LocalDate getCreateDate() {
        return LocalDate.parse(createDate);
    }

    /**
     * Date the invoice was created.
     * <br>
     * This is sent to the API in YYYY-MM-DD format.
     *
     * @param createDate
     */
    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate.format(Util.getStandardDateFormatter());
    }

    /**
     * Get the time the invoice was created.
     * <br><br>
     * <i>Note:</i> The API returns this data in "US/Eastern", but it is converted here to UTC.
     *
     * @return Created time in UTC
     */
    public ZonedDateTime getCreatedAt() {
        return Util.getZonedDateTimeFromAccountingLocalTime(this.createdAt);
    }

    /**
     * Three-letter currency code for invoice.
     *
     * @return
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Three-letter currency code for invoice.
     *
     * @param currencyCode
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * The current name of the client's organization. Once an invoice is set, the organization
     * will not reflect any changes to the client but the current organization is available.
     *
     * @return The client organization
     */
    public String getCurrentOrganization() {
        return currentOrganization;
    }

    /**
     * Get the id of the client the invoice is for.
     * <br><br>
     * <i>Note:</i> The API request/response uses <code>customerid</code> rather than
     * <code>clientid</code>, so this method is identical to <code>getClientId()</code>.
     *
     * @return Id of client
     */
    public long getCustomerId() {
        return customerId;
    }

    /**
     * Set the id of the client the invoice is for.
     * <br><br>
     * <i>Note:</i> The API request/response uses <code>customerid</code> rather than
     * <code>clientid</code>, so this method is identical to <code>setClientId()</code>.
     *
     * @param customerId Id of client
     */
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    /**
     * Date invoice was fully paid.
     *
     * The API returns this in YYYY-MM-DD format.
     *
     * @return date object
     */
    public LocalDate getDatePaid() {
        return LocalDate.parse(datePaid);
    }

    /**
     * Amount required as deposit if required.
     *
     * @return Money object of required deposit
     */
    public Money getDepositAmount() {
        return depositAmount;
    }

    /**
     * Amount required as deposit if required. Can be set directly or calculated
     * by the invoice <code>depositPercentage</code>.
     *
     * @param depositAmount Money object of required deposit
     */
    public void setDepositAmount(Money depositAmount) {
        this.depositAmount = depositAmount;
    }

    /**
     * Description of deposits applied to invoice.
     *
     * @return
     */
    public InvoiceDepositStatus getDepositStatus() {
        return depositStatus;
    }

    /**
     * Description of deposits applied to invoice.
     * <br><br>
     * <i>Note:</i> This is only writeable on creation.
     *
     * @param depositStatus
     */
    public void setDepositStatus(InvoiceDepositStatus depositStatus) {
        this.depositStatus = depositStatus;
    }

    /**
     * Percent of the invoice's value required as a deposit.
     *
     * @return BigDecimal of percentage
     */
    public BigDecimal getDepositPercentage() {
        return new BigDecimal(depositPercentage);
    }

    /**
     * Percent of the invoice's value required as a deposit.
     * If set, invoice will have a <code>depositAmount</code> calculated.
     *
     * @param depositPercentage BigDecimal of percentage
     */
    public void setDepositPercentage(BigDecimal depositPercentage) {
        this.depositPercentage = depositPercentage.toString();
    }

    /**
     * Description of first line of invoice
     *
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Client-visible note about discount.
     *
     * @return
     */
    public String getDiscountDescription() {
        return discountDescription;
    }

    /**
     * Client-visible note about discount.
     *
     * @param discountDescription
     */
    public void setDiscountDescription(String discountDescription) {
        this.discountDescription = discountDescription;
    }

    /**
     * Amount outstanding on invoice.
     *
     * @return Object containing amount and currency code.
     */
    public Money getDiscountTotal() {
        return discountTotal;
    }

    /**
     * Percent amount being discounted from the subtotal.
     *
     * @return BigDecimal of percentage
     */
    public BigDecimal getDiscountValue() {
        return new BigDecimal(discountValue);
    }

    /**
     * Percent amount being discounted from the subtotal, decimal amount ranging from 0 to 100.
     *
     * @param discountValue BigDecimal of percentage
     */
    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue.toString();
    }

    /**
     * Description of status. Used primarily for the FreshBooks UI.
     *
     * @return InvoiceDisplayStatus value
     */
    public InvoiceDisplayStatus getDisplayStatus() {
        return displayStatus;
    }

    /**
     * Description of status. Used primarily for the FreshBooks UI.
     *
     * <br><br>
     * <i>Note:</i> This is only writeable on creation.
     *
     * @param displayStatus
     */
    public void setDisplayStatus(InvoiceDisplayStatus displayStatus) {
        this.displayStatus = displayStatus;
    }

    /**
     * Date invoice is marked as due by calculated from <code>due_offset_days</code>.
     * If <code>due_offset_days</code> is not set, it will default to the date of issue.
     *
     * The API returns this in YYYY-MM-DD format.
     *
     * @return date object
     */
    public LocalDate getDueDate() {
        return LocalDate.parse(dueDate);
    }

    /**
     * Number of days from creation that invoice is due.
     *
     * @return
     */
    public int getDueOffsetDays() {
        return dueOffsetDays;
    }

    /**
     * Number of days from creation that invoice is due.
     * <br>
     * If not set, the due date will default to the date of issue.
     *
     * @param dueOffsetDays
     */
    public void setDueOffsetDays(int dueOffsetDays) {
        this.dueOffsetDays = dueOffsetDays;
    }

    /**
     * First name of client being invoiced.
     *
     * @return
     */
    public String getFirstName() {
        return fname;
    }

    /**
     * First name of client being invoiced.
     *
     * @param fname
     */
    public void setFirstName(String fname) {
        this.fname = fname;
    }

    /**
     * Id of associated estimate, 0 if none.
     *
     * @return Id of estimate
     */
    public long getEstimateId() {
        return estimateId;
    }

    /**
     * Id of associated estimate
     *
     * @param estimateId Id of estimate
     */
    public void setEstimateId(long estimateId) {
        this.estimateId = estimateId;
    }

    /**
     * The date the invoice was generated from a invoice profile or
     * null if it was not generated.
     * <br>
     * The API returns this in YYYY-MM-DD format.
     *
     * @return date object
     */
    public LocalDate getGenerationDate() {
        if (!generationDate.equals("")) {
            return LocalDate.parse(generationDate);
        }
        return null;
    }

    /**
     * The date the invoice was generated from a invoice profile or
     * null if it was not generated.
     *
     * @param generationDate The date
     */
    public void setGenerationDate(LocalDate generationDate) {
        this.generationDate = generationDate.format(Util.getStandardDateFormatter());
    }

    /**
     * Whether to send via ground mail.
     *
     * @return
     */
    public boolean isGroundMail() {
        return groundMail;
    }

    /**
     * The user-specified number that appears on the invoice.
     *
     * @return The invoice number
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * The user-specified number that appears on the invoice.
     *
     * @param invoiceNumber The invoice number
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * Two-letter language code, e.g. "en".
     *
     * @return
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Two-letter language code, e.g. "en".
     *
     * @param language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Describes status of last attempted payment.
     *
     * @return
     */
    public InvoiceOrderStatus getLastOrderStatus() {
        return lastOrderStatus;
    }

    /**
     * Describes status of last attempted payment.
     * <br><br>
     * <i>Note:</i> This is only writeable on creation.
     *
     * @param lastOrderStatus
     */
    public void setLastOrderStatus(InvoiceOrderStatus lastOrderStatus) {
        this.lastOrderStatus = lastOrderStatus;
    }

    /**
     * Last name of client being invoiced.
     *
     * @return
     */
    public String getLastName() {
        return lname;
    }

    /**
     * Last name of client being invoiced.
     *
     * @param lname
     */
    public void setLastName(String lname) {
        this.lname = lname;
    }

    /**
     * Lines of the invoice.
     * <br><br>
     * <i>Note:</i> These are only returned with a invoice call using a "lines" include.
     * <br>
     * Eg.
     * <pre>{@code
     *     IncludesQueryBuilder inc = new IncludesQueryBuilder().include("lines");
     *
     *     Invoice invoiceResponse = invoices.get(accountId, invoiceId, inc);
     *
     *     ArrayList<QueryBuilder> builders = new ArrayList<QueryBuilder>();
     *     builders.add(inc);
     *     InvoiceList invoiceListResponse = invoices.list(accountId, builders);
     * }</pre>
     * @return
     */
    public List<LineItem> getLines() {
        return lines;
    }

    /**
     * Lines of the invoice.
     *
     * @param lines
     */
    public void setLines(List<LineItem> lines) {
        this.lines = lines;
    }

    /**
     * Notes listed on invoice.
     *
     * @return
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Notes listed on invoice.
     *
     * @param notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Name of organization being invoiced. This is the value of the organization of the client
     * but is denormalized so that changes to the client are not reflected on past invoices.
     *
     * @return The client organization
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * Name of organization being invoiced. This is the value of the organization of the client
     * but is denormalized so that changes to the client are not reflected on past invoices.
     *
     * @param organization The client organization
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * Amount outstanding on invoice.
     *
     * @return Object containing amount and currency code.
     */
    public Money getOutstanding() {
        return outstanding;
    }

    /**
     * Id of creator of invoice. 1 if business admin, other if created by another user.
     * Eg. a contractor.
     *
     * @return User id from system
     */
    public long getOwnerId() {
        return ownerId;
    }

    /**
     * User id of creator of invoice. Typically 1 for the business admin.
     * <br><br>
     * <i>Note:</i> This is only writeable on creation.
     *
     * @param ownerId User id
     */
    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Amount paid on invoice.
     *
     * @return Object containing amount and currency code.
     */
    public Money getPaid() {
        return paid;
    }

    /**
     * Id of object this invoice was generated from, 0 if none
     *
     * @return Id of parent resource
     */
    public long getParent() {
        return parent;
    }

    /**
     * Id of object this invoice was generated from, 0 if none
     * <br><br>
     * <i>Note:</i> This is only writeable on creation.
     *
     * @param parent Id of the parent resource
     */
    public void setParent(long parent) {
        this.parent = parent;
    }

    /**
     * Description of payment status.
     * One of 'unpaid', 'partial', 'paid', and 'auto-paid'.
     * <br>See the v3_status table on this page for descriptions of each.
     *
     * @return
     */
    public InvoicePaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Description of payment status.
     * One of 'unpaid', 'partial', 'paid', and 'auto-paid'.
     * <br>See the v3_status table on this page for descriptions of each.
     * <br><br>
     * <i>Note:</i> This is only writeable on creation.
     *
     * @param paymentStatus
     */
    public void setPaymentStatus(InvoicePaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * Reference number for address on invoice.
     *
     * @return
     */
    public String getPONumber() {
        return PONumber;
    }

    /**
     * Reference number for address on invoice.
     *
     * @param PONumber
     */
    public void setPONumber(String PONumber) {
        this.PONumber = PONumber;
    }

    /**
     * Define invoice logo and styles.
     * <br>
     * By default, if no presentation specified in a new invoice request payload,
     * it will be assigned a default presentation. To override this default behaviour,
     * set <code>useDefaultPresentation</code> to false.
     * <br><br>
     * <i>Note:</i> The presentation details are only returned with a invoice call
     * using a "presentation" include.
     * <br>
     * <pre>{@code
     *     IncludesQueryBuilder inc = new IncludesQueryBuilder().include("presentation");
     *
     *     Invoice invoiceResponse = invoices.get(accountId, invoiceId, inc);
     *
     *     ArrayList<QueryBuilder> builders = new ArrayList<QueryBuilder>();
     *     builders.add(inc);
     *     InvoiceList invoiceListResponse = invoices.list(accountId, builders);
     * }</pre>
     *
     * @return
     */
    public InvoicePresentation getPresentation() {
        return presentation;
    }

    /**
     * Define invoice logo and styles.
     * <br>
     * By default, if no presentation specified in a new invoice request payload,
     * it will be assigned a default presentation. To override this default behaviour,
     * set <code>useDefaultPresentation</code> to false.
     * <br><br>
     * <i>Note:</i> The presentation details are only returned with a invoice call
     * using a "presentation" include.
     * <br>
     * <pre>{@code
     *     IncludesQueryBuilder inc = new IncludesQueryBuilder().include("presentation");
     *
     *     Invoice invoiceResponse = invoices.get(accountId, invoiceId, inc);
     *
     *     ArrayList<QueryBuilder> builders = new ArrayList<QueryBuilder>();
     *     builders.add(inc);
     *     InvoiceList invoiceListResponse = invoices.list(accountId, builders);
     * }</pre>
     *
     * @param presentation
     */
    public void setPresentation(InvoicePresentation presentation) {
        this.presentation = presentation;
    }

    /**
     * Province/state for address on invoice.
     *
     * @return
     */
    public String getProvince() {
        return province;
    }

    /**
     * Province/state for address on invoice.
     *
     * @param province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * User id of user who sent the invoice. Typically 1 for the business admin.
     *
     * @return User id from system
     */
    public long getSentId() {
        return sentId;
    }

    /**
     * User id of user who sent the invoice. Typically 1 for the business admin.
     * <br><br>
     * <i>Note:</i> This is only writeable on creation.
     *
     * @param sentId User id
     */
    public void setSentId(long sentId) {
        this.sentId = sentId;
    }

    /**
     * Whether attachments on invoice are rendered in FreshBooks UI.
     *
     * @return
     */
    public boolean isShowAttachments() {
        return showAttachments;
    }

    /**
     * Whether attachments on invoice are rendered in FreshBooks UI.
     *
     * @param showAttachments
     */
    public void setShowAttachments(boolean showAttachments) {
        this.showAttachments = showAttachments;
    }

    /**
     * Street for address on invoice.
     *
     * @return
     */
    public String getStreet() {
        return street;
    }

    /**
     * Street for address on invoice.
     *
     * @param street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Second line of street for address on invoice.
     *
     * @return
     */
    public String getStreet2() {
        return street2;
    }

    /**
     * Second line of street for address on invoice.
     *
     * @param street2
     */
    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    /**
     * Get the status of the invoice.
     *
     * @return Enum of the invoice status.
     */
    public InvoiceStatus getStatus() {
        return InvoiceStatus.valueOf(this.status);
    }

    /**
     * Set the status of the invoice. This is only writable at creation and cannot
     * be manually set later.
     *
     * @param status InvoiceStatus value
     */
    public void setStatus(InvoiceStatus status) {
        this.status = status.getValue();
    }

    /**
     * Terms listed on invoice.
     *
     * @return
     */
    public String getTerms() {
        return terms;
    }

    /**
     * Terms listed on invoice.
     *
     * @param terms
     */
    public void setTerms(String terms) {
        this.terms = terms;
    }

    /**
     * Get the time of last modification to the invoice.
     * <br><br>
     * <i>Note:</i> The API returns this data in "US/Eastern", but it is converted here to UTC.
     *
     * @return Updated time in UTC
     */
    public ZonedDateTime getUpdated() {
        return Util.getZonedDateTimeFromAccountingLocalTime(this.updated);
    }

    /**
     * By default, if no presentation specified in new invoice request payload,
     * it will be assigned a default presentation.
     * <br>
     * To override this default behaviour, set useDefaultPresentation to false.
     *
     * @return
     */
    public boolean isUseDefaultPresentation() {
        return useDefaultPresentation;
    }

    /**
     * By default, if no presentation specified in new invoice request payload,
     * it will be assigned a default presentation.
     * <br>
     * To override this default behaviour, set useDefaultPresentation to false.
     *
     * @param useDefaultPresentation
     */
    public void setUseDefaultPresentation(boolean useDefaultPresentation) {
        this.useDefaultPresentation = useDefaultPresentation;
    }

    /**
     * v3 status fields give a descriptive name to states which can be used in filters
     *
     * @return InvoiceV3Status value
     */
    public InvoiceV3Status getV3Status() {
        return v3Status;
    }

    /**
     * v3 status fields give a descriptive name to states which can be used in filters
     * <br><br>
     * <i>Note:</i> This is only writeable on creation.
     *
     * @param v3Status
     */
    public void setV3Status(InvoiceV3Status v3Status) {
        this.v3Status = v3Status;
    }

    /**
     * Value Added Tax name of client if provided.
     *
     * @return
     */
    public String getVATName() {
        return VATName;
    }

    /**
     * Value Added Tax name of client.
     *
     * @param VATName
     */
    public void setVATName(String VATName) {
        this.VATName = VATName;
    }

    /**
     * Value Added Tax number of client if provided.
     *
     * @return
     */
    public String getVATNumber() {
        return VATNumber;
    }

    /**
     * Value Added Tax number of client.
     *
     * @param VATNumber
     */
    public void setVATNumber(String VATNumber) {
        this.VATNumber = VATNumber;
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
     * Set the visibility state of the invoice.
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
        if (this.id == null) {
            Util.convertContent(content, "ownerid", this.ownerId);
            Util.convertContent(content, "estimateid", this.estimateId);
            Util.convertContent(content, "sentid", this.sentId);
            Util.convertContent(content, "status", this.status);
            Util.convertContent(content, "display_status", this.displayStatus);
            Util.convertContent(content, "autobill_status", this.autoBillStatus);
            Util.convertContent(content, "payment_status", this.paymentStatus);
            Util.convertContent(content, "last_order_status", this.lastOrderStatus);
            Util.convertContent(content, "deposit_status", this.depositStatus);
            Util.convertContent(content, "auto_bill", this.autoBill);
            Util.convertContent(content, "v3_status", this.v3Status);
        }
        Util.convertContent(content, "invoice_number", this.invoiceNumber);
        Util.convertContent(content, "customerid", this.customerId);
        Util.convertContent(content, "create_date", this.createDate);
        Util.convertContent(content, "generation_date", this.generationDate);
        Util.convertContent(content, "discount_value", this.discountValue);
        Util.convertContent(content, "discount_description", this.discountDescription);
        Util.convertContent(content, "po_number", this.PONumber);
        Util.convertContent(content, "currency_code", this.currencyCode);
        Util.convertContent(content, "language", this.language);
        Util.convertContent(content, "terms", this.terms);
        Util.convertContent(content, "notes", this.notes);
        Util.convertContent(content, "address", this.address);
        Util.convertContent(content, "deposit_amount", this.depositAmount);
        Util.convertContent(content, "deposit_percentage", this.depositPercentage);
        Util.convertContent(content, "show_attachments", this.showAttachments);
        Util.convertContent(content, "street", this.street);
        Util.convertContent(content, "street2", this.street2);
        Util.convertContent(content, "city", this.city);
        Util.convertContent(content, "province", this.province);
        Util.convertContent(content, "code", this.code);
        Util.convertContent(content, "country", this.country);
        Util.convertContent(content, "organization", this.organization);
        Util.convertContent(content, "fname", this.fname);
        Util.convertContent(content, "lname", this.lname);
        Util.convertContent(content, "vat_name", this.VATName);
        Util.convertContent(content, "vat_number", this.VATNumber);
        Util.convertContent(content, "due_offset_days", this.dueOffsetDays);
        Util.convertContent(content, "use_default_presentation", this.useDefaultPresentation);

        // Includes and sub-resources
        Util.convertContent(content, "lines", this.lines);
        Util.convertContent(content, "presentation", this.presentation);

        return content;
    }


    /**
     * Invoice Presentations are used to style an invoice including font, colors, and logos.
     *
     * By default, when a new invoice is created, it automatically uses the presentation style
     * of the most recently created invoice (including logos, colors, and fonts). If you wish
     * to change the style of an invoice via the API, or if the user has not styled their
     * invoices (such as in a brand new account), then you can pass a <code>presentation</code>
     * object as part of the invoice call. Subsequent invoices will use this style until an
     * invoice is created with a new style. If you do not wish for an invoice to use any styles,
     * you can include <code>use_default_presentation: false</code> in the invoice call.
     */
    public static class InvoicePresentation extends GenericJson {
        @Key("date_format") String dateFormat;
        @Key("image_banner_src") String imageBannerSrc;
        @Key("image_logo_src") String imageLogoSrc;
        @Key("theme_font_name") String themeFontName;
        @Key("theme_layout") String themeLayout;
        @Key("theme_primary_color") String themePrimaryColor;

        /**
         * Override business date format for this invoice.
         *
         * @return string format of: “mm/dd/yyyy”, “dd/mm/yyyy”, or “yyyy-mm-dd”
         */
        public String getDateFormat() {
            return dateFormat;
        }

        /**
         * Override business date format for this invoice.
         *
         * @param dateFormat string format of: “mm/dd/yyyy”, “dd/mm/yyyy”, or “yyyy-mm-dd”
         */
        public void setDateFormat(String dateFormat) {
            this.dateFormat = dateFormat;
        }

        /**
         * The invoice banner image for "modern" invoices.
         *
         * @return “/uploads/images/JWT_TOKEN_FROM_IMAGE_UPLOAD”
         */
        public String getImageBannerSrc() {
            return imageBannerSrc;
        }

        /**
         * The invoice banner image for "modern" invoices.
         *
         * @param imageBannerSrc “/uploads/images/JWT_TOKEN_FROM_IMAGE_UPLOAD”
         */
        public void setImageBannerSrc(String imageBannerSrc) {
            this.imageBannerSrc = imageBannerSrc;
        }

        /**
         * The invoice logo image.
         *
         * @return “/uploads/images/JWT_TOKEN_FROM_IMAGE_UPLOAD”
         */
        public String getImageLogoSrc() {
            return imageLogoSrc;
        }

        /**
         * The invoice logo image.
         *
         * @param imageLogoSrc “/uploads/images/JWT_TOKEN_FROM_IMAGE_UPLOAD”
         */
        public void setImageLogoSrc(String imageLogoSrc) {
            this.imageLogoSrc = imageLogoSrc;
        }

        /**
         * Which invoice font is in use.
         *
         * @return “modern” or “classic”
         */
        public String getThemeFontName() {
            return themeFontName;
        }

        /**
         * Which invoice font to use.
         *
         * @param themeFontName “modern” or “classic”
         */
        public void setThemeFontName(String themeFontName) {
            this.themeFontName = themeFontName;
        }

        /**
         * Which invoice template is in use.
         *
         * @return “simple, “modern”, or “classic”
         */
        public String getThemeLayout() {
            return themeLayout;
        }

        /**
         * Which invoice template to use.
         *
         * @param themeLayout “simple, “modern”, or “classic”
         */
        public void setThemeLayout(String themeLayout) {
            this.themeLayout = themeLayout;
        }

        /**
         * Primary highlight colour for the invoice.
         *
         * @return eg. “#345beb”
         */
        public String getThemePrimaryColor() {
            return themePrimaryColor;
        }

        /**
         * Primary highlight colour for the invoice.
         *
         * @param themePrimaryColor eg. “#345beb”
         */
        public void setThemePrimaryColor(String themePrimaryColor) {
            this.themePrimaryColor = themePrimaryColor;
        }

        public Map<String, Object> getContent() {
            Map<String, Object> content = new HashMap<>();
            Util.convertContent(content, "date_format", this.dateFormat);
            Util.convertContent(content, "image_banner_src", this.imageLogoSrc);
            Util.convertContent(content, "image_logo_src", this.imageLogoSrc);
            Util.convertContent(content, "theme_font_name", this.themeFontName);
            Util.convertContent(content, "theme_layout", this.themeLayout);
            Util.convertContent(content, "theme_primary_color", this.themePrimaryColor);
            return content;
        }
    }

    public enum InvoiceAutoBillStatus {
        @Value("failed") FAILED,
        @Value("retry") RETRY,
        @Value("success") SUCCESS,
        @NullValue NONE;
    }

    /**
     * Description of deposits applied to invoice.
     */
    public enum InvoiceDepositStatus {
        @Value("paid") PAID,
        @Value("unpaid") UNPAID,
        @Value("partial") PARTIAL,
        @Value("converted") CONVERTED,
        @Value("none") NONE;
    }

    /**
     * Description of status. Used primarily for the FreshBooks UI.
     *
     * <br>
     * Values are:
     * <ul>
     * <li><b>DRAFT</b>
     * <li><b>CREATED</b>
     * <li><b>SENT</b>
     * <li><b>VIEWED</b>
     * <li><b>OUTSTANDING</b>
     * </ul>
     *
     * @see <a href="https://www.freshbooks.com/api/invoices">FreshBooks API - Invoices</a>
     */
    public enum InvoiceDisplayStatus {
        @Value("draft") DRAFT,
        @Value("created") CREATED,
        @Value("sent") SENT,
        @Value("viewed") VIEWED,
        @Value("outstanding") OUTSTANDING;
    }

    public enum InvoiceOrderStatus {
        @Value("pending") PENDING,
        @Value("declined") DECLINED,
        @Value("success") SUCCESS,
        @NullValue NONE;
    }

    /**
     * Description of payment status.
     * <br>
     * Values are:
     * <ul>
     * <li><b>UNPAID</b>
     * <li><b>PARTIAL</b>
     * <li><b>PAID</b>
     * <li><b>AUTO_PAID</b>
     * </ul>
     *
     * @see <a href="https://www.freshbooks.com/api/invoices">FreshBooks API - Invoices</a>
     */
    public enum InvoicePaymentStatus {
        @Value("unpaid") UNPAID,
        @Value("partial") PARTIAL,
        @Value("paid") PAID,
        @Value("auto-paid") AUTO_PAID;
    }

    /**
     * Status values for an invoice.
     *
     * <br>
     * Values are:
     * <ul>
     * <li><b>DISPUTED</b>: An Invoice with the Dispute option enabled, which has been disputed by a Client.
     * This is a feature of FreshBooks Classic only and should only appear in migrated accounts.</li>
     * <li><b>DRAFT</b>: An Invoice that has been created, but not yet sent.</li>
     * <li><b>SENT</b>: An Invoice that has been sent to a Client or marked as sent.</li>
     * <li><b>VIEWED</b>: An Invoice that has been viewed by a Client.</li>
     * <li><b>PAID</b>: A fully paid Invoice.</li>
     * <li><b>AUTOPAID</b>: An Invoice paid automatically with a saved credit card.</li>
     * <li><b>RETRY</b>: An Invoice that would normally be paid automatically, but encountered a processing
     * issue, either due to a bad card or a service outage. It will be retried 1 day later.</li>
     * <li><b>FAILED</b>: An Invoice that was in Retry status which again encountered a processing
     * issue when being retried.</li>
     * <li><b>PARTIAL</b>: An Invoice that has been partially paid.</li>
     * </ul>
     *
     * @see <a href="https://www.freshbooks.com/api/invoices">FreshBooks API - Invoices</a>
     */
    public enum InvoiceStatus {
        @Value DISPUTED(0),
        @Value DRAFT(1),
        @Value SENT(2),
        @Value VIEWED(3),
        @Value PAID(4),
        @Value AUTOPAID(5),
        @Value RETRY(6),
        @Value FAILED(7),
        @Value PARTIAL(8);

        private final int value;
        private static final Map<Integer, InvoiceStatus> map = new HashMap<>();

        InvoiceStatus(int value) {
            this.value = value;
        }

        static {
            for (InvoiceStatus status : InvoiceStatus.values()) {
                map.put(status.value, status);
            }
        }

        public static InvoiceStatus valueOf(int invoiceStatus) {
            return map.get(invoiceStatus);
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * v3 status fields give a descriptive name to states which can be used in filters.
     * <br>
     * Values are:
     * <ul>
     * <li><b>CREATED</b>: Invoice is created and in no other state</li>
     * <li><b>DRAFT</b>: Invoice is saved in draft status</li>
     * <li><b>SENT</b>: Invoice has been sent</li>
     * <li><b>VIEWED</b>: Invoice has been viewed by recipient</li>
     * <li><b>FAILED</b>: An autobill related to the invoice has been tried more than once and failed</li>
     * <li><b>RETRY</b>: An autobill related to the invoice has been tried once and failed, and will be retried</li>
     * <li><b>SUCCESS</b>: An autobill related to the invoice has succeeded</li>
     * <li><b>AUTOPAID</b>: A payment has been tied to the invoice automatically via autobill</li>
     * <li><b>PAID</b>: Payments related to the invoice have succeeded and the object is fully paid</li>
     * <li><b>PARTIAL</b>: Some payment related to the invoice has succeeded but the invoice is not yet paid off</li>
     * <li><b>DISPUTED</b>: The invoice is disputed</li>
     * <li><b>RESOLVED</b>: The invoice was disputed and the dispute has been marked as resolved</li>
     * <li><b>OVERDUE</b>: The invoice required an action at an earlier date that was not met</li>
     * <li><b>DEPOSIT_PARTIAL</b>: The invoice has a related deposit which has been partially paid</li>
     * <li><b>DEPOSIT_PAID</b>: The invoice has a related deposit which has been fully paid</li>
     * <li><b>DECLINED</b>: The invoice has a related order which has been declined</li>
     * <li><b>PENDING</b>: The invoice has a related order which is pending</li>
     * </ul>
     *
     * @see <a href="https://www.freshbooks.com/api/invoices">FreshBooks API - Invoices</a>
     */
    public enum InvoiceV3Status {
        @Value("created") CREATED,
        @Value("draft") DRAFT,
        @Value("sent") SENT,
        @Value("viewed") VIEWED,
        @Value("failed") FAILED,
        @Value("retry") RETRY,
        @Value("success") SUCCESS,
        @Value("autopaid") AUTOPAID,
        @Value("paid") PAID,
        @Value("partial") PARTIAL,
        @Value("disputed") DISPUTED,
        @Value("resolved") RESOLVED,
        @Value("overdue") OVERDUE,
        @Value("deposit-partial") DEPOSIT_PARTIAL,
        @Value("deposit-paid") DEPOSIT_PAID,
        @Value("declined") DECLINED,
        @Value("pending") PENDING;
    }
}
