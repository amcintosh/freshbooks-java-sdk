package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpResponse;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.Identity;
import net.amcintosh.freshbooks.models.api.AuthMeResponse;
import net.amcintosh.freshbooks.resources.api.AuthResource;

import java.io.IOException;

/**
 * Resource for the currently authorized user.
 */
public class CurrentUser extends AuthResource {
    /**
     * @param freshBooksClient Initialized instance of FreshBooksClient
     */
    public CurrentUser(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    /**
     * Get the currently authorized identity from the FreshBooks /me endpoint.
     *
     * @return Identity of the authorized user.
     * @throws FreshBooksException If the call is not successful
     */
    public Identity get() throws FreshBooksException {
        String url = this.getUrl("users/me");
        HttpResponse response = this.handleRequest(HttpMethods.GET, url);
        AuthMeResponse result = null;
        try {
            result = response.parseAs(AuthMeResponse.class);
        } catch (IOException e) {
            throw new FreshBooksException(
                    "Returned an unexpected response",
                    response.getStatusMessage(),
                    response.getStatusCode()
            );
        }
        return result.identity;

    }

}
