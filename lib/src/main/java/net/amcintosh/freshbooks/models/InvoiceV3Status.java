package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Value;

import java.util.HashMap;
import java.util.Map;

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
