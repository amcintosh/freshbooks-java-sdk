package net.amcintosh.freshbooks.models;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.Util;
import net.amcintosh.freshbooks.models.api.ConvertibleContent;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Other Income is for recording income that doesn't require an invoice and is received through other means.
 *
 * @see <a href="https://www.freshbooks.com/api/other_income">FreshBooks API - Other Income</a>
 */
public class OtherIncome extends GenericJson implements ConvertibleContent {

    @Key("incomeid") Long incomeId;
    @Key String date;
    @Key String note;
    @Key("category_name") String categoryName;
    @Key("payment_type") String paymentType;
    @Key("sourceid") Long sourceId;
    @Key String source;
    @Key Money amount;
    @Key("userid") Long userId;
    @Key List<Tax> taxes;
    @Key("created_at") String createdAt;
    @Key("updated_at") String updatedAt;
    @Key("vis_state") int visState;

    /**
     * Get the unique identifier of this income within this business.
     *
     * @return id
     */
    public long getIncomeId() {
        return incomeId;
    }

    /**
     * Get the date the income was received.
     *
     * @return date
     */
    public LocalDate getDate() {
        return LocalDate.parse(date);
    }

    /**
     * Set the date the income was received.
     *
     * @param date of income transaction
     */
    public void setDate(LocalDate date) {
        this.date = date.format(Util.getStandardDateFormatter());
    }

    /**
     * Descriptive text for other income.
     *
     * @return E.g. "Cool beans!"
     */
    public String getNote() {
        return note;
    }

    /**
     * Descriptive text for other income.
     *
     * @param note E.g. "Cool beans!"
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Get category for other income.
     *
     * @return Enum of the other income category
     */
    public OtherIncomeCategory getCategory() {
        return OtherIncomeCategory.valueOfCategory(categoryName);
    }

    /**
     * Set category for other income.
     *
     * @param category Category value
     */
    public void setCategory(OtherIncomeCategory category) {
        this.categoryName = category.getValue();
    }

    /**
     * Type of payment.
     *
     * @return E.g. "Check", "Credit", "Cash", etc.
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * Type of payment.
     *
     * @param paymentType E.g. "Check", "Credit", "Cash", etc.
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * Get the unique identifier of source of external income.
     *
     * @return id
     */
    public long getSourceId() {
        return sourceId;
    }

    /**
     * Source of external income.
     *
     * @return E.g. Shopify, Etsy, Farmers’ Market
     */
    public String getSource() {
        return source;
    }

    /**
     * Source of external income.
     *
     * @param source E.g. Shopify, Etsy, Farmers’ Market
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Amount of external income.
     *
     * @return Money
     */
    public Money getAmount() {
        return amount;
    }

    /**
     * Amount of external income.
     *
     * @param money Amount of external income
     */
    public void setAmount(Money money) {
        this.amount = money;
    }

    /**
     * Get the unique identifier of user.
     *
     * @return id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * List of taxes of external income.
     *
     * @return List of Taxes
     */
    public List<Tax> getTaxes() {
        return taxes;
    }

    /**
     * List of taxes of external income.
     *
     * @param taxes list of taxes
     */
    public void setTaxes(List<Tax> taxes) {
        this.taxes = taxes;
    }

    /**
     * Get the time of first creation.
     * <br><br>
     * <i>Note:</i> The API returns this data in "US/Eastern", but it is converted to UTC.
     *
     * @return Created time in UTC
     */
    public ZonedDateTime getCreated() {
        return Util.getZonedDateTimeFromAccountingLocalTime(this.createdAt);
    }

    /**
     * Get the time of last modification.
     * <br><br>
     * <i>Note:</i> The API returns this data in "US/Eastern", but it is converted to UTC.
     *
     * @return Updated time in UTC
     */
    public ZonedDateTime getUpdated() {
        return Util.getZonedDateTimeFromAccountingLocalTime(this.updatedAt);
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
        Util.convertContent(content, "date", date);
        Util.convertContent(content, "note", note);
        Util.convertContent(content, "category_name", categoryName);
        Util.convertContent(content, "payment_type", paymentType);
        Util.convertContent(content, "source", source);
        Util.convertContent(content, "amount", amount);
        Util.convertContent(content, "taxes", taxes);

        return content;
    }
}
