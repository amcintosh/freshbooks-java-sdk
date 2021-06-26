package net.amcintosh.freshbooks.resources.api;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.api.AuthErrorResponse;

import java.io.IOException;

/**
 * Handles resources under the <code>/auth</code> endpoints.
 */
public class AuthResource extends Resource {

    /**
     * @param freshBooksClient Initialized instance of FreshBooksClient
     */
    public AuthResource(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    @Override
    protected ResourceType getResourceType() {
        return ResourceType.AUTH;
    }

    protected String getUrl(String endpoint) {
        return String.format("/auth/api/v1/%s", endpoint);
    }

    protected HttpResponse handleRequest(String method, String url) throws FreshBooksException {
        HttpResponse response;
        int statusCode = 0;
        String statusMessage = null;

        try {
            HttpRequest request = this.freshBooksClient.request(method, url);
            response = request.execute();
            statusCode = response.getStatusCode();
            statusMessage = response.getStatusMessage();

            if (response.getContent() != null) {
                if (response.isSuccessStatusCode()) {
                    return response;
                }
                AuthErrorResponse error = response.parseAs(AuthErrorResponse.class);
                if (error != null && error.error != null) {
                    throw new FreshBooksException(error.errorDescription, response.getStatusMessage(), response.getStatusCode());
                }
            }
            throw new FreshBooksException("Returned an unexpected response", statusMessage, statusCode);
        } catch (IOException | IllegalArgumentException e) {
            throw new FreshBooksException("Returned an unexpected response", statusMessage, statusCode, e);
        }
    }
}
