package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpResponse;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.AuthorizationToken;
import net.amcintosh.freshbooks.resources.api.AuthResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Resource for authorization calls for bearer and refresh tokens.
 */
public class Authorization extends AuthResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(Authorization.class);

    private static final String API_TOKEN_PATH = "/auth/oauth/token";
    private static final String AUTH_PATH = "/service/auth/oauth/authorize";

    /**
     * @param freshBooksClient Initialized instance of FreshBooksClient
     */
    public Authorization(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    /**
     * Returns the url that a client needs to request an oauth grant from the server.
     *
     * @param scopes  List of scopes if your want an access token with only a subset of your registered scopes.
     * @return The URL for the authorization request
     */
    public String getAuthRequestUri(
            String authorizationUrl, String clientId, String clientSecret, String redirectUri, List<String> scopes)
    {
        ImmutableMap.Builder<String, String> paramBuilder = ImmutableMap.builder();
        paramBuilder.put("client_id", clientId);
        paramBuilder.put("response_type", "code");
        paramBuilder.put("redirect_uri", redirectUri);
        if ("".equals(redirectUri)) {
            LOGGER.warn("Client redirectUri has not been set.");
        }
        if (scopes != null) {
            paramBuilder.put("scope", String.join(" ", scopes));
        }
        ImmutableMap<String, String> params = paramBuilder.build();

        return new StringBuilder()
                .append(authorizationUrl)
                .append(AUTH_PATH)
                .append("?")
                .append(Joiner.on("&").withKeyValueSeparator("=").join(params)).toString();
    }
    /**
     * Get the currently authorized identity from the FreshBooks /me endpoint.
     *
     * @return Identity of the authorized user.
     * @throws FreshBooksException If the call is not successful
     */
    public AuthorizationToken getToken(Map<String, Object> content) throws FreshBooksException {
        HttpResponse response = this.handleRequest(HttpMethods.POST, API_TOKEN_PATH, content);
        try {
            return response.parseAs(AuthorizationToken.class);
        } catch (IOException e) {
            throw new FreshBooksException(
                    "Returned an unexpected response",
                    response.getStatusMessage(),
                    response.getStatusCode()
            );
        }
    }

}
