package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.TestUtil;
import net.amcintosh.freshbooks.models.Client;
import net.amcintosh.freshbooks.models.ClientList;
import net.amcintosh.freshbooks.models.VisState;
import org.junit.Test;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientsTest {

    @Test
    public void getClient() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/get_client_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/users/clients/12345", null)).thenReturn(mockRequest);

        long clientId = 12345;
        Clients clients = new Clients(mockedFreshBooksClient);
        Client client = clients.get("ABC123", clientId);

        assertEquals(clientId, client.getId());
        assertEquals(clientId, client.get("id"));
        assertEquals("ACM123", client.getAccountingSystemId());
        assertEquals("416-867-5309", client.getBusinessPhone());
        assertEquals("", client.getCompanyIndustry());
        assertEquals("", client.getCompanySize());
        assertEquals("CAD", client.getCurrencyCode());
        assertEquals("gordon.shumway@AmericanCyanamid.com", client.getEmail());
        assertEquals("416-444-4444", client.getFax());
        assertEquals("Gordon", client.getFirstName());
        assertEquals("416-444-4445", client.getHomePhone());
        assertEquals("en", client.getLanguage());
        assertEquals("", client.getLastActivity());
        assertEquals("Shumway", client.getLastName());
        assertEquals("416-444-4446", client.getMobilePhone());
        assertEquals("I like cats", client.getNote());
        assertEquals("American Cyanamid", client.getOrganization());
        assertEquals("Toronto", client.getBillingCity());
        assertEquals("M5T 2B3", client.getBillingCode());
        assertEquals("Canada", client.getBillingCountry());
        assertEquals("ON", client.getBillingProvince());
        assertEquals("123 Huron St", client.getBillingStreet());
        assertEquals("", client.getBillingStreet2());
        assertEquals("", client.getShippingCity());
        assertEquals("", client.getShippingCode());
        assertEquals("", client.getShippingCountry());
        assertEquals("", client.getShippingProvince());
        assertEquals("", client.getShippingStreet());
        assertEquals("", client.getShippingStreet2());
        assertEquals(ZonedDateTime.of(2020, 10, 31, 15,
                25, 34, 0, ZoneId.of("UTC")),
                client.getSignupDate());
        assertEquals(ZonedDateTime.of(2020, 11, 1, 18,
                11, 10, 0, ZoneId.of("UTC")),
                client.getUpdated()
        );
        assertEquals(clientId, client.getUserId());
        assertEquals("", client.getVatName());
        assertEquals("", client.getVatNumber());
        assertEquals(VisState.ACTIVE, client.getVisState());
    }

    @Test
    public void listClients() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/list_clients_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/users/clients")).thenReturn(mockRequest);

        Clients clients = new Clients(mockedFreshBooksClient);
        ClientList clientList = clients.list("ABC123");

        ImmutableList<Integer> clientIds = ImmutableList.of(12345, 12346, 12457);

        assertEquals(3, clientList.getPages().getTotal());
        assertEquals("American Cyanamid", clientList.getClients().get(0).getOrganization());
        for (int i=0; i<clientIds.size(); i++) {
            assertEquals(clientIds.get(i).longValue(), clientList.getClients().get(i).getId());
        }
    }

    @Test
    public void createClient_dataMap() throws FreshBooksException, IOException {
        String email = "john.doe@abcorp.com";
        Map<String, Object> data = ImmutableMap.of("email", email);

        String jsonResponse = TestUtil.loadTestJson("fixtures/create_client_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/accounting/account/ABC123/users/clients",
                ImmutableMap.of("client", data))
        ).thenReturn(mockRequest);


        Clients clients = new Clients(mockedFreshBooksClient);
        Client client = clients.create("ABC123", data);

        assertEquals(56789, client.getId());
        assertEquals(email, client.getEmail());
    }

    @Test
    public void createClient_clientObject() throws FreshBooksException, IOException {
        String email = "john.doe@abcorp.com";
        Client data = new Client();
        data.setEmail(email);

        String jsonResponse = TestUtil.loadTestJson("fixtures/create_client_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/accounting/account/ABC123/users/clients",
                ImmutableMap.of("client", data.getContent()))
        ).thenReturn(mockRequest);


        Clients clients = new Clients(mockedFreshBooksClient);
        Client client = clients.create("ABC123", data);

        assertEquals(56789, client.getId());
        assertEquals(email, client.getEmail());
    }

    @Test
    public void updateClient_dataMap() throws FreshBooksException, IOException {
        long clientId = 56789;
        String email = "john.doe@abcorp.com";
        Map<String, Object> data = ImmutableMap.of("email", email);

        String jsonResponse = TestUtil.loadTestJson("fixtures/create_client_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/accounting/account/ABC123/users/clients/56789",
                ImmutableMap.of("client", data))
        ).thenReturn(mockRequest);


        Clients clients = new Clients(mockedFreshBooksClient);
        Client client = clients.update("ABC123", clientId, data);

        assertEquals(clientId, client.getId());
        assertEquals(email, client.getEmail());
    }

    @Test
    public void updateClient_clientObject() throws FreshBooksException, IOException {
        long clientId = 56789;
        String email = "john.doe@abcorp.com";
        Client data = new Client();
        data.setEmail(email);

        String jsonResponse = TestUtil.loadTestJson("fixtures/create_client_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/accounting/account/ABC123/users/clients/56789",
                ImmutableMap.of("client", data.getContent()))
        ).thenReturn(mockRequest);


        Clients clients = new Clients(mockedFreshBooksClient);
        Client client = clients.update("ABC123", clientId, data);

        assertEquals(clientId, client.getId());
        assertEquals(email, client.getEmail());
    }

    @Test
    public void deleteClient() throws FreshBooksException, IOException {
        long clientId = 12345;

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_client_response.json");
        jsonResponse = jsonResponse.replace("\"vis_state\": 0", "\"vis_state\": 1");

        ImmutableMap<String, Object> data = ImmutableMap.of("vis_state", VisState.DELETED.getValue());

        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/accounting/account/ABC123/users/clients/12345",
                ImmutableMap.of("client", data))
        ).thenReturn(mockRequest);


        Clients clients = new Clients(mockedFreshBooksClient);
        Client client = clients.delete("ABC123", clientId);

        assertEquals(clientId, client.getId());
        assertEquals(VisState.DELETED, client.getVisState());
    }
}
