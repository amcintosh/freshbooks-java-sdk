package net.amcintosh.freshbooks;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.common.collect.ImmutableMap;
import net.amcintosh.freshbooks.models.AuthorizationToken;
import net.amcintosh.freshbooks.models.Identity;
import net.amcintosh.freshbooks.resources.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(FreshBooksClient.class);
    private static final JsonFactory JSON_FACTORY = new GsonFactory();

    private final static String VERSION_PROPERTIES = "version.properties";

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    private final String baseUrl;
    private final String authorizationUrl;
    private final String tokenUrl;

    private AuthorizationToken authorizationToken;

    private final String userAgent;
    private static Optional<String> version = Optional.empty();

    private final int connectTimeout;
    private final int readTimeout;
    private final int writeTimeout;
    private final boolean shouldRetry;

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
        this.authorizationToken = builder.authorizationToken;

        this.userAgent = Optional.ofNullable(builder.userAgent).orElseGet(this::defaultUserAgent);
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.shouldRetry = builder.shouldRetry;
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
                    LOGGER.warn("Unable to close version input stream");
                }
            }
        }
        version = Optional.of(props.getProperty("version", "unknown"));
        return version.get();
    }

    @Override
    public String toString() {
        return "Client{"
                + "clientId='" + clientId + '\''
                + '}';
    }

    /**
     * Returns the url that a client needs to request an oauth grant from the server.
     * <br><br>
     * To get an oauth access token, send your user to this URL. The user will be prompted to
     * log in to FreshBooks, after which they will be redirected to the `redirectUri` set on
     * the client with the access grant as a parameter. That grant can then be used to fetch an
     * access token by calling `getAccessToken`.
     * <br><br>
     * If scopes are not specified, then the access token will be given the default scopes your
     * application is registered for.
     * <br><br>
     * <i>Note:</i> The `redirectUri` must be one of the URLs your application is registered for.
     *
     * @see <a href="https://www.freshbooks.com/api/authentication">FreshBooks API - Authentication</a>
     *
     * @return The URL for the authorization request
     */
    public String getAuthRequestUri() {
        return new Authorization(this).getAuthRequestUri(
            authorizationUrl, clientId, clientSecret, redirectUri, null
        );
    }

    /**
     * Returns the url that a client needs to request an oauth grant from the server.
     * <br><br>
     * To get an oauth access token, send your user to this URL. The user will be prompted to
     * log in to FreshBooks, after which they will be redirected to the `redirectUri` set on
     * the client with the access grant as a parameter. That grant can then be used to fetch an
     * access token by calling `getAccessToken`.
     * <br><br>
     * If scopes are not specified, then the access token will be given the default scopes your
     * application is registered for.
     * <br><br>
     * <i>Note:</i> The `redirectUri` must be one of the URLs your application is registered for.
     *
     * @see <a href="https://www.freshbooks.com/api/authentication">FreshBooks API - Authentication</a>
     *
     * @param scopes  List of scopes if your want an access token with only a subset of your registered scopes.
     * @return The URL for the authorization request
     */
    public String getAuthRequestUri(List<String> scopes) {
        return new Authorization(this).getAuthRequestUri(authorizationUrl, clientId, clientSecret, redirectUri, scopes);
    }

    /**
     * Makes a call to the FreshBooks token URL to get an access_token.
     * <br><br>
     * This requires the access_grant code obtained after the user is redirected by the authorization
     * step. See {@link #getAuthRequestUri()}.
     * <br><br>
     * This call sets the authorization token details on the FreshBooksClient instance and then returns
     * those values in an {@link AuthorizationToken AuthorizationToken}.
     *
     * @param code access_grant code from the authorization redirect
     * @return
     */
    public AuthorizationToken getAccessToken(String code) throws FreshBooksException {
        ImmutableMap<String, Object> payload = ImmutableMap.of(
                "client_id", this.clientId,
                "client_secret", this.clientSecret,
                "grant_type", "authorization_code",
                "redirect_uri", this.redirectUri,
                "code", code
        );
        authorizationToken = new Authorization(this).getToken(payload);
        return authorizationToken;
    }

    /**
     * Makes a call to the FreshBooks token URL to refresh an access_token.
     * <br><br>
     * This requires the AuthorizationToken on the client to have a valid refreshToken
     * <br><br>
     * This call sets the authorization token details on the FreshBooksClient instance and then returns
     * those values in an {@link AuthorizationToken AuthorizationToken}.
     *
     * @return
     */
    public AuthorizationToken refreshAccessToken() throws FreshBooksException {
        ImmutableMap<String, Object> payload = ImmutableMap.of(
                "client_id", this.clientId,
                "client_secret", this.clientSecret,
                "grant_type", "refresh_token",
                "redirect_uri", this.redirectUri,
                "refresh_token", this.authorizationToken.getRefreshToken()
        );
        authorizationToken = new Authorization(this).getToken(payload);
        return authorizationToken;
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
    public HttpRequest request(String requestMethod, String resourceUrl, @Nullable Map<String, Object> data)
            throws IOException {
        GenericUrl requestUrl = new GenericUrl(this.baseUrl + resourceUrl);
        HttpHeaders requestHeaders = new HttpHeaders()
                .setAuthorization("Bearer " + this.authorizationToken.getAccessToken())
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
        if (this.shouldRetry) {
            request.setUnsuccessfulResponseHandler(this.createUnsuccessfulResponseHandler());
        }

        return request;
    }

    /**
     * Creates a UnsuccessfulResponseHandler to enable retries.
     * Handler will retry on any 5xx error or a 429 error and will use an
     * exponential backoff.
     *
     * @return Configured HttpUnsuccessfulResponseHandler
     */
    private HttpUnsuccessfulResponseHandler createUnsuccessfulResponseHandler() {
        HttpBackOffUnsuccessfulResponseHandler handler = new HttpBackOffUnsuccessfulResponseHandler(
                new ExponentialBackOff()
        );
        HttpBackOffUnsuccessfulResponseHandler.BackOffRequired ON_SERVER_ERROR_OR_RATE_LIMIT =
                new HttpBackOffUnsuccessfulResponseHandler.BackOffRequired() {
                    public boolean isRequired(HttpResponse response) {
                        int code = response.getStatusCode();
                        return (code == 429) || (code / 100 == 5);
                    }
                };
        handler.setBackOffRequired(ON_SERVER_ERROR_OR_RATE_LIMIT);
        return handler;
    }

    /**
     * The identity details of the currently authenticated user.
     *
     * @see <a href="https://www.freshbooks.com/api/me_endpoint">FreshBooks API - Business, Roles, and Identity</a>
     *
     * @throws FreshBooksException
     */
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
     * FreshBooks expenses resource with calls to get, list, create, update, delete.
     *
     * @return Expenses resource initialized with this FreshBooksClient
     */
    public Expenses expenses() {
        return new Expenses(this);
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
     * FreshBooks items resource with calls to get, list, create, update, delete.
     *
     * @return Items resource initialized with this FreshBooksClient
     */
    public Items items() {
        return new Items(this);
    }

    /**
     * FreshBooks other income resource with calls to get, list, create, update, delete.
     *
     * @return OtherIncomes resource initialized with this FreshBooksClient
     */
    public OtherIncomes otherIncomes() {
        return new OtherIncomes(this);
    }

    /**
     * FreshBooks payments resource with calls to get, list, create, update, delete.
     *
     * @return Payments resource initialized with this FreshBooksClient
     */
    public Payments payments() {
        return new Payments(this);
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
     * FreshBooks reports resource with calls the various reports available.
     * <br><br>
     * Eg. <code>freshbooksClient.reports.profitAndLoss(accountId)</code>
     *
     * @return Reports resource initialized with this FreshBooksClient
     */
    public AccountingReports reports() {
        return new AccountingReports(this);
    }

    /**
     * FreshBooks services resource with calls to get, list, create, update, delete.
     *
     * @return Services resource initialized with this FreshBooksClient
     */
    public Services services() {
        return new Services(this);
    }

    /**
     * FreshBooks service rates resource with calls to get, list, create, update.
     *
     * @return ServiceRates resource initialized with this FreshBooksClient
     */
    public ServiceRates serviceRates() {
        return new ServiceRates(this);
    }

    /**
     * FreshBooks tasks resource with calls to get, list, create, update, delete.
     *
     * @return Tasks resource initialized with this FreshBooksClient
     */
    public Tasks tasks() {
        return new Tasks(this);
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
     * FreshBooks time entries resource with calls to get, list, create, update, delete.
     *
     * @return TimeEntries resource initialized with this FreshBooksClient
     */
    public TimeEntries timeEntries() {
        return new TimeEntries(this);
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
        private String clientSecret = "";
        private String redirectUri = "";

        private AuthorizationToken authorizationToken = new AuthorizationToken();

        private String userAgent;
        private int connectTimeout = -1;
        private int readTimeout = -1;
        private int writeTimeout = -1;
        private boolean shouldRetry = true;

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
            this.authorizationToken = new AuthorizationToken(accessToken);
            return this;
        }

        /**
         * Initialize the client with a authorization token object containing
         * an valid access token, refresh token, and expiration details.
         *
         * @param authorizationToken An AuthorizationToken with existing valid OAuth2 tokens
         * @return The builder instance
         */
        public FreshBooksClientBuilder withAuthorizationToken(AuthorizationToken authorizationToken) {
            this.authorizationToken = authorizationToken;
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
         * Disables automatic retries on a 429 or 5xx response code.
         *
         * @return The builder instance
         */
        public FreshBooksClientBuilder withoutRetries() {
            this.shouldRetry = false;
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
                this.connectTimeout = DEFAULT_TIMEOUT;
            }
            if (this.readTimeout < 0) {
                this.readTimeout = DEFAULT_TIMEOUT;
            }
            if (this.writeTimeout < 0) {
                this.writeTimeout = DEFAULT_WRITE_TIMEOUT;
            }
            return new FreshBooksClient(this);
        }
    }
}
