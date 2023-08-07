package net.amcintosh.freshbooks.models.api;

import com.google.api.client.util.Key;


public class AccountingErrorDetails {
    @Key("@type")
    public String type;

    @Key
    public String reason;
    @Key
    public String domain;
    @Key
    public AccountingErrorDetailsMetadata metadata;

    public int getReason() {
        return Integer.parseInt(reason);
    }

    public static class AccountingErrorDetailsMetadata {
        @Key
        public String object;
        @Key
        public String message;
        @Key
        public String field;
        @Key
        public String value;
    }
}
