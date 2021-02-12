package net.amcintosh.freshbooks.models.builders;

import net.amcintosh.freshbooks.resources.api.ResourceType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PaginationQueryBuilderTest {

    @Test
    public void buildPaginationQuery_initalized() {
        PaginationQueryBuilder p = new PaginationQueryBuilder(1, 3);
        assertEquals("&page=1&per_page=3", p.build(ResourceType.ACCOUNTING_LIKE));
        assertEquals("PaginationQueryBuilder{page=1, perPage=3}", p.toString());
    }

    @Test
    public void buildPaginationQuery_chained() {
        PaginationQueryBuilder p = new PaginationQueryBuilder();
        p.page(1).perPage(3);
        assertEquals("&page=1&per_page=3", p.build(ResourceType.ACCOUNTING_LIKE));
    }

    @Test
    public void buildPaginationQuery_page_only() {
        PaginationQueryBuilder p = new PaginationQueryBuilder();
        p.page(4);
        assertEquals("&page=4", p.build(ResourceType.ACCOUNTING_LIKE));
    }

    @Test
    public void buildPaginationQuery_per_page_only() {
        PaginationQueryBuilder p = new PaginationQueryBuilder();
        p.perPage(5);
        assertEquals("&per_page=5", p.build(ResourceType.ACCOUNTING_LIKE));
    }

    @Test
    public void buildPaginationQuery_min_max_values() {
        PaginationQueryBuilder p = new PaginationQueryBuilder(0, 200);
        assertEquals("&page=1&per_page=100", p.build(ResourceType.ACCOUNTING_LIKE));

        p.page(0).perPage(200);
        assertEquals("&page=1&per_page=100", p.build(ResourceType.ACCOUNTING_LIKE));
    }
}
