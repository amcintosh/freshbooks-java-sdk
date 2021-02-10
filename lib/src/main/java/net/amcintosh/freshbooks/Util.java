package net.amcintosh.freshbooks;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Utility functions. Mostly around dates and timezones.
 */
public class Util {
    public static final ZoneId UTC_ZONE = ZoneId.of("UTC");
    public static final ZoneId ACCOUNTING_LOCAL_ZONE = ZoneId.of("US/Eastern");

    public static DateTimeFormatter getAccountingDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // 2021-01-08 20:39:52;
    }

    public static ZonedDateTime getZonedDateTimeFromAccountingLocalTime(String dateString) {
        LocalDateTime accountingLocalTime = LocalDateTime.parse(dateString, Util.getAccountingDateTimeFormatter());
        return accountingLocalTime.atZone(ACCOUNTING_LOCAL_ZONE).withZoneSameInstant(UTC_ZONE);
    }

    public static String getAccountingLocalTimeFromZonedDateTime(ZonedDateTime datetime) {
        return datetime.withZoneSameInstant(ACCOUNTING_LOCAL_ZONE).format(Util.getAccountingDateTimeFormatter());
    }

    public static ZonedDateTime getZonedDateTimeFromProjectNaiveUTC(String dateString) {
        return LocalDateTime.parse(dateString).atZone(UTC_ZONE);
    }

    public static void putIfNotNull(Map<String, Object> data, String key, Object value){
        if (value != null) {
            data.put(key, value);
        }
    }
}
