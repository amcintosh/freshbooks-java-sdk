package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Value;

import java.util.HashMap;
import java.util.Map;

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
