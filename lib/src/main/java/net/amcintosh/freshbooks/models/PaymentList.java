package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.api.AccountingListResponse;

import java.util.ArrayList;

/**
 * Results of Payments list call containing list of payments and pagination data.
 */
public class PaymentList extends ListResult {

    @Key
    private final ArrayList<Payment> payments;

    public PaymentList(AccountingListResponse.AccountingListInnerResponse.AccountingListResult result) {
        payments = result.payments;
        this.pages = new Pages(result.page, result.pages, result.perPage, result.total);
    }

    /**
     * Array of payments returned by the `list` call.
     *
     * @return Arraylist of Payment objects
     */
    public ArrayList<Payment> getPayments() {
        return payments;
    }
}
