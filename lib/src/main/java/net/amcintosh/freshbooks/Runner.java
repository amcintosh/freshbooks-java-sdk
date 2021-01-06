package net.amcintosh.freshbooks;
import net.amcintosh.freshbooks.models.*;
import net.amcintosh.freshbooks.resources.Clients;

public class Runner {

    public static void main(String[] args) {
        System.out.println("Running right now");
        String accountId = "6VApk";
        long businessId = 38408;

        FreshBooksClient freshBooksClient = new FreshBooksClient.FreshBooksClientBuilder(
                "34b6b595adef067730d964b8f2cc9ef070f73908d74c297f66d0f8f2cfbb8ee1")
                .withAccessToken("f6b58d64bc0bda37bd924bf7a793c647d0a78a93e71e6892fa0d8ab5ec8cc726")
                .withUserAgent("tester")
                .build();

        System.out.println(freshBooksClient);

        Clients clients = new Clients(freshBooksClient);
        Client clientResponse = null;
        try {
            clientResponse = clients.get(accountId, 640807);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Bad End");
            System.exit(0);
        }
        System.out.println("Response: ");
        //System.out.println(clientResponse.response.result.client.organization);
        System.out.println(clientResponse.organization);
        //System.out.println(clientResponse.response.errors);
        System.out.println("End");
    }
}
