package net.amcintosh.freshbooks.models;

import com.google.api.client.util.NullValue;
import com.google.api.client.util.Value;

/**
 * Method of calculating billing for a project
 */
public enum ProjectBillingMethod {
    @Value business_rate,
    @Value project_rate,
    @Value service_rate,
    @Value team_member_rate,
    @NullValue none
}
