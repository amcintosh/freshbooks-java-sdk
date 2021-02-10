package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.api.ProjectListResponse;

import java.util.ArrayList;

/**
 * Results of projects list call containing list of projects and pagination data.
 */
public class ProjectList extends ListResult {

    @Key private final ArrayList<Project> projects;

    public ProjectList(ProjectListResponse result) {
        projects = result.projects;
        this.pages = new Pages(result.meta.page, result.meta.pages, result.meta.perPage, result.meta.total);
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }
}
