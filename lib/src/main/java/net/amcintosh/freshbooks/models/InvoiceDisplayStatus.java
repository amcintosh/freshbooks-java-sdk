package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Value;

/**
 * Description of status. Used primarily for the FreshBooks UI.
 *
 * <br>
 * Values are:
 * <ul>
 * <li><b>DRAFT</b>
 * <li><b>CREATED</b>
 * <li><b>SENT<b>
 * <li><b>VIEWED<b>
 * <li><b>OUTSTANDING<b>
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
