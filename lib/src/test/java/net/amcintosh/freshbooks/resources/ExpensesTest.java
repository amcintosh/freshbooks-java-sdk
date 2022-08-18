package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.TestUtil;
import net.amcintosh.freshbooks.models.Expense;
import net.amcintosh.freshbooks.models.ExpenseList;
import net.amcintosh.freshbooks.models.VisState;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExpensesTest {

    @Test
    public void getExpense() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/get_expense_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/expenses/expenses/23456", null)).thenReturn(mockRequest);

        long expenseId = 23456;
        Expenses expenses = new Expenses(mockedFreshBooksClient);
        Expense expense = expenses.get("ABC123", expenseId);

        assertEquals(expenseId, expense.getId());
        assertEquals(expenseId, expense.get("id"));
        assertEquals(expenseId, expense.getExpenseId());
        assertEquals("", expense.getAccountName());
        assertEquals(0, expense.getAccountId());
        assertEquals("ACM123", expense.getAccountingSystemId());
        assertEquals(new BigDecimal("54.00"), expense.getAmount().getAmount());
        assertEquals("54.00", expense.getAmount().getAmount().toString());
        assertEquals("CAD", expense.getAmount().getCode());
        assertEquals("ABC Bank", expense.getBankName());
        assertEquals(false, expense.isBillable());
        assertEquals(654654, expense.getCategoryId());
        assertEquals(0, expense.getClientId());
        assertEquals(LocalDate.of(2021, 7, 19),
                expense.getDate()
        );
        assertEquals("", expense.getExtAccountId());
        assertEquals(0, expense.getExtInvoiceId());
        assertEquals(0, expense.getExtSystemId());
        assertEquals(false, expense.isFromBulkImport());
        assertEquals(false, expense.isHasReceipt());
        assertEquals(false, expense.isIncludeReceipt());
        assertEquals(0, expense.getInvoiceId());
        assertEquals(false, expense.isCogs());
        assertEquals(false, expense.isDuplicate());
        assertEquals("0", expense.getMarkupPercent());
        assertEquals(0, expense.getModernProjectId());
        assertEquals("For lunch", expense.getNotes());
        assertEquals(6541, expense.getProfileId());
        assertEquals(0, expense.getProjectId());
        assertEquals(1, expense.getStaffId());
        assertEquals(Expense.ExpenseStatus.INTERNAL, expense.getStatus());
        assertEquals("6.21", expense.getTaxAmount1().getAmount().toString());
        assertEquals("CAD", expense.getTaxAmount1().getCode());
        assertEquals(null, expense.getTaxAmount2().getAmount());
        assertEquals(null, expense.getTaxAmount2().getCode());
        assertEquals("HST1", expense.getTaxName1());
        assertEquals("", expense.getTaxName2());
        assertEquals("", expense.getTaxPercent1());
        assertEquals("", expense.getTaxPercent2());
        assertEquals(0, expense.getTransactionId());
        assertEquals(ZonedDateTime.of(2021, 7, 19, 8,
                42, 3, 0, ZoneId.of("UTC")),
                expense.getUpdated()
        );
        assertEquals("Serano Bakery", expense.getVendor());
        assertEquals(VisState.ACTIVE, expense.getVisState());
    }

    @Test
    public void listExpenses() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/list_expenses_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/expenses/expenses")).thenReturn(mockRequest);

        Expenses expenses = new Expenses(mockedFreshBooksClient);
        ExpenseList expenseList = expenses.list("ABC123");

        ImmutableList<Integer> expenseIds = ImmutableList.of(23457, 23456);

        assertEquals(2, expenseList.getPages().getTotal());
        assertEquals("Serano Bakery", expenseList.getExpenses().get(0).getVendor());
        for (int i=0; i<expenseIds.size(); i++) {
            assertEquals(expenseIds.get(i).longValue(), expenseList.getExpenses().get(i).getId());
        }
    }

    @Test
    public void createExpense_dataMap() throws FreshBooksException, IOException {
        String vendor = "Serano Bakery";
        Map<String, Object> data = ImmutableMap.of("vendor", vendor);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_expense_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/accounting/account/ABC123/expenses/expenses",
                ImmutableMap.of("expense", data))
        ).thenReturn(mockRequest);

        Expenses expenses = new Expenses(mockedFreshBooksClient);
        Expense expense = expenses.create("ABC123", data);

        assertEquals(23456, expense.getId());
        assertEquals(vendor, expense.getVendor());
    }

    @Test
    public void createExpense_expenseObject() throws FreshBooksException, IOException {
        String vendor = "Serano Bakery";
        Expense data = new Expense();
        data.setVendor(vendor);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_expense_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
            HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/accounting/account/ABC123/expenses/expenses",
                ImmutableMap.of("expense", data.getContent()))
        ).thenReturn(mockRequest);

        Expenses expenses = new Expenses(mockedFreshBooksClient);
        Expense expense = expenses.create("ABC123", data);

        assertEquals(23456, expense.getId());
        assertEquals(vendor, expense.getVendor());
    }

    @Test
    public void updateExpense_dataMap() throws FreshBooksException, IOException {
        long expenseId = 23456;
        String vendor = "Serano Bakery";
        Map<String, Object> data = ImmutableMap.of("vendor", vendor);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_expense_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/accounting/account/ABC123/expenses/expenses/23456",
                ImmutableMap.of("expense", data))
        ).thenReturn(mockRequest);

        Expenses expenses = new Expenses(mockedFreshBooksClient);
        Expense expense = expenses.update("ABC123", expenseId, data);

        assertEquals(expenseId, expense.getId());
        assertEquals(vendor, expense.getVendor());
    }

    @Test
    public void updateExpense_clientObject() throws FreshBooksException, IOException {
        long expenseId = 23456;
        String vendor = "Serano Bakery";
        Expense data = new Expense();
        data.setVendor(vendor);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_expense_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/accounting/account/ABC123/expenses/expenses/23456",
                ImmutableMap.of("expense", data.getContent()))
        ).thenReturn(mockRequest);


        Expenses expenses = new Expenses(mockedFreshBooksClient);
        Expense expense = expenses.update("ABC123", expenseId, data);

        assertEquals(expenseId, expense.getId());
        assertEquals(vendor, expense.getVendor());
    }

    @Test
    public void deleteExpense() throws FreshBooksException, IOException {
        long expenseId = 23456;

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_expense_response.json");
        jsonResponse = jsonResponse.replace("\"vis_state\": 0", "\"vis_state\": 1");

        ImmutableMap<String, Object> data = ImmutableMap.of("vis_state", VisState.DELETED.getValue());

        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/accounting/account/ABC123/expenses/expenses/23456",
                ImmutableMap.of("expense", data))
        ).thenReturn(mockRequest);

        Expenses expenses = new Expenses(mockedFreshBooksClient);
        Expense expense = expenses.delete("ABC123", expenseId);

        assertEquals(expenseId, expense.getId());
        assertEquals(VisState.DELETED, expense.getVisState());
    }
}
