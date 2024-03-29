package net.amcintosh.freshbooks.resources.api;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.common.collect.ImmutableList;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.api.AccountingError;
import net.amcintosh.freshbooks.models.api.AccountingErrorDetails;
import net.amcintosh.freshbooks.models.api.AccountingListResponse;
import net.amcintosh.freshbooks.models.api.AccountingResponse;
import net.amcintosh.freshbooks.models.builders.IncludesQueryBuilder;
import net.amcintosh.freshbooks.models.builders.QueryBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Handles resources under the <code>/accounting</code> endpoints.
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
        return this.getUrl(accountId, resourceId, null);
    }

    protected String getUrl(String accountId, long resourceId, IncludesQueryBuilder builder) {
        String queryString = "";
        if (builder != null) {
            queryString = this.buildQueryString(ImmutableList.of(builder));
        }
        return String.format("/accounting/account/%s/%s/%s%s", accountId, this.getPath(), resourceId, queryString);
    }

    protected AccountingResponse handleRequest(String method, String url) throws FreshBooksException {
        return this.handleRequest(method, url, null);
    }

    private boolean isNewAccountingError(AccountingResponse responseModel) {
        return responseModel != null && responseModel.message != null;
    }

    private boolean isOldAccountingError(AccountingResponse responseModel) {
        return responseModel != null && responseModel.response != null && responseModel.response.errors != null;
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

        if (!response.isSuccessStatusCode() && this.isOldAccountingError(model)) {
            AccountingError error = model.response.errors.get(0);
            throw new FreshBooksException(error.message, statusMessage, statusCode,
                    error.errno, error.field, error.object, error.value);
        }
        if (!response.isSuccessStatusCode() && this.isNewAccountingError(model)) {
            if (model.details.size() > 0) {
                AccountingErrorDetails error = model.details.get(0);
                if (error.type.equals("type.googleapis.com/google.rpc.ErrorInfo") && error.metadata != null) {
                    throw new FreshBooksException(error.metadata.message, statusMessage, statusCode,
                            error.getReason(), error.metadata.field, error.metadata.object, error.metadata.value);
                }
            }
            throw new FreshBooksException(model.message, statusMessage, statusCode);
        }

        if (response.isSuccessStatusCode() && method.equals(HttpMethods.DELETE)  && model != null && model.response != null ) {
            return null;
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
