package net.amcintosh.freshbooks.models.api;

import java.util.Map;

/**
 * Interface for models that can be converted into Maps.
 */
public interface ConvertibleContent {

    /**
     * Return the objects values as a Map suitable to sending to FreshBooks
     * in a POST or PUT request.
     *
     * @return
     */
    public Map<String, Object> getContent();
}
