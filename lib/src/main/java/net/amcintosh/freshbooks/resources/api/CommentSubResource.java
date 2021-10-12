package net.amcintosh.freshbooks.resources.api;

import com.google.common.collect.ImmutableList;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.models.builders.IncludesQueryBuilder;
import net.amcintosh.freshbooks.models.builders.QueryBuilder;

import java.util.List;

/**
 * Handles sub-resources under the `/comments` endpoints.
 *
 * Eg. `/comments/business/{business_id}/services/{service_id}/rate`
 *
 * These are handled similarly to `/projects` endpoints.
 */
public abstract class CommentSubResource extends ProjectResource {

    /**
     *
     * @param freshBooksClient Initialized instance of FreshBooksClient
     */
    public CommentSubResource(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    @Override
    protected ResourceType getResourceType() {
        return ResourceType.PROJECT_LIKE;
    }

    protected abstract String getPathForParent();

    protected String getUrl(long businessId) {
        return this.getUrl(businessId, null);
    }

    protected String getUrl(long businessId,  List<QueryBuilder> builders) {
        String queryString = "";
        if (builders != null) {
            queryString = this.buildQueryString(builders);
        }
        return String.format("/comments/business/%s/%s%s", businessId, getPathForList(), queryString);
    }

    protected String getUrl(long businessId, long resourceId) {
        return this.getUrl(businessId, resourceId, null);
    }

    protected String getUrl(long businessId, long resourceId, IncludesQueryBuilder builder) {
        String queryString = "";
        if (builder != null) {
            queryString = this.buildQueryString(ImmutableList.of(builder));
        }
        return String.format("/comments/business/%s/%s/%s/%s%s", businessId, this.getPathForParent(), resourceId, this.getPathForSingle(), queryString);
    }

}
