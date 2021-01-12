package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;

public class Client {

    @Key public long id;
    @Key public String organization;
    @Key public String language;

    @Key("vis_state") public int visState;

    public VisState getVisState() {
        return VisState.valueOf(this.visState);
    }
    public void setVisState(VisState visState) {
        this.visState = visState.getValue();
    }
}
