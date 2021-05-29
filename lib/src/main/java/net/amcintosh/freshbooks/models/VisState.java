package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Value;

import java.util.HashMap;
import java.util.Map;


/**
 * Visibility values for a resource. Mostly used in accounting-type resources, not project-type.
 * <br>
 * Values are:
 * <ul>
 * <li><b>ACTIVE</b>: refers to objects that are both completed and non-completed.</li>
 * <li><b>DELETED</b>: objects are "soft-deleted" meaning they will not show up in list calls by default, and will not
 * count towards finances, account limits, etc., but can be undeleted at any time.</li>
 * <li><b>ARCHIVED</b>: Are hidden from FreshBook's UI by default, but still count towards finances, account limits,
 * etc.</li>
 * </ul>
 *
 * @see <a href="https://www.freshbooks.com/api/active_deleted">FreshBooks API - Active and Deleted Objects</a>
 */
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
