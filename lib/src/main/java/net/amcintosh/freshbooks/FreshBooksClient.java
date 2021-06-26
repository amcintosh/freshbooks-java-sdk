package net.amcintosh.freshbooks;


import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import net.amcintosh.freshbooks.models.Identity;
import net.amcintosh.freshbooks.resources.*;
import net.amcintosh.freshbooks.resources.api.AuthResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * FreshBooks API client.
 *
 * <pre>{@code
 * FreshBooksClient freshBooksClient = new FreshBooksClient.FreshBooksClientBuilder("your application id")
 *     .withToken("a valid token")
 *     .build();
 * }</pre>
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
    private static Optional<String> version = Optional.empty();

    private final int connectTimeout;
    private final int readTimeout;
    private final int writeTimeout;

    /**
     * Create a new API client instance from the FreshBooks client builder.
     *
     * Requires a <code>clientId</code>.
     *
     * Specifying a <code>clientSecret</code> and <code>redirectUri</code> will allow
     * you to follow the authentication flow to get an <code>accessToken</code>.
     *
     * Alternatively, you can provide an <code>accessToken</code> directly, in which
     * case then you don't need to specify a <code>clientSecret</code>
     * (though the token cannot be refreshed in this case).
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
        this.userAgent = Optional.ofNullable(builder.userAgent).orElseGet(this::defaultUserAgent);
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
    }

    private String defaultUserAgent() {
        return String.format("FreshBooks java sdk/%s client_id %s", this.getVersion(), this.clientId);
    }

    /**
     * Get the freshbooks-sdk version.
     *
     * @return the version number
     */
    public String getVersion() {
        if (version.isPresent()) {
            return version.get();
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
        version = Optional.of(props.getProperty("version", "unknown"));
        return version.get();
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId='" + clientId + '\'' +
                '}';
    }

    /**
     * Make a request to FreshBooks.
     * @param requestMethod GET, POST, PUT, DELETE
     * @param resourceUrl Relative URL (eg. <code>/accounting/account/{accountId}/users/clients</code>)
     * @return HttpRequest object
     * @throws IOException
     */
    public HttpRequest request(String requestMethod, String resourceUrl) throws IOException {
        return this.request(requestMethod, resourceUrl, null);
    }

    /**
     * Make a request to FreshBooks with a data payload.
     *
     * @param requestMethod GET, POST, PUT, DELETE
     * @param resourceUrl Relative URL (eg. <code>/accounting/account/{accountId}/users/clients</code>)
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

    public Identity currentUser() throws FreshBooksException {
        return new CurrentUser(this).get();
    }

    /**
     * FreshBooks clients resource with calls to get, list, create, update, delete.
     *
     * @return Clients resource initialized with this FreshBooksClient
     */
    public Clients clients() {
        return new Clients(this);
    }

    /**
     * FreshBooks invoices resource with calls to get, list, create, update, delete.
     *
     * @return Invoices resource initialized with this FreshBooksClient
     */
    public Invoices invoices() {
        return new Invoices(this);
    }

    /**
     * FreshBooks projects resource with calls to get, list, create, update, delete.
     *
     * @return Projects resource initialized with this FreshBooksClient
     */
    public Projects projects() {
        return new Projects(this);
    }

    /**
     * FreshBooks taxes resource with calls to get, list, create, update, delete.
     *
     * @return Taxes resource initialized with this FreshBooksClient
     */
    public Taxes taxes() {
        return new Taxes(this);
    }


    /**
     * Builder for FreshBooksClient.
     */
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
         * Builder for FreshBooksClient. Requires a <code>clientId</code>, which will then allow you
         * to provide an <code>accessToken</code> by appending the call to <code>withAccessToken()</code>.
         *
         * @param clientId Your FreshBooks application client Id
         */
        public FreshBooksClientBuilder(String clientId) {
            this.clientId = clientId;
        }

        /**
         * Builder for FreshBooksClient. Requires a <code>clientId</code>, <code>clientSecret</code>, and
         * <code>redirectUri</code> which will allow you to follow the authentication flow to get an
         * <code>accessToken</code>.
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
