package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.api.AccountingListResponse;

import java.util.ArrayList;

/**
 * Results of expenses list call containing list of expenses and pagination data.
 */
public class ExpenseList extends ListResult {

    @Key private final ArrayList<Expense> expenses;

    public ExpenseList(AccountingListResponse.AccountingListInnerResponse.AccountingListResult result) {
        expenses = result.expenses;
        this.pages = new Pages(result.page, result.pages, result.perPage, result.total);
    }

    /**
     * Array of expenses returned by the `list` call.
     *
     * @return Arraylist of Expense objects
     */
    public ArrayList<Expense> getExpenses() {
        return expenses;
    }
}
