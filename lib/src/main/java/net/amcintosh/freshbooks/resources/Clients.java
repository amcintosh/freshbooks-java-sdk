package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.*;
import net.amcintosh.freshbooks.resources.responses.AccountingListResponse;
import net.amcintosh.freshbooks.resources.responses.AccountingRequest;
import net.amcintosh.freshbooks.resources.responses.AccountingResponse;

public class Clients extends AccountingResource {

    public Clients(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    @Override
    protected String getPath() {
        return "users/clients";
    }

    public ClientList list(String accountId) throws FreshBooksException {
        String url = this.getUrl(accountId);
        AccountingListResponse result = this.handleListRequest(url);
        ClientList results = new ClientList(result.response.result);
        return results;
    }

    public Client get(String accountId, long clientId) throws FreshBooksException {
        String url = this.getUrl(accountId, clientId);
        AccountingResponse result = this.handleRequest(HttpMethods.GET, url);
        return result.response.result.client;
    }

    public Client create(String accountId, Client data) throws FreshBooksException {
        String url = this.getUrl(accountId);
        AccountingRequest content = new AccountingRequest();
        content.client = data;
        AccountingResponse result = this.handleRequest(HttpMethods.POST, url, content);
        return result.response.result.client;
    }
}
