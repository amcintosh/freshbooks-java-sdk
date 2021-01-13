package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Value;

import java.util.HashMap;
import java.util.Map;

public enum VisState {
    @Value ACTIVE(0),
    @Value DELETED(1),
    @Value ARCHIVED(2);

    private final int value;
    private static final Map<Integer, VisState> map = new HashMap<>();

    VisState(int value) {
        this.value = value;
    }

    static {
        for (VisState visState : VisState.values()) {
            map.put(visState.value, visState);
        }
    }

    public static VisState valueOf(int visState) {
        return map.get(visState);
    }

    public int getValue() {
        return value;
    }
}
