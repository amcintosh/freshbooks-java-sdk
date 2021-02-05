package net.amcintosh.freshbooks;

/**
 * Wraps errors coming from FreshBooks API calls.
 */
public class FreshBooksException extends Exception {
    public String statusMessage;
    public int statusCode;
    public int errorNo;
    public String field;
    public String object;
    public String value;

    public FreshBooksException(String message, String statusMessage, int statusCode, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public FreshBooksException(String message, String statusMessage, int statusCode) {
        super(message);
        this.statusMessage = statusMessage;
        this.statusCode = statusCode;
    }

    public FreshBooksException(String message, String statusMessage, int statusCode, int errorNo) {
        super(message);
        this.statusMessage = statusMessage;
        this.statusCode = statusCode;
        this.errorNo = errorNo;
    }

    public FreshBooksException(String message, String statusMessage, int statusCode, int errorNo, String field,
                               String object, String value) {
        super(message);
        this.statusMessage = statusMessage;
        this.statusCode = statusCode;
        this.errorNo = errorNo;
        this.field = field;
        this.object = object;
        this.value = value;
    }

    public String getValidationError() {
        if (this.field != null && this.value != null) {
            return "ValidationError in " + object + ". " + field + "='" + value + "'.";
        }
        return null;
    }
}
