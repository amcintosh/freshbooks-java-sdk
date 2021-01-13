package net.amcintosh.freshbooks.resources.api;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.api.GenericRequest;
import net.amcintosh.freshbooks.models.api.ProjectResponse;

import java.io.IOException;

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

    protected ProjectResponse handleRequest(String method, String url, GenericRequest content) throws FreshBooksException {
        HttpResponse response;
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
        } catch (IOException e) {
            throw new FreshBooksException("Returned an unexpected response", statusMessage, statusCode, e);
        }

        if (!response.isSuccessStatusCode() && model != null) {
            throw new FreshBooksException(model.error.title, statusMessage, statusCode, model.errno);
        }

        if (response.isSuccessStatusCode() && model != null) {
            return model;
        }

        throw new FreshBooksException("Returned an unexpected response", statusMessage, statusCode);
    }

}
