package net.amcintosh.freshbooks.models;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.google.api.client.util.NullValue;
import com.google.api.client.util.Value;
import net.amcintosh.freshbooks.Util;
import net.amcintosh.freshbooks.models.api.ConvertibleContent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Projects in FreshBooks are used to track business projects and related information
 * such as hourly rate, service(s) being offered, projected end date...etc
 *
 * @see <a href="https://www.freshbooks.com/api/project">FreshBooks API - Projects</a>
 */
public class Project extends GenericJson implements ConvertibleContent {

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
    @Key("project_manager_id") Long projectManagerId;
    @Key("project_type") ProjectType projectType;
    @Key String rate;
    @Key("retainer_id") Long retainerId;
    @Key List<Service> services;
    @Key String title;
    @Key("updated_at") String updatedAt;

    /**
     * Get the unique identifier of this project within this business.
     *
     * @return Project id
     */
    public long getId() {
        return id;
    }

    /**
     * Whether the project is active or not.
     * <br><br>
     * <i>Note: A resource that is not active is essentially a "soft" delete.</i>
     *
     * @return True if active
     */
    public boolean getActive() {
        return active;
    }

    /**
     * Set whether the project is active or not.
     * <br><br>
     * <i>Note: A resource that is not active is essentially a "soft" delete.</i>
     *
     * @param active active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * The amount that has been invoiced for this project.
     *
     * @return The decimal currency amount
     */
    public BigDecimal getBilledAmount() {
        return new BigDecimal(billedAmount);
    }

    /**
     * If the project has been billed or not.
     *
     * @return Unbilled, Partial, or Billed status
     */
    public ProjectBilledStatus getBilledStatus() {
        return billedStatus;
    }

    /**
     * Get the method by which the project is billed.
     * <br><br>
     * Eg. By business hourly rate, team member's rate, different rates
     * by service provided, or a rate for the project.
     *
     * @return Billing method
     */
    public ProjectBillingMethod getBillingMethod() {
        return billingMethod;
    }

    /**
     * Set the method by which the project is billed.
     * <br><br>
     * Eg. By business hourly rate, team member's rate, different rates
     * by service provided, or a rate for the project.
     *
     * @param billingMethod Billing method
     */
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

    /**
     * Get the id of the client this project is for.
     *
     * @return The client id in the business.
     */
    public long getClientId() {
        return clientId;
    }

    /**
     * Set the id of the client the project is for.
     *
     * @param clientId The client id in the business.
     */
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    /**
     * If the project has been completed and is archived.
     * <br><br>
     * Archived projects do not return in list results by default.
     *
     * @return True if project is completed and archived
     */
    public boolean getComplete() {
        return complete;
    }

    /**
     * Marks the project as completed and archived.
     * <br><br>
     * Archived projects do not return in list results by default.
     *
     * @param complete
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    /**
     * Get the creation time of the project.
     * <br><br>
     * <i>Note:</i> The API returns this data in UTC.
     *
     * @return Create time in UTC
     */
    public ZonedDateTime getCreatedAt() {
        return Util.getZonedDateTimeFromProjectNaiveUTC(createdAt);
    }

    /**
     * Get the description of project.
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of project.
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Date of projected completion.
     *
     * @return
     */
    public LocalDate getDueDate() {
        return LocalDate.parse(dueDate);
    }

    /**
     * Date of projected completion.
     *
     * @param dueDate
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate.format(Util.getStandardDateFormatter());
    }

    /**
     * String percentage markup to be applied to expenses fo this project.
     *
     * @return
     */
    public String getExpenseMarkup() {
        return expenseMarkup;
    }

    /**
     * String percentage markup to be applied to expenses fo this project.
     *
     * @param expenseMarkup
     */
    public void setExpenseMarkup(String expenseMarkup) {
        this.expenseMarkup = expenseMarkup;
    }

    /**
     * For projects that are of project type "fixed_price" this is the price for the project.
     *
     * @return Decimal price for the project.
     */
    public BigDecimal getFixedPrice() {
        return new BigDecimal(fixedPrice);
    }

    /**
     * For projects that are of project type "fixed_price" this is the price for the project.
     *
     * @param fixedPrice Decimal price for the project.
     */
    public void setFixedPrice(BigDecimal fixedPrice) {
        this.fixedPrice = fixedPrice.toString();
    }

