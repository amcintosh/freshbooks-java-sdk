package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.api.AccountingListResponse;

import java.util.ArrayList;

/**
 * Results of other incomes list call containing list of other incomes and pagination data.
 */
public class OtherIncomeList extends ListResult {

    @Key
    private final ArrayList<OtherIncome> otherIncomes;

    public OtherIncomeList(AccountingListResponse.AccountingListInnerResponse.AccountingListResult result) {
        otherIncomes = result.otherIncomes;
        this.pages = new Pages(result.page, result.pages, result.perPage, result.total);
    }

    /**
     * Array of other incomes returned by the `list` call.
     *
     * @return Arraylist of OtherIncome objects
     */
    public ArrayList<OtherIncome> getOtherIncomes() {
        return otherIncomes;
    }
}
