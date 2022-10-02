package net.amcintosh.freshbooks.models.api;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.*;
import net.amcintosh.freshbooks.models.reports.*;

import java.util.ArrayList;

public class AccountingResponse {

    @Key public AccountingInnerResponse response;

    public static class AccountingInnerResponse {

        @Key public AccountingResult result;

        @Key public ArrayList<AccountingError> errors;

    }

    public static class AccountingResult {

        @Key public Client client;

        @Key public Expense expense;

        @Key public Invoice invoice;

        @Key public Item item;

        @Key public Payment payment;

        @Key public Tax tax;

        @Key public Task task;

        @Key("other_income") public OtherIncome otherIncome;

        // Reports

        @Key("profitloss") public ProfitAndLoss profitAndLoss;
    }
}
