package net.amcintosh.freshbooks.models;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.google.api.client.util.Value;
import net.amcintosh.freshbooks.Util;
import net.amcintosh.freshbooks.models.api.ConvertibleContent;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class Expense extends GenericJson implements ConvertibleContent {

    @Key("account_name") String accountName;
    @Key("accountid") Long accountId;
    @Key("accounting_systemid") String accountingSystemId;
    @Key Money amount;
    @Key("bank_name") String bankName;
    //"bill_matches": [],
    @Key boolean billable;
    @Key("categoryid") Long categoryId;
    @Key("clientid") Long clientId;
    @Key String date;
    @Key("expenseid") Long expenseId;
    @Key("ext_accountid") String extAccountId;
    @Key("ext_invoiceid") Long extInvoiceId;
    @Key("ext_systemid") Long extSystemId;
    @Key("from_bulk_import") boolean fromBulkImport;
    @Key("has_receipt") boolean hasReceipt;
    @Key Long id;
    @Key("include_receipt") boolean includeReceipt;
    @Key("invoiceid") Long invoiceId;
    @Key("is_cogs") boolean isCogs;
    @Key("isduplicate") boolean isDuplicate;
    @Key("markup_percent") String markupPercent;
    @Key("modern_projectid") Long modernProjectId;
    @Key String notes;
    @Key("potential_bill_payment") boolean potentialBillPayment;
    @Key("profileid") Long profileId;
    @Key("projectid") Long projectId;
    @Key("staffid") Long staffId;
    @Key int status;
    @Key Money taxAmount1;
    @Key Money taxAmount2;
    @Key String taxName1;
    @Key String taxName2;
    @Key String taxPercent1;
    @Key String taxPercent2;
    @Key("transactionid") Long transactionId;
    @Key String updated;
    @Key String vendor;
    @Key("vis_state") int visState;

    // Includes
    @Key ExpenseAttachment attachment;
    //@Key("expense_profile") ExpenseProfile expenseProfile;

    /**
     * Get the unique identifier of this expense within this business.
     *
     * @return The id of the expense
     */
    public long getId() {
        return id;
    }

    /**
     * Get unique to this business id for expense.
     *
     * Note: This is the same as <code>getId</code>.
     *
     * @return The id of the expense
     */
    public long getExpenseId() {
        return expenseId;
    }

    /**
     * Name of related account if applicable.
     *
     * @return
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Name of related account if applicable.
     *
     * @param accountName
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * Id of expense account if applicable.
     *
     * @return
     */
    public long getAccountId() {
        return accountId;
    }

    /**
     * Id of expense account if applicable.
     *
     * @param accountId
     */
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    /**
     * Get unique identifier of business the expense exists on.
     *
     * @return Accounting System Id
     */
    public String getAccountingSystemId() {
        return accountingSystemId;
    }

    /**
     * Total amount of the expense.
     *
     * @return
     */
    public Money getAmount() {
        return amount;
    }

    /**
     * Total amount of the expense.
     *
     * @param amount
     */
    public void setAmount(Money amount) {
        this.amount = amount;
    }

    /**
     * Name of bank expense was imported from, if applicable.
     *
     * @return
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * Name of bank expense was imported from, if applicable.
     *
     * @param bankName
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * Can the expense be billed to a Client or Project.
     *
     * @return
     */
    public boolean isBillable() {
        return billable;
    }

    /**
     * Can the expense be billed to a Client or Project.
     *
     * @param billable
     */
    public void setBillable(boolean billable) {
        this.billable = billable;
    }

    /**
     * The id of related expense category.
     *
     * @return Id of the category
     */
    public long getCategoryId() {
        return categoryId;
    }

    /**
     * Set the id of the expense category.
     *
     * @param categoryId Id of the category
     */
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Id of client the expense has been assigned to if applicable.
     *
     * @return Client Id
     */
    public long getClientId() {
        return clientId;
    }

    /**
     * Id of client to assign the expense to.
     *
     * @param clientId Client Id
     */
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    /**
     * Date of the expense.
     * <br>
     * The API returns this in YYYY-MM-DD format. It is converted to a LocalDate.
     *
     * @return converted LocalDate object
     */
    public LocalDate getDate() {
        return LocalDate.parse(date);
    }

    /**
     * Date of the expense.
     * <br>
     * This is sent to the API in YYYY-MM-DD format.
     *
     * @param date
     */
    public void setDate(LocalDate date) {
        this.date = date.format(Util.getStandardDateFormatter());
    }

    /**
     * Get the id of related contractor account if applicable.
     *
     * @return
     */
    public String getExtAccountId() {
        return extAccountId;
    }

    /**
     * Set the id of related contractor account if applicable.
     *
     * @param extAccountId
     */
    public void setExtAccountId(String extAccountId) {
        this.extAccountId = extAccountId;
    }

    /**
     * Get the id of related contractor invoice if applicable.
     *
     * @return
     */
    public long getExtInvoiceId() {
        return extInvoiceId;
    }

    /**
     * Set the id of related contractor invoice if applicable.
     *
     * @param extInvoiceId
     */
    public void setExtInvoiceId(long extInvoiceId) {
        this.extInvoiceId = extInvoiceId;
    }

    /**
     * Get the id of related contractor system if applicable.
     *
     * @return
     */
    public long getExtSystemId() {
        return extSystemId;
    }

    /**
     * Set the id of related contractor system if applicable.
     *
     * @param extSystemId
     */
    public void setExtSystemId(long extSystemId) {
        this.extSystemId = extSystemId;
    }

    /**
     * Indicates if the expense was created via a bulk import action.
     *
     * @return
     */
    public boolean isFromBulkImport() {
        return fromBulkImport;
    }

    /**
     * Indicates if the expense has an attached receipt.
     *
     * @return
     */
    public boolean isHasReceipt() {
        return hasReceipt;
    }

    /**
     * Indicates if the expense has an attached receipt.
     *
     * @param hasReceipt
     */
    public void setHasReceipt(boolean hasReceipt) {
        this.hasReceipt = hasReceipt;
    }

    public boolean isIncludeReceipt() {
        return includeReceipt;
    }

    public void setIncludeReceipt(boolean includeReceipt) {
        this.includeReceipt = includeReceipt;
    }

    /**
     * Get the id of related invoice if applicable.
     * @return
     */
    public long getInvoiceId() {
        return invoiceId;
    }

    /**
     * Set the id of related invoice if applicable.
     *
     * @param invoiceId
     */
    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * Get if the expense counts as "Cost of Goods Sold" and is associated
     * with a client.
     *
     * @return
     */
    public boolean isCogs() {
        return isCogs;
    }

    /**
     * Set if the expense counts as "Cost of Goods Sold" and can be associated
     * with a client.
     *
     * @param cogs
     */
    public void setCogs(boolean cogs) {
        isCogs = cogs;
    }

    /**
     * If the expense is a duplicated expense.
     * @return
     */
    public boolean isDuplicate() {
        return isDuplicate;
    }

    /**
     * If the expense is a duplicated expense.
     *
     * @param duplicate
     */
    public void setDuplicate(boolean duplicate) {
        isDuplicate = duplicate;
    }

    /**
     * Note of percent to mark expense up.
     *
     * @return String-decimal percentage
     */
    public String getMarkupPercent() {
        return markupPercent;
    }

    /**
     * Note of percent to mark expense up.
     * @param markupPercent String-decimal percentage
     */
    public void setMarkupPercent(String markupPercent) {
        this.markupPercent = markupPercent;
    }

    /**
     * Id of related new FreshBooks (not Classic) project if applicable.
     *
     * @return Project Id
     */
    public long getModernProjectId() {
        return modernProjectId;
    }

    /**
     * Set the Id of a new FreshBooks (not Classic) project to associate the
     * expense with the project.
     *
     * @param modernProjectId Project Id
     */
    public void setModernProjectId(long modernProjectId) {
        this.modernProjectId = modernProjectId;
    }

    /**
     * Get notes about the expense.
     *
     * @return
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Set notes about the expense.
     *
     * @param notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isPotentialBillPayment() {
        return potentialBillPayment;
    }

    public void setPotentialBillPayment(boolean potentialBillPayment) {
        this.potentialBillPayment = potentialBillPayment;
    }

    /**
     * Id of related recurring expense profile if applicable.
     *
     * @return
     */
    public long getProfileId() {
        return profileId;
    }

    /**
     * Id of related FreshBooks Classic project if applicable.
     *
     * @return Project Id
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Set the Id of a FreshBooks Classic project to associate the expense with
     * the project.
     *
     * @param projectId Classic Project Id
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Get the id of related staff member if applicable.
     *
     * @return
     */
    public long getStaffId() {
        return staffId;
    }

    /**
     * Set the id of related staff member if applicable.
     *
     * @param staffId
     */
    public void setStaffId(long staffId) {
        this.staffId = staffId;
    }

    /**
     * Get the status of the expense.
     *
     * @return Enum of the expense status.
     */
    public ExpenseStatus getStatus() {
        return ExpenseStatus.valueOf(this.status);
    }

    /**
     * Set the status of the expense. This is only writable at creation and cannot
     * be manually set later.
     *
     * @param status ExpenseStatus value
     */
    public void setStatus(ExpenseStatus status) {
        this.status = status.getValue();
    }

    /**
     * Get the amount of the first tax making up the total amount of the expense.
     *
     * @return Object containing amount and currency code.
     */
    public Money getTaxAmount1() {
        return taxAmount1;
    }

    /**
     * Set the amount of the first tax making up the total amount of the expense.
     *
     * @param taxAmount Money object of required deposit
     */
    public void setTaxAmount1(Money taxAmount) {
        this.taxAmount1 = taxAmount;
    }

    /**
     * Get the amount of the second tax making up the total amount of the expense.
     *
     * @return Object containing amount and currency code.
     */
    public Money getTaxAmount2() {
        return taxAmount2;
    }

    /**
     * Set the amount of the second tax making up the total amount of the expense.
     *
     * @param taxAmount Money object of required deposit
     */
    public void setTaxAmount2(Money taxAmount) {
        this.taxAmount2 = taxAmount;
    }

    /**
     * Get the name of first tax.
     *
     * @return Eg. "HST"
     */
    public String getTaxName1() {
        return taxName1;
    }

    /**
     * Set the name of first tax.
     *
     * @param taxName Eg. "HST"
     */
    public void setTaxName1(String taxName) {
        this.taxName1 = taxName;
    }

    /**
     * Get the name of second tax.
     *
     * @return Eg. "HST"
     */
    public String getTaxName2() {
        return taxName2;
    }

    /**
     * Set the name of second tax.
     *
     * @param taxName Eg. "HST"
     */
    public void setTaxName2(String taxName) {
        this.taxName2 = taxName;
    }

    /**
     * Get the string-decimal tax amount for first tax – indicates the maximum tax percentage
     * for this expense.
     *
     * @return
     */
    public String getTaxPercent1() {
        return taxPercent1;
    }

    /**
     * Set the string-decimal tax amount for first tax – indicates the maximum tax percentage
     * for this expense, this does not add tax to the expense, instead use taxAmount1.
     *
     * @param taxPercent1
     */
    public void setTaxPercent1(String taxPercent1) {
        this.taxPercent1 = taxPercent1;
    }

    /**
     * Get the string-decimal tax amount for second tax – indicates the maximum tax percentage
     * for this expense.
     *
     * @return
     */
    public String getTaxPercent2() {
        return taxPercent2;
    }

    /**
     * Set the string-decimal tax amount for second tax – indicates the maximum tax percentage
     * for this expense, this does not add tax to the expense, instead use taxAmount2.
     *
     * @param taxPercent2
     */
    public void setTaxPercent2(String taxPercent2) {
        this.taxPercent2 = taxPercent2;
    }

    /**
     * Get the id of related bank imported transaction if applicable.
     *
     * @return
     */
    public long getTransactionId() {
        return transactionId;
    }

    /**
     * Set the id of related bank imported transaction if applicable.
     *
     * @param transactionId
     */
    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
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
     * Get the name of the vendor.
     *
     * @return Eg. "Home Depot"
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * Get the name of the vendor.
     *
     * @param vendor Eg. "Home Depot"
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
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
     * Set the visibility state of the expense.
     * <br><br>
     * @see <a href="https://www.freshbooks.com/api/active_deleted">FreshBooks API - Active and Deleted Objects</a>
     *
     * @param visState VisState value
     */
    public void setVisState(VisState visState) {
        this.visState = visState.getValue();
    }

    /**
     * Get the attached receipt image details.
     * <br><br>
     * <i>Note:</i> This data is not in the default response and will only be
     * present with the use of a corresponding "includes" filter.
     *
     * @return
     */
    public ExpenseAttachment getAttachment() {
        return attachment;
    }

    /**
     * Set the details for an attached receipt.
     *
     * @param attachment
     */
    public void setAttachment(ExpenseAttachment attachment) {
        this.attachment = attachment;
    }

    @Override
    public Map<String, Object> getContent() {
        Map<String, Object> content = new HashMap<>();
        if (this.id == null) {
            Util.convertContent(content, "status", this.status);
        }
        Util.convertContent(content, "account_name", this.accountName);
        Util.convertContent(content, "accountid", this.accountId);
        Util.convertContent(content, "amount", this.amount);
        Util.convertContent(content, "bank_name", this.bankName);
        Util.convertContent(content, "billable", this.billable);
        Util.convertContent(content, "categoryid", this.categoryId);
        Util.convertContent(content, "clientid", this.clientId);
        Util.convertContent(content, "ext_accountid", this.extAccountId);
        Util.convertContent(content, "ext_invoiceid", this.extInvoiceId);
        Util.convertContent(content, "ext_systemid", this.extSystemId);
        Util.convertContent(content, "has_receipt", this.hasReceipt);
        Util.convertContent(content, "include_receipt", this.includeReceipt);
        Util.convertContent(content, "invoiceid", this.invoiceId);
        Util.convertContent(content, "is_cogs", this.isCogs);
        Util.convertContent(content, "isduplicate", this.isDuplicate);
        Util.convertContent(content, "markup_percent", this.markupPercent);
        Util.convertContent(content, "modern_projectid", this.modernProjectId);
        Util.convertContent(content, "notes", this.notes);
        Util.convertContent(content, "projectid", this.projectId);
        Util.convertContent(content, "staffid", this.staffId);
        Util.convertContent(content, "status", this.status);
        Util.convertContent(content, "taxAmount1", this.taxAmount1);
        Util.convertContent(content, "taxAmount2", this.taxAmount2);
        Util.convertContent(content, "taxName1", this.taxName1);
        Util.convertContent(content, "taxName2", this.taxName2);
        Util.convertContent(content, "taxPercent1", this.taxPercent1);
        Util.convertContent(content, "taxPercent2", this.taxPercent2);
        Util.convertContent(content, "transactionid", this.transactionId);
        Util.convertContent(content, "vendor", this.vendor);

        // Includes and sub-resources
        Util.convertContent(content, "attachment", this.attachment);

        return content;
    }

    public enum ExpenseStatus {
        @Value INTERNAL(0), // internal rather than client
        @Value OUTSTANDING(1), // has client, needs to be applied to an invoice
        @Value INVOICED(2), // has client, attached to an invoice
        @Value RECOUPED(4); // has client, attached to an invoice, and paid

        private final int value;
        private static final Map<Integer, Expense.ExpenseStatus> map = new HashMap<>();

        ExpenseStatus(int value) {
            this.value = value;
        }

        static {
            for (Expense.ExpenseStatus status : Expense.ExpenseStatus.values()) {
                map.put(status.value, status);
            }
        }

        public static Expense.ExpenseStatus valueOf(int expenseStatus) {
            return map.get(expenseStatus);
        }

        public int getValue() {
            return value;
        }
    }

    public static class ExpenseAttachment {
        @Key("attachmentid") Long attachmentId;
        @Key Long id;
        @Key String jwt;
        @Key("media_type") String mediaType;

        public long getAttachmentId() {
            return attachmentId;
        }

        public long getId() {
            return id;
        }

        public String getJwt() {
            return jwt;
        }

        public void setJwt(String jwt) {
            this.jwt = jwt;
        }

        public String getMediaType() {
            return mediaType;
        }

        public void setMediaType(String mediaType) {
            this.mediaType = mediaType;
        }
    }

    // TODO: Add expense profiles
    //public static class ExpenseProfile extends GenericJson {}
}
