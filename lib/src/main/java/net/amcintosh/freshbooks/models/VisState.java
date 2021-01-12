package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Value;

import java.util.HashMap;
import java.util.Map;

public enum VisState {
    @Value ACTIVE(0),
    @Value DELETED(1),
    @Value ARCHIVED(2);

    private int value;
    private static Map map = new HashMap<>();

    private VisState(int value) {
        this.value = value;
    }

    static {
        for (VisState pageType : VisState.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static VisState valueOf(int pageType) {
        return (VisState) map.get(pageType);
    }

    public int getValue() {
        return value;
    }
}
