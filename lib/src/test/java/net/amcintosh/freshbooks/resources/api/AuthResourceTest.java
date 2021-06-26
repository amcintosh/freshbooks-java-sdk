package net.amcintosh.freshbooks.resources.api;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.TestUtil;
import net.amcintosh.freshbooks.models.Identity;
import net.amcintosh.freshbooks.resources.Clients;
import net.amcintosh.freshbooks.resources.CurrentUser;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthResourceTest {

    @Test
    public void getResource_noAuthorization() throws IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/auth_me_response__no_auth.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(401, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/auth/api/v1/users/me")).thenReturn(mockRequest);

        CurrentUser currentUser = new CurrentUser(mockedFreshBooksClient);


        try {
            currentUser.get();
        } catch (FreshBooksException e) {
            assertEquals(401, e.statusCode);
            assertEquals("This action requires authentication to continue.", e.getMessage());
            assertEquals(0, e.errorNo);
            assertEquals(null, e.field);
            assertEquals(null, e.object);
            assertEquals(null, e.value);
        }
    }
}
