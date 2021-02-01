package net.amcintosh.freshbooks;

import com.google.api.client.http.*;
import com.google.api.client.json.Json;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.testing.http.HttpTesting;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;

public class TestUtil {

    public static String loadTestJson(String fileName) {
        URL url = Resources.getResource(fileName);
        try {
            String data = Resources.toString(url, Charsets.UTF_8);
            return data;
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load a JSON file.", e);
        }
    }

    public static HttpRequest buildMockHttpRequest(int statusCode, String jsonResponse) {
        HttpTransport transport = new MockHttpTransport() {
            @Override
            public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                return new MockLowLevelHttpRequest() {
                    @Override
                    public LowLevelHttpResponse execute() throws IOException {
                        MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                        response.setContentType(Json.MEDIA_TYPE);
                        response.setStatusCode(statusCode);
                        response.setContent(jsonResponse);
                        return response;
                    }
                };
            }
        };
        try {
            HttpRequestFactory requestFactory = transport.createRequestFactory(
                    new HttpRequestInitializer() {
                        @Override
                        public void initialize(HttpRequest request) {
                            request.setParser(new JsonObjectParser(new GsonFactory()));
                        }
                    });
            return requestFactory.buildGetRequest(HttpTesting.SIMPLE_GENERIC_URL)
                    .setThrowExceptionOnExecuteError(false);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't create Mock Http Request.", e);
        }

    }

}