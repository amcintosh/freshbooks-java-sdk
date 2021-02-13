package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.Util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Projects in FreshBooks are used to track business projects and related information
 * such as hourly rate, service(s) being offered, projected end date...etc
 *
 * @see <a href="https://www.freshbooks.com/api/project">FreshBooks API - Projects</a>
 */
public class Project {

    @Key Long id;
    @Key Boolean active;
    @Key("billed_amount") String billedAmount;
    @Key("billed_status") ProjectBilledStatus billedStatus;
    @Key("billing_method") ProjectBillingMethod billingMethod;
    @Key Integer budget;
    @Key("client_id") Long clientId;
    @Key Boolean complete;
    @Key("created_at") String createdAt;
    @Key String description;
    @Key("due_date") String dueDate;
    @Key("expense_markup") String expenseMarkup;
    @Key("fixed_price") String fixedPrice;
    @Key ProjectGroup group;
    @Key Boolean internal;
    @Key("logged_duration") Integer loggedDuration;
    @Key("pending_invitations") ArrayList<PendingInvitation> pendingInvitations;
    @Key("project_manager_id") Long projectManagerId;
    @Key("project_type") ProjectType projectType;
    @Key String rate;
    @Key("retainer_id") Long retainerId;
    @Key Boolean sample;
    @Key ArrayList<Service> services;
    @Key String title;
    @Key("updated_at") String updatedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BigDecimal getBilledAmount() {
        return new BigDecimal(billedAmount);
    }

    public void setBilledAmount(BigDecimal billedAmount) {
        this.billedAmount = billedAmount.toString();
    }

    public ProjectBilledStatus getBilledStatus() {
        return billedStatus;
    }

    public void setBilledStatus(ProjectBilledStatus billedStatus) {
        this.billedStatus = billedStatus;
    }

    public ProjectBillingMethod getBillingMethod() {
        return billingMethod;
    }

    public void setBillingMethod(ProjectBillingMethod billingMethod) {
        this.billingMethod = billingMethod;
    }

    /**
     * Get time budgeted for the project in seconds.
     *
     * @return int of time budget in seconds.
     */
    public int getBudget() {
        return budget;
    }

    /**
     * Set time budgeted for the project.
     *
     * @param budget seconds of time budget
     */
    public void setBudget(int budget) {
        this.budget = budget;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public boolean getComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public ZonedDateTime getCreatedAt() {
        return Util.getZonedDateTimeFromProjectNaiveUTC(createdAt);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return LocalDate.parse(dueDate);
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate.toString();
    }

    public int getExpenseMarkup() {
        return new Integer(expenseMarkup).intValue();
    }

    public void setExpenseMarkup(int expenseMarkup) {
        this.expenseMarkup = new Integer(expenseMarkup).toString();
    }

    public BigDecimal getFixedPrice() {
        return new BigDecimal(fixedPrice);
    }

    public void setFixedPrice(BigDecimal fixedPrice) {
        this.fixedPrice = fixedPrice.toString();
    }

    public ProjectGroup getGroup() {
        return group;
    }

    public boolean getInternal() {
        return internal;
    }

    public void setInternal(boolean internal) {
        this.internal = internal;
    }

    public int getLoggedDuration() {
        return loggedDuration;
    }

    public ArrayList<PendingInvitation> getPendingInvitations() {
        return pendingInvitations;
    }

    public long getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(Long projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    public BigDecimal getRate() {
        return new BigDecimal(rate);
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate.toString();
    }

    public long getRetainerId() {
        return retainerId;
    }

    public void setRetainerId(long retainerId) {
        this.retainerId = retainerId;
    }

    public Boolean getSample() {
        return sample;
    }

    public void setSample(Boolean sample) {
        this.sample = sample;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the time of last modification to the project.
     *
     * @return Updated time in UTC
     */
    public ZonedDateTime getUpdatedAt() {
        return Util.getZonedDateTimeFromProjectNaiveUTC(this.updatedAt);
    }

    public Map<String, Object> getContent() {
        Map<String, Object> content = new HashMap<>();
        Util.putIfNotNull(content, "active", this.active);
        Util.putIfNotNull(content, "billed_amount", this.billedAmount);
        Util.putIfNotNull(content, "billed_status", this.billedStatus);
        Util.putIfNotNull(content, "billing_method", this.billingMethod);
        Util.putIfNotNull(content, "budget", this.budget);
        Util.putIfNotNull(content, "client_id", this.clientId);
        Util.putIfNotNull(content, "complete", this.complete);
        Util.putIfNotNull(content, "description", this.description);
        Util.putIfNotNull(content, "due_date", this.dueDate);
        Util.putIfNotNull(content, "expense_markup", this.expenseMarkup);
        Util.putIfNotNull(content, "fixed_price", this.fixedPrice);
        Util.putIfNotNull(content, "internal", this.internal);
        Util.putIfNotNull(content, "project_manager_id", this.projectManagerId);
        Util.putIfNotNull(content, "project_type", this.projectType);
        Util.putIfNotNull(content, "rate", this.rate);
        Util.putIfNotNull(content, "retainer_id", this.retainerId);
        Util.putIfNotNull(content, "title", this.title);
        return content;
    }

    public static class ProjectGroup {
        @Key Long id;
        @Key ArrayList<ProjectGroupMember> members;

        public long getId() {
            return id;
        }

        public ArrayList<ProjectGroupMember> getMembers() {
            return members;
        }
    }

    public static class ProjectGroupMember {
        @Key Long id;
        @Key Boolean active;
        @Key String company;
        @Key String email;
        @Key("first_name") String firstName;
        @Key("identity_id") Long identityId;
        @Key("last_name") String lastName;
        @Key String role;

        public long getId() {
            return id;
        }

        public boolean getActive() {
            return active;
        }

        public String getCompany() {
            return company;
        }

        public String getEmail() {
            return email;
        }

        public String getFirstName() {
            return firstName;
        }

        public long getIdentityId() {
            return identityId;
        }

        public String getLastName() {
            return lastName;
        }

        public String getRole() {
            return role;
        }
    }

    public static class PendingInvitation {
        @Key Long id;
        @Key("group_id") Long groundId;
        @Key String capacity;
        @Key("to_email") String toEmail;

        public long getId() {
            return id;
        }

        public long getGroundId() {
            return groundId;
        }

        public String getCapacity() {
            return capacity;
        }

        public String getToEmail() {
            return toEmail;
        }
    }
}
