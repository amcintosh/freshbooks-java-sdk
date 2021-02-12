package net.amcintosh.freshbooks.resources.api;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.models.builders.QueryBuilder;

import java.util.List;

/**
 * Base class for API resources.
 */
public abstract class Resource {
    protected FreshBooksClient freshBooksClient;

    protected abstract ResourceType getResourceType();

    public Resource(FreshBooksClient freshBooksClient) {
        this.freshBooksClient = freshBooksClient;
    }

    protected String buildQueryString(List<QueryBuilder> builders) {
        StringBuilder queryString = new StringBuilder();
        for (QueryBuilder builder : builders) {
            queryString.append(builder.build(this.getResourceType()));
        }
        if (queryString.length() > 0) {
            return "?" + queryString.toString().replaceFirst("&", "");
        }
        return "";

    }
}
