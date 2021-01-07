package net.amcintosh.freshbooks.resources;

import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.AccountingModel;
import net.amcintosh.freshbooks.models.Client;

public class Clients extends AccountingResource {

    public Clients(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    public Client get(String accountId, long clientId) throws FreshBooksException {
        AccountingModel result = this.getRequest(accountId, clientId);
        return result.response.result.client;
    }
}
