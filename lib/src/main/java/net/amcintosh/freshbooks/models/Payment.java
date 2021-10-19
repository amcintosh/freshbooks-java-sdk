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

/**
 * Payments are a record of the payments made on invoices.
 *
 * @see <a href="https://www.freshbooks.com/api/payments">FreshBooks API - Payments</a>
 */
public class Payment extends GenericJson implements ConvertibleContent {

    @Key Long id;
    @Key("logid") Long logId;
    @Key("accounting_systemid") String accountingSystemId;
    @Key Money amount;
    @Key("bulk_paymentid") Long bulkPaymentId;
    @Key("clientid") Long clientId;
    @Key("creditid") Long creditId;
    @Key String date;
    @Key("from_credit") boolean fromCredit;
    @Key String gateway;
    @Key("invoiceid") Long invoiceId;
    @Key String note;
    @Key("orderid") Long orderId;
    @Key("overpaymentid") Long overpaymentId;
    @Key("send_client_notification") Boolean sendClientNotification;
    @Key PaymentType type;
    @Key String updated;
    @Key("vis_state") int visState;

    /**
     * The unique id (across this business) for the payment.
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * The unique id (across this business) for the payment.
     * <br><br>
     * Notes:
     * <ul>
     * <li>This is the same as <code>getId</code>.</li>
     * <li>The API parameter is <code>logId</code> because internally
     * this resource is called "payment log", but this is a terrible name
     * from an API perspective, so we'll hide it with paymentId here.</li>
     * </ul>
     * @return
     */
    public long getPaymentId() {
        return logId;
    }

    /**
     * Get unique identifier of business the payment exists on.
     *
     * @return Accounting System Id
     */
    public String getAccountingSystemId() {
        return accountingSystemId;
    }

    /**
     * The amount of the payment.
     *
     * @return
     */
    public Money getAmount() {
        return amount;
    }

    /**
     * The amount of the payment.
     *
     * @param amount
     */
    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public long getBulkPaymentId() {
        return bulkPaymentId;
    }

    public void setBulkPaymentId(long bulkPaymentId) {
        this.bulkPaymentId = bulkPaymentId;
    }

    /**
     * Id of client who made the payment.
     *
     * @return
     */
    public long getClientId() {
        return clientId;
    }

    /**
     * The id of a related credit resource.
     *
     * @return
     */
    public long getCreditId() {
        return creditId;
    }

    /**
     * The id of a related Credit resource.
     *
     * @param creditId
     */
    public void setCreditId(long creditId) {
        this.creditId = creditId;
    }

    /**
     * Date the payment was made.
     * <br>
     * The API returns this in YYYY-MM-DD format. It is converted to a LocalDate.
     *
     * @return converted LocalDate object
     */
    public LocalDate getDate() {
        return LocalDate.parse(date);
    }

    /**
     * Date the payment was made.
     * <br>
     * This is sent to the API in YYYY-MM-DD format.
     *
     * @param date
     */
    public void setDate(LocalDate date) {
        this.date = date.format(Util.getStandardDateFormatter());
    }

    /**
     * If the payment was converted from a Credit on a Client's account.
     *
     * @return
     */
    public boolean isFromCredit() {
        return fromCredit;
    }

    /**
     * If the payment was converted from a Credit on a Client's account.
     *
     * @param fromCredit
     */
    public void setFromCredit(boolean fromCredit) {
        this.fromCredit = fromCredit;
    }

    /**
     * The payment processor (gateway) used to make the payment, if any.
     *
     * @return Eg. "Stripe"
     */
    public String getGateway() {
        return gateway;
    }

    /**
     * The id of a related Invoice resource.
     *
     * @return
     */
    public long getInvoiceId() {
        return invoiceId;
    }

    /**
     * The id of a related Invoice resource.
     *
     * @param invoiceId
     */
    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * Notes on payment, often used for credit card reference number.
     *
     * @return
     */
    public String getNote() {
        return note;
    }

    /**
     * Notes on payment, often used for credit card reference number.
     * <br><br>
     * <b>Do not store actual credit card numbers here.</b>
     * @param note "Ref #ABC123"
     */
    public void setNote(String note) {
        this.note = note;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    /**
     * Id of related overpayment Credit if relevant.
     *
     * @return
     */
    public long getOverpaymentId() {
        return overpaymentId;
    }

    /**
     * Whether to send the client a notification of this payment.
     *
     * @return
     */
    public boolean isSendClientNotification() {
        return sendClientNotification;
    }

    /**
     * Whether to send the client a notification of this payment.
     *
     * @param sendClientNotification
     */
    public void setSendClientNotification(boolean sendClientNotification) {
        this.sendClientNotification = sendClientNotification;
    }

    /**
     * Type of payment made.
     *
     * @return Eg. “Check”, “Credit”, “Cash”
     */
    public PaymentType getType() {
        return type;
    }

    /**
     * Type of payment made;
     *
     * @param type Eg. “Check”, “Credit”, “Cash”
     */
    public void setType(PaymentType type) {
        this.type = type;
    }

    /**
     * Get the time of last modification to the payment.
     * <br><br>
     * <i>Note:</i> The API returns this data in "US/Eastern", but it is converted here to UTC.
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

    @Override
    public Map<String, Object> getContent() {
        Map<String, Object> content = new HashMap<>();
        Util.convertContent(content, "amount", this.amount);
        Util.convertContent(content, "bulk_paymentid", this.bulkPaymentId);
        Util.convertContent(content, "creditid", this.creditId);
        Util.convertContent(content, "date", this.date);
        Util.convertContent(content, "from_credit", this.fromCredit);
        Util.convertContent(content, "invoiceid", this.invoiceId);
        Util.convertContent(content, "note", this.note);
        Util.convertContent(content, "orderid", this.orderId);
        Util.convertContent(content, "overpaymentid", this.overpaymentId);
        Util.convertContent(content, "send_client_notification", this.sendClientNotification);
        Util.convertContent(content, "type", this.type);

        return content;
    }

    public enum PaymentType {
        @Value("2Checkout") TWOCHECKOUT,
        @Value("Ach") ACH,
        @Value("AMEX") AMEX,
        @Value("Bank Transfer") BANK_TRANSFER,
        @Value("Cash") CASH,
        @Value("Check") CHECK,
        @Value("Credit") CREDIT,
        @Value("Credit Card") CREDIT_CARD,
        @Value("Debit") DEBIT,
        @Value("Diners") DINERS,
        @Value("Discover") DISCOVER,
        @Value("Eurocard") EUROCARD,
        @Value("JCB") JCB,
        @Value("MasterCard") MASTERCARD,
        @Value("Nova") NOVA,
        @Value("Other") OTHER,
        @Value("PayPal") PAYPAL,
        @Value("Visa") VISA;
    }
}
