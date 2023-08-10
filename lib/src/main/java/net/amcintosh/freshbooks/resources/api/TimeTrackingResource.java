package net.amcintosh.freshbooks.resources.api;

import com.google.common.collect.ImmutableList;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.models.builders.IncludesQueryBuilder;
import net.amcintosh.freshbooks.models.builders.QueryBuilder;

import java.util.List;

/**
 * Handles resources under the '<code>/timetracking</code>'.
 * <br><br>
 * These are handled identically to `/projects` endpoints.
 * Refer to  {@link ProjectResource ProjectResource}.
 */
public abstract class TimeTrackingResource extends ProjectResource {

    /**
     * @param freshBooksClient Initialized instance of FreshBooksClient
     */
    public TimeTrackingResource(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    @Override
    protected ResourceType getResourceType() {
        return ResourceType.PROJECT_LIKE;
    }

    protected String getUrl(long businessId) {
        return this.getUrl(businessId, null);
    }

    protected String getUrl(long businessId,  List<QueryBuilder> builders) {
        String queryString = "";
        if (builders != null) {
            queryString = this.buildQueryString(builders);
        }
        return String.format("/timetracking/business/%s/%s%s", businessId, getPathForList(), queryString);
    }

    protected String getUrl(long businessId, long resourceId) {
        return this.getUrl(businessId, resourceId, null);
    }

    protected String getUrl(long businessId, long resourceId, IncludesQueryBuilder builder) {
        String queryString = "";
        if (builder != null) {
            queryString = this.buildQueryString(ImmutableList.of(builder));
        }
        return String.format("/timetracking/business/%s/%s/%s%s", businessId, this.getPathForSingle(), resourceId, queryString);
    }
}
