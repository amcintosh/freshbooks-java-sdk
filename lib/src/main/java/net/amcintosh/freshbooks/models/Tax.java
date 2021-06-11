package net.amcintosh.freshbooks.models;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.Util;
import net.amcintosh.freshbooks.models.api.ConvertibleContent;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * System-wide taxes for invoices.
 *
 * @see <a href="https://www.freshbooks.com/api/taxes">FreshBooks API - Taxes</a>
 */
public class Tax extends GenericJson implements ConvertibleContent {

    @Key Long id;
    @Key("accounting_systemid") String accountingSystemId;
    @Key String amount;
    @Key String name;
    @Key String number;
    @Key("taxid") Long taxId;
    @Key String updated;

    /**
     * Get the unique identifier of this tax within this business.
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * Get unique identifier of business tax exists on.
     *
     * @return Accounting System Id
     */
    public String getAccountingSystemId() {
        return accountingSystemId;
    }

    /**
     * Decimal representing percentage value of tax.
     *
     * @return
     */
    public BigDecimal getAmount() {
        return new BigDecimal(amount);
    }

    /**
     * Decimal representing percentage value of tax.
     *
     * @param amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount.toString();
    }

    /**
     * Identifiable name for the tax.
     *
     * @return e.g. "GST"
     */
    public String getName() {
        return name;
    }

    /**
     * Identifiable name for the tax.
     *
     * @param name e.g. "GST"
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * An external number that identifies your tax submission.
     *
     * @return
     */
    public String getNumber() {
        return number;
    }

    /**
     * An external number that identifies your tax submission.
     *
     * @param number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Duplicate of id.
     *
     * @return
     */
    public long getTaxId() {
        return taxId;
    }

    /**
     * Get the time of last modification.
     * <br><br>
     * <i>Note:</i> The API returns this data in "US/Eastern", but it is converted to UTC.
     *
     * @return Updated time in UTC
     */
    public ZonedDateTime getUpdated() {
        return Util.getZonedDateTimeFromAccountingLocalTime(this.updated);
    }

    @Override
    public Map<String, Object> getContent() {
        Map<String, Object> content = new HashMap<>();
        Util.convertContent(content, "amount", this.amount);
        Util.convertContent(content, "name", this.name);
        Util.convertContent(content, "number", this.number);
        return content;
    }
}
