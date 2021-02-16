package net.amcintosh.freshbooks.resources.api;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpStatusCodes;
import com.google.common.collect.ImmutableList;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.api.ProjectListResponse;
import net.amcintosh.freshbooks.models.api.ProjectResponse;
import net.amcintosh.freshbooks.models.builders.IncludesQueryBuilder;
import net.amcintosh.freshbooks.models.builders.QueryBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Handles resources under the `/project` and project-like endpoints.
 */
public abstract class ProjectResource extends Resource {

    public ProjectResource(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    @Override
    protected ResourceType getResourceType() {
        return ResourceType.PROJECT_LIKE;
    }

    protected abstract String getPathForSingle();
    protected abstract String getPathForList();

    protected String getUrl(long businessId, boolean isList) {
        return this.getUrl(businessId, isList, null);
    }

    protected String getUrl(long businessId, boolean isList,  List<QueryBuilder> builders) {
        String path = isList ? getPathForList() : getPathForSingle();
        String queryString = "";
        if (builders != null) {
            queryString = this.buildQueryString(builders);
        }
        return String.format("/projects/business/%s/%s%s", businessId, path, queryString);
    }

    protected String getUrl(long businessId, long resourceId) {
        return this.getUrl(businessId, resourceId, null);
    }

    protected String getUrl(long businessId, long resourceId, IncludesQueryBuilder builder) {
        String queryString = "";
        if (builder != null) {
            queryString = this.buildQueryString(ImmutableList.of(builder));
        }
        return String.format("/projects/business/%s/%s/%s%s", businessId, this.getPathForSingle(), resourceId, queryString);
    }

    protected ProjectResponse handleRequest(String method, String url) throws FreshBooksException {
        return this.handleRequest(method, url, null);
    }


    protected ProjectResponse handleRequest(String method, String url, Map<String, Object> content) throws FreshBooksException {
        HttpResponse response = null;
        ProjectResponse model = null;
        int statusCode = 0;
        String statusMessage = null;

        try {
            HttpRequest request = this.freshBooksClient.request(method, url, content);
            response = request.execute();
            statusCode = response.getStatusCode();
            statusMessage = response.getStatusMessage();

            if (response.getStatusCode() == HttpStatusCodes.STATUS_CODE_NO_CONTENT) {
                return null;
            }
            if (response.getContent() != null) {
                model = response.parseAs(ProjectResponse.class);
            }
        } catch (IOException | IllegalArgumentException e) {
            if (response != null && response.getStatusCode() == HttpStatusCodes.STATUS_CODE_NOT_FOUND) {
                throw new FreshBooksException("Requested resource could not be found.", statusMessage, statusCode);
            }
            throw new FreshBooksException("Returned an unexpected response", statusMessage, statusCode, e);
        }

        if (!response.isSuccessStatusCode() && model != null) {
            String errorMessage = statusMessage;
            if (model.error != null) {
                errorMessage = model.error.title;
            }
            throw new FreshBooksException(errorMessage, statusMessage, statusCode, model.errno);
        }

        if (response.isSuccessStatusCode() && model != null) {
            return model;
        }

        throw new FreshBooksException("Returned an unexpected response", statusMessage, statusCode);
    }

    protected ProjectListResponse handleListRequest(String url) throws FreshBooksException {
        HttpResponse response;
        ProjectListResponse model = null;
        int statusCode = 0;
        String statusMessage = null;

        try {
            HttpRequest request = this.freshBooksClient.request(HttpMethods.GET, url);
            response = request.execute();
            statusCode = response.getStatusCode();
            statusMessage = response.getStatusMessage();

            if (response.getContent() != null) {
                model = response.parseAs(ProjectListResponse.class);
            }
        } catch (IOException e) {
            throw new FreshBooksException("Returned an unexpected response", statusMessage, statusCode, e);
        }

        if (!response.isSuccessStatusCode() && model != null) {
            String errorMessage = statusMessage;
            if (model.error != null) {
                errorMessage = model.error;
            }
            throw new FreshBooksException(errorMessage, statusMessage, statusCode);
        }

        if (response.isSuccessStatusCode() && model != null) {
            return model;
        }

        throw new FreshBooksException("Returned an unexpected response", statusMessage, statusCode);
    }
}
