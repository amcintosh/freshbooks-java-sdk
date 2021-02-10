package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.common.collect.ImmutableMap;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.Project;
import net.amcintosh.freshbooks.models.ProjectList;
import net.amcintosh.freshbooks.models.api.ProjectListResponse;
import net.amcintosh.freshbooks.models.api.ProjectResponse;
import net.amcintosh.freshbooks.resources.api.ProjectResource;

import java.util.Map;

/**
 * FreshBooks projects resource with calls to get, list, create, update, delete
 */
public class Projects extends ProjectResource {

    public Projects(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    @Override
    protected String getPathForList() {
        return "projects";
    }

    @Override
    protected String getPathForSingle() {
        return "project";
    }

    public ProjectList list(long businessId) throws FreshBooksException {
        String url = this.getUrl(businessId, true);
        ProjectListResponse result = this.handleListRequest(url);
        return new ProjectList(result);
    }

    /**
     * Get a single project with the corresponding id.
     *
     * @param businessId Id of the business
     * @param projectId Id of the resource to return
     * //@param builder (Optional) IncludesBuilder object for including additional data, sub-resources, etc.
     *
     * @return The Project
     * @throws FreshBooksException If the call is not successful
     */
    public Project get(long businessId, long projectId) throws FreshBooksException {
        String url = this.getUrl(businessId, projectId);
        ProjectResponse result = this.handleRequest(HttpMethods.GET, url);
        return result.project;
    }

    /**
     * Create a new project from the provided Project model.
     * Makes a POST call against the project resource endpoint.
     *
     * This calls `project.getContent()` to get a hash map of data.
     *
     * @param businessId Id of the business
     * @param data Project model with create data
     *
     * @return The created Project
     * @throws FreshBooksException If the call is not successful
     */
    public Project create(long businessId, Project data) throws FreshBooksException {
        return this.create(businessId, data.getContent());
    }

    /**
     * Create a new project from the provided data.
     * Makes a POST call against the project resource endpoint.
     *
     * @param businessId Id of the business
     * @param data Map of create data
     *
     * @return The created Project
     * @throws FreshBooksException If the call is not successful
     */
    public Project create(long businessId, Map<String, Object> data) throws FreshBooksException {
        String url = this.getUrl(businessId, false);
        ImmutableMap<String, Object> content = ImmutableMap.of("project", data);
        ProjectResponse result = this.handleRequest(HttpMethods.POST, url, content);
        return result.project;
    }

    /**
     * Update the project with the corresponding id.
     * Makes a PUT call against the project resource endpoint.
     *
     * @param businessId Id of the business
     * @param projectId Id of the resource to update
     * @param data Project model with updated data
     *
     * @return The updated Project
     * @throws FreshBooksException If the call is not successful
     */
    public Project update(long businessId, long projectId, Project data) throws FreshBooksException {
        return this.update(businessId, projectId, data.getContent());
    }

    /**
     * Update the project with the corresponding id.
     * Makes a PUT call against the project resource endpoint.
     *
     * @param businessId Id of the business
     * @param projectId Id of the resource to update
     * @param data Map of data to change
     *
     * @return The updated Project
     * @throws FreshBooksException If the call is not successful
     */
    public Project update(long businessId, long projectId, Map<String, Object> data) throws FreshBooksException {
        String url = this.getUrl(businessId, projectId);
        ImmutableMap<String, Object> content = ImmutableMap.of("project", data);
        ProjectResponse result = this.handleRequest(HttpMethods.PUT, url, content);
        return result.project;
    }

    /**
     * Delete the project with the corresponding id.
     * Makes a DELETE call against the project resource endpoint.
     * <br><br>
     * <i>Note:</i> Most FreshBooks resources are soft-deleted,
     *
     * @param businessId Id of the business
     * @param projectId Id of the resource to update
     *
     * @throws FreshBooksException If the call is not successful
     */
    public void delete(long businessId, long projectId) throws FreshBooksException {
        String url = this.getUrl(businessId, projectId);
        System.out.println(url);
        this.handleRequest(HttpMethods.DELETE, url);
    }

}
