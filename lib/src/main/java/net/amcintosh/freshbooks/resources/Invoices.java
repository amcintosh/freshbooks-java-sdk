package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.common.collect.ImmutableMap;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.Invoice;
import net.amcintosh.freshbooks.models.InvoiceList;
import net.amcintosh.freshbooks.models.api.AccountingListResponse;
import net.amcintosh.freshbooks.models.api.AccountingResponse;
import net.amcintosh.freshbooks.models.builders.IncludesQueryBuilder;
import net.amcintosh.freshbooks.models.builders.QueryBuilder;
import net.amcintosh.freshbooks.resources.api.AccountingResource;

import java.util.List;
import java.util.Map;

/**
 * FreshBooks invoices resource with calls to get, list, create, update, delete
 */
public class Invoices extends AccountingResource {

    public Invoices(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    @Override
    protected String getPath() {
        return "invoices/invoices";
    }

    /**
     * Get a list of invoices using the query strings generated by the QueryBuilder list.
     *
     * @param accountId Id of the account
     * @return InvoiceList containing invoices and pagination details
     * @throws FreshBooksException If the call is not successful
     */
    public InvoiceList list(String accountId) throws FreshBooksException {
        return this.list(accountId, null);
    }

    /**
     * Get a list of invoices using the query strings generated by the QueryBuilder list.
     *
     * @param accountId Id of the account
     * @param builders List of QueryBuilders
     * @return InvoiceList containing invoices and pagination details
     * @throws FreshBooksException If the call is not successful
     */
    public InvoiceList list(String accountId, List<QueryBuilder> builders) throws FreshBooksException {
        String url = this.getUrl(accountId, builders);
        AccountingListResponse result = this.handleListRequest(url);
        return new InvoiceList(result.response.result);
    }

    /**
     * Get a single invoice with the corresponding id.
     *
     * @param accountId The alpha-numeric account id
     * @param invoiceId Id of the resource to return
     *
     * @return The Invoice
     * @throws FreshBooksException If the call is not successful
     */
    public Invoice get(String accountId, long invoiceId) throws FreshBooksException {
        return this.get(accountId, invoiceId, null);
    }

    /**
     * Get a single invoice with the corresponding id.
     *
     * @param accountId The alpha-numeric account id
     * @param invoiceId Id of the resource to return
     * @param builder IncludesQueryBuilder object for including additional data, sub-resources, etc.
     *
     * @return The Invoice
     * @throws FreshBooksException If the call is not successful
     */
    public Invoice get(String accountId, long invoiceId, IncludesQueryBuilder builder) throws FreshBooksException {
        String url = this.getUrl(accountId, invoiceId, builder);
        AccountingResponse result = this.handleRequest(HttpMethods.GET, url);
        return result.response.result.invoice;
    }

    /**
     * Create a new invoice from the provided Invoice model.
     * Makes a POST call against the invoice resource endpoint.
     *
     * This calls `invoice.getContent()` to get a hash map of data.
     *
     * @param accountId The alpha-numeric account id
     * @param data Invoice model with create data
     *
     * @return The created Invoice
     * @throws FreshBooksException If the call is not successful
     */
    public Invoice create(String accountId, Invoice data) throws FreshBooksException {
        return this.create(accountId, data.getContent());
    }

    /**
     * Create a new invoice from the provided data.
     * Makes a POST call against the invoice resource endpoint.
     *
     * @param accountId The alpha-numeric account id
     * @param data Map of create data
     *
     * @return The created Invoice
     * @throws FreshBooksException If the call is not successful
     */
    public Invoice create(String accountId, Map<String, Object> data) throws FreshBooksException {
        String url = this.getUrl(accountId);
        ImmutableMap<String, Object> content = ImmutableMap.of("invoice", data);
        AccountingResponse result = this.handleRequest(HttpMethods.POST, url, content);
        return result.response.result.invoice;
    }

    /**
     * Update the invoice with the corresponding id.
     * Makes a PUT call against the invoice resource endpoint.
     *
     * @param accountId The alpha-numeric account id
     * @param invoiceId Id of the resource to return
     * @param data Invoice model with updated data
     *
     * @return The updated Invoice
     * @throws FreshBooksException If the call is not successful
     */
    public Invoice update(String accountId, long invoiceId, Invoice data) throws FreshBooksException {
        return this.update(accountId, invoiceId, data.getContent());
    }

    /**
     * Update the invoice with the corresponding id.
     * Makes a PUT call against the invoice resource endpoint.
     *
     * @param accountId The alpha-numeric account id
     * @param invoiceId Id of the resource to update
     * @param data Map of data to change
     *
     * @return The updated Invoice
     * @throws FreshBooksException If the call is not successful
     */
    public Invoice update(String accountId, long invoiceId, Map<String, Object> data) throws FreshBooksException {
        String url = this.getUrl(accountId, invoiceId);
        ImmutableMap<String, Object> content = ImmutableMap.of("invoice", data);
        AccountingResponse result = this.handleRequest(HttpMethods.PUT, url, content);
        return result.response.result.invoice;
    }

    /**
     * Delete the invoice with the corresponding id.
     * <br><br>
     * <i>Note:</i> Most FreshBooks resources are soft-deleted,
     * @see <a href="https://www.freshbooks.com/api/active_deleted">FreshBooks API - Active and Deleted Objects</a>
     *
     * @param accountId The alpha-numeric account id
     * @param invoiceId Id of the resource to update
     *
     * @throws FreshBooksException If the call is not successful
     */
    public void delete(String accountId, long invoiceId) throws FreshBooksException {
        String url = this.getUrl(accountId, invoiceId);
        this.handleRequest(HttpMethods.DELETE, url);
    }
}