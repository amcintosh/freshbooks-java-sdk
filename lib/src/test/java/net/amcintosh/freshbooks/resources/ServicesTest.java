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
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicesTest {

    @Test
    public void getService() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/get_service_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/comments/business/25/service/4054453", null)).thenReturn(mockRequest);

        long serviceId = 4054453;
        Services services = new Services(mockedFreshBooksClient);
        Service service = services.get(25, serviceId);

        assertEquals(25, service.getBusinessId());
        assertEquals(serviceId, service.getId());
        assertEquals("Document Review", service.getName());
        assertTrue(service.isBillable());
        assertEquals(VisState.ACTIVE, service.getVisState());
    }

    @Test
    public void listServices() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/list_services_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/comments/business/25/services")).thenReturn(mockRequest);

        Services services = new Services(mockedFreshBooksClient);
        ServiceList serviceList = services.list(25);

        assertEquals(2, serviceList.getPages().getTotal());
        assertEquals(4054453, serviceList.getServices().get(0).getId());
        assertEquals("Document Review", serviceList.getServices().get(0).getName());
    }

    @Test
    public void createService_dataMap() throws FreshBooksException, IOException {
        String serviceName = "Document Review";
        Map<String, Object> data = ImmutableMap.of("name", serviceName);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_service_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/comments/business/25/service",
                ImmutableMap.of("service", data))
        ).thenReturn(mockRequest);

        Services services = new Services(mockedFreshBooksClient);
        Service service = services.create(25, data);

        assertEquals(4054453, service.getId());
        assertEquals(serviceName, service.getName());
    }

}
