package net.amcintosh.freshbooks.models.builders;

import net.amcintosh.freshbooks.resources.api.ResourceType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IncludesQueryBuilderTest {

    @Test
    public void buildIncludesQuery_accountingType() {
        IncludesQueryBuilder query = new IncludesQueryBuilder();
        query.include("late_reminders").include("lines");
        assertEquals("&include[]=late_reminders&include[]=lines", query.build(ResourceType.ACCOUNTING_LIKE));
        assertEquals("IncludesQueryBuilder{includes=[late_reminders, lines]}", query.toString());
    }

    @Test
    public void buildPaginationQuery_projectType() {
        IncludesQueryBuilder query = new IncludesQueryBuilder();
        query.include("include_overdue_fees");
        assertEquals("&include_overdue_fees=true", query.build(ResourceType.PROJECT_LIKE));
        assertEquals("IncludesQueryBuilder{includes=[include_overdue_fees]}", query.toString());
    }

}
