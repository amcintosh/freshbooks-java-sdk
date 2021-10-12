package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.common.collect.ImmutableMap;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.ServiceRate;
import net.amcintosh.freshbooks.models.ServiceRateList;
import net.amcintosh.freshbooks.models.api.ProjectListResponse;
import net.amcintosh.freshbooks.models.api.ProjectResponse;
import net.amcintosh.freshbooks.resources.api.CommentSubResource;

import java.util.Map;

/**
 * FreshBooks service rates sub-resource with calls to get, list, create, update
 */
public class ServiceRates extends CommentSubResource {

    public ServiceRates(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    @Override
    protected String getPathForParent() {
        return "service";
    }

    @Override
    protected String getPathForSingle() {
        return "rate";
    }

    @Override
    protected String getPathForList() {
        return "service_rates";
    }

    /**
     * Get a list of service rates for this business.
     *
     * @param businessId Id of the business
     * @return ServiceRateList containing service rates
     * @throws FreshBooksException If the call is not successful
     */
    public ServiceRateList list(long businessId) throws FreshBooksException {
        String url = this.getUrl(businessId);
        ProjectListResponse result = this.handleListRequest(url);
        return new ServiceRateList(result);
    }

    /**
     * Get a single service rate from the service with the corresponding id.
     *
     * @param businessId Id of the business
     * @param serviceId Id of the resource to return
     *
     * @return The ServiceRate
     * @throws FreshBooksException If the call is not successful
     */
    public ServiceRate get(long businessId, long serviceId) throws FreshBooksException {
        String url = this.getUrl(businessId, serviceId);
        ProjectResponse result = this.handleRequest(HttpMethods.GET, url);
        return result.serviceRate;
    }

    /**
     * Create a new service rate from the provided ServiceRate model.
     * Makes a POST call against the service rates resource endpoint.
     *
     * This calls `serviceRate.getContent()` to get a hash map of data.
     *
     * @param businessId Id of the business
     * @param serviceId Id of the service to set the rate for
     * @param data ServiceRate model with create data
     *
     * @return The created ServiceRate
     * @throws FreshBooksException If the call is not successful
     */
    public ServiceRate create(long businessId, long serviceId, ServiceRate data) throws FreshBooksException {
        return this.create(businessId, serviceId, data.getContent());
    }

    /**
     * Create a new service rate from the provided data.
     * Makes a POST call against the service rates resource endpoint.
     *
     * @param businessId Id of the business
     * @param serviceId Id of the service to set the rate for
     * @param data Map of create data
     *
     * @return The created ServiceRate
     * @throws FreshBooksException If the call is not successful
     */
    public ServiceRate create(long businessId, long serviceId, Map<String, Object> data) throws FreshBooksException {
        String url = this.getUrl(businessId, serviceId);
        ImmutableMap<String, Object> content = ImmutableMap.of("service_rate", data);
        ProjectResponse result = this.handleRequest(HttpMethods.POST, url, content);
        return result.serviceRate;
    }

    /**
     * Update the service rate with the corresponding id.
     * Makes a PUT call against the service rate resource endpoint.
     *
     * @param businessId Id of the business
     * @param serviceId Id of the service to set the rate for
     * @param data ServiceRate model with updated data
     *
     * @return The updated ServiceRate
     * @throws FreshBooksException If the call is not successful
     */
    public ServiceRate update(long businessId, long serviceId, ServiceRate data) throws FreshBooksException {
        return this.update(businessId, serviceId, data.getContent());
    }

    /**
     * Update the service rate with the corresponding id.
     * Makes a PUT call against the service rate resource endpoint.
     *
     * @param businessId Id of the business
     * @param serviceId Id of the service to set the rate for
     * @param data Map of data to change
     *
     * @return The updated ServiceRate
     * @throws FreshBooksException If the call is not successful
     */
    public ServiceRate update(long businessId, long serviceId, Map<String, Object> data) throws FreshBooksException {
        String url = this.getUrl(businessId, serviceId);
        ImmutableMap<String, Object> content = ImmutableMap.of("service_rate", data);
        ProjectResponse result = this.handleRequest(HttpMethods.PUT, url, content);
        return result.serviceRate;
    }
}
