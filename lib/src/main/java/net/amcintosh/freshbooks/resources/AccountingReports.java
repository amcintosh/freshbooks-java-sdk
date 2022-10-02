package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.common.collect.ImmutableList;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.api.AccountingResponse;
import net.amcintosh.freshbooks.models.builders.FilterQueryBuilder;
import net.amcintosh.freshbooks.models.reports.ProfitAndLoss;
import net.amcintosh.freshbooks.resources.api.AccountingResource;
import net.amcintosh.freshbooks.resources.api.ResourceType;

/**
 * FreshBooks taxes resource with calls to get, list, create, update, delete
 */
public class AccountingReports extends AccountingResource {

    public AccountingReports(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    @Override
    protected ResourceType getResourceType() {
        return ResourceType.ACCOUNTING_REPORT;
    }

    @Override
    protected String getPath() {
        return "reports/accounting";
    }

    protected String getUrl(String accountId, String reportName, FilterQueryBuilder builder) {
        String queryString = "";
        if (builder != null) {
            queryString = this.buildQueryString(ImmutableList.of(builder));
        }
        return String.format("/accounting/account/%s/%s/%s%s", accountId, this.getPath(), reportName, queryString);
    }

    /**
     * The profit/Loss Report shows all the information involving both your Profits and Losses
     *
     * @param accountId Id of the account
     * @return ProfitAndLoss Report data
     * @throws FreshBooksException If the call is not successful
     */
    public ProfitAndLoss profitAndLoss(String accountId) throws FreshBooksException {
        return this.profitAndLoss(accountId, null);
    }

    /**
     * The profit/Loss Report shows all the information involving both your Profits and Losses
     *
     * @param accountId Id of the account
     * @param builder FilterQueryBuilder to set report parameters
     * @return ProfitAndLoss Report data
     * @throws FreshBooksException If the call is not successful
     */
    public ProfitAndLoss profitAndLoss(String accountId, FilterQueryBuilder builder) throws FreshBooksException {
        String url = this.getUrl(accountId, "profitloss", builder);
        AccountingResponse result = this.handleRequest(HttpMethods.GET, url);
        return result.response.result.profitAndLoss;
    }
}
