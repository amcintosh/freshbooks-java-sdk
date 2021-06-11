package net.amcintosh.freshbooks.models.builders;

import net.amcintosh.freshbooks.resources.api.ResourceType;

/**
 * Builder for making paginated list queries.
 *
 * Has two attributes, <code>page</code> and <code>perPage</code>. When a <code>PaginationQueryBuilder</code> is passed
 * to a <code>.list()</code> call, the call will fetch only the <code>perPage</code> number of results and will
 * fetch the results offset by <code>page</code>.
 *
 * <pre>{@code
 *     FreshBooksClient freshBooksClient = new FreshBooksClient.FreshBooksClientBuilder("some_client_id")
 *         .withAccessToken("some_token")
 *         .build();
 *     Clients clients = new Clients(freshBooksClient);
 *
 *     PaginationQueryBuilder p = new PaginationQueryBuilder(2, 4);
 *     assertEquals("&page=2&per_page=4", p.build(ResourceType.ACCOUNTING_LIKE));
 *
 *     ArrayList<QueryBuilder> builders = new ArrayList<QueryBuilder>();
 *     builders.add(p);
 *
 *     ClientList clientListResponse = clients.list(accountId, builders);
 * }</pre>
 */
public class PaginationQueryBuilder implements QueryBuilder {

    private static final int MAX_PER_PAGE = 100;
    private static final int MIN_PAGE = 1;

    private int page;
    private int perPage;

    /**
     * Create an uninitialized pagination query builder (page=1, perPage=100).
     */
    public PaginationQueryBuilder() {}

    /**
     * Create an pagination query builder.
     *
     * @param page Page of results to return
     * @param perPage Number of results to return
     */
    public PaginationQueryBuilder(int page, int perPage) {
        this.page =Math.max(page, MIN_PAGE);
        this.perPage = Math.min(perPage, MAX_PER_PAGE);
    }

    /**
     *
     * @param page Page of results to return
     * @return The PaginationQueryBuilder instance.
     */
    public PaginationQueryBuilder page(int page) {
        this.page =Math.max(page, MIN_PAGE);
        return this;
    }

    /**
     *
     * @param perPage Number of results to return
     * @return The PaginationQueryBuilder instance.
     */
    public PaginationQueryBuilder perPage(int perPage) {
        this.perPage = Math.min(perPage, MAX_PER_PAGE);
        return this;
    }

    @Override
    public String toString() {
        return "PaginationQueryBuilder{" +
                "page=" + page +
                ", perPage=" + perPage +
                '}';
    }

    /**
     * Build the query string parameters for the list call. While all FreshBooks resources
     * use the same structure of page paramters, a resource type is still required for interface
     * consistency.
     *
     * @param resourceType The resource type.
     * @return The composed query string parameters.
     */
    @Override
    public String build(ResourceType resourceType) {
        StringBuilder stringBuilder = new StringBuilder();
        if (page > 0) {
            stringBuilder.append("&page=").append(page);
        }
        if (perPage > 0) {
            stringBuilder.append("&per_page=").append(perPage);
        }
        return stringBuilder.toString();
    }
}
