package net.amcintosh.freshbooks.models.builders;

import net.amcintosh.freshbooks.resources.api.ResourceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for including relationships, sub-resources, or additional data in the response.
 *
 * Can be passed to a <code>.get()</code> call, or to a <code>.list()</code> call.
 *
 * <pre>{@code
 *     FreshBooksClient freshBooksClient = new FreshBooksClient.FreshBooksClientBuilder("some_client_id")
 *         .withAccessToken("some_token")
 *         .build();
 *     Clients clients = new Clients(freshBooksClient);
 *
 *     IncludesQueryBuilder inc = new IncludesQueryBuilder();
 *     inc.includes("last_activity").includes("late_reminders");
 *     assertEquals("&includes[]=last_activity&includes[]=late_reminders", inc.build(ResourceType.ACCOUNTING_LIKE));
 *
 *     ClientList clientResponse = clients.get(accountId, clientId, inc);
 *
 *     ArrayList<QueryBuilder> builders = new ArrayList<QueryBuilder>();
 *     builders.add(inc);
 *     ClientList clientListResponse = clients.list(accountId, builders);
 * }</pre>
 */
public class IncludesQueryBuilder implements QueryBuilder {

    private List<String> includes = new ArrayList<>();

    /**
     * Add an include key to the builder.
     *
     * @param key The key for the included field, sub-resource, etc.
     * @return The IncludesQueryBuilder instance.
     */
    public IncludesQueryBuilder include(String key) {
        this.includes.add(key);
        return this;
    }

    @Override
    public String toString() {
        return "IncludesQueryBuilder{" +
                "includes=" + includes +
                '}';
    }

    /**
     * Build the query string parameters for the list or get call. As different FreshBooks resources
     * use different structure for includes paramters, a resource type is required.
     *
     * @param resourceType The resource type.
     * @return The composed query string parameters.
     */
    @Override
    public String build(ResourceType resourceType) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key: this.includes) {
            if (resourceType.equals(ResourceType.ACCOUNTING_LIKE)) {
                stringBuilder.append("&include[]=");
                stringBuilder.append(key);
            } else {
                stringBuilder.append("&");
                stringBuilder.append(key);
                stringBuilder.append("=true");
            }
        }
        return stringBuilder.toString();
    }
}
