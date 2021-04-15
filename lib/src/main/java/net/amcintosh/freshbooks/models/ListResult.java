package net.amcintosh.freshbooks.models;

import com.google.api.client.json.GenericJson;

/**
 * Base class for resource lists to extend.
 */
public class ListResult extends GenericJson {

    protected Pages pages;

    public Pages getPages() {
        return this.pages;
    }

}
