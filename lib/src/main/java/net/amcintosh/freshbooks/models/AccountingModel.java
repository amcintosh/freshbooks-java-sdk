package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;

class AccountingModel {

    public static class Error {
        @Key public int errno;
        @Key public String field;
        @Key public String message;
        @Key public String object;
        @Key public String value;
    }
}
