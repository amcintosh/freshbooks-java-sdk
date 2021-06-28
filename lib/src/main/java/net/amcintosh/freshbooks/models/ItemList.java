package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.api.AccountingListResponse;

import java.util.ArrayList;

/**
 * Results of items list call containing list of items and pagination data.
 */
public class ItemList extends ListResult {

    @Key private final ArrayList<Item> items;

    public ItemList(AccountingListResponse.AccountingListInnerResponse.AccountingListResult result) {
        items = result.items;
        this.pages = new Pages(result.page, result.pages, result.perPage, result.total);
    }

    /**
     * Array of items returned by the `list` call.
     *
     * @return Arraylist of Item objects
     */
    public ArrayList<Item> getItems() {
        return items;
    }
}
