package net.amcintosh.freshbooks.models.builders;

import com.google.common.base.Joiner;
import net.amcintosh.freshbooks.Util;
import net.amcintosh.freshbooks.resources.api.ResourceType;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Builder for making filtered list queries.
 *
 * Filters can be chained together.
 *
 * <pre>{@code
 *     FreshBooksClient freshBooksClient = new FreshBooksClient.FreshBooksClientBuilder("some_client_id")
 *         .withAccessToken("some_token")
 *         .build();
 *     Clients clients = new Clients(freshBooksClient);
 *
 *     FilterQueryBuilder filters = new FilterQueryBuilder();
 *     filters.inList("clientids", [123, 456]);
 *     assertEquals("&search[clientids][]=123&search[clientids][]=456", filters.build(ResourceType.ACCOUNTING_LIKE));
 *
 *     FilterQueryBuilder filters = new FilterQueryBuilder();
 *     filters.like("email_like", "@freshbooks.com").boolean("active", false);
 *     assertEquals("&search[email_like]=@freshbooks.com&active=False", filters.build(ResourceType.ACCOUNTING_LIKE));
 *
 *     ArrayList<QueryBuilder> builders = new ArrayList<QueryBuilder>();
 *     builders.add(filters);
 *
 *     ClientList clientListResponse = clients.list(accountId, builders);
 * }</pre>
 */
public class FilterQueryBuilder implements QueryBuilder {

    private List<Filter> filters = new ArrayList<>();

    /**
     * Filters results where the provided field is between two values.
     *
     * In general 'between' filters in FreshBooks end in a '<code>_min</code>' or '<code>_max</code>'
     * (as in '<code>amount_min</code>' or '<code>amount_max</code>') or '<code>_date</code>' (as in
     * '<code>start_date</code>', '<code>end_date</code>'). Thus for numerical values '<code>_min</code>'
     * and '<code>_max</code>' will be appended to the '<code>field</code>' value.
     * <br><br>
     * Eg. '<code>addBetween("amount", 1, 10);</code>' will result in a filter of '<code>&amp;search[amount_min]=1&amp;search[amount_max]=10</code>'.
     * <br><br>
     * For  '<code>_date</code>' fields, or non-standard between filters, they must be composed individually:
     * <br><br>
     * Eg. '<code>addBetween("start_date", "2022-01-19");</code>' will result in filters of
     * '<code>&amp;search[start_date]=2022-01-19</code>'.
     *
     * @param field The API response field to filter on
     * @param min The value the field should be greater than (or equal to)
     * @param max The value the field should be less than (or equal to)
     * @return The FilterQueryBuilder instance.
     */
    public FilterQueryBuilder addBetween(String field, int min, int max) {
        this.filters.add(new SearchFilter(field + "_min", String.valueOf(min)));
        this.filters.add(new SearchFilter(field + "_max", String.valueOf(max)));
        return this;
    }

    /**
     * Filters results where the provided field is either greater than or less than the value
     * based on the filter field.
     * <br><br>
     * Eg.
     * <ul>
     *   <li>'<code>addBetween("amount_min", 1);</code>' will result in a filter of
     *   '<code>&amp;search[amount_min]=1</code>'.</li>
     *   <li>'<code>addBetween("amount_max", 10);</code>' will result in a filter of
     *   '<code>&amp;search[amount_max]=10</code>'.</li>
     * </ul>
     *
     * @param field The API filter key
     * @param value The value the key should be compared to
     * @return The FilterQueryBuilder instance.
     */
    public FilterQueryBuilder addBetween(String field, int value) {
        this.filters.add(new SearchFilter(field, String.valueOf(value)));
        return this;
    }

    /**
     * Filters results where the provided field is either greater than or less than the value
     * based on the filter field.
     * <br>
     * Eg. '<code>addBetween("start_date", "2022-01-19");</code>' will result in a filter of
     * '<code>&amp;search[start_date]=2022-01-19</code>'.
     *
     * @param field The API filter key
     * @param value The value the key should be compared to
     * @return The FilterQueryBuilder instance.
     */
    public FilterQueryBuilder addBetween(String field, String value) {
        this.filters.add(new SearchFilter(field, value));
        return this;
    }

    /**
     * Filters results where the field is equal to true or false.
     * <br>
     * Eg. '<code>addBoolean("active", false);</code>' will result in a filter of
     * '<code>&amp;active=false</code>'.
     *
     * @param field The API response field to filter on
     * @param value true or false
     * @return The FilterQueryBuilder instance.
     */
    public FilterQueryBuilder addBoolean(String field, boolean value) {
        this.filters.add(new SimpleFilter(field, String.valueOf(value)));
        return this;
    }

    /**
     * Filters for entries that come before or after a particular time, as specified
     * by the field. Eg. "updated_since" on Time Entries will return time entries updated
     * after the provided date.
     * <br>
     * Eg. '<code>addDateTime("updated_since", LocalDate.now())</code>' will yield
     * '<code>&amp;updated_since=2022-01-28</code>'.
     *
     * @param field The API response field to filter on
     * @param value The date object
     * @return The FilterQueryBuilder instance.
     */
    public FilterQueryBuilder addDate(String field, LocalDate value) {
        this.filters.add(new SimpleFilter(field, value.format(Util.getStandardDateFormatter())));
        return this;
    }

