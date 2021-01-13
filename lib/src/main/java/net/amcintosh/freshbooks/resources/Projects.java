package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.models.Project;
import net.amcintosh.freshbooks.resources.api.ProjectResource;
import net.amcintosh.freshbooks.models.api.ProjectResponse;

public class Projects extends ProjectResource {

    public Projects(FreshBooksClient freshBooksClient) {
        super(freshBooksClient);
    }

    @Override
    protected String getPath() {
        return "projects";
    }

    public Project get(long businessId, long clientId) throws FreshBooksException {
        String url = this.getUrl(businessId, clientId);
        ProjectResponse result = this.handleRequest(HttpMethods.GET, url);
        return result.project;
    }

}
