package net.amcintosh.freshbooks.resources.api;

import com.google.common.collect.ImmutableList;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.models.builders.IncludesQueryBuilder;
import net.amcintosh.freshbooks.models.builders.QueryBuilder;

import java.util.List;

/**
 * Handles resources under the `/comments` which are project-like endpoints.
 */
public abstract class CommentResource extends ProjectResource {

    /**
     *
     * @param freshBooksClient Initialized instance of FreshBooksClient
     */
    public CommentResource(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    @Override
    protected ResourceType getResourceType() {
        return ResourceType.PROJECT_LIKE;
    }

    protected String getUrl(long businessId, boolean isList) {
        return this.getUrl(businessId, isList, null);
    }

    protected String getUrl(long businessId, boolean isList,  List<QueryBuilder> builders) {
        String path = isList ? getPathForList() : getPathForSingle();
        String queryString = "";
        if (builders != null) {
            queryString = this.buildQueryString(builders);
        }
        return String.format("/comments/business/%s/%s%s", businessId, path, queryString);
    }

    protected String getUrl(long businessId, long resourceId) {
        return this.getUrl(businessId, resourceId, null);
    }

    protected String getUrl(long businessId, long resourceId, IncludesQueryBuilder builder) {
        String queryString = "";
        if (builder != null) {
            queryString = this.buildQueryString(ImmutableList.of(builder));
        }
        return String.format("/comments/business/%s/%s/%s%s", businessId, this.getPathForSingle(), resourceId, queryString);
    }

}
