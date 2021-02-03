package net.amcintosh.freshbooks.models.api;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.Client;
import net.amcintosh.freshbooks.models.Invoice;

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
    }
}
