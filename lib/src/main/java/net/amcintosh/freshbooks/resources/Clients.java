package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpStatusCodes;
import com.google.common.reflect.TypeToken;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.Client;
import com.google.api.client.http.HttpResponseException;


import java.io.IOException;
import java.lang.reflect.Type;

public class Clients extends AccountingResource {

    public Clients(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    public Client get(String accountId, long clientId) throws FreshBooksException {
        HttpResponse response = null;
        Client client = null;
        try {
            response = this.freshBooksClient.get(this.getUrl("users/clients", accountId, clientId));
            Type type = new TypeToken<Client>() {}.getType();
            client = (Client) response.parseAs(type);
        } catch (IOException e) {
            throw new FreshBooksException("Returned an unexpected response", response.getStatusCode(), response);
        }

        if (!response.isSuccessStatusCode() && client.response != null && client.response.errors != null) {
            throw new FreshBooksException(
                    client.response.errors.get(0).message,
                    client.response.errors.get(0).errno,
                    response);
        }

        if (response.isSuccessStatusCode() && client.response != null && client.response.result != null) {
            return client.response.result.client;
        }

        throw new FreshBooksException("Returned an unexpected response", response.getStatusCode(), response);
    }
}
