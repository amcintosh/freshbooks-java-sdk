package net.amcintosh.freshbooks;

import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.amcintosh.freshbooks.models.AuthorizationToken;
import net.amcintosh.freshbooks.resources.Authorization;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FreshBooksClientTest {

    @Test
    public void FreshBooksClientBuilder_WithTimeouts() throws IOException {
        FreshBooksClient freshBooksClient = new FreshBooksClient.FreshBooksClientBuilder("some_client_id")
                .withConnectTimeout(1)
                .withReadTimeout(2)
                .withWriteTimeout(3)
                .build();

        HttpRequest request = freshBooksClient.request(HttpMethods.GET, "http://some_url.amcintosh.net");

        assertEquals(1, request.getConnectTimeout());
        assertEquals(2, request.getReadTimeout());
        assertEquals(3, request.getWriteTimeout());
    }

    @Test
    public void FreshBooksClientBuilder_WithUserAgent() throws IOException {
        FreshBooksClient freshBooksClient = new FreshBooksClient.FreshBooksClientBuilder("some_client_id")
                .withUserAgent("my customer agent")
                .build();

        HttpRequest request = freshBooksClient.request(HttpMethods.GET, "http://some_url.amcintosh.net");

        assertEquals("my customer agent", request.getHeaders().getUserAgent());
    }

    @Test
    public void FreshBooksClientBuilder_DefaultUserAgent() throws IOException {
        FreshBooksClient freshBooksClient = new FreshBooksClient.FreshBooksClientBuilder("some_client_id").build();
        String version = freshBooksClient.getVersion();

        HttpRequest request = freshBooksClient.request(HttpMethods.GET, "http://some_url.amcintosh.net");

        String expected_agent = String.format("FreshBooks java sdk/%s client_id some_client_id", version);
        assertEquals(expected_agent, request.getHeaders().getUserAgent());
    }

    @Test
    public void FreshBooksClient_AuthTokenHeader() throws IOException {
        FreshBooksClient freshBooksClient = new FreshBooksClient.FreshBooksClientBuilder("some_client_id")
                .withAccessToken("my_valid_token")
                .build();

        HttpRequest request = freshBooksClient.request(HttpMethods.GET, "http://some_url.amcintosh.net");

        assertEquals("Bearer my_valid_token", request.getHeaders().getAuthorization());
    }

    @Test
    public void FreshBooksClient_getAuthRequestUrl() {
        FreshBooksClient freshBooksClient = new FreshBooksClient.FreshBooksClientBuilder(
                "some_client_id", "some_secret", "http://my.redirect.url"
        ).build();

        ImmutableList<String> scopes = ImmutableList.of(
                "user:invoices:read", "user:invoices:write", "user:clients:read"
        );

        assertEquals(
                "https://auth.freshbooks.com/service/auth/oauth/authorize?client_id=some_client_id"
                        + "&response_type=code&redirect_uri=http://my.redirect.url&scope=user:invoices:read "
                        + "user:invoices:write user:clients:read",
                freshBooksClient.getAuthRequestUri(scopes)
        );
    }

    @Test
    public void FreshBooksClient_getAuthRequestUrl_noScopes() {
        FreshBooksClient freshBooksClient = new FreshBooksClient.FreshBooksClientBuilder(
                "some_client_id", "some_secret", "http://my.redirect.url"
        ).build();

        assertEquals(
                "https://auth.freshbooks.com/service/auth/oauth/authorize?client_id=some_client_id"
                        + "&response_type=code&redirect_uri=http://my.redirect.url",
                freshBooksClient.getAuthRequestUri()
        );
    }

    @Test
    public void FreshBooksClient_getAccessToken() throws IOException, FreshBooksException {
        ImmutableMap<String, Object> data = ImmutableMap.of(
                "client_id", "some_client_id",
                "client_secret", "some_secret",
                "grant_type", "authorization_code",
                "redirect_uri", "https://my.redirect.url",
                "code", "some_code"
        );

        String jsonResponse = TestUtil.loadTestJson("fixtures/auth_token_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST, "/auth/oauth/token", data)).thenReturn(mockRequest);

        AuthorizationToken token = new Authorization(mockedFreshBooksClient).getToken(data);

        assertEquals("my_access_token", token.getAccessToken());
        assertEquals("my_refresh_token", token.getRefreshToken());
        assertEquals(ZonedDateTime.of(2021, 7, 26, 16,
                57, 6, 0, ZoneId.of("UTC")),
                token.getCreatedAt());
        assertEquals(100, token.getExpiresIn());
        assertEquals(ZonedDateTime.of(2021, 7, 26, 16,
                58, 46, 0, ZoneId.of("UTC")),
                token.getExpiresAt());
        assertEquals(ImmutableList.of("some:scope", "some:other:scope"), token.getScopes());
    }

    @Test
    public void FreshBooksClient_getAccessToken_badResponse() throws IOException, FreshBooksException {
        ImmutableMap<String, Object> data = ImmutableMap.of(
                "client_id", "some_client_id",
                "client_secret", "some_secret",
                "grant_type", "authorization_code",
                "redirect_uri", "https://my.redirect.url",
                "code", "some_code"
        );

        String jsonResponse = "stuff";
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(500, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST, "/auth/oauth/token", data)).thenReturn(mockRequest);

        try {
            new Authorization(mockedFreshBooksClient).getToken(data);
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
    public void FreshBooksClient_RequestContent() throws IOException {
        FreshBooksClient freshBooksClient = new FreshBooksClient.FreshBooksClientBuilder("some_client_id").build();

        Map<String, Object> data = ImmutableMap.of("foo", "bar");
        HttpRequest request = freshBooksClient.request(HttpMethods.GET, "http://some_url.amcintosh.net", data);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpContent content = request.getContent();
        content.writeTo(out);

        assertEquals("{\"foo\":\"bar\"}", out.toString());
    }
}
