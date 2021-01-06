package net.amcintosh.freshbooks;

import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;

public class FreshBooksException extends HttpResponseException {

    public String message;
    public int errorNo;

    public FreshBooksException(HttpResponse response) {
        super(response);
    }

    public FreshBooksException(String message, int errorNo, HttpResponse response) {
        super(response);
        this.message = message;
        this.errorNo = errorNo;
    }
}
