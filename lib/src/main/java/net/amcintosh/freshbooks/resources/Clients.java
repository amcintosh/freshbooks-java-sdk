package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.Client;
import net.amcintosh.freshbooks.models.ClientList;
import net.amcintosh.freshbooks.models.VisState;
import net.amcintosh.freshbooks.resources.responses.AccountingListResponse;
import net.amcintosh.freshbooks.resources.responses.AccountingRequest;
import net.amcintosh.freshbooks.resources.responses.AccountingResponse;

/**
 * FreshBooks clients resource with calls to get, list, create, update, delete
 */
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

    /**
     * Get a single client with the corresponding id.
     *
     * @param accountId The alpha-numeric account id
     * @param clientId Id of the resource to return
     * //@param builder (Optional) IncludesBuilder object for including additional data, sub-resources, etc.
     * @return The Client
     * @throws FreshBooksException If the call is not successful
     */
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

    public Client update(String accountId, long clientId, Client data) throws FreshBooksException {
        String url = this.getUrl(accountId, clientId);
        AccountingRequest content = new AccountingRequest();
        content.client = data;
        AccountingResponse result = this.handleRequest(HttpMethods.PUT, url, content);
        return result.response.result.client;

    }

    public Client delete(String accountId, long clientId) throws FreshBooksException {
        String url = this.getUrl(accountId, clientId);
        AccountingRequest content = new AccountingRequest();
        Client deleteClient = new Client();
        deleteClient.setVisState(VisState.DELETED);
        content.client = deleteClient;
        AccountingResponse result = this.handleRequest(HttpMethods.PUT, url, content);
        return result.response.result.client;

    }

}