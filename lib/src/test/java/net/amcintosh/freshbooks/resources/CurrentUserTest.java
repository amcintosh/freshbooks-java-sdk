package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.TestUtil;
import net.amcintosh.freshbooks.models.Business;
import net.amcintosh.freshbooks.models.Identity;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CurrentUserTest {
    @Test
    public void getCurrentUser() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/auth_me_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/auth/api/v1/users/me", null)).thenReturn(mockRequest);

        CurrentUser currentUser = new CurrentUser(mockedFreshBooksClient);
        Identity identity = currentUser.get();


        assertEquals(12345, identity.getIdentityId());
        assertEquals("a_uuid", identity.getIdentityUUID());
        assertEquals("Simon", identity.getFirstName());
        assertEquals("Kovalic", identity.getLastName());
        assertEquals("skovalic@cis.com", identity.getEmail());
        assertEquals("en", identity.getLanguage());
        assertEquals(ZonedDateTime.of(2017, 5, 23, 5,
                57, 24, 0, ZoneId.of("UTC")),
                identity.getConfirmedAt()
        );
        assertEquals(ZonedDateTime.of(2017, 5, 23, 5,
                42, 42, 0, ZoneId.of("UTC")),
                identity.getCreatedAt()
        );

        assertEquals(2, identity.getBusinessMemberships().size());
        Identity.BusinessMembership membership = identity.getBusinessMemberships().get(0);
        assertEquals(140130, membership.getId());
        assertEquals(Identity.BusinessRole.OWNER, membership.getRole());

        Business business = membership.getBusiness();
        assertEquals(439000, business.getId());
        assertEquals("a_uuid1", business.getBusinessUUID());
        assertEquals("ABC123", business.getAccountId());
        assertEquals("Commonwealth of Independent Systems", business.getName());
        assertEquals("dd/mm/yyyy", business.getDateFormat());
        assertEquals(666111, business.getPhoneNumber().getId());
        assertEquals("8675309", business.getPhoneNumber().getPhoneNumber());

        Business.Address address = business.getAddress();
        assertEquals(76433, address.getId());
        assertEquals("123 Somewhere", address.getStreet());
        assertEquals("Toronto", address.getCity());
        assertEquals("Ontario", address.getProvince());
        assertEquals("Canada", address.getCountry());
        assertEquals("M6P 2T6", address.getPostalCode());
    }
}
