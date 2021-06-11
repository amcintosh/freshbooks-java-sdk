package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.api.AccountingListResponse;

import java.util.ArrayList;

/**
 * Results of taxes list call containing list of taxes and pagination data.
 */
public class TaxList extends ListResult {

    @Key private final ArrayList<Tax> taxes;

    public TaxList(AccountingListResponse.AccountingListInnerResponse.AccountingListResult result) {
        taxes = result.taxes;
        this.pages = new Pages(result.page, result.pages, result.perPage, result.total);
    }

    /**
     * Array of taxes returned by the `list` call.
     *
     * @return Arraylist of Tax objects
     */
    public ArrayList<Tax> getTaxes() {
        return taxes;
    }
}
