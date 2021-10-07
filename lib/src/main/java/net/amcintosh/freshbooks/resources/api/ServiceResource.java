package net.amcintosh.freshbooks.resources.api;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.common.collect.ImmutableList;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.api.ServiceListResponse;
import net.amcintosh.freshbooks.models.api.ServiceResponse;
import net.amcintosh.freshbooks.models.builders.IncludesQueryBuilder;
import net.amcintosh.freshbooks.models.builders.QueryBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Handles resources under the <code>/comments/business/[businessId]/service</code> endpoints.
 */
public abstract class ServiceResource extends Resource {
    /**
     *
     * @param freshBooksClient Initialized instance of FreshBooksClient
     */
    public ServiceResource(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    @Override
    protected ResourceType getResourceType() {
        return ResourceType.PROJECT_LIKE;
    }

    protected String getUrl(long businessId) {
        return this.getUrl(businessId, null);
    }

    protected String getUrl(long businessId, List<QueryBuilder> builders) {
        String queryString = "";
        if (builders != null) {
            queryString = this.buildQueryString(builders);
        }
        return String.format("/comments/business/%s/service%s", businessId, queryString);
    }

    protected String getUrl(long businessId, long serviceId) {
        return this.getUrl(businessId, serviceId, null);
    }

    protected String getUrl(long businessId, long serviceId, IncludesQueryBuilder builder) {
        String queryString = "";
        if (builder != null) {
            queryString = this.buildQueryString(ImmutableList.of(builder));
        }
        return String.format("/comments/business/%s/service/%s%s", businessId, serviceId, queryString);
    }

    protected String getListUrl(long businessId) {
        return this.getListUrl(businessId, null);
    }

    protected String getListUrl(long businessId, List<QueryBuilder> builders) {
        String queryString = "";
        if (builders != null) {
            queryString = this.buildQueryString(builders);
        }
        return String.format("/comments/business/%s/services%s", businessId, queryString);
    }

    protected ServiceResponse handleRequest(String method, String url) throws FreshBooksException {
        return this.handleRequest(method, url, null);
    }

    protected ServiceResponse handleRequest(String method, String url, Map<String, Object> content) throws FreshBooksException {
        HttpResponse response;
        ServiceResponse model = null;
        int statusCode = 0;
        String statusMessage = null;

        try {
            HttpRequest request = this.freshBooksClient.request(method, url, content);
            response = request.execute();
            statusMessage = response.getStatusMessage();
            statusCode = response.getStatusCode();
            if (response.getContent() != null) {
                model = response.parseAs(ServiceResponse.class);
            }
        } catch (IOException | IllegalArgumentException e) {
            throw new FreshBooksException("Returned an unexpected response", statusMessage, statusCode, e);
        }

        if (response.isSuccessStatusCode() && model != null) {
            return model;
        }

        throw new FreshBooksException("Returned an unexpected response", statusMessage, statusCode);
    }

    protected ServiceListResponse handleListRequest(String url) throws FreshBooksException {
        HttpResponse response;
        ServiceListResponse model = null;
        int statusCode = 0;
        String statusMessage = null;

        try {
            HttpRequest request = this.freshBooksClient.request(HttpMethods.GET, url);
            response = request.execute();
            statusCode = response.getStatusCode();
            statusMessage = response.getStatusMessage();

            if (response.getContent() != null) {
                model = response.parseAs(ServiceListResponse.class);
            }
        } catch (IOException e) {
            throw new FreshBooksException("Returned an unexpected response", statusMessage, statusCode, e);
        }

        if (response.isSuccessStatusCode() && model != null) {
            return model;
        }

        throw new FreshBooksException("Returned an unexpected response", statusMessage, statusCode);
    }
}
