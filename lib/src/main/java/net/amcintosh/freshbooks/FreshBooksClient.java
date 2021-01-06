package net.amcintosh.freshbooks;


import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
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
    //TODO: timeout, retries

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
     * Called internally by resources (eg. Clients) which then parse the http response.
     *
     * @param resource_url Relative URL (eg. /accounting/account/<accountId>/users/clients
     * @return HttpResponse object
     * @throws IOException
     */
    public HttpResponse get(String resource_url) throws IOException {
        GenericUrl requestUrl = new GenericUrl(this.baseUrl + resource_url);
        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory(
                new HttpRequestInitializer() {
                    @Override
                    public void initialize(HttpRequest request) {
                        request.setParser(new JsonObjectParser(JSON_FACTORY));
                    }
                });
        HttpRequest request = null;
        HttpHeaders headers =
                new HttpHeaders().setAuthorization("Bearer " + this.accessToken).setUserAgent(this.userAgent); //
        // .setContentType
        // ("application/json");
        request = requestFactory.buildGetRequest(requestUrl).setHeaders(headers).setThrowExceptionOnExecuteError(false);
        HttpResponse response = request.execute();
        return response;
    }

    public static class FreshBooksClientBuilder {
        private static final String API_BASE_URL = "https://api.freshbooks.com";
        private static final String AUTH_BASE_URL = "https://auth.freshbooks.com";
        private static final int DEFAULT_TIMEOUT = 30;

        private String baseUrl;
        private String authorizationUrl;
        private String tokenUrl;

        private final String clientId;
        private String clientSecret;
        private String redirectUri;

        private String accessToken;
        private String refreshToken;

        private String userAgent;

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

        public FreshBooksClientBuilder withRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public FreshBooksClientBuilder withUserAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        private String getEnvDefault(String var, String defaultValue) {
            String value = System.getenv(var);
            if (value == null) {
                return defaultValue;
            }
            return value;
        }

        public FreshBooksClient build() {
            this.baseUrl = this.getEnvDefault("FRESHBOOKS_API_URL", API_BASE_URL);
            this.authorizationUrl = this.getEnvDefault("FRESHBOOKS_AUTH_URL", AUTH_BASE_URL);
            this.tokenUrl = this.getEnvDefault("FRESHBOOKS_AUTH_URL", AUTH_BASE_URL);
            return new FreshBooksClient(this);
        }
    }
}
