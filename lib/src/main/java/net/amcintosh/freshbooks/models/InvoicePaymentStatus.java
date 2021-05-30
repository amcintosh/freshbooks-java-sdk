package net.amcintosh.freshbooks.models;


import com.google.api.client.util.Value;

/**
 * Description of payment status.
 * <br>
 * Values are:
 * <ul>
 * <li><b>UNPAID</b>
 * <li><b>PARTIAL</b>
 * <li><b>PAID<b>
 * <li><b>AUTO_PAID<b>
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
