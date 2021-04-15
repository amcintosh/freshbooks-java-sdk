package net.amcintosh.freshbooks.models;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

/**
 * Invoices in FreshBooks are what gets sent to Clients, detailing specific goods or
 * services performed or provided by the Administrator of their System, and the amount
 * that Client owes to the Admin.
 *
 * @see <a href="https://www.freshbooks.com/api/invoices">FreshBooks API - Invoices</a>
 */
public class Invoice extends GenericJson {

    @Key public long id;

}
