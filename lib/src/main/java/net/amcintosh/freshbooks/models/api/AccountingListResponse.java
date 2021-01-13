package net.amcintosh.freshbooks.models.api;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.Client;
import net.amcintosh.freshbooks.models.Invoice;

import java.util.ArrayList;

public class AccountingListResponse {

    @Key public AccountingListInnerResponse response;

    public static class AccountingListInnerResponse {

        @Key public AccountingListResult result;

        @Key public ArrayList<AccountingError> errors;

        public static class AccountingListResult {
            @Key public int page;
            @Key public int pages;
            @Key("per_page") public int perPage;
            @Key public int total;

            @Key public ArrayList<Client> clients;

            @Key public ArrayList<Invoice> invoices;
        }

    }
}
