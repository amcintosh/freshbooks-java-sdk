package net.amcintosh.freshbooks.models;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.Util;
import net.amcintosh.freshbooks.models.api.ConvertibleContent;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Time Entries represent time spent working for a client or project.
 *
 * @see <a href="https://www.freshbooks.com/api/time_entries">FreshBooks API - Time Entries</a>
 */
public class TimeEntry extends GenericJson implements ConvertibleContent {

    @Key Long id;

    @Key Boolean active;
    @Key Boolean billable;
    @Key Boolean billed;
    @Key("client_id") Long clientId;
    @Key("created_at") String createdAt;
    @Key long duration;
    @Key("identity_id") Long identityId;
    @Key Boolean internal;
    @Key("is_logged") Boolean isLogged;
    @Key("local_started_at") String localStartedAt;
    @Key("local_timezone") String localTimezone;
    @Key String note;
    @Key("pending_client") String pendingClient;
    @Key("pending_project") String pendingProject;
    @Key("pending_task") String pendingTask;
    @Key("project_id") Long projectId;
    @Key("retainer_id") Long retainerId;
    @Key("service_id") Long serviceId;
    @Key("started_at") String startedAt;
    @Key("task_id") Long taskId;

    /*
    "timer": {
        "id": 15234483,
        "is_running": false
    }
    */

    /**
     * Get the unique identifier of this time entry within this business.
     *
     * @return TimeEntry id
     */
    public long getId() {
        return id;
    }

    /**
     * Whether the time entry is active or not.
     * <br><br>
     * <i>Note: A resource that is not active is essentially a "soft" delete.</i>
     *
     * @return True if active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set whether the time entry is active or not.
     * <br><br>
     * <i>Note: A resource that is not active is essentially a "soft" delete.</i>
     *
     * @param active active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Whether the time entry can be added to an invoice.
     *
     * @return True if billable
     */
    public boolean isBillable() {
        return billable;
    }

    /**
     * Set whether the time entry can be added to an invoice.
     *
     * @param billable billable
     */
    public void setBillable(boolean billable) {
        this.billable = billable;
    }

    /**
     * Whether the time entry has already been added to an invoice or manually marked as billed.
     *
     * @return True if it has been billed
     */
    public boolean isBilled() {
        return billed;
    }

    /**
     * Manually sets the time entry as billed.
     *
     * @param billed
     */
    public void setBilled(boolean billed) {
        this.billed = billed;
    }

    /**
     * Get the id of the client this time entry is/will be billed for.
     *
     * @return The client id in the business.
     */
    public long getClientId() {
        return clientId;
    }

    /**
     * Get the id of the client this time entry will be billed for.
     *
     * @param clientId
     */
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    /**
     * Get the creation time of the time entry record. This is not the same as when
     * the time entry began, which can be found with <code>getStartedAt</code>.
     * <br><br>
     * <i>Note:</i> The API returns this data in UTC.
     *
     * @return Create time in UTC
     */
    public ZonedDateTime getCreatedAt() {
        return Util.getZonedDateTimeFromISO(createdAt);
    }

    /**
     * The length of the time entry.
     * <br><br>
     * <i>Note:</i> The API returns this data as seconds.
     *
     * @return
     */
    public Duration getDuration() {
        return Duration.ofSeconds(duration);
    }

    /**
     * The length of the time entry in seconds.
     * <br><br>
     * <i>Note:</i> The API accepts this data as seconds.
     *
     * @param duration
     */
    public void setDuration(Duration duration) {
        this.duration = duration.getSeconds();
    }

    /**
     * The unique identifier of the team member or user logging the time entry.
     *
     * @return
     */
    public long getIdentityId() {
        return identityId;
    }

    /**
     * The unique identifier of the team member or user logging the time entry.
     *
     * @param identityId
     */
    public void setIdentityId(long identityId) {
        this.identityId = identityId;
    }

    /**
     * Get if the time entry is for internal work and not assigned to a client.
     *
     * @return True if the time entry is not assigned to a client
     */
    public boolean isInternal() {
        return internal;
    }

    /**
     * Set if the time entry is for internal work and not assigned to a client.
     *
     * @param internal
     */
    public void setInternal(boolean internal) {
        this.internal = internal;
    }

    /**
     * Whether the time entry has been finished and logged. Returns true if
     * the time entry was created directly. Returns false if the time entry
     * has been created by a timer which is still running.
     *
     * @return False if the time entry is currently being created from a running timer
     */
    public boolean isLogged() {
        return isLogged;
    }

