package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.common.base.Optional;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.AccountingModel;

import java.io.IOException;


public class AccountingResource extends Resource {

    public AccountingResource(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    protected String getUrl(String path, String accountId, long resourceId) {
        String url = String.format("/accounting/account/%s/%s/%s", accountId, path, resourceId);
        return url;
    }

    protected AccountingModel getRequest(String accountId, long clientId) throws FreshBooksException {
        HttpResponse response = null;
        AccountingModel model = null;
        int statusCode;

        try {
            String url = this.getUrl("users/clients", accountId, clientId);
            HttpRequest request = this.freshBooksClient.request(HttpMethods.GET, url);
            response = request.execute();
            statusCode = response.getStatusCode();

            model = response.parseAs(AccountingModel.class);
        } catch (IOException e) {
            throw new FreshBooksException("Returned an unexpected response", response);
        }

        if (!response.isSuccessStatusCode() && model.response != null && model.response.errors != null) {
            throw new FreshBooksException(
                    model.response.errors.get(0).message,
                    model.response.errors.get(0).errno,
                    response);
        }

        if (response.isSuccessStatusCode() && model.response != null && model.response.result != null) {
            return model;
        }

        throw new FreshBooksException("Returned an unexpected response", response);
    }
}
