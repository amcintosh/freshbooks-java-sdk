package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.Util;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Authorization data including the OAuth bearer token, expiry, and refresh token.
 */
public class AuthorizationToken {
    @Key("access_token") String accessToken;
    @Key("refresh_token") String refreshToken;
    @Key("created_at") Long createdAt;
    @Key("expires_in") Long expiresIn;
    @Key String scope;

    /**
     * Create empty authorization data.
     */
    public AuthorizationToken() {}

    /**
     * Create authorization data from a pre-existing, valid, bearer token.
     *
     * @param accessToken
     */
    public AuthorizationToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Create authorization data from pre-existing, valid, bearer and refresh tokens.
     *
     * @param accessToken
     * @param refreshToken
     */
    public AuthorizationToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    /**
     * Get the authorized bearer token from the OAuth2 token response.
     *
     * @return
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Set the bearer token for use in the client.
     *
     * @param accessToken
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Get the authorized refresh token from the OAuth2 token response.
     *
     * @return
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Set the refresh token for use in the client. This will allow the client to
     * call the token refresh and get a new bearer token if expired.
     *
     * @param refreshToken
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Time the bearer token was created.
     *
     * @return UTC ZonedDateTime
     */
    public ZonedDateTime getCreatedAt() {
        return Instant.ofEpochSecond(createdAt).atZone(Util.UTC_ZONE);
    }

    /**
     * Time the bearer token expires at.
     *
     * @return UTC ZonedDateTime
     */
    public ZonedDateTime getExpiresAt() {
        return Instant.ofEpochSecond(createdAt + expiresIn).atZone(Util.UTC_ZONE);
    }

    /**
     * Number of seconds since creation that the token will expire at.
     * <br><br>
     * Please note {@link #getExpiresAt()} which calculates the expiry time from this
     * and createdAt.
     *
     * @return Seconds
     */
    public long getExpiresIn() {
        return expiresIn;
    }

    /**
     * Get the scopes that the token is authorized for.
     *
     * @return List of scope strings.
     */
    public List<String> getScopes() {
        return Arrays.asList(scope.split(" "));
    }
}
