package net.amcintosh.freshbooks.models.builders;

import net.amcintosh.freshbooks.resources.api.ResourceType;

/**
 * Builder for sorting <code>.list()</code> call responses by sortable fields.
 *
 * <pre>{@code
 *     FreshBooksClient freshBooksClient = new FreshBooksClient.FreshBooksClientBuilder("some_client_id")
 *         .withAccessToken("some_token")
 *         .build();
 *     Clients clients = new Clients(freshBooksClient);
 *
 *     SortQueryBuilder sort = new SortQueryBuilder();
 *     sort.ascending("invoice_date");
 *     assertEquals("&sort=invoice_date_asc", sort.build(ResourceType.ACCOUNTING_LIKE));
 *
 *     ArrayList<QueryBuilder> builders = new ArrayList<QueryBuilder>();
 *     builders.add(sort);
 *     ClientList clientListResponse = clients.list(accountId, builders);
 * }</pre>
 */
public class SortQueryBuilder implements QueryBuilder {

    private String sort;
    private boolean ascending = false;

    /**
     * Add a sort by the field in ascending order.
     *
     * @param key The field for the resource list to be sorted by
     */
    public void ascending(String key) {
        this.sort = key;
        this.ascending = true;
    }

    /**
     * Add a sort by the field in descending order.
     *
     * @param key The field for the resource list to be sorted by
     */
    public void descending(String key) {
        this.sort = key;
        this.ascending = false;
    }

    @Override
    public String toString() {
        return "SortQueryBuilder{" +
                "sort=" + sort +
                (ascending ? ";ascending" : ";descending") +
                '}';
    }

    /**
     * Build the query string parameters for the list call. As different FreshBooks resources
     * use different structure for sort parameters, a resource type is required.
     *
     * @param resourceType The resource type.
     * @return The composed query string parameters.
     */
    @Override
    public String build(ResourceType resourceType) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("&sort=");
            if (resourceType.equals(ResourceType.ACCOUNTING_LIKE)) {
                stringBuilder.append(sort);
                stringBuilder.append(ascending ? "_asc" : "_desc");
            } else {
                stringBuilder.append(ascending ? "" : "-");
                stringBuilder.append(sort);
            }
        return stringBuilder.toString();
    }
}
