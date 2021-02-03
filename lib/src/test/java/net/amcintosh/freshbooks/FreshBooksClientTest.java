package net.amcintosh.freshbooks;

import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

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
    public void FreshBooksClient_RequestContent() throws IOException {
        FreshBooksClient freshBooksClient = new FreshBooksClient.FreshBooksClientBuilder("some_client_id").build();

        Map<String, Object> data = ImmutableMap.of("foo", "bar");
        HttpRequest request = freshBooksClient.request(HttpMethods.GET, "http://some_url.amcintosh.net", data);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpContent content = request.getContent();
        content.writeTo(out);

        assertEquals("{\"foo\":\"bar\"}", new String(out.toByteArray()));
    }
}
