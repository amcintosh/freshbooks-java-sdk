package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.api.AccountingListResponse;

import java.util.ArrayList;

/**
 * Results of tasks list call containing list of tasks and pagination data.
 */
public class TaskList extends ListResult {
    @Key private final ArrayList<Task> tasks;

    public TaskList(AccountingListResponse.AccountingListInnerResponse.AccountingListResult result) {
        tasks = result.tasks;
        this.pages = new Pages(result.page, result.pages, result.perPage, result.total);
    }

    /**
     * Array of tasks returned by the `list` call.
     *
     * @return Arraylist of Task objects
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

}
