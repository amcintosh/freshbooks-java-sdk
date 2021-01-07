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
                .withAccessToken("ed371320b7eac0b5cd499a9316575e7f6ae1064dc0dfe8c7b254e0f21066c2be")
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
