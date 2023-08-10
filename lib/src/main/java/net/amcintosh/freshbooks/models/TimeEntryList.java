package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.api.ProjectListResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Results of time_entries list call containing list of time_entry, pagination, and billing data.
 */
public class TimeEntryList extends ListResult {

    private final List<TimeEntry> timeEntries;

    public TimeEntryList(ProjectListResponse result) {
        timeEntries = result.timeEntries;
        this.pages = new Pages(result.meta.page, result.meta.pages, result.meta.perPage, result.meta.total);
    }

    /**
     * Array of time entries returned by the `list` call.
     *
     * @return Arraylist of TimeEntry objects
     */
    public List<TimeEntry> getTimeEntries() {
        return timeEntries;
    }
}
