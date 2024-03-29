package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.common.collect.ImmutableMap;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.*;
import net.amcintosh.freshbooks.models.api.AccountingListResponse;
import net.amcintosh.freshbooks.models.api.AccountingResponse;
import net.amcintosh.freshbooks.models.builders.IncludesQueryBuilder;
import net.amcintosh.freshbooks.models.builders.QueryBuilder;
import net.amcintosh.freshbooks.resources.api.AccountingResource;

import java.util.List;
import java.util.Map;

/**
 * FreshBooks other incomes resource with calls to get, list, create, update, delete
 */
public class OtherIncomes extends AccountingResource {

    public OtherIncomes(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    @Override
    protected String getPath() {
        return "other_incomes/other_incomes";
    }

    /**
     * Get a list of other incomes using the query strings generated by the QueryBuilder list.
     *
     * @param accountId Id of the account
     * @return OtherIncomeList containing other incomes and pagination details
     * @throws FreshBooksException If the call is not successful
     */
    public OtherIncomeList list(String accountId) throws FreshBooksException {
        return list(accountId, null);
    }

    /**
     * Get a list of other incomes using the query strings generated by the QueryBuilder list.
     *
     * @param accountId Id of the account
     * @param builders List of QueryBuilders
     * @return OtherIncomeList containing other incomes and pagination details
     * @throws FreshBooksException If the call is not successful
     */
    public OtherIncomeList list(String accountId, List<QueryBuilder> builders) throws FreshBooksException {
        String url = this.getUrl(accountId, builders);
        AccountingListResponse result = this.handleListRequest(url);
        return new OtherIncomeList(result.response.result);
    }

    /**
     * Get a single other income with the corresponding id.
     *
     * @param accountId The alpha-numeric account id
     * @param incomeId Id of the resource to return
     *
     * @return The Other Income
     * @throws FreshBooksException If the call is not successful
     */
    public OtherIncome get(String accountId, long incomeId) throws FreshBooksException {
        return this.get(accountId, incomeId, null);
    }

    /**
     * Get a single other income with the corresponding id.
     *
     * @param accountId The alpha-numeric account id
     * @param incomeId Id of the resource to return
     * @param builder IncludesQueryBuilder object for including additional data, sub-resources, etc.
     *
     * @return The Other Income
     * @throws FreshBooksException If the call is not successful
     */
    public OtherIncome get(String accountId, long incomeId, IncludesQueryBuilder builder) throws FreshBooksException {
        String url = this.getUrl(accountId, incomeId, builder);
        AccountingResponse result = this.handleRequest(HttpMethods.GET, url);
        return result.response.result.otherIncome;
    }

    /**
     * Create a new other income from the provided OtherIncome model.
     * Makes a POST call against the other income resource endpoint.
     *
     * This calls `otherIncome.getContent()` to get a hash map of data.
     *
     * @param accountId The alpha-numeric account id
     * @param data OtherIncome model with create data
     *
     * @return The created OtherIncome
     * @throws FreshBooksException If the call is not successful
     */
    public OtherIncome create(String accountId, OtherIncome data) throws FreshBooksException {
        return create(accountId, data.getContent());
    }

    /**
     * Create a new other income from the provided data.
     * Makes a POST call against the other income resource endpoint.
     *
     * @param accountId The alpha-numeric account id
     * @param data Map of create data
     *
     * @return The created OtherIncome
     * @throws FreshBooksException If the call is not successful
     */
    public OtherIncome create(String accountId, Map<String, Object> data) throws FreshBooksException {
        String url = this.getUrl(accountId);
        ImmutableMap<String, Object> content = ImmutableMap.of("other_income", data);
        AccountingResponse result = this.handleRequest(HttpMethods.POST, url, content);
        return result.response.result.otherIncome;
    }

    /**
     * Update the other income with the corresponding id.
     * Makes a PUT call against the other income resource endpoint.
     *
     * @param accountId The alpha-numeric account id
     * @param otherIncomeId Id of the resource to return
     * @param data OtherIncome model with updated data
     *
     * @return The updated OtherIncome
     * @throws FreshBooksException If the call is not successful
     */
    public OtherIncome update(String accountId, long otherIncomeId, OtherIncome data) throws FreshBooksException {
        return update(accountId, otherIncomeId, data.getContent());
    }

    /**
     * Update the other income with the corresponding id.
     * Makes a PUT call against the other income resource endpoint.
     *
     * @param accountId The alpha-numeric account id
     * @param otherIncomeId Id of the resource to update
     * @param data Map of data to change
     *
     * @return The updated OtherIncome
     * @throws FreshBooksException If the call is not successful
     */
    public OtherIncome update(String accountId, long otherIncomeId, Map<String, Object> data) throws FreshBooksException {
        String url = this.getUrl(accountId, otherIncomeId);
        ImmutableMap<String, Object> content = ImmutableMap.of("other_income", data);
        AccountingResponse result = this.handleRequest(HttpMethods.PUT, url, content);
        return result.response.result.otherIncome;
    }

    /**
     * Delete the other income with the corresponding id.
     *
     * @param accountId The alpha-numeric account id
     * @param otherIncomeId Id of the resource to delete
     *
     * @throws FreshBooksException If the call is not successful
     */
    public void delete(String accountId, long otherIncomeId) throws FreshBooksException {
        String url = this.getUrl(accountId, otherIncomeId);
        this.handleRequest(HttpMethods.DELETE, url);
    }

}
