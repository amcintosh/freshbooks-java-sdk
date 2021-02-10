package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Value;

/**
 * Types of projects
 */
public enum ProjectType {
    @Value fixed_price,
    @Value hourly_rate
}
