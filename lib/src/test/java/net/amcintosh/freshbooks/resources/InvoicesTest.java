package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.util.Key;
import com.google.common.collect.ImmutableList;
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
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InvoicesTest {

    @Test
    public void getInvoice() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/get_invoice_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/invoices/invoices/987654", null)).thenReturn(mockRequest);

        long invoiceId = 987654;
        Invoices invoices = new Invoices(mockedFreshBooksClient);
        Invoice invoice = invoices.get("ABC123", invoiceId);

        assertEquals(invoiceId, invoice.getId());
        assertEquals(invoiceId, invoice.getInvoiceId());
        assertEquals("ACM123", invoice.getAccountingSystemId());
        assertEquals("ACM123", invoice.getAccountId());
        assertEquals("", invoice.getAddress());
        assertEquals(new BigDecimal("41.94"), invoice.getAmount().getAmount());
        assertEquals("41.94", invoice.getAmount().getAmount().toString());
        assertEquals("CAD", invoice.getAmount().getCode());
        assertFalse(invoice.isAutoBill());
        assertEquals(Invoice.InvoiceAutoBillStatus.NONE, invoice.getAutoBillStatus());
        assertEquals("Toronto", invoice.getCity());
        assertEquals("M5T 2B3", invoice.getCode());
        assertEquals("Canada", invoice.getCountry());
        assertEquals(12345, invoice.getClientId());
        assertEquals("CAD", invoice.getCurrencyCode());
        assertEquals("Gordon Shumway", invoice.getCurrentOrganization());
        assertEquals(LocalDate.of(2021, 4, 16),
                invoice.getCreateDate()
        );
        assertEquals(ZonedDateTime.of(2021, 4, 16, 14,
                31, 19, 0, ZoneId.of("UTC")),
                invoice.getCreatedAt()
        );
        assertEquals(12345, invoice.getCustomerId());
        assertEquals(LocalDate.of(2021, 4, 16), invoice.getDatePaid());
        assertEquals(new BigDecimal("1.00"), invoice.getDepositAmount().getAmount());
        assertEquals("CAD", invoice.getDepositAmount().getCode());
        assertEquals("", invoice.getDiscountDescription());
        assertEquals(new BigDecimal("1.5"), invoice.getDepositPercentage());
        assertEquals(Invoice.InvoiceDepositStatus.NONE, invoice.getDepositStatus());
        assertEquals("Melmac melamine resin molded dinnerware", invoice.getDescription());
        assertEquals("-4.40", invoice.getDiscountTotal().getAmount().toString());
        assertEquals("CAD", invoice.getDiscountTotal().getCode());
        assertEquals(new BigDecimal("10"), invoice.getDiscountValue());
        assertEquals(Invoice.InvoiceDisplayStatus.SENT, invoice.getDisplayStatus());
        assertEquals(LocalDate.of(2021, 4, 16), invoice.getDueDate());
        assertEquals(0, invoice.getDueOffsetDays());
        assertEquals(0, invoice.getEstimateId());
        assertEquals("Gordon", invoice.getFirstName());
        assertNull(invoice.getGenerationDate());
        assertFalse(invoice.isGroundMail());
        assertEquals("ACM0002", invoice.getInvoiceNumber());
        assertEquals("en", invoice.getLanguage());
        assertEquals(Invoice.InvoiceOrderStatus.NONE, invoice.getLastOrderStatus());
        assertEquals("Shumway", invoice.getLastName());
        assertEquals("Gordon Shumway", invoice.getOrganization());
        assertEquals("21.94", invoice.getOutstanding().getAmount().toString());
        assertEquals("CAD", invoice.getOutstanding().getCode());
        assertEquals(1, invoice.getOwnerId());
        assertEquals("Thanks for your business", invoice.getNotes());
        assertEquals("20.00", invoice.getPaid().getAmount().toString());
        assertEquals("CAD", invoice.getPaid().getCode());
        assertEquals(0, invoice.getParent());
        assertEquals(Invoice.InvoicePaymentStatus.PARTIAL, invoice.getPaymentStatus());
        assertEquals("", invoice.getPONumber());
        assertEquals("Ontario", invoice.getProvince());
        assertEquals(1, invoice.getSentId());
        assertFalse(invoice.isShowAttachments());
        assertEquals("123 Huron St.", invoice.getStreet());
        assertEquals("", invoice.getStreet2());
        assertEquals(Invoice.InvoiceStatus.SENT, invoice.getStatus());
        assertEquals("", invoice.getTerms());
        assertEquals(ZonedDateTime.of(2021, 4, 16, 14,
                31, 58, 0, ZoneId.of("UTC")),
                invoice.getUpdated()
        );
        assertEquals(Invoice.InvoiceV3Status.PARTIAL, invoice.getV3Status());
        assertEquals("VAT Number", invoice.getVATName());
        assertEquals("123", invoice.getVATNumber());
        assertEquals(VisState.ACTIVE, invoice.getVisState());

        // Lines
        assertEquals(2, invoice.getLines().size());
        assertEquals(1, invoice.getLines().get(0).getLineId());
        assertEquals(new BigDecimal("20.00"), invoice.getLines().get(0).getAmount().getAmount());
        assertEquals("CAD", invoice.getLines().get(0).getAmount().getCode());
        assertEquals("Melmac melamine resin molded dinnerware", invoice.getLines().get(0).getDescription());
        assertEquals(0, invoice.getLines().get(0).getExpenseId());
        assertEquals("Bowls", invoice.getLines().get(0).getName());
        assertEquals(new BigDecimal(4), invoice.getLines().get(0).getQuantity());
        assertEquals("13", invoice.getLines().get(0).getTaxAmount1());
        assertEquals("0", invoice.getLines().get(0).getTaxAmount2());
        assertEquals("HST1", invoice.getLines().get(0).getTaxName1());
        assertEquals("", invoice.getLines().get(0).getTaxName2());
        assertEquals("RT", invoice.getLines().get(0).getTaxNumber1());
        assertEquals("", invoice.getLines().get(0).getTaxNumber2());
        assertEquals(LineItem.LineItemType.NORMAL, invoice.getLines().get(0).getType());
        assertEquals(new BigDecimal("5.00"), invoice.getLines().get(0).getUnitCost().getAmount());
        assertEquals("CAD", invoice.getLines().get(0).getUnitCost().getCode());
        assertEquals(ZonedDateTime.of(2021, 4, 16, 14,
                31, 19, 0, ZoneId.of("UTC")),
                invoice.getLines().get(0).getUpdated()
        );

        // Presentation
        assertEquals("dd/mm/yyyy", invoice.getPresentation().getDateFormat());
        assertEquals("", invoice.getPresentation().getImageLogoSrc());
        assertEquals("modern", invoice.getPresentation().getThemeFontName());
        assertEquals("simple", invoice.getPresentation().getThemeLayout());
        assertEquals("#663399", invoice.getPresentation().getThemePrimaryColor());
    }

    @Test
    public void listInvoices() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/list_invoices_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/invoices/invoices")).thenReturn(mockRequest);

        Invoices invoices = new Invoices(mockedFreshBooksClient);
        InvoiceList invoiceList = invoices.list("ABC123");

        assertEquals(1, invoiceList.getPages().getTotal());
        assertEquals(987654, invoiceList.getInvoices().get(0).getId());
        assertEquals(12345, invoiceList.getInvoices().get(0).getCustomerId());
    }

    @Test
    public void createInvoice_dataMap() throws FreshBooksException, IOException {
        long clientId = 12345;
        Map<String, Object> data = ImmutableMap.of("customerid", clientId);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_invoice_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/accounting/account/ABC123/invoices/invoices",
                ImmutableMap.of("invoice", data))
        ).thenReturn(mockRequest);

        Invoices invoices = new Invoices(mockedFreshBooksClient);
        Invoice invoice = invoices.create("ABC123", data);

        assertEquals(987654, invoice.getId());
        assertEquals(clientId, invoice.getCustomerId());
    }

    @Test
    public void createInvoice_invoiceObject() throws FreshBooksException, IOException {
        long clientId = 12345;
        LineItem line = new LineItem();
        line.setName("Bowls");
        line.setUnitCost(new Money(new BigDecimal("20.00"), "CAD"));
        line.setQuantity(new BigDecimal(4));

        Invoice data = new Invoice();
        data.setCustomerId(clientId);
        data.setLines(ImmutableList.of(line));

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_invoice_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/accounting/account/ABC123/invoices/invoices",
                ImmutableMap.of("invoice", data.getContent()))
        ).thenReturn(mockRequest);

        Invoices invoices = new Invoices(mockedFreshBooksClient);
        Invoice invoice = invoices.create("ABC123", data);

        assertEquals(987654, invoice.getId());
        assertEquals(clientId, invoice.getCustomerId());
        assertEquals("Bowls", invoice.getLines().get(0).getName());
    }

    @Test
    public void updateInvoice_dataMap() throws FreshBooksException, IOException {
        long invoiceId = 987654;
        Map<String, Object> data = ImmutableMap.of("customerid", 12346);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_invoice_response.json");
        jsonResponse = jsonResponse.replace("\"customerid\": 12345", "\"customerid\": 12346");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/accounting/account/ABC123/invoices/invoices/987654",
                ImmutableMap.of("invoice", data))
        ).thenReturn(mockRequest);

        Invoices invoices = new Invoices(mockedFreshBooksClient);
        Invoice invoice = invoices.update("ABC123", invoiceId, data);

        assertEquals(invoiceId, invoice.getId());
        assertEquals(12346, invoice.getCustomerId());
    }

    @Test
    public void updateInvoice_invoiceObject() throws FreshBooksException, IOException {
        long invoiceId = 987654;
        Invoice data = new Invoice();
        data.setCustomerId(12346);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_invoice_response.json");
        jsonResponse = jsonResponse.replace("\"customerid\": 12345", "\"customerid\": 12346");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/accounting/account/ABC123/invoices/invoices/987654",
                ImmutableMap.of("invoice", data.getContent()))
        ).thenReturn(mockRequest);

        Invoices invoices = new Invoices(mockedFreshBooksClient);
        Invoice invoice = invoices.update("ABC123", invoiceId, data);

        assertEquals(invoiceId, invoice.getId());
        assertEquals(12346, invoice.getCustomerId());
    }

    @Test
    public void deleteInvoice() throws FreshBooksException, IOException {
        long invoiceId = 987654;

        String jsonResponse = "{\n\"response\": {}\n}";

        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.DELETE,
                "/accounting/account/ABC123/invoices/invoices/987654",
                null)
        ).thenReturn(mockRequest);


        Invoices invoices = new Invoices(mockedFreshBooksClient);
        invoices.delete("ABC123", invoiceId);
    }
}
