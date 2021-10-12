package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.common.collect.ImmutableMap;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.TestUtil;
import net.amcintosh.freshbooks.models.ServiceRate;
import net.amcintosh.freshbooks.models.ServiceRateList;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServiceRatesTest {

    @Test
    public void getServiceRate() throws FreshBooksException, IOException {
        long serviceId = 4054453;

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_service_rate_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/comments/business/25/service/4054453/rate", null)).thenReturn(mockRequest);

        ServiceRates rates = new ServiceRates(mockedFreshBooksClient);
        ServiceRate rate = rates.get(25, serviceId);

        assertEquals(serviceId, rate.getServiceId());
        assertEquals(25, rate.getBusinessId());
        assertEquals(new BigDecimal("10.00"), rate.getRate());
    }

    @Test
    public void listServiceRates() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/list_service_rates_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/comments/business/25/service_rates")).thenReturn(mockRequest);

        ServiceRates rates = new ServiceRates(mockedFreshBooksClient);
        ServiceRateList rateList = rates.list(25);

        assertEquals(2, rateList.getServiceRates().size());
        assertEquals(new BigDecimal("10.00"), rateList.getServiceRates().get(0).getRate());
        assertEquals(new BigDecimal("8.50"), rateList.getServiceRates().get(1).getRate());
    }

    @Test
    public void createServiceRate_dataMap() throws FreshBooksException, IOException {
        long serviceId = 4054453;
        BigDecimal rate = new BigDecimal("10.00");
        Map<String, Object> data = ImmutableMap.of("rate", rate);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_service_rate_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/comments/business/25/service/4054453/rate",
                ImmutableMap.of("service_rate", data))
        ).thenReturn(mockRequest);

        ServiceRates rates = new ServiceRates(mockedFreshBooksClient);
        ServiceRate serviceRate = rates.create(25, serviceId, data);

        assertEquals(serviceId, serviceRate.getServiceId());
        assertEquals(25, serviceRate.getBusinessId());
        assertEquals(new BigDecimal("10.00"), serviceRate.getRate());
    }

    @Test
    public void createServiceRate_serviceRateObject() throws FreshBooksException, IOException {
        long serviceId = 4054453;
        BigDecimal rate = new BigDecimal("10.00");
        ServiceRate data = new ServiceRate();
        data.setRate(rate);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_service_rate_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/comments/business/25/service/4054453/rate",
                ImmutableMap.of("service_rate", data.getContent()))
        ).thenReturn(mockRequest);

        ServiceRates rates = new ServiceRates(mockedFreshBooksClient);
        ServiceRate serviceRate = rates.create(25, serviceId, data);

        assertEquals(serviceId, serviceRate.getServiceId());
        assertEquals(25, serviceRate.getBusinessId());
        assertEquals(new BigDecimal("10.00"), serviceRate.getRate());
    }

    @Test
    public void updateServiceRate_dataMap() throws FreshBooksException, IOException {
        long serviceId = 4054453;
        BigDecimal rate = new BigDecimal("15.50");
        Map<String, Object> data = ImmutableMap.of("rate", rate);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_service_rate_response.json");
        jsonResponse = jsonResponse.replace("10.00", "15.50");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/comments/business/25/service/4054453/rate",
                ImmutableMap.of("service_rate", data))
        ).thenReturn(mockRequest);

        ServiceRates rates = new ServiceRates(mockedFreshBooksClient);
        ServiceRate serviceRate = rates.update(25, serviceId, data);

        assertEquals(serviceId, serviceRate.getServiceId());
        assertEquals(rate, serviceRate.getRate());
    }

    @Test
    public void updateServiceRate_serviceRateObject() throws FreshBooksException, IOException {
        long serviceId = 4054453;
        BigDecimal rate = new BigDecimal("15.50");
        ServiceRate data = new ServiceRate();
        data.setRate(rate);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_service_rate_response.json");
        jsonResponse = jsonResponse.replace("10.00", "15.50");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/comments/business/25/service/4054453/rate",
                ImmutableMap.of("service_rate", data.getContent()))
        ).thenReturn(mockRequest);

        ServiceRates rates = new ServiceRates(mockedFreshBooksClient);
        ServiceRate serviceRate = rates.update(25, serviceId, data);

        assertEquals(serviceId, serviceRate.getServiceId());
        assertEquals(rate, serviceRate.getRate());
    }
}
