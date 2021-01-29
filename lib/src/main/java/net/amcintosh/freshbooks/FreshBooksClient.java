package net.amcintosh.freshbooks;


import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Map;
import java.util.Properties;

/**
 * FreshBooks API client.
 *
 * ```java
 * FreshBooksClient freshBooksClient = new FreshBooksClient.FreshBooksClientBuilder("your application id")
 *     .withToken("a valid token")
 *     .build();
 * ```
 */
public class FreshBooksClient {
    private static final Logger log = LoggerFactory.getLogger(FreshBooksClient.class);
    private static final JsonFactory JSON_FACTORY = new GsonFactory();

    private static final String API_TOKEN_URL = "auth/oauth/token";
    private static final String AUTH_URL = "/service/auth/oauth/authorize";
    private final static String VERSION_PROPERTIES = "version.properties";

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    private final String baseUrl;
    private final String authorizationUrl;
    private final String tokenUrl;

    private String accessToken;
    private String refreshToken;
    private Instant accessTokenExpiresAt; //ofEpochSecond(long)

    private final String userAgent;
    private static String version;

    private final int connectTimeout;
    private final int readTimeout;
    private final int writeTimeout;

    /**
     * Create a new API client instance from the FreshBooks client builder.
     *
     * Requires a `clientId`.
     *
     * Specifying a `clientSecret` and `redirectUri` will allow you to follow the authentication flow to get an
     * `accessToken`.
     *
     * Alternatively, you can provide an `accessToken` directly, in which case then you don't need to specify a
     * `clientSecret` (though the token cannot be refreshed in this case).
     *
     * @param builder Instance of FreshBooksClientBuilder
     */
    private FreshBooksClient(FreshBooksClientBuilder builder) {
        this.baseUrl = builder.baseUrl;
        this.authorizationUrl = builder.authorizationUrl;
        this.tokenUrl = builder.tokenUrl;

        this.clientId = builder.clientId;
        this.clientSecret = builder.clientSecret;
        this.redirectUri = builder.redirectUri;
        this.accessToken = builder.accessToken;
        this.refreshToken = builder.refreshToken;
        if (builder.userAgent != null) {
            this.userAgent = builder.userAgent;
        } else {
            this.userAgent = String.format("FreshBooks java sdk/%s client_id %s", this.getVersion(), this.clientId);
        }
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
    }

