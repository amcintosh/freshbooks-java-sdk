package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;

public class Service {
    @Key Long id;
    @Key("business_id") Long businessId;
    @Key String name;
    @Key boolean billable;
    @Key("vis_state") int visState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBillable() {
        return billable;
    }

    public void setBillable(boolean billable) {
        this.billable = billable;
    }

    /**
     * Get the visibility state: active, deleted, or archived
     *
     * @return Enum of the visibility state.
     */
    public VisState getVisState() {
        return VisState.valueOf(this.visState);
    }

    /**
     * Set the visibility state of the client.
     *
     * @param visState VisState value
     */
    public void setVisState(VisState visState) {
        this.visState = visState.getValue();
    }
}
