package net.amcintosh.freshbooks.models;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.Util;
import net.amcintosh.freshbooks.models.api.ConvertibleContent;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Tasks represent services that your business offers to clients.
 * Tasks are used to keep track of invoicing details of the service such as name and hourly rate.
 *
 * @see <a href="https://www.freshbooks.com/api/tasks">FreshBooks API - Tasks</a>
 */
public class Task extends GenericJson implements ConvertibleContent {

    @Key Long id;
    @Key String name;
    @Key String description;
    @Key("taskid") Long taskId;
    @Key("tname") String taskName;
    @Key("tdesc") String taskDescription;
    @Key Money rate;
    @Key Boolean billable;
    @Key String updated;
    @Key("vis_state") int visState;

    /**
     * Get the unique identifier of this task within this business.
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Descriptive name of task.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Descriptive name of task.
     *
     * @param name e.g.:Piloting
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Descriptive text for task.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Descriptive text for task.
     *
     * @param description e.g.: Piloting based on expectations of the executive
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Duplicate of id.
     *
     * @return id
     */
    public long getTaskId() {
        return taskId;
    }

    /**
     * Duplicate of descriptive name of task.
     *
     * @return name
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Duplicate of descriptive name of task.
     *
     * @param taskName e.g.: Piloting
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Duplicate if descriptive text for task.
     *
     * @return taskDescription
     */
    public String getTaskDescription() {
        return taskDescription;
    }

    /**
     * Duplicate of descriptive text for task.
     *
     * @param taskDescription e.g.: Piloting based on expectations of the executive
     */
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    /**
     * The hourly amount rate charged for task.
     *
     * @return rate
     */
    public Money getRate() {
        return rate;
    }

    /**
     * The hourly amount rate charged for task.
     *
     * @param rate The hourly amount rate
     */
    public void setRate(Money rate) {
        this.rate = rate;
    }

    /**
     * Whether this task billable.
     *
     * @return boolean if task is billable.
     */
    public boolean isBillable() {
        return billable;
    }

    /**
     * Whether this task billable.
     *
     * @param billable True if billable
     */
    public void setBillable(boolean billable) {
        this.billable = billable;
    }

    /**
     * Get the time of last modification.
     *
     * <i>Note:</i> The API returns this data in "US/Eastern", but it is converted to UTC.
     *
     * @return Updated time in UTC
     */
    public ZonedDateTime getUpdated() {
        return Util.getZonedDateTimeFromAccountingLocalTime(this.updated);
    }

    /**
     * Get the visibility state: active, deleted, or archived
     *
     * @return Enum of the visibility state.
     * @see <a href="https://www.freshbooks.com/api/active_deleted">FreshBooks API - Active and Deleted Objects</a>
     */
    public VisState getVisState() {
        return VisState.valueOf(this.visState);
    }

    /**
     * Set the visibility state of the item.
     *
     * @param visState VisState value
     * @see <a href="https://www.freshbooks.com/api/active_deleted">FreshBooks API - Active and Deleted Objects</a>
     */
    public void setVisState(VisState visState) {
        this.visState = visState.getValue();
    }


    @Override
    public Map<String, Object> getContent() {
        Map<String, Object> content = new HashMap<>();
        Util.convertContent(content, "name", name);
        Util.convertContent(content, "description", description);
        Util.convertContent(content, "tname", taskName);
        Util.convertContent(content, "tdesc", taskDescription);
        Util.convertContent(content, "billable", billable);
        Util.convertContent(content, "rate", rate);

        return content;
    }
}
