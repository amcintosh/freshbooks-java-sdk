package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.common.collect.ImmutableMap;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.TestUtil;
import net.amcintosh.freshbooks.models.Tax;
import net.amcintosh.freshbooks.models.TaxList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaxesTest {

    @Test
    public void getTax() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/get_tax_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/taxes/taxes/7840", null)).thenReturn(mockRequest);

        long taxId = 7840;
        Taxes taxes = new Taxes(mockedFreshBooksClient);
        Tax tax = taxes.get("ABC123", taxId);

        assertEquals(taxId, tax.getId());
        assertEquals(taxId, tax.getTaxId());
        assertEquals("ACM123", tax.getAccountingSystemId());
        assertEquals(new BigDecimal("13"), tax.getAmount());
        assertEquals("HST", tax.getName());
        assertEquals("RT 1234", tax.getNumber());
        assertEquals(ZonedDateTime.of(2020, 6, 16, 14,
                04, 37, 0, ZoneId.of("UTC")),
                tax.getUpdated()
        );
    }

    @Test
    public void listTaxes() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/list_taxes_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/taxes/taxes")).thenReturn(mockRequest);

        Taxes taxes = new Taxes(mockedFreshBooksClient);
        TaxList taxList = taxes.list("ABC123");

        assertEquals(2, taxList.getPages().getTotal());
        assertEquals(7840, taxList.getTaxes().get(0).getId());
        assertEquals("HST", taxList.getTaxes().get(0).getName());
    }

    @Test
    public void createTax_dataMap() throws FreshBooksException, IOException {
        String taxName = "HST";
        BigDecimal taxAmount = new BigDecimal("13");
        Map<String, Object> data = ImmutableMap.of("name", taxName, "amount", taxAmount);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_tax_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/accounting/account/ABC123/taxes/taxes",
                ImmutableMap.of("tax", data))
        ).thenReturn(mockRequest);

        Taxes taxes = new Taxes(mockedFreshBooksClient);
        Tax tax = taxes.create("ABC123", data);

        assertEquals(7840, tax.getId());
        assertEquals(taxName, tax.getName());
        assertEquals(taxAmount, tax.getAmount());
    }

    @Test
    public void createTax_taxObject() throws FreshBooksException, IOException {
        String taxName = "HST";
        BigDecimal taxAmount = new BigDecimal("13");
        Tax data = new Tax();
        data.setName(taxName);
        data.setAmount(taxAmount);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_tax_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/accounting/account/ABC123/taxes/taxes",
                ImmutableMap.of("tax", data.getContent()))
        ).thenReturn(mockRequest);

        Taxes taxes = new Taxes(mockedFreshBooksClient);
        Tax tax = taxes.create("ABC123", data);

        assertEquals(7840, tax.getId());
        assertEquals(taxName, tax.getName());
        assertEquals(taxAmount, tax.getAmount());
    }

    @Test
    public void updateTax_dataMap() throws FreshBooksException, IOException {
        long taxId = 7840;
        Map<String, Object> data = ImmutableMap.of("name", "HST2");

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_tax_response.json");
        jsonResponse = jsonResponse.replace("HST", "HST2");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/accounting/account/ABC123/taxes/taxes/7840",
                ImmutableMap.of("tax", data))
        ).thenReturn(mockRequest);

        Taxes taxes = new Taxes(mockedFreshBooksClient);
        Tax tax = taxes.update("ABC123", taxId, data);

        assertEquals(taxId, tax.getId());
        assertEquals("HST2", tax.getName());
    }

    @Test
    public void updateTax_taxObject() throws FreshBooksException, IOException {
        long taxId = 7840;
        Tax data = new Tax();
        data.setName("HST2");

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_tax_response.json");
        jsonResponse = jsonResponse.replace("HST", "HST2");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/accounting/account/ABC123/taxes/taxes/7840",
                ImmutableMap.of("tax", data.getContent()))
        ).thenReturn(mockRequest);

        Taxes taxes = new Taxes(mockedFreshBooksClient);
        Tax tax = taxes.update("ABC123", taxId, data);

        assertEquals(taxId, tax.getId());
        assertEquals("HST2", tax.getName());
    }

    @Test
    public void deleteTax() throws FreshBooksException, IOException {
        long taxId = 7840;

        String jsonResponse = "{\n\"response\": {}\n}";

        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.DELETE,
                "/accounting/account/ABC123/taxes/taxes/7840",
                null)
        ).thenReturn(mockRequest);

        Taxes taxes = new Taxes(mockedFreshBooksClient);
        taxes.delete("ABC123", taxId);
    }
}
