package net.amcintosh.freshbooks.models.builders;

import com.google.common.collect.ImmutableList;
import net.amcintosh.freshbooks.resources.api.ResourceType;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class FilterQueryBuilderTest {

    @Test
    public void buildBetweenFilter_minAndMax() {
        FilterQueryBuilder query = new FilterQueryBuilder();
        query.addBetween("amount", 1, 10);
        assertEquals("&search[amount_min]=1&search[amount_max]=10", query.build(ResourceType.ACCOUNTING_LIKE));
        assertEquals("FilterQueryBuilder{(amount_min=1),(amount_max=10)}", query.toString());
    }

    @Test
    public void buildBetweenFilter_minOnly() {
        FilterQueryBuilder query = new FilterQueryBuilder();
        query.addBetween("amount_min", 1);
        assertEquals("&search[amount_min]=1", query.build(ResourceType.ACCOUNTING_LIKE));
        assertEquals("FilterQueryBuilder{(amount_min=1)}", query.toString());
    }

    @Test
    public void buildBetweenFilter_maxOnly() {
        FilterQueryBuilder query = new FilterQueryBuilder();
        query.addBetween("amount_max", 10);
        assertEquals("&search[amount_max]=10", query.build(ResourceType.ACCOUNTING_LIKE));
        assertEquals("FilterQueryBuilder{(amount_max=10)}", query.toString());
    }

    @Test
    public void buildBetweenFilter_dateString() {
        FilterQueryBuilder query = new FilterQueryBuilder();
        query.addBetween("start_date", "2020-10-17");
        assertEquals("&search[start_date]=2020-10-17", query.build(ResourceType.ACCOUNTING_LIKE));
        assertEquals("FilterQueryBuilder{(start_date=2020-10-17)}", query.toString());
    }

    @Test
    public void buildBooleanFilter_True() {
        FilterQueryBuilder query = new FilterQueryBuilder();
        query.addBoolean("active", true);
        assertEquals("&active=true", query.build(ResourceType.ACCOUNTING_LIKE));
        assertEquals("FilterQueryBuilder{(active=true)}", query.toString());
    }

    @Test
    public void buildBooleanFilter_False() {
        FilterQueryBuilder query = new FilterQueryBuilder();
        query.addBoolean("active", false);
        assertEquals("&active=false", query.build(ResourceType.ACCOUNTING_LIKE));
        assertEquals("FilterQueryBuilder{(active=false)}", query.toString());
    }

    @Test
    public void buildDateFilter() {
        FilterQueryBuilder query = new FilterQueryBuilder();
        query.addDate("updated_since", LocalDate.of(2020, 10, 17));
        assertEquals("&updated_since=2020-10-17", query.build(ResourceType.ACCOUNTING_LIKE));
        assertEquals("FilterQueryBuilder{(updated_since=2020-10-17)}", query.toString());
    }

    @Test
    public void buildDateTimeFilter() {
        FilterQueryBuilder query = new FilterQueryBuilder();
        query.addDateTime("updated_since", ZonedDateTime.of(
                2020, 10, 17, 13,
                14, 7, 0, ZoneId.of("UTC")));
        assertEquals("&updated_since=2020-10-17T13:14:07", query.build(ResourceType.ACCOUNTING_LIKE));
        assertEquals("FilterQueryBuilder{(updated_since=2020-10-17T13:14:07)}", query.toString());
    }

    @Test
    public void buildEqualsFilter_accountingType() {
        FilterQueryBuilder query = new FilterQueryBuilder();
        query.addEquals("username", "Bob");
        assertEquals("&search[username]=Bob", query.build(ResourceType.ACCOUNTING_LIKE));
        assertEquals("FilterQueryBuilder{(username=Bob)}", query.toString());
    }

    @Test
    public void buildEqualsFilter_projectType() {
        FilterQueryBuilder query = new FilterQueryBuilder();
        query.addEquals("username", "Bob");
        assertEquals("&username=Bob", query.build(ResourceType.PROJECT_LIKE));
        assertEquals("FilterQueryBuilder{(username=Bob)}", query.toString());
    }

    @Test
    public void buildInListFilter_plural() {
        FilterQueryBuilder query = new FilterQueryBuilder();
        query.addInList("userids", ImmutableList.of(1, 2));
        assertEquals("&search[userids][]=1&search[userids][]=2", query.build(ResourceType.ACCOUNTING_LIKE));
        assertEquals("FilterQueryBuilder{(userids=[1,2])}", query.toString());
    }

    @Test
    public void buildInListFilter_pluralized() {
        FilterQueryBuilder query = new FilterQueryBuilder();
        query.addInList("userid", Arrays.asList(1, 2));
        assertEquals("&search[userids][]=1&search[userids][]=2", query.build(ResourceType.ACCOUNTING_LIKE));
        assertEquals("FilterQueryBuilder{(userids=[1,2])}", query.toString());
    }

    @Test
    public void buildLikeFilter() {
        FilterQueryBuilder query = new FilterQueryBuilder();
        query.addLike("user_like", "leaf");
        assertEquals("&search[user_like]=leaf", query.build(ResourceType.ACCOUNTING_LIKE));
        assertEquals("FilterQueryBuilder{(user_like=leaf)}", query.toString());
    }

    @Test
    public void buildMultipleFilters() {
        FilterQueryBuilder query = new FilterQueryBuilder();
        query.addEquals("username", "Bob")
                .addBoolean("active", false);
        query.addBetween("amount", 1, 10);
        assertEquals(
                "&search[username]=Bob&active=false&search[amount_min]=1&search[amount_max]=10",
                query.build(ResourceType.ACCOUNTING_LIKE)
        );
        assertEquals(
                "FilterQueryBuilder{(username=Bob),(active=false),(amount_min=1),(amount_max=10)}",
                query.toString()
        );
    }
}
