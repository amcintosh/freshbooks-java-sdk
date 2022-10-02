package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.common.collect.ImmutableList;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.TestUtil;
import net.amcintosh.freshbooks.models.reports.ProfitAndLoss;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountingReportsTest {

    @Test
    public void getProfitAndLoss() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/report_profit_and_loss.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/reports/accounting/profitloss", null)).thenReturn(mockRequest);

        AccountingReports reports = new AccountingReports(mockedFreshBooksClient);
        ProfitAndLoss profitAndLoss = reports.profitAndLoss("ABC123");

        assertEquals("American Cyanamid", profitAndLoss.getCompanyName());
        assertFalse(profitAndLoss.isCashBased());
        assertEquals("CAD", profitAndLoss.getCurrencyCode());
        assertEquals("some_token", profitAndLoss.getDownloadToken());
        assertEquals(LocalDate.of(2022, 1, 1), profitAndLoss.getStartDate());
        assertEquals(LocalDate.of(2022, 12, 31), profitAndLoss.getEndDate());
        assertEquals("3m", profitAndLoss.getResolution());
        assertEquals(ImmutableList.of("2022-01-01", "2022-04-01", "2022-07-01", "2022-10-01"), profitAndLoss.getLabels());

        assertEquals(4, profitAndLoss.getDates().size());
        assertEquals(LocalDate.of(2022, 1, 1), profitAndLoss.getDates().get(0).getStartDate());
        assertEquals(LocalDate.of(2022, 3, 31), profitAndLoss.getDates().get(0).getEndDate());
        assertEquals(LocalDate.of(2022, 10, 1), profitAndLoss.getDates().get(3).getStartDate());
        assertEquals(LocalDate.of(2022, 12, 31), profitAndLoss.getDates().get(3).getEndDate());

        List<ProfitAndLoss.ProfitAndLossEntry> expenses = profitAndLoss.getExpenses();
        assertEquals("Other Expenses", expenses.get(0).getDescription());
        assertEquals("debit", expenses.get(0).getEntryType());
        assertEquals(new BigDecimal("3.98"), expenses.get(0).getTotal().getAmount());
        assertEquals("CAD", expenses.get(0).getTotal().getCode());
        assertEquals(4, expenses.get(0).getData().size());
        assertEquals(new BigDecimal("3.98"), expenses.get(0).getData().get(0).getAmount());
        assertEquals("CAD", expenses.get(0).getData().get(0).getCode());

        List<ProfitAndLoss.ProfitAndLossEntry> expenseChildren = profitAndLoss.getExpenses().get(0).getChildren();
        assertEquals("Bank Fees", expenseChildren.get(0).getDescription());
        assertEquals("debit", expenseChildren.get(0).getEntryType());
        assertEquals(new BigDecimal("3.98"), expenseChildren.get(0).getTotal().getAmount());
        assertEquals("CAD", expenseChildren.get(0).getTotal().getCode());
        assertEquals(4, expenseChildren.get(0).getData().size());
        assertEquals(new BigDecimal("3.98"), expenseChildren.get(0).getData().get(0).getAmount());
        assertEquals("CAD", expenseChildren.get(0).getData().get(0).getCode());

        ProfitAndLoss.ProfitAndLossEntry grossMargin = profitAndLoss.getGrossMargin();
        assertEquals("Gross Margin", grossMargin.getDescription());
        assertEquals("none", grossMargin.getEntryType());
        assertEquals(new BigDecimal("100.00"), grossMargin.getTotal().getAmount());
        assertEquals("%", grossMargin.getTotal().getCode());

        List<ProfitAndLoss.ProfitAndLossEntry> income = profitAndLoss.getIncome();
        assertEquals(2, income.size());
        assertEquals("Sales", income.get(0).getDescription());
        assertEquals("credit", income.get(0).getEntryType());
        assertEquals(new BigDecimal("12.00"), income.get(0).getTotal().getAmount());
        assertEquals("CAD", income.get(0).getTotal().getCode());
        assertEquals("Cost of Goods Sold", income.get(1).getDescription());
        assertEquals("credit", income.get(1).getEntryType());
        assertEquals(new BigDecimal("0.00"), income.get(1).getTotal().getAmount());
        assertEquals("CAD", income.get(1).getTotal().getCode());

        ProfitAndLoss.ProfitAndLossEntry netProfit = profitAndLoss.getNetProfit();
        assertEquals("Net Profit (CAD)", netProfit.getDescription());
        assertEquals("credit", netProfit.getEntryType());
        assertEquals(new BigDecimal("8.02"), netProfit.getTotal().getAmount());
        assertEquals("CAD", netProfit.getTotal().getCode());

        ProfitAndLoss.ProfitAndLossEntry totalExpenses = profitAndLoss.getTotalExpenses();
        assertEquals("Total Expenses", totalExpenses.getDescription());
        assertEquals("debit", totalExpenses.getEntryType());
        assertEquals(new BigDecimal("3.98"), totalExpenses.getTotal().getAmount());
        assertEquals("CAD", totalExpenses.getTotal().getCode());

        ProfitAndLoss.ProfitAndLossEntry totalIncome = profitAndLoss.getTotalIncome();
        assertEquals("Gross Profit", totalIncome.getDescription());
        assertEquals("credit", totalIncome.getEntryType());
        assertEquals(new BigDecimal("12.00"), totalIncome.getTotal().getAmount());
        assertEquals("CAD", totalIncome.getTotal().getCode());
    }
}
