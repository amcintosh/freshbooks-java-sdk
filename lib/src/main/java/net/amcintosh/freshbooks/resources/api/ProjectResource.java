package net.amcintosh.freshbooks.resources.api;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.api.ProjectListResponse;
import net.amcintosh.freshbooks.models.api.ProjectResponse;

import java.io.IOException;
import java.util.HashMap;

/**
 * Handles resources under the `/project` and project-like endpoints.
 */
public abstract class ProjectResource extends Resource {


    public ProjectResource(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    protected abstract String getPath();

    protected String getUrl(long businessId) {
        return String.format("/projects/business/%s/%s", businessId, this.getPath());
    }

    protected String getUrl(long businessId, long resourceId) {
        return String.format("/projects/business/%s/%s/%s", businessId, this.getPath(), resourceId);
    }

    protected ProjectResponse handleRequest(String method, String url) throws FreshBooksException {
        return this.handleRequest(method, url, null);
    }


    protected ProjectResponse handleRequest(String method, String url, HashMap<String, Object> content) throws FreshBooksException {
        HttpResponse response = null;
        ProjectResponse model = null;
        int statusCode = 0;
        String statusMessage = null;

        try {
            HttpRequest request = this.freshBooksClient.request(method, url, content);
            response = request.execute();
            statusCode = response.getStatusCode();
            statusMessage = response.getStatusMessage();

            if (response.getContent() != null) {
                model = response.parseAs(ProjectResponse.class);
            }
        } catch (IOException | IllegalArgumentException e) {
            if (response != null && response.getStatusCode() == 404) {
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
