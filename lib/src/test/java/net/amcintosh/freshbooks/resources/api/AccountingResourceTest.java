package net.amcintosh.freshbooks.resources.api;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.common.collect.ImmutableList;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.TestUtil;
import net.amcintosh.freshbooks.models.Client;
import net.amcintosh.freshbooks.models.ClientList;
import net.amcintosh.freshbooks.models.builders.IncludesQueryBuilder;
import net.amcintosh.freshbooks.models.builders.PaginationQueryBuilder;
import net.amcintosh.freshbooks.models.builders.QueryBuilder;
import net.amcintosh.freshbooks.resources.Clients;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountingResourceTest {

    @Test
    public void getClient_includes() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/get_client_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/users/clients/12345" +
                "?include[]=late_reminders&include[]=last_activity", null)).thenReturn(mockRequest);

        IncludesQueryBuilder includes = new IncludesQueryBuilder().include("late_reminders").include("last_activity");
        long clientId = 12345;
        Clients clients = new Clients(mockedFreshBooksClient);
        Client client = clients.get("ABC123", clientId, includes);

        assertEquals(clientId, client.getId());
        assertEquals("American Cyanamid", client.getOrganization());
    }

    @Test
    public void getResource_notFound() throws IOException {
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
    public void getResource_badResponse() throws IOException {
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
    public void getResource_missingResponse() throws IOException {
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
    public void listResource_noMatches() throws FreshBooksException, IOException {
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

    @Test
    public void listResource_paged() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/list_clients_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/users/clients?page=2&per_page=3")).thenReturn(mockRequest);

        List<QueryBuilder> builders = ImmutableList.of(new PaginationQueryBuilder(2,3));
        Clients clients = new Clients(mockedFreshBooksClient);
        ClientList clientList = clients.list("ABC123", builders);

        assertEquals(3, clientList.getPages().getTotal());
    }

    @Test
    public void listResource_includes() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/list_clients_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/users/clients" +
                "?include[]=late_reminders&include[]=last_activity")).thenReturn(mockRequest);

        IncludesQueryBuilder includes = new IncludesQueryBuilder().include("late_reminders").include("last_activity");
        List<QueryBuilder> builders = ImmutableList.of(includes);

        Clients clients = new Clients(mockedFreshBooksClient);
        clients.list("ABC123", builders);
    }
}
