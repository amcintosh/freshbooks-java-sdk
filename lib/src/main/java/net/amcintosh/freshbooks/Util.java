package net.amcintosh.freshbooks;

import com.google.common.collect.ImmutableList;
import net.amcintosh.freshbooks.models.api.ConvertibleContent;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Utility functions. Mostly around dates and timezones.
 */
public class Util {

    /**
     * UTC timezone.
     */
    public static final ZoneId UTC_ZONE = ZoneId.of("UTC");
    /**
     * Local timezone used by the FreshBooks accounting service.
     */
    public static final ZoneId ACCOUNTING_LOCAL_ZONE = ZoneId.of("US/Eastern");

    /**
     * Get a DateTimeFormmatter configured for accounting endpoint format ("yyyy-MM-dd HH:mm:ss").
     *
     * @return The formatter for accounting endpoints. Eg. 2021-01-08 20:39:52
     */
    public static DateTimeFormatter getAccountingDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Get a datetime zoned to UTC from an accounting endpoint date string.
     * The accounting service stores almost all dates in the US/Eastern timezone.
     *
     * @param dateString An accounting date-string. eg. 2021-01-08 20:39:52
     * @return UTC-zoned datetime
     */
    public static ZonedDateTime getZonedDateTimeFromAccountingLocalTime(String dateString) {
        LocalDateTime accountingLocalTime = LocalDateTime.parse(dateString, Util.getAccountingDateTimeFormatter());
        return accountingLocalTime.atZone(ACCOUNTING_LOCAL_ZONE).withZoneSameInstant(UTC_ZONE);
    }

    /**
     * Get a date string in the accounting endpoint format and timezone from a ZonedDateTime object.
     * The accounting service stores almost all dates in the US/Eastern timezone.
     *
     * @param datetime datetime object from any timezone
     * @return String An accounting date-string in US/Eastern. eg. 2021-01-08 20:39:52
     */
    public static String getAccountingLocalTimeFromZonedDateTime(ZonedDateTime datetime) {
        return datetime.withZoneSameInstant(ACCOUNTING_LOCAL_ZONE).format(Util.getAccountingDateTimeFormatter());
    }

    /**
     * Get a datetime zoned to UTC from an project-like endpoint date string.
     *
     * The project services store their dates in UTC, but depending on the resource do not
     * indicate that in the response. Eg. "2020-09-13T03:10:13" rather than "2020-09-13T03:10:13Z".
     *
     * @param dateString A project-like date-string. eg. 2020-09-13T03:10:13
     * @return UTC-zoned datetime
     */
    public static ZonedDateTime getZonedDateTimeFromProjectNaiveUTC(String dateString) {
        return LocalDateTime.parse(dateString).atZone(UTC_ZONE);
    }

    /**
     * Get a datetime zoned to UTC from an ISO date string. Eg. "2020-09-13T03:10:13Z"
     *
     * @param dateString A project-like date-string. eg. 2020-09-13T03:10:13Z
     * @return UTC-zoned datetime
     */
    public static ZonedDateTime getZonedDateTimeFromISO(String dateString) {
        return ZonedDateTime.parse(dateString).withZoneSameInstant(ZoneId.of("UTC"));
    }

    /**
     * Add a key/value to the provided Map only if the value is not null.
     * <br>
     * If the value is a List, then iterates over the list.
     * <br>
     * If the value is an object implementing <code>ConvertibleContent</code> then
     * it returns a Map of that object by calling <code>getContent()</code>.
     *
     * @param data The Map to add to
     * @param key The key to add
     * @param value The value of the key if not null
     */
    public static void convertContent(Map<String, Object> data, String key, Object value){
        if (value != null) {
            if (value instanceof List) {
                ImmutableList.Builder builder = ImmutableList.builder();
                for (Object item : (List)value) {
                    if (item instanceof ConvertibleContent) {
                        builder.add(((ConvertibleContent)item).getContent());
                    }
                    data.put(key, builder.build());
                }
            } else if (value instanceof ConvertibleContent) {
                data.put(key, ((ConvertibleContent)value).getContent());
            } else {
                data.put(key, value);
            }
        }
    }
}
