package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Value;

/**
 * Types of projects
 */
public enum ProjectType {
    @Value("fixed_price") FIXED_PRICE,
    @Value("hourly_rate") HOURLY_RATE;
}
