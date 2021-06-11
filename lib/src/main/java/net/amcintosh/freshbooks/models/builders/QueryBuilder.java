package net.amcintosh.freshbooks.models.builders;

import net.amcintosh.freshbooks.resources.api.ResourceType;

public interface QueryBuilder {

    /**
     * Build a http query string from the paramters of this builder that conforms to the
     * style of the <code>ResourceType</code>.
     *
     * @param resourceType
     * @return
     */
    public String build(ResourceType resourceType);
}
