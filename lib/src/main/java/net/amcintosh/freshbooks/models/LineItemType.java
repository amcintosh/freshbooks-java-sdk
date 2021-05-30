package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * Type for line items.
 * <ul>
 * <li><b>NORMAL</b>: A regular inovice line item.
 * <li><b>REBILLING_EXPENSE</b>: A rebilling expense line item.
 * </ul>
 */
public enum LineItemType {
    @Value NORMAL(0),
    @Value REBILLING_EXPENSE(1);

    private final int value;
    private static final Map<Integer, LineItemType> map = new HashMap<>();

    LineItemType(int value) {
        this.value = value;
    }

    static {
        for (LineItemType lineItemType : LineItemType.values()) {
            map.put(lineItemType.value, lineItemType);
        }
    }

    public static LineItemType valueOf(int lineItemType) {
        return map.get(lineItemType);
    }

    public int getValue() {
        return value;
    }
}
