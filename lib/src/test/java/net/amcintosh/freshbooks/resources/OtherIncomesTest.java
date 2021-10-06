package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.common.collect.ImmutableMap;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.TestUtil;
import net.amcintosh.freshbooks.models.*;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OtherIncomesTest {

    @Test
    public void getOtherIncome() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/get_other_income_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/other_incomes/other_incomes/1234", null)).thenReturn(mockRequest);

        long otherIncomeId = 1234;
        OtherIncomes otherIncomes = new OtherIncomes(mockedFreshBooksClient);
        OtherIncome otherIncome = otherIncomes.get("ABC123", otherIncomeId);

        assertEquals(otherIncomeId, otherIncome.getIncomeId());
        assertEquals(LocalDate.of(2020, 9, 25), otherIncome.getDate());
        assertEquals("Cool beans!", otherIncome.getNote());
        assertEquals(OtherIncomeCategory.ONLINE_SALES, otherIncome.getCategory());
        assertEquals("PayPal", otherIncome.getPaymentType());
        assertEquals(0, otherIncome.getSourceId());
        assertEquals("Etsy", otherIncome.getSource());
        assertEquals(new BigDecimal("25.00"), otherIncome.getAmount().getAmount());
        assertEquals("USD", otherIncome.getAmount().getCode());
        assertEquals(0, otherIncome.getUserId());
        assertEquals(new BigDecimal("3"), otherIncome.getTaxes().get(0).getAmount());
        assertEquals("HST", otherIncome.getTaxes().get(0).getName());
        assertEquals(new BigDecimal("10"), otherIncome.getTaxes().get(1).getAmount());
        assertEquals("Empire Tax", otherIncome.getTaxes().get(1).getName());
        assertEquals(ZonedDateTime.of(2020, 9, 25, 14,
                        53, 22, 0, ZoneId.of("UTC")),
                otherIncome.getUpdated()
        );
        assertEquals(ZonedDateTime.of(2020, 9, 25, 14,
                        53, 22, 0, ZoneId.of("UTC")),
                otherIncome.getUpdated()
        );
        assertEquals(VisState.ACTIVE, otherIncome.getVisState());
    }

    @Test
    public void listOtherIncomes() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/list_other_incomes_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/other_incomes/other_incomes")).thenReturn(mockRequest);

        OtherIncomes otherIncomes = new OtherIncomes(mockedFreshBooksClient);
        OtherIncomeList otherIncomeList = otherIncomes.list("ABC123");

        assertEquals(2, otherIncomeList.getPages().getTotal());
        assertEquals(1234, otherIncomeList.getOtherIncomes().get(0).getIncomeId());
        assertEquals("Cool beans!", otherIncomeList.getOtherIncomes().get(0).getNote());
    }

    @Test
    public void createOtherIncome_dataMap() throws FreshBooksException, IOException {
        String otherIncomeNote = "Cool beans!";
        Map<String, Object> data = ImmutableMap.<String, Object>builder()
                .put("date", "2020-09-25")
                .put("note", otherIncomeNote)
                .put("category_name", "online_sales")
                .put("payment_type", "PayPal")
                .put("source", "Etsy")
                .put("amount", "{\"amount\":\"25.00\",\"code\":\"USD\"}")
                .put("taxes", "[{\"amount\":\"3\",\"name\":\"HST\"},{\"amount\":\"10\",\"name\":\"Empire Tax\"}]")
                .build();

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_other_income_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/accounting/account/ABC123/other_incomes/other_incomes",
                ImmutableMap.of("other_income", data))
        ).thenReturn(mockRequest);

        OtherIncomes otherIncomes = new OtherIncomes(mockedFreshBooksClient);
        OtherIncome otherIncome = otherIncomes.create("ABC123", data);

        assertEquals(1234, otherIncome.getIncomeId());
        assertEquals(otherIncomeNote, otherIncome.getNote());
    }

    @Test
    public void createOtherIncome_otherIncomeObject() throws FreshBooksException, IOException {
        String otherIncomeNote = "Cool beans!";
        OtherIncome data = new OtherIncome();
        data.setDate(LocalDate.of(2020, 9, 25));
        data.setNote(otherIncomeNote);
        data.setCategory(OtherIncomeCategory.ONLINE_SALES);
        data.setPaymentType("PayPal");
        data.setSource("Etsy");

        Money amount = new Money();
        amount.setAmount(new BigDecimal("25.00"));
        amount.setCode("USD");
        data.setAmount(amount);

        List<Tax> taxes = new ArrayList<>();

        Tax tax1 = new Tax();
        tax1.setAmount(new BigDecimal("3"));
        tax1.setName("HST");
        taxes.add(tax1);

        Tax tax2 = new Tax();
        tax2.setAmount(new BigDecimal("10.0"));
        tax2.setName("Empire Tax");
        taxes.add(tax2);
        data.setTaxes(taxes);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_other_income_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/accounting/account/ABC123/other_incomes/other_incomes",
                ImmutableMap.of("other_income", data.getContent()))
        ).thenReturn(mockRequest);

        OtherIncomes otherIncomes = new OtherIncomes(mockedFreshBooksClient);
        OtherIncome otherIncome = otherIncomes.create("ABC123", data);

        assertEquals(1234, otherIncome.getIncomeId());
        assertEquals(otherIncomeNote, otherIncome.getNote());
    }

    @Test
    public void updateOtherIncome_dataMap() throws FreshBooksException, IOException {
        long otherIncomeId = 1234;
        String otherIncomeNote = "New Note";
        Map<String, Object> data = ImmutableMap.of("note", otherIncomeNote);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_other_income_response.json");
        jsonResponse = jsonResponse.replace("Cool beans!", otherIncomeNote);
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/accounting/account/ABC123/other_incomes/other_incomes/1234",
                ImmutableMap.of("other_income", data))
        ).thenReturn(mockRequest);

        OtherIncomes otherIncomes = new OtherIncomes(mockedFreshBooksClient);
        OtherIncome otherIncome = otherIncomes.update("ABC123", otherIncomeId, data);

        assertEquals(otherIncomeId, otherIncome.getIncomeId());
        assertEquals(otherIncomeNote, otherIncome.getNote());
    }

    @Test
    public void updateItem_otherIncomeObject() throws FreshBooksException, IOException {
        long otherIncomeId = 1234;
        String otherIncomeNote = "New Note";
        OtherIncome data = new OtherIncome();
        data.setNote(otherIncomeNote);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_other_income_response.json");
        jsonResponse = jsonResponse.replace("Cool beans!", otherIncomeNote);
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/accounting/account/ABC123/other_incomes/other_incomes/1234",
                ImmutableMap.of("other_income", data.getContent()))
        ).thenReturn(mockRequest);

        OtherIncomes otherIncomes = new OtherIncomes(mockedFreshBooksClient);
        OtherIncome otherIncome = otherIncomes.update("ABC123", otherIncomeId, data);

        assertEquals(otherIncomeId, otherIncome.getIncomeId());
        assertEquals(otherIncomeNote, otherIncome.getNote());
    }

    @Test
    public void deleteOtherIncome() throws FreshBooksException, IOException {
        long otherIncomeId = 1234;

        String jsonResponse = "{\n\"response\": {}\n}";

        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.DELETE,
                "/accounting/account/ABC123/other_incomes/other_incomes/1234",
                null)
        ).thenReturn(mockRequest);

        OtherIncomes otherIncomes = new OtherIncomes(mockedFreshBooksClient);
        otherIncomes.delete("ABC123", otherIncomeId);
    }
}
