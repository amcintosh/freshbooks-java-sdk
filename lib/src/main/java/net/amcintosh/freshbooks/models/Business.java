package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;

public class Business {

    @Key Long id;
    @Key("business_uuid") String businessUUID;
    @Key String name;
    @Key("account_id") String accountId;
    @Key("date_format") String dateFormat;
    @Key Address address;
    @Key("phone_number") PhoneNumber phoneNumber;

    /**
     * Unique id of the business.
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * UUID of the business. FreshBooks will be moving from id to business_uuid
     * in future API calls.
     *
     * @return
     */
    public String getBusinessUUID() {
        return businessUUID;
    }

    /**
     * Name of the business.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Unique identifier of the accounting system the business is
     * associated with.
     *
     * @return Accounting System Id
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Date format used by the business in FreshBooks.
     *
     * @return eg. "dd/mm/yyyy"
     */
    public String getDateFormat() {
        return dateFormat;
    }

    /**
     * Address of the business.
     *
     * @return
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Phone number of the business.
     *
     * @return
     */
    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Address object
     */
    public static class Address {
        @Key Long id;
        @Key String street;
        @Key String city;
        @Key String province;
        @Key String country;
        @Key("postal_code") String postalCode;

        /**
         * Unique id of this Address.
         *
         * @return
         */
        public long getId() {
            return id;
        }

        /**
         * Street address of business.
         *
         * @return The street address.
         */
        public String getStreet() {
            return street;
        }

        /**
         * City for address of business.
         *
         * @return
         */
        public String getCity() {
            return city;
        }

        /**
         * Province/state for address of business.
         *
         * @return
         */
        public String getProvince() {
            return province;
        }

        /**
         * Country for address of business.
         *
         * @return
         */
        public String getCountry() {
            return country;
        }

        /**
         * Postal/ZIP code for address of business.
         *
         * @return
         */
        public String getPostalCode() {
            return postalCode;
        }
    }

    /**
     * Phone number object
     */
    public static class PhoneNumber {
        @Key Long id;
        @Key("phone_number") String phoneNumber;

        /**
         * Unique id of this phone number.
         *
         * @return
         */
        public long getId() {
            return id;
        }

        /**
         * The phone number
         *
         * @return
         */
        public String getPhoneNumber() {
            return phoneNumber;
        }
    }

}
