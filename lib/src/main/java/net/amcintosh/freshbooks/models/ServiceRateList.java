package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.api.ProjectListResponse;

import java.util.List;

/**
 * Results of service rates list call containing list of service rates.
 * This endpoint is not paginated so there is no 'pages' attribute.
 */
public class ServiceRateList {

    @Key
    private final List<ServiceRate> serviceRates;

    public ServiceRateList(ProjectListResponse result) {
        serviceRates = result.serviceRates;
    }

    /**
     * Array of service rates returned by the `list` call.
     *
     * @return List of ServiceRate objects
     */
    public List<ServiceRate> getServiceRates() {
        return serviceRates;
    }
}
