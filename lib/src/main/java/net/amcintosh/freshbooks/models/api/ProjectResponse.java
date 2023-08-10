package net.amcintosh.freshbooks.models.api;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.Project;
import net.amcintosh.freshbooks.models.Service;
import net.amcintosh.freshbooks.models.ServiceRate;
import net.amcintosh.freshbooks.models.TimeEntry;

import java.util.List;

public class ProjectResponse {

    @Key public Project project;
    @Key public Service service;
    @Key("service_rate") public ServiceRate serviceRate;
    @Key("time_entry") public TimeEntry timeEntry;

    @Key public int errno;
    @Key public ProjectError error;

    public static class ProjectError {
        @Key public String title;
    }
}
