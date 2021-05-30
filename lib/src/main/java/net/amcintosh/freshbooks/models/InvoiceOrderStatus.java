package net.amcintosh.freshbooks.models;

import com.google.api.client.util.NullValue;
import com.google.api.client.util.Value;

public enum InvoiceOrderStatus {
    @Value("pending") PENDING,
    @Value("declined") DECLINED,
    @Value("success") SUCCESS,
    @NullValue NONE;
}
