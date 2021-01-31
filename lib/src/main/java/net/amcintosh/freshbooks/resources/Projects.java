package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.Project;
import net.amcintosh.freshbooks.models.ProjectList;
import net.amcintosh.freshbooks.models.api.ProjectListResponse;
import net.amcintosh.freshbooks.models.api.ProjectResponse;
import net.amcintosh.freshbooks.resources.api.ProjectResource;

public class Projects extends ProjectResource {

    public Projects(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    @Override
    protected String getPath() {
        return "projects";
    }

    public ProjectList list(long businessId) throws FreshBooksException {
        String url = this.getUrl(businessId);
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

}