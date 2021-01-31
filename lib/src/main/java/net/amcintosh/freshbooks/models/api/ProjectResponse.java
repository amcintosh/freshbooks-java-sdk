package net.amcintosh.freshbooks.models.api;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.Project;

public class ProjectResponse {

    @Key public Project project;

    @Key public int errno;
    @Key public ProjectError error;

    public static class ProjectError {
        @Key public String title;
    }
}