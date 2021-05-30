package net.amcintosh.freshbooks.models;

import com.google.api.client.util.NullValue;
import com.google.api.client.util.Value;

public enum InvoiceAutoBillStatus {
    @Value("failed") FAILED,
    @Value("retry") RETRY,
    @Value("success") SUCCESS,
    @NullValue NONE;
}