    /**
     * Filters for entries that come before or after a particular time, as specified
     * by the field. Eg. "updated_since" on Time Entries will return time entries updated
     * after the provided time.
     * <br>
     * Eg. '<code>addDateTime("updated_since", ZonedDateTime.now())</code>' will yield
     * '<code>&amp;updated_since=2022-01-28T13:14:07</code>'.
     *
     * @param field The API response field to filter on
     * @param value The timezone-aware date time object
     * @return The FilterQueryBuilder instance.
     */
    public FilterQueryBuilder addDateTime(String field, ZonedDateTime value) {
        this.filters.add(new SimpleFilter(field, value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        return this;
    }

    /**
     * Filters results where the field is equal to the provided value.
     * <br>
     * Eg. '<code>addEquals("username", "Bob")</code>' will yield
     * '<code>&amp;search[username]=Bob</code>' for an accounting-like resource and
     * '<code>&amp;username=Bob</code>' for a project-like resource.
     *
     * @param field The API response field to filter on
     * @param value The value the field should equal
     * @return The FilterQueryBuilder instance.
     */
    public FilterQueryBuilder addEquals(String field, String value) {
        this.filters.add(new EqualsFilter(field, value));
        return this;
    }

    /**
     * Filters results where the field is equal to the provided value.
     * <br>
     * Eg. '<code>addEquals("username", "Bob")</code>' will yield
     * '<code>&amp;search[username]=Bob</code>' for an accounting-like resource and
     * '<code>&amp;username=Bob</code>' for a project-like resource.
     *
     * @param field The API response field to filter on
     * @param value The value the field should equal
     * @return The FilterQueryBuilder instance.
     */
    public FilterQueryBuilder addEquals(String field, int value) {
        this.filters.add(new EqualsFilter(field, String.valueOf(value)));
        return this;
    }

    public FilterQueryBuilder addInList(String field, List<Integer> values) {
        if (!"s".equals(field.substring(field.length() - 1))) {
            field = field + "s";
        }
        this.filters.add(new ListFilter(field, values));
        return this;
    }

    /**
     * Filters for a match contained within the field being searched. For example,
     * "leaf" will Like-match "aleaf" and "leafy", but not "leav", and "leafs" would
     * not Like-match "leaf".
     * <br>
     * Eg. '<code>addLike("organization_like", "fresh")</code>' will yield
     * '<code>&amp;search[organization_like]=fresh</code>'.
     *
     * @param field The API response field to filter on
     * @param value The value the field should equal
     * @return The FilterQueryBuilder instance.
     */
    public FilterQueryBuilder addLike(String field, String value) {
        this.filters.add(new SearchFilter(field, value));
        return this;
    }

    @Override
    public String toString() {
        return "FilterQueryBuilder{" + Joiner.on(",").join(this.filters) + "}";
    }

    /**
     * Build the query string parameters for the list. As different FreshBooks resources
     * use different structure for filter parameters, a resource type is required.
     *
     * @param resourceType The resource type.
     * @return The composed query string parameters.
     */
    @Override
    public String build(ResourceType resourceType) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Filter filter: this.filters) {
            stringBuilder.append(filter.build(resourceType));
        }
        return stringBuilder.toString();
    }

    private interface Filter {
        public String build(ResourceType resourceType);
    }

    /**
     * Filter for basic equality. Has differing forms for accounting-like vs project-like resources.
     */
    private class EqualsFilter implements Filter {
        String field;
        String value;

        public EqualsFilter(String field, String value) {
            this.field = field;
            this.value = value;
        }

        @Override
        public String toString() {
            return "(" + field + "=" + value + ")";
        }

        public String build(ResourceType resourceType) {
            if (resourceType.equals(ResourceType.ACCOUNTING_LIKE)) {
                return "&search[" + field + "]=" + value;
            }
            return "&" + field + "=" + value;
        }
    }

    /**
     * Accounting-like search filter in the form of '<code>search[key]=value</code>'.
     */
    private class SearchFilter implements Filter {
        String field;
        String value;

        public SearchFilter(String field, String value) {
            this.field = field;
            this.value = value;
        }

        @Override
        public String toString() {
            return "(" + field + "=" + value + ")";
        }

        public String build(ResourceType resourceType) {
            return "&search[" + field + "]=" + value;
        }
    }

    /**
     * A simple filter in the form of '<code>key=value</code>'.
     */
    private class SimpleFilter implements Filter {
        String field;
        String value;

        public SimpleFilter(String field, String value) {
            this.field = field;
            this.value = value;
        }

        @Override
        public String toString() {
            return "(" + field + "=" + value + ")";
        }

        public String build(ResourceType resourceType) {
            return "&" + field + "=" + value;
        }
    }

    /**
     * Filter for basic equality. Has differing forms for accounting-like vs project-like resources.
     */
    private class ListFilter implements Filter {
        String field;
        List<Integer> values;

        public ListFilter(String field, List<Integer> values) {
            this.field = field;
            this.values = values;
        }

        @Override
        public String toString() {
            return "(" + field + "=[" + Joiner.on(",").join(this.values) + "])";
        }

        public String build(ResourceType resourceType) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Integer value: this.values) {
                stringBuilder.append("&search[" + this.field + "][]=" + value);
            }
            return stringBuilder.toString();
        }
    }
}