    /**
     * The project group contains the identity details of all the members of the project
     * and any pending invitations to the project.
     *
     * @return
     */
    public ProjectGroup getGroup() {
        return group;
    }

    /**
     * Clarifies that the project is internal to the business and has no client
     * (client is the company).
     *
     * @return
     */
    public boolean getInternal() {
        return internal;
    }

    /**
     * Sets that the project is internal to the business and has no client
     * (client is the company).
     *
     * @param internal
     */
    public void setInternal(boolean internal) {
        this.internal = internal;
    }

    /**
     * The time logged against the project in seconds.
     *
     * @return
     */
    public int getLoggedDuration() {
        return loggedDuration;
    }


    public long getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(Long projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    /**
     * The project type. Either a fixed price or hourly rate.
     * <br><br>
     * The type of hourly rate used is found in <code>getBillingMethod()</code>.
     *
     * @return
     */
    public ProjectType getProjectType() {
        return projectType;
    }

    /**
     * The project type. Either a fixed price or hourly rate.
     * <br><br>
     * The type of hourly rate used is set with <code>getBillingMethod()</code>.
     *
     * @param projectType
     */
    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    /**
     * The hourly rate for project_rate hourly projects.
     *
     * @return
     */
    public BigDecimal getRate() {
        return new BigDecimal(rate);
    }

    /**
     * The hourly rate for project_rate hourly projects.
     *
     * @param rate
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate.toString();
    }

    public long getRetainerId() {
        return retainerId;
    }

    public void setRetainerId(long retainerId) {
        this.retainerId = retainerId;
    }

    /**
     * The services that work in this project can be logged against and will appear on
     * invoices when the project is billed for.
     *
     * @return
     */
    public List<Service> getServices() {
        return services;
    }

    /**
     * The project title
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * The project title
     *
     * @param title
     */
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
        Util.convertContent(content, "active", this.active);
        Util.convertContent(content, "billing_method", this.billingMethod);
        Util.convertContent(content, "budget", this.budget);
        Util.convertContent(content, "client_id", this.clientId);
        Util.convertContent(content, "complete", this.complete);
        Util.convertContent(content, "description", this.description);
        Util.convertContent(content, "due_date", this.dueDate);
        Util.convertContent(content, "expense_markup", this.expenseMarkup);
        Util.convertContent(content, "fixed_price", this.fixedPrice);
        Util.convertContent(content, "internal", this.internal);
        Util.convertContent(content, "project_manager_id", this.projectManagerId);
        Util.convertContent(content, "project_type", this.projectType);
        Util.convertContent(content, "rate", this.rate);
        Util.convertContent(content, "retainer_id", this.retainerId);
        Util.convertContent(content, "title", this.title);
        return content;
    }

    public static class ProjectGroup {
        @Key Long id;
        @Key List<ProjectGroupMember> members;
        @Key("pending_invitations") List<PendingInvitation> pendingInvitations;

        public long getId() {
            return id;
        }

        /**
         * The members of this project.
         *
         * @return
         */
        public List<ProjectGroupMember> getMembers() {
            return members;
        }
        /**
         * The pending invites to employees and contractors within the project.
         *
         * @return
         */
        public List<PendingInvitation> getPendingInvitations() {
            return pendingInvitations;
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

    /**
     * Billing statuses for a project, computed from invoice totals that have been sent
     * for that project.
     */
    public enum ProjectBilledStatus {
        @Value("billed") BILLED,
        @Value("partially_billed") PARTIALLY_BILLED,
        @Value("unbilled") UNBILLED
    }

    /**
     * Method of calculating billing for a project
     */
    public enum ProjectBillingMethod {
        @Value("business_rate") BUSINESS_RATE,
        @Value("project_rate") PROJECT_RATE,
        @Value("service_rate") SERVICE_RATE,
        @Value("team_member_rate") TEAM_MEMBER_RATE,
        @NullValue NONE
    }

    /**
     * Billing types for projects
     */
    public enum ProjectType {
        @Value("fixed_price") FIXED_PRICE,
        @Value("hourly_rate") HOURLY_RATE;
    }

}
