package net.amcintosh.freshbooks.models;

import com.google.api.client.json.GenericJson;

/**
 * Base class for resource lists to extend.
 */
public class ListResult extends GenericJson {

    protected Pages pages;

    /**
     * Get Pages object of list pagination details
     * @return Populated Pages object
     */
    public Pages getPages() {
        return this.pages;
    }

}
