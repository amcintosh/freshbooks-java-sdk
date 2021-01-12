package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.resources.responses.AccountingError;
import net.amcintosh.freshbooks.resources.responses.AccountingListResponse;
import net.amcintosh.freshbooks.resources.responses.AccountingRequest;
import net.amcintosh.freshbooks.resources.responses.AccountingResponse;

import java.io.IOException;

/**
 * Handles resources under the `/accounting` endpoints.
 */
public abstract class AccountingResource extends Resource {

    public AccountingResource(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    protected abstract String getPath();

    protected String getUrl(String accountId) {
        String url = String.format("/accounting/account/%s/%s", accountId, this.getPath());
        return url;
    }

    protected String getUrl(String accountId, long resourceId) {
        String url = String.format("/accounting/account/%s/%s/%s", accountId, this.getPath(), resourceId);
        return url;
    }

    protected AccountingResponse handleRequest(String method, String url) throws FreshBooksException {
        return this.handleRequest(method, url, null);
    }

    protected AccountingResponse handleRequest(String method, String url, AccountingRequest content) throws FreshBooksException {
        HttpResponse response = null;
        AccountingResponse model = null;
        int statusCode = 0;
        String statusMessage = null;

        try {
            HttpRequest request = this.freshBooksClient.request(method, url, content);
            response = request.execute();
            statusCode = response.getStatusCode();
            statusMessage = response.getStatusMessage();

            if (response.getContent() != null) {
                model = response.parseAs(AccountingResponse.class);
            }
        } catch (IOException e) {
            throw new FreshBooksException("Returned an unexpected response", statusMessage, statusCode, e);
        }

        if (!response.isSuccessStatusCode() && model != null && model.response != null && model.response.errors != null) {
            AccountingError error = model.response.errors.get(0);
            throw new FreshBooksException(error.message, statusMessage, statusCode,
                    error.errno, error.field, error.object, error.value);
        }

        if (response.isSuccessStatusCode() && model != null && model.response != null && model.response.result != null) {
            return model;
        }

        throw new FreshBooksException("Returned an unexpected response", statusMessage, statusCode);
    }

    protected AccountingListResponse handleListRequest(String url) throws FreshBooksException {
        HttpResponse response = null;
        AccountingListResponse model = null;
        int statusCode = 0;
        String statusMessage = null;

        try {
            HttpRequest request = this.freshBooksClient.request(HttpMethods.GET, url);
            response = request.execute();
            statusCode = response.getStatusCode();
            statusMessage = response.getStatusMessage();

            if (response.getContent() != null) {
                model = response.parseAs(AccountingListResponse.class);
            }
        } catch (IOException e) {
            throw new FreshBooksException("Returned an unexpected response", statusMessage, statusCode, e);
        }

        if (!response.isSuccessStatusCode() && model != null && model.response != null && model.response.errors != null) {
            AccountingError error = model.response.errors.get(0);
            throw new FreshBooksException(error.message, statusMessage, statusCode,
                    error.errno, error.field, error.object, error.value);
        }

        if (response.isSuccessStatusCode() && model != null && model.response != null && model.response.result != null) {
            return model;
        }

        throw new FreshBooksException("Returned an unexpected response", statusMessage, statusCode);

    }
}
