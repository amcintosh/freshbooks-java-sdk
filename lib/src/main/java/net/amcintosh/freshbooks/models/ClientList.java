package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.api.AccountingListResponse;

import java.util.ArrayList;

/**
 * Results of clients list call containing list of clients and pagination data.
 */
public class ClientList extends ListResult {

    @Key private final ArrayList<Client> clients;

    public ClientList(AccountingListResponse.AccountingListInnerResponse.AccountingListResult result) {
        clients = result.clients;
        this.pages = new Pages(result.page, result.pages, result.perPage, result.total);
    }

    /**
     * Array of clients returned by the `list` call.
     *
     * @return Arraylist of Client objects
     */
    public ArrayList<Client> getClients() {
        return clients;
    }
}