    /**
     * Get the freshbooks-sdk version.
     *
     * @return the version number
     */
    public String getVersion() {
        if (version != null) {
            return version;
        }
        InputStream inputStream = null;
        Properties props = new Properties();
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(VERSION_PROPERTIES);
            props.load(inputStream);
        } catch (Throwable t) {
            throw new RuntimeException("Error loading " + VERSION_PROPERTIES, t);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.warn("Unable to close version input stream");
                }
            }
        }
        version = props.getProperty("version", "unknown");
        return version;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", redirectUri='" + redirectUri + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                ", authorizationUrl='" + authorizationUrl + '\'' +
                ", tokenUrl='" + tokenUrl + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", accessTokenExpiresAt=" + accessTokenExpiresAt +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }

    /**
     *
     * @param requestMethod GET, POST, PUT, DELETE
     * @param resourceUrl Relative URL (eg. /accounting/account/{accountId}/users/clients
     * @return HttpRequest object
     * @throws IOException
     */
    public HttpRequest request(String requestMethod, String resourceUrl) throws IOException {
        return this.request(requestMethod, resourceUrl, null);
    }

    /**
     *
     * @param requestMethod GET, POST, PUT, DELETE
     * @param resourceUrl Relative URL (eg. /accounting/account/{accountId}/users/clients
     * @param data
     * @return HttpRequest object
     * @throws IOException
     */
    public HttpRequest request(String requestMethod, String resourceUrl, @Nullable Map<String, Object> data) throws IOException {
        GenericUrl requestUrl = new GenericUrl(this.baseUrl + resourceUrl);
        HttpHeaders requestHeaders = new HttpHeaders()
                .setAuthorization("Bearer " + this.accessToken)
                .setUserAgent(this.userAgent);

        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory(
                new HttpRequestInitializer() {
                    @Override
                    public void initialize(HttpRequest request) {
                        request.setParser(new JsonObjectParser(JSON_FACTORY));
                    }
                });

        HttpRequest request;
        HttpContent content = null;
        if (data != null) {
            content = new JsonHttpContent(JSON_FACTORY, data);
        }
        request = requestFactory.buildRequest(requestMethod, requestUrl, content)
                .setHeaders(requestHeaders)
                .setConnectTimeout(this.connectTimeout)
                .setReadTimeout(this.readTimeout)
                .setWriteTimeout(this.writeTimeout)
                .setThrowExceptionOnExecuteError(false);
        return request;
    }

    public static class FreshBooksClientBuilder {
        private static final String API_BASE_URL = "https://api.freshbooks.com";
        private static final String AUTH_BASE_URL = "https://auth.freshbooks.com";
        private static final int DEFAULT_TIMEOUT = 20000;
        private static final int DEFAULT_WRITE_TIMEOUT = 60000;

        private String baseUrl;
        private String authorizationUrl;
        private String tokenUrl;

        private final String clientId;
        private String clientSecret;
        private String redirectUri;

        private String accessToken;
        private String refreshToken;

        private String userAgent;
        private int connectTimeout = -1;
        private int readTimeout = -1;
        private int writeTimeout = -1;

        /**
         * Builder for FreshBooksClient. Requires a `clientId`, which will then allow you to provide an `accessToken`
         * by appending the call to `withAccessToken()`.

         * @param clientId Your FreshBooks application client Id
         */
        public FreshBooksClientBuilder(String clientId) {
            this.clientId = clientId;
        }

        /**
         * Builder for FreshBooksClient. Requires a `clientId`, `clientSecret`, and `redirectUri` which will allow
         * you to follow the authentication flow to get an `accessToken`.
         *
         * @param clientId Your FreshBooks application client Id
         * @param clientSecret Your FreshBooks application client secret
         * @param redirectUri Where the user should be redirected to after authentication
         */
        public FreshBooksClientBuilder(String clientId, String clientSecret, String redirectUri) {
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            this.redirectUri = redirectUri;
        }

        /**
         * Initialize the client with an access token.
         *
         * @param accessToken A existing valid OAuth2 access token
         * @return The builder instance
         */
        public FreshBooksClientBuilder withAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        /**
         * Initialize the client with a refresh token.
         *
         * @param refreshToken A existing valid OAuth2 refresh token
         * @return The builder instance
         */
        public FreshBooksClientBuilder withRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        /**
         * Override the default user-agent header.
         *
         * @param userAgent String to pass for the user-agent header in requests.
         * @return The builder instance
         */
        public FreshBooksClientBuilder withUserAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        /**
         * Set the timeout in milliseconds to establish a connection (0 for infinite).
         *
         * Defaults to {@value #DEFAULT_TIMEOUT}.
         *
         * @param timeout Connect timeout in milliseconds
         * @return The builder instance
         */
        public FreshBooksClientBuilder withConnectTimeout(int timeout) {
            this.connectTimeout = timeout;
            return this;
        }

        /**
         * Set the timeout in milliseconds to read data from a connection (0 for infinite).
         *
         * Defaults to {@value #DEFAULT_TIMEOUT}.
         *
         * @param timeout Read timeout in milliseconds
         * @return The builder instance
         */
        public FreshBooksClientBuilder withReadTimeout(int timeout) {
            this.readTimeout = timeout;
            return this;
        }

        /**
         * Set the timeout in milliseconds to send POST/PUT data (0 for infinite).
         *
         * Defaults to {@value #DEFAULT_WRITE_TIMEOUT}.
         *
         * @param timeout Write timeout in milliseconds
         * @return The builder instance
         */
        public FreshBooksClientBuilder withWriteTimeout(int timeout) {
            this.writeTimeout = timeout;
            return this;
        }

        private String getEnvDefault(String var, String defaultValue) {
            String value = System.getenv(var);
            if (value == null) {
                return defaultValue;
            }
            return value;
        }

        /**
         * Build the FreshBooksClient.
         *
         * @return A FreshBooksClient instance
         */
        public FreshBooksClient build() {
            this.baseUrl = this.getEnvDefault("FRESHBOOKS_API_URL", API_BASE_URL);
            this.authorizationUrl = this.getEnvDefault("FRESHBOOKS_AUTH_URL", AUTH_BASE_URL);
            this.tokenUrl = this.getEnvDefault("FRESHBOOKS_AUTH_URL", AUTH_BASE_URL);
            if (this.connectTimeout < 0) {
                this.connectTimeout = this.DEFAULT_TIMEOUT;
            }
            if (this.readTimeout < 0) {
                this.readTimeout = this.DEFAULT_TIMEOUT;
            }
            if (this.writeTimeout < 0) {
                this.writeTimeout = this.DEFAULT_WRITE_TIMEOUT;
            }
            return new FreshBooksClient(this);
        }
    }
}
