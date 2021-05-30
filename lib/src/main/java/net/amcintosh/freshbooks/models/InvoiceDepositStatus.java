package net.amcintosh.freshbooks.models;

import com.google.api.client.util.NullValue;
import com.google.api.client.util.Value;

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
