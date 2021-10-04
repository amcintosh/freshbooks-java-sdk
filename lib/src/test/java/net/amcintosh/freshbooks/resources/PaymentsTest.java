package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.util.Key;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentsTest {

    @Test
    public void getPayment() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/get_payment_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/payments/payments/235435", null)).thenReturn(mockRequest);

        long paymentId = 235435;
        Payments payments = new Payments(mockedFreshBooksClient);
        Payment payment = payments.get("ABC123", paymentId);

        assertEquals(paymentId, payment.getId());
        assertEquals(paymentId, payment.getPaymentId());
        assertEquals("ACM123", payment.getAccountingSystemId());
        assertEquals(new BigDecimal("41.94"), payment.getAmount().getAmount());
        assertEquals("CAD", payment.getAmount().getCode());
        assertEquals(0, payment.getBulkPaymentId());
        assertEquals(12345, payment.getClientId());
        assertEquals(0, payment.getCreditId());
        assertEquals(LocalDate.of(2021, 4, 16),
                payment.getDate()
        );
        assertEquals(false, payment.isFromCredit());
        assertEquals("", payment.getGateway());
        assertEquals(987654, payment.getInvoiceId());
        assertEquals("Some note", payment.getNote());
        assertEquals(0, payment.getOrderId());
        assertEquals(0, payment.getOverpaymentId());
        assertEquals(true, payment.isSendClientNotification());

        assertEquals(Payment.PaymentType.CHECK, payment.getType());
        assertEquals(ZonedDateTime.of(2021, 4, 17, 9,
                29, 31, 0, ZoneId.of("UTC")),
                payment.getUpdated()
        );
        assertEquals(VisState.ACTIVE, payment.getVisState());
    }

    @Test
    public void listPayments() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/list_payments_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/payments/payments")).thenReturn(mockRequest);

        Payments payments = new Payments(mockedFreshBooksClient);
        PaymentList paymentList = payments.list("ABC123");

        assertEquals(2, paymentList.getPages().getTotal());
        assertEquals(235435, paymentList.getPayments().get(0).getId());
        assertEquals("Some note", paymentList.getPayments().get(0).getNote());
        assertEquals(235436, paymentList.getPayments().get(1).getId());
        assertEquals("Some other note", paymentList.getPayments().get(1).getNote());
    }

    @Test
    public void createPayment_dataMap() throws FreshBooksException, IOException {
        String paymentNote = "Some note";
        Map<String, Object> data = ImmutableMap.of("note", paymentNote);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_payment_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/accounting/account/ABC123/payments/payments",
                ImmutableMap.of("payment", data))
        ).thenReturn(mockRequest);

        Payments payments = new Payments(mockedFreshBooksClient);
        Payment payment = payments.create("ABC123", data);

        assertEquals(235435, payment.getId());
        assertEquals(paymentNote, payment.getNote());
    }

    @Test
    public void createPayment_paymentObject() throws FreshBooksException, IOException {
        String paymentNote = "Some note";
        Payment data = new Payment();
        data.setNote(paymentNote);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_payment_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/accounting/account/ABC123/payments/payments",
                ImmutableMap.of("payment", data.getContent()))
        ).thenReturn(mockRequest);

        Payments payments = new Payments(mockedFreshBooksClient);
        Payment payment = payments.create("ABC123", data);

        assertEquals(235435, payment.getId());
        assertEquals(paymentNote, payment.getNote());
    }

    @Test
    public void updatePayment_dataMap() throws FreshBooksException, IOException {
        long paymentId = 235435;
        String newPaymentNote = "Some new note";
        Map<String, Object> data = ImmutableMap.of("note", newPaymentNote);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_payment_response.json");
        jsonResponse = jsonResponse.replace("Some note", newPaymentNote);
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/accounting/account/ABC123/payments/payments/235435",
                ImmutableMap.of("payment", data))
        ).thenReturn(mockRequest);

        Payments payments = new Payments(mockedFreshBooksClient);
        Payment payment = payments.update("ABC123", paymentId, data);

        assertEquals(paymentId, payment.getId());
        assertEquals(newPaymentNote, payment.getNote());
    }

    @Test
    public void updatePayment_paymentObject() throws FreshBooksException, IOException {
        long paymentId = 235435;
        String newPaymentNote = "Some new note";
        Payment data = new Payment();
        data.setNote(newPaymentNote);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_payment_response.json");
        jsonResponse = jsonResponse.replace("Some note", newPaymentNote);
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/accounting/account/ABC123/payments/payments/235435",
                ImmutableMap.of("payment", data.getContent()))
        ).thenReturn(mockRequest);

        Payments payments = new Payments(mockedFreshBooksClient);
        Payment payment = payments.update("ABC123", paymentId, data);

        assertEquals(paymentId, payment.getId());
        assertEquals(newPaymentNote, payment.getNote());
    }

    @Test
    public void deletePayment() throws FreshBooksException, IOException {
        long paymentId = 235435;

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_payment_response.json");
        jsonResponse = jsonResponse.replace("\"vis_state\": 0", "\"vis_state\": 1");

        ImmutableMap<String, Object> data = ImmutableMap.of("vis_state", VisState.DELETED.getValue());

        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/accounting/account/ABC123/payments/payments/235435",
                ImmutableMap.of("payment", data))
        ).thenReturn(mockRequest);


        Payments payments = new Payments(mockedFreshBooksClient);
        Payment payment = payments.delete("ABC123", paymentId);

        assertEquals(paymentId, payment.getId());
        assertEquals(VisState.DELETED, payment.getVisState());
    }
}
