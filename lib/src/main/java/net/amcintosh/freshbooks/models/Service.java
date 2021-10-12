package net.amcintosh.freshbooks.models;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.Util;
import net.amcintosh.freshbooks.models.api.ConvertibleContent;

import java.util.HashMap;
import java.util.Map;

/**
 * Services represent things that a business offers to clients. Services are added to projects
 * to to allow tracking of time entries by type of work. Services keep track of details such
 * as hourly rate. See {@link ServiceRate} for adding a rate to a service.
 * <br>
 * Services automatically get converted to tasks for inclusion on invoices.
 *
 * @see <a href="https://www.freshbooks.com/api/services">FreshBooks API - Services</a>
 */
public class Service extends GenericJson implements ConvertibleContent {
    @Key Long id;
    @Key("business_id") Long businessId;
    @Key String name;
    @Key boolean billable;
    @Key("vis_state") int visState;

    /**
     * The unique id of the service.
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * The unique id for business.
     *
     * @return
     */
    public long getBusinessId() {
        return businessId;
    }

    /**
     * The descriptive name of service.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * The descriptive name of service.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Whether the service is billable to clients or not.
     *
     * @return
     */
    public boolean isBillable() {
        return billable;
    }

    /**
     * Whether the service is billable to clients or not.
     *
     * @param billable
     */
    public void setBillable(boolean billable) {
        this.billable = billable;
    }

    /**
     * Get the visibility state: active, deleted, or archived
     * <br><br>
     * @see <a href="https://www.freshbooks.com/api/active_deleted">FreshBooks API - Active and Deleted Objects</a>
     *
     * @return Enum of the visibility state.
     */
    public VisState getVisState() {
        return VisState.valueOf(this.visState);
    }

    /**
     * Set the visibility state of the client.
     * <br><br>
     * @see <a href="https://www.freshbooks.com/api/active_deleted">FreshBooks API - Active and Deleted Objects</a>
     *
     * @param visState VisState value
     */
    public void setVisState(VisState visState) {
        this.visState = visState.getValue();
    }

    @Override
    public Map<String, Object> getContent() {
        Map<String, Object> content = new HashMap<>();
        Util.convertContent(content, "name", name);
        Util.convertContent(content, "billable", billable);

        return content;
    }
}
