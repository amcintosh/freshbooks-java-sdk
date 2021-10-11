package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.api.ProjectListResponse;

import java.util.List;

/**
 * Results of services list call containing list of services and pagination data.
 */
public class ServiceList extends ListResult {

    @Key
    private final List<Service> services;

    public ServiceList(ProjectListResponse result) {
        services = result.services;
        this.pages = new Pages(result.meta.page, result.meta.pages, result.meta.perPage, result.meta.total);
    }

    /**
     * Array of services returned by the `list` call.
     *
     * @return List of Service objects
     */
    public List<Service> getServices() {
        return services;
    }
}
