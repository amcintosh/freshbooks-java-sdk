package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Value;

public enum ProjectBilledStatus {
    @Value billed,
    @Value partially_billed,
    @Value unbilled
}