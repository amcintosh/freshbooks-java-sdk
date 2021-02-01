package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.common.collect.ImmutableList;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.TestUtil;
import net.amcintosh.freshbooks.models.Client;
import net.amcintosh.freshbooks.models.ClientList;
import org.junit.Test;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
        assertEquals("American Cyanamid", client.getOrganization());
        assertEquals(ZonedDateTime.of(2020, 11, 1, 18,
                11, 10, 0, ZoneId.of("UTC")),
                client.getUpdated()
        );
    }

    @Test
    public void getClient_notFound() throws IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/get_client_response__not_found.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(404, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/users/clients/12345", null)).thenReturn(mockRequest);

        long clientId = 12345;
        Clients clients = new Clients(mockedFreshBooksClient);

        try {
            clients.get("ABC123", clientId);
        } catch (FreshBooksException e) {
            assertEquals(404, e.statusCode);
            assertEquals("Client not found.", e.getMessage());
            assertEquals(1012, e.errorNo);
            assertEquals("userid", e.field);
            assertEquals("client", e.object);
            assertEquals("12345", e.value);
        }
    }

    @Test
    public void getClient_badResponse() throws IOException {
        String jsonResponse = "stuff";
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(500, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/users/clients/12345", null)).thenReturn(mockRequest);

        long clientId = 12345;
        Clients clients = new Clients(mockedFreshBooksClient);

        try {
            clients.get("ABC123", clientId);
        } catch (FreshBooksException e) {
            assertEquals(500, e.statusCode);
            assertEquals("Returned an unexpected response", e.getMessage());
            assertEquals(0, e.errorNo);
            assertNull(e.field);
            assertNull(e.object);
            assertNull(e.value);
        }
    }

    @Test
    public void getClient_missingResponse() throws IOException {
        String jsonResponse = "{\"foo\": \"bar\"}";
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/users/clients/12345", null)).thenReturn(mockRequest);

        long clientId = 12345;
        Clients clients = new Clients(mockedFreshBooksClient);

        try {
            clients.get("ABC123", clientId);
        } catch (FreshBooksException e) {
            assertEquals(200, e.statusCode);
            assertEquals("Returned an unexpected response", e.getMessage());
            assertEquals(0, e.errorNo);
            assertNull(e.field);
            assertNull(e.object);
            assertNull(e.value);
        }
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
    public void listClients_noMatchingClients() throws FreshBooksException, IOException {
        String jsonResponse = "{\"response\": {\"result\": {\"clients\": [], " +
                "\"page\": 1,\"pages\": 0,\"per_page\": 15,\"total\": 0} } }";
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/users/clients")).thenReturn(mockRequest);

        Clients clients = new Clients(mockedFreshBooksClient);
        ClientList clientList = clients.list("ABC123");


        assertEquals(0, clientList.getPages().getTotal());
        assertEquals(ImmutableList.of(), clientList.getClients());
    }

}