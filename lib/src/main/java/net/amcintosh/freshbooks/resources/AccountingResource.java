package net.amcintosh.freshbooks.resources;

import net.amcintosh.freshbooks.FreshBooksClient;


public class AccountingResource extends Resource {
    public AccountingResource(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    protected String getUrl(String path, String accountId, long resourceId) {
        String url = String.format("/accounting/account/%s/%s/%s", accountId, path, resourceId);
        return url;
    }
}
