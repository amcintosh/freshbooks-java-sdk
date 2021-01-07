package net.amcintosh.freshbooks.models;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

import java.util.ArrayList;

public class AccountingModel {

    @Key
    public AccountingResponse response;

    public static class AccountingResponse {

        @Key
        public AccountingResult result;

        @Key
        public ArrayList<Error> errors;
    }

    public static class Error {
        @Key public int errno;
        @Key public String field;
        @Key public String message;
        @Key public String object;
        @Key public String value;
    }


    public static class AccountingResult {

        @Key
        public Client client;

    }
}