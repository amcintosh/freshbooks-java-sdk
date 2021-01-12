package net.amcintosh.freshbooks.resources.responses;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.Client;
import net.amcintosh.freshbooks.models.Invoice;

public class AccountingRequest {

    @Key public Client client;

    @Key public Invoice invoice;

}
