package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Value;

import java.util.Arrays;

/**
 * Other income categories
 * <br>
 * Values are:
 * <ul>
 * <li><b>ADVERTISING</b></li>
 * <li><b>IN_PERSON_SALES</b></li>
 * <li><b>ONLINE_SALES</b></li>
 * <li><b>RENTALS</b></li>
 * <li><b>OTHER</b></li>
 * </ul>
 *
 * @see <a href="https://www.freshbooks.com/api/other_income">FreshBooks API - Other Income</a>
 */

public enum OtherIncomeCategory {
    @Value ADVERTISING,
    @Value IN_PERSON_SALES,
    @Value ONLINE_SALES,
    @Value RENTALS,
    @Value OTHER;

    public static OtherIncomeCategory valueOfCategory(String category) {
        return Arrays.stream(OtherIncomeCategory.values())
                .filter(cat -> cat.name().equalsIgnoreCase(category))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getValue() {
        return name().toLowerCase();
    }
}
