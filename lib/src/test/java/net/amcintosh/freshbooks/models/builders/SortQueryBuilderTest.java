package net.amcintosh.freshbooks.models.builders;

import net.amcintosh.freshbooks.resources.api.ResourceType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SortQueryBuilderTest {

    @Test
    public void buildSortQuery_accountingAscending() {
        SortQueryBuilder query = new SortQueryBuilder();
        query.ascending("invoice_date");
        assertEquals("&sort=invoice_date_asc", query.build(ResourceType.ACCOUNTING_LIKE));
        assertEquals("SortQueryBuilder{sort=invoice_date;ascending}", query.toString());
    }

    @Test
    public void buildSortQuery_accountingDescending() {
        SortQueryBuilder query = new SortQueryBuilder();
        query.descending("invoice_date");
        assertEquals("&sort=invoice_date_desc", query.build(ResourceType.ACCOUNTING_LIKE));
        assertEquals("SortQueryBuilder{sort=invoice_date;descending}", query.toString());
    }

    @Test
    public void buildSortQuery_projectAscending() {
        SortQueryBuilder query = new SortQueryBuilder();
        query.ascending("due_date");
        assertEquals("&sort=due_date", query.build(ResourceType.PROJECT_LIKE));
        assertEquals("SortQueryBuilder{sort=due_date;ascending}", query.toString());
    }

    @Test
    public void buildSortQuery_projectDescending() {
        SortQueryBuilder query = new SortQueryBuilder();
        query.descending("due_date");
        assertEquals("&sort=-due_date", query.build(ResourceType.PROJECT_LIKE));
        assertEquals("SortQueryBuilder{sort=due_date;descending}", query.toString());
    }
}
