package net.amcintosh.freshbooks.resources.api;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.api.AccountingError;
import net.amcintosh.freshbooks.models.api.AccountingListResponse;
import net.amcintosh.freshbooks.models.api.AccountingResponse;
import net.amcintosh.freshbooks.models.builders.QueryBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Handles resources under the `/accounting` endpoints.
 */
public abstract class AccountingResource extends Resource {

    /**
     *
     * @param freshBooksClient Initialized instance of FreshBooksClient
     */
    public AccountingResource(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    @Override
    protected ResourceType getResourceType() {
        return ResourceType.ACCOUNTING_LIKE;
    }

    protected abstract String getPath();

    protected String getUrl(String accountId) {
        return this.getUrl(accountId, null);
    }

    protected String getUrl(String accountId, List<QueryBuilder> builders) {
        String queryString = "";
        if (builders != null) {
            queryString = this.buildQueryString(builders);
        }
        return String.format("/accounting/account/%s/%s%s", accountId, this.getPath(), queryString);
    }

    protected String getUrl(String accountId, long resourceId) {
        return String.format("/accounting/account/%s/%s/%s", accountId, this.getPath(), resourceId);
    }

    protected AccountingResponse handleRequest(String method, String url) throws FreshBooksException {
        return this.handleRequest(method, url, null);
    }

    protected AccountingResponse handleRequest(String method, String url, Map<String, Object> content) throws FreshBooksException {
        HttpResponse response;
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
        } catch (IOException | IllegalArgumentException e) {
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
        HttpResponse response;
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
