package net.amcintosh.freshbooks.models.api;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.Client;
import net.amcintosh.freshbooks.models.Invoice;
import net.amcintosh.freshbooks.models.Project;

public class GenericRequest {
    @Key public Client client;

    @Key public Invoice invoice;

    @Key public Project project;
}
