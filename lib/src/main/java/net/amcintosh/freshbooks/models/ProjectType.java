package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Value;

public enum ProjectType {
    @Value fixed_price,
    @Value hourly_rate
}