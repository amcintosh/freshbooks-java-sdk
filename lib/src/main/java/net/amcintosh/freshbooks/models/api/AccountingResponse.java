package net.amcintosh.freshbooks.models.api;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.Client;
import net.amcintosh.freshbooks.models.Invoice;
import net.amcintosh.freshbooks.models.Item;
import net.amcintosh.freshbooks.models.Tax;

import java.util.ArrayList;

public class AccountingResponse {

    @Key public AccountingInnerResponse response;

    public static class AccountingInnerResponse {

        @Key public AccountingResult result;

        @Key public ArrayList<AccountingError> errors;

    }

    public static class AccountingResult {

        @Key public Client client;

        @Key public Invoice invoice;

        @Key public Item item;

        @Key public Tax tax;
    }
}
