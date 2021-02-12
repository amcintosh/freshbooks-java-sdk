package net.amcintosh.freshbooks.models.builders;

import net.amcintosh.freshbooks.resources.api.ResourceType;

public interface QueryBuilder {
    public String build(ResourceType resourceType);
}
