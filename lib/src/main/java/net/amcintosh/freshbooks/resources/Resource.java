package net.amcintosh.freshbooks.resources;
import net.amcintosh.freshbooks.FreshBooksClient;

public class Resource {
    protected FreshBooksClient freshBooksClient;

    public Resource(FreshBooksClient freshBooksClient) {
        this.freshBooksClient = freshBooksClient;
    }
}
