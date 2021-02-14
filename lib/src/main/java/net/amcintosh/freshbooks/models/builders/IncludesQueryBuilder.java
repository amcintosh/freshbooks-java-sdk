package net.amcintosh.freshbooks.models.builders;

import net.amcintosh.freshbooks.resources.api.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class IncludesQueryBuilder implements QueryBuilder {

    private List<String> includes = new ArrayList<>();

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
