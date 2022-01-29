package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.api.AccountingListResponse;

import java.util.ArrayList;

/**
 * Results of invoices list call containing list of invoices and pagination data.
 */
public class InvoiceList extends ListResult {

    @Key private final ArrayList<Invoice> invoices;

    public InvoiceList(AccountingListResponse.AccountingListInnerResponse.AccountingListResult result) {
        invoices = result.invoices;
        this.pages = new Pages(result.page, result.pages, result.perPage, result.total);
    }

    /**
     * Array of invoices returned by the <code>list</code> call.
     *
     * @return Arraylist of Invoice objects
     */
    public ArrayList<Invoice> getInvoices() {
        return invoices;
    }
}
