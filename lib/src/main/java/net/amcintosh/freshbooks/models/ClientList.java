package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.resources.responses.AccountingListResponse;

import java.util.ArrayList;

public class ClientList extends ListResult {

    @Key private ArrayList<Client> clients;

    public ClientList(AccountingListResponse.AccountingListInnerResponse.AccountingListResult result) {
        clients = result.clients;
        this.pages = new Pages(result.page, result.pages, result.perPage, result.total);
    }

    public ArrayList<Client> getClients() {
        return clients;
    }
}
