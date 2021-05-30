package net.amcintosh.freshbooks.models;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.Util;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class LineItem extends GenericJson {

    @Key("lineid") Long lineId;
    @Key Money amount;
    @Key String description;
    @Key("expenseid") Long expenseId;
    @Key String name;
    @Key("qty") int quantity;
    @Key String taxAmount1;
    @Key String taxAmount2;
    @Key String taxName1;
    @Key String taxName2;
    @Key String taxNumber1;
    @Key String taxNumber2;
    @Key int type;
    @Key("unit_cost") Money unitCost;
    @Key String updated;

    /**
     * Unique-to-this-invoice line id.
     *
     * @return
     */
    public long getLineId() {
        return lineId;
    }

    /**
     * Unique-to-this-invoice line id.
     *
     * @param lineId
     */
    public void setLineId(long lineId) {
        this.lineId = lineId;
    }

    /**
     * Amount total of a line item, calculated from unit cost, quantity and tax.
     *
     * @return
     */
    public Money getAmount() {
        return amount;
    }

    /**
     * Description for the line item.
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Description for the line item.
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Id of unbilled expense, required when invoice line type is REBILLING_EXPENSE,
     * otherwise should be excluded.
     *
     * @return
     */
    public long getExpenseId() {
        return expenseId;
    }

    /**
     * Id of unbilled expense, required when invoice line type is REBILLING_EXPENSE,
     * otherwise should be excluded.
     *
     * @param expenseId
     */
    public void setExpenseId(long expenseId) {
        this.expenseId = expenseId;
    }

    /**
     * Name for the line item.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Name for the line item.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Quantity of the line unit, multiplied against unit_cost to get amount.
     *
     * @return
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Quantity of the line unit, multiplied against unit_cost to get amount.
     *
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * First tax amount, in percentage, up to 3 decimal places.
     *
     * @return
     */
    public String getTaxAmount1() {
        return taxAmount1;
    }

    /**
     * First tax amount, in percentage, up to 3 decimal places.
     *
     * @param taxAmount1
     */
    public void setTaxAmount1(String taxAmount1) {
        this.taxAmount1 = taxAmount1;
    }

    /**
     * Second tax amount, in percentage, up to 3 decimal places.
     *
     * @return
     */
    public String getTaxAmount2() {
        return taxAmount2;
    }

    /**
     * Second tax amount, in percentage, up to 3 decimal places.
     *
     * @param taxAmount2
     */
    public void setTaxAmount2(String taxAmount2) {
        this.taxAmount2 = taxAmount2;
    }

    /**
     * Name for the first tax on the line item.
     *
     * @return
     */
    public String getTaxName1() {
        return taxName1;
    }

    /**
     * Name for the first tax on the line item.
     *
     * @param taxName1
     */
    public void setTaxName1(String taxName1) {
        this.taxName1 = taxName1;
    }

    /**
     * Name for the second tax on the line item.
     *
     * @return
     */
    public String getTaxName2() {
        return taxName2;
    }

    /**
     * Name for the second tax on the line item.
     *
     * @param taxName2
     */
    public void setTaxName2(String taxName2) {
        this.taxName2 = taxName2;
    }

    /**
     * First tax number on the line item.
     *
     * @return
     */
    public String getTaxNumber1() {
        return taxNumber1;
    }

    /**
     * First tax number on the line item.
     *
     * @param taxNumber1
     */
    public void setTaxNumber1(String taxNumber1) {
        this.taxNumber1 = taxNumber1;
    }

    /**
     * Second tax number on the line item.
     *
     * @return
     */
    public String getTaxNumber2() {
        return taxNumber2;
    }

    /**
     * Second tax number on the line item.
     *
     * @param taxNumber2
     */
    public void setTaxNumber2(String taxNumber2) {
        this.taxNumber2 = taxNumber2;
    }

    /**
     * Line item type. Either normal or a rebilling expense line.
     *
     * @return
     */
    public LineItemType getType() {
        return LineItemType.valueOf(type);
    }

    /**
     * Line item type. Either normal or a rebilling expense line.
     *
     * @param type
     */
    public void setType(LineItemType type) {
        this.type = type.getValue();
    }

    /**
     * Unit cost of the line item.
     *
     * @return
     */
    public Money getUnitCost() {
        return unitCost;
    }

    /**
     * Unit cost of the line item.
     *
     * @param unitCost
     */
    public void setUnitCost(Money unitCost) {
        this.unitCost = unitCost;
    }

    /**
     * Get the time this line was last updated at.
     * <br><br>
     * <i>Note:</i> The API returns this data in "US/Eastern", but it is converted here to UTC.
     *
     * @return Updated time in UTC
     */
    public ZonedDateTime getUpdated() {
        return Util.getZonedDateTimeFromAccountingLocalTime(this.updated);
    }

    public Map<String, Object> getContent() {
        Map<String, Object> content = new HashMap<>();
        Util.putIfNotNull(content, "lineid", this.lineId);
        Util.putIfNotNull(content, "description", this.description);
        Util.putIfNotNull(content, "expenseid", this.expenseId);
        Util.putIfNotNull(content, "name", this.name);
        Util.putIfNotNull(content, "qty", this.quantity);
        Util.putIfNotNull(content, "taxAmount1", this.taxAmount1);
        Util.putIfNotNull(content, "taxAmount2", this.taxAmount2);
        Util.putIfNotNull(content, "taxName1", this.taxName1);
        Util.putIfNotNull(content, "taxName2", this.taxName2);
        Util.putIfNotNull(content, "taxNumber1", this.taxNumber1);
        Util.putIfNotNull(content, "taxNumber2", this.taxNumber2);
        Util.putIfNotNull(content, "type", this.type);
        Util.putIfNotNull(content, "unit_cost", this.unitCost);
        return content;
    }
}
