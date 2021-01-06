package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;

import java.util.ArrayList;

public class Client extends AccountingModel {

    @Key
    public AccountingResponse response;

    public static class AccountingResponse {
        @Key
        public AccountingResult result;

        @Key
        public ArrayList<Error> errors;
    }

    public static class AccountingResult {
        @Key
        public Client client;
    }

    @Key
    public long id;

    @Key
        public String organization;
}
