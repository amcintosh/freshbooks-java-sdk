package net.amcintosh.freshbooks.models;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.Util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Money extends GenericJson {
    @Key String amount;
    @Key String code;

    public Money() { }

    public Money(BigDecimal amount, String code) {
        this.amount = amount.toString();
        this.code = code;
    }

    /**
     * Monetary amount with decimal places appropriate to the currency.
     *
     * @return Eg. "9.99"
     */
    public BigDecimal getAmount() {
        return new BigDecimal(amount);
    }

    /**
     * Monetary amount with decimal places appropriate to the currency.
     *
     * @param amount Eg. "9.99"
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount.toString();
    }

    /**
     * The three-letter currency code
     *
     * @return Eg. "USD"
     */
    public String getCode() {
        return code;
    }

    /**
     * The three-letter currency code
     *
     * @param code Eg. "USD"
     */
    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, Object> getContent() {
        Map<String, Object> content = new HashMap<>();
        Util.putIfNotNull(content, "amount", this.amount);
        Util.putIfNotNull(content, "code", this.code);
        return content;
    }
}