    /**
     * Set if the time entry has been manually logged. A time entry must
     * be logged or belong to a timer.
     *
     * @param isLogged If the time entry is being manually logged.
     */
    public void setLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }

    /**
     * The date/time at which the work recorded in this time entry started using
     * the user's local timezone (set with <code>setLocalTimezone()</code>.
     *
     * @return Start time in user specified local timezone.
     */
    public ZonedDateTime getLocalStartedAt() {
        return LocalDateTime.parse(localStartedAt).atZone(this.getLocalTimezone());
    }

    /**
     * Get the local timezone specified for this time entry.
     *
     * @return ZoneId of user's timezone
     */
    public ZoneId getLocalTimezone() {
        return ZoneId.of(localTimezone);
    }

    /**
     * Set the user's local timezone for this time entry.
     *
     * @param localTimezone ZoneId of user's timezone
     */
    public void setLocalTimezone(ZoneId localTimezone) {
        this.localTimezone = localTimezone.toString();
    }

    /**
     * A short description of the work being done during the time.
     *
     * @return
     */
    public String getNote() {
        return note;
    }

    /**
     * A short description of the work being done during the time.
     *
     * @param note
     */
    public void setNote(String note) {
        this.note = note;
    }

    public String getPendingClient() {
        return pendingClient;
    }

    public void setPendingClient(String pendingClient) {
        this.pendingClient = pendingClient;
    }

    public String getPendingProject() {
        return pendingProject;
    }

    public void setPendingProject(String pendingProject) {
        this.pendingProject = pendingProject;
    }

    public String getPendingTask() {
        return pendingTask;
    }

    public void setPendingTask(String pendingTask) {
        this.pendingTask = pendingTask;
    }

    /**
     * The unique identifier of the project worked on during the time.
     *
     * @return
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * The unique identifier of the project worked on during the time.
     *
     * @param projectId
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * The unique identifier of the retainer worked on during the time.
     * @return
     */
    public long getRetainerId() {
        return retainerId;
    }

    /**
     * The unique identifier of the retainer worked on during the time.
     *
     * @param retainerId
     */
    public void setRetainerId(long retainerId) {
        this.retainerId = retainerId;
    }

    /**
     * The unique identifier of the project service worked on during the time.
     *
     * @return
     */
    public long getServiceId() {
        return serviceId;
    }

    /**
     * The unique identifier of the project service worked on during the time.
     *
     * @param serviceId
     */
    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * The date/time at which the work recorded in this time entry started in UTC.
     *
     * @return Start time in UTC
     */
    public ZonedDateTime getStartedAt() {
        return Util.getZonedDateTimeFromISO(startedAt);
    }

    /**
     * The date/time to record for when the work started.
     *
     * @param startedAt Start time in UTC
     */
    public void setStartedAt(ZonedDateTime startedAt) {
        this.startedAt = startedAt.toString();
    }

    /**
     * The unique identifier of the task worked on during the time.
     * @return
     */
    public long getTaskId() {
        return taskId;
    }

    /**
     * The unique identifier of the task worked on during the time.
     * @param taskId
     */
    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    @Override
    public Map<String, Object> getContent() {
        Map<String, Object> content = new HashMap<>();

        Util.convertContent(content, "active", this.active);
        Util.convertContent(content, "billable", this.billable);
        Util.convertContent(content, "billed", this.billed);
        Util.convertContent(content, "client_id", this.clientId);
        Util.convertContent(content, "duration", this.duration);
        Util.convertContent(content, "client_id", this.clientId);
        Util.convertContent(content, "identity_id", this.identityId);
        Util.convertContent(content, "internal", this.internal);
        Util.convertContent(content, "is_logged", this.isLogged);
        Util.convertContent(content, "local_timezone", this.localTimezone);
        Util.convertContent(content, "note", this.note);
        Util.convertContent(content, "pending_client", this.pendingClient);
        Util.convertContent(content, "pending_project", this.pendingProject);
        Util.convertContent(content, "pending_task", this.pendingTask);
        Util.convertContent(content, "project_id", this.projectId);
        Util.convertContent(content, "retainer_id", this.retainerId);
        Util.convertContent(content, "service_id", this.serviceId);
        Util.convertContent(content, "started_at", this.startedAt);
        Util.convertContent(content, "task_id", this.taskId);
        return content;
    }
}
