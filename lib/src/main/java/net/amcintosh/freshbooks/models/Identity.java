package net.amcintosh.freshbooks.models;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.google.api.client.util.Value;
import net.amcintosh.freshbooks.Util;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Users are uniquely identified by their email across all of FreshBooks, so if
 * leafy@example.com is an Owner of one account and gets added as a Client on another,
 * they will have some access to both. They could then open a second business of their
 * own, or be added as an employee of another person’s business.
 */
public class Identity extends GenericJson {

    @Key("identity_id") Long identityId;
    @Key("identity_uuid") String identityUUID;
    @Key("first_name") String firstName;
    @Key("last_name") String lastName;
    @Key String email;
    @Key String language;
    @Key("confirmed_at") String confirmedAt;
    @Key("created_at") String createdAt;
    //@Key List<> groups
    @Key("business_memberships") List<BusinessMembership> businessMemberships;

    /**
     * The identity's unique id.
     *
     * @return
     */
    public long getIdentityId() {
        return identityId;
    }

    /**
     * UUID of the identity. FreshBooks will be moving from id to identity_uuid
     * in future API calls.
     *
     * @return
     */
    public String getIdentityUUID() {
        return identityUUID;
    }

    /**
     * The identity's first name.
     *
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * The identity's last name.
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * The identity's email.
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * The language used by the identity in FreshBooks.
     *
     * @return
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Date the identity confirmed their email.
     *
     * @return
     */
    public ZonedDateTime getConfirmedAt() {
        return Util.getZonedDateTimeFromISO(confirmedAt);
    }

    /**
     * Date the identity was created.
     *
     * @return
     */
    public ZonedDateTime getCreatedAt() {
        return Util.getZonedDateTimeFromISO(createdAt);
    }

    /**
     * List of businesses this identity is a part of and their role in each.
     *
     * @return
     */
    public List<BusinessMembership> getBusinessMemberships() {
        return businessMemberships;
    }

    /**
     * Business/Identity relationship
     */
    public static class BusinessMembership {
        @Key Long id;
        @Key BusinessRole role;
        @Key Business business;

        /**
         * Membership Id.
         *
         * @return
         */
        public long getId() {
            return id;
        }

        /**
         * Identity's role in this business.
         *
         * @return
         */
        public BusinessRole getRole() {
            return role;
        }

        /**
         * The business details.
         *
         * @return
         */
        public Business getBusiness() {
            return business;
        }
    }

    public enum BusinessRole {
        @Value("owner") OWNER,
        @Value("manager") MANAGER,
        @Value("business_accountant") BUSINESS_ACCOUNTANT,
        @Value("business_manager") BUSINESS_MANAGER,
        @Value("business_employee") BUSINESS_EMPLOYEE,
        @Value("business_partner") BUSINESS_PARTNER,
        @Value("systemless_owner") SYSTEMLESS_OWNER,
        @Value("systemless_manager") SYSTEMLESS_MANAGER,
    }
}
