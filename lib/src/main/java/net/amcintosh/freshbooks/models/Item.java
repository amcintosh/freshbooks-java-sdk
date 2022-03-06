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
 * Items are stored from invoice lines to make invoicing easier in the future.
 *
 * @see <a href="https://www.freshbooks.com/api/">FreshBooks API - Items</a>
 */
public class Item extends GenericJson implements ConvertibleContent {

    @Key Long id;
    @Key("accounting_systemid") String accountingSystemId;
    @Key String description;
    @Key String inventory;
    @Key("itemid") Long itemId;
    @Key String name;
    @Key("qty") String quantity;
    @Key String sku;
    @Key Long tax1;
    @Key Long tax2;
    @Key("unit_cost") Money unitCost;
    @Key String updated;
    @Key("vis_state") int visState;

    /**
     * Get the unique identifier of this item within this business.
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * Get unique identifier of business item exists on.
     *
     * @return Accounting System Id
     */
    public String getAccountingSystemId() {
        return accountingSystemId;
    }

    /**
     * Duplicate of id.
     *
     * @return
     */
    public long getItemId() {
        return itemId;
    }

    /**
     * Descriptive text for item.
     *
     * @return Eg. "65L BackPack"
     */
    public String getDescription() {
        return description;
    }

    /**
     * Descriptive text for item.
     *
     * @param description Eg. "65L BackPack"
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Decimal-string count of inventory.
     *
     * @return Eg. 10
     */
    public String getInventory() {
        return inventory;
    }

    /**
     * Decimal-string count of inventory.
     *
     * @param inventory Eg. 10
     */
    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    /**
     * Descriptive name of item.
     *
     * @return e.g. "BackPack"
     */
    public String getName() {
        return name;
    }

    /**
     * Descriptive name of item.
     *
     * @param name e.g. "GST"
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Decimal quantity to multiply unit cost by.
     *
     * @return
     */
    public BigDecimal getQuantity() {
        if (this.quantity != null) {
            return new BigDecimal(quantity);
        }
        return null;
    }

    /**
     * Decimal quantity to multiply unit cost by.
     *
     * @param quantity
     */
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity.toString();
    }

    /**
     * Quantity to multiply unit cost by.
     *
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = String.valueOf(quantity);
    }

    /**
     * Quantity to multiply unit cost by.
     *
     * @param quantity
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * Id for a specific item or product, used in inventory management.
     *
     * @return
     */
    public String getSku() {
        return sku;
    }

    /**
     * Id for a specific item or product, used in inventory management.
     *
     * @param sku
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * Id of the first tax to apply to this item.
     *
     * @return
     */
    public long getTax1() {
        return tax1;
    }

    /**
     * Id of the first tax to apply to this item.
     *
     * @param tax1
     */
    public void setTax1(long tax1) {
        this.tax1 = tax1;
    }

    /**
     * Id of the second tax to apply to this item.
     *
     * @return
     */
    public long getTax2() {
        return tax2;
    }

    /**
     * Id of the second tax to apply to this item.
     *
     * @param tax2
     */
    public void setTax2(long tax2) {
        this.tax2 = tax2;
    }

    /**
     * Unit cost of the item.
     *
     * @return
     */
    public Money getUnitCost() {
        return unitCost;
    }

    /**
     * Unit cost of the item.
     *
     * @param unitCost
     */
    public void setUnitCost(Money unitCost) {
        this.unitCost = unitCost;
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

    /**
     * Get the visibility state: active, deleted, or archived
     * <br><br>
     * @see <a href="https://www.freshbooks.com/api/active_deleted">FreshBooks API - Active and Deleted Objects</a>
     *
     * @return Enum of the visibility state.
     */
    public VisState getVisState() {
        return VisState.valueOf(this.visState);
    }

    /**
     * Set the visibility state of the item.
     * <br><br>
     * @see <a href="https://www.freshbooks.com/api/active_deleted">FreshBooks API - Active and Deleted Objects</a>
     *
     * @param visState VisState value
     */
    public void setVisState(VisState visState) {
        this.visState = visState.getValue();
    }

    @Override
    public Map<String, Object> getContent() {
        Map<String, Object> content = new HashMap<>();
        Util.convertContent(content, "description", this.description);
        Util.convertContent(content, "inventory", this.inventory);
        Util.convertContent(content, "name", this.name);
        Util.convertContent(content, "qty", this.quantity);
        Util.convertContent(content, "sku", this.sku);
        Util.convertContent(content, "tax1", this.tax1);
        Util.convertContent(content, "tax2", this.tax2);
        Util.convertContent(content, "unit_cost", this.unitCost);

        return content;
    }
}
