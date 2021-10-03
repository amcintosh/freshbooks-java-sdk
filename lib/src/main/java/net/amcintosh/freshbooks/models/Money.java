package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.Util;
import net.amcintosh.freshbooks.models.api.ConvertibleContent;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Monetary amount represented by a decimal value and a currency code.
 */
public class Money implements ConvertibleContent {
    @Key String amount;
    @Key String code;

    /**
     * Create an uninitialized Money object.
     */
    public Money() { }

    /**
     * Create a Money object.
     *
     * @param amount Decimal amount
     * @param code Three-letter currency code (Eg. USD, CAD, EUR, GBP)
     */
    public Money(BigDecimal amount, String code) {
        this.amount = amount.toString();
        this.code = code;
    }

    /**
     * Monetary amount with decimal places appropriate to the currency.
     *
     * @return Eg. BigDecimal("9.99")
     */
    public BigDecimal getAmount() {
        if (this.amount != null) {
            return new BigDecimal(amount);
        }
        return null;
    }

    /**
     * Monetary amount with decimal places appropriate to the currency.
     *
     * @param amount Eg. BigDecimal("9.99")
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount.toString();
    }

    /**
     * The three-letter currency code
     *
     * @return (Eg. USD, CAD, EUR, GBP)
     */
    public String getCode() {
        return code;
    }

    /**
     * The three-letter currency code
     *
     * @param code (Eg. USD, CAD, EUR, GBP)
     */
    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, Object> getContent() {
        Map<String, Object> content = new HashMap<>();
        Util.convertContent(content, "amount", this.amount);
        Util.convertContent(content, "code", this.code);
        return content;
    }
}
