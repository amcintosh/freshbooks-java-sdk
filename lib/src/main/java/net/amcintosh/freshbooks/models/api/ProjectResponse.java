package net.amcintosh.freshbooks.models.api;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.Project;
import net.amcintosh.freshbooks.models.Service;

public class ProjectResponse {

    @Key public Project project;
    @Key public Service service;

    @Key public int errno;
    @Key public ProjectError error;

    public static class ProjectError {
        @Key public String title;
    }
}
