package net.amcintosh.freshbooks.resources.responses;

import com.google.api.client.util.Key;

public class AccountingError {

    @Key public int errno;
    @Key public String field;
    @Key public String message;
    @Key public String object;
    @Key public String value;

}
