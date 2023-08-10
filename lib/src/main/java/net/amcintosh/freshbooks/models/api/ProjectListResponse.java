package net.amcintosh.freshbooks.models.api;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.Project;
import net.amcintosh.freshbooks.models.Service;
import net.amcintosh.freshbooks.models.ServiceRate;
import net.amcintosh.freshbooks.models.TimeEntry;

import java.util.ArrayList;
import java.util.List;

public class ProjectListResponse {

    @Key
    public ArrayList<Project> projects;

    @Key
    public List<Service> services;

    @Key("service_rates")
    public List<ServiceRate> serviceRates;

    @Key("time_entries")
    public List<TimeEntry> timeEntries;

    @Key
    public ProjectMeta meta;

    @Key public String error;

    public static class ProjectMeta {
        @Key public int total;
        @Key("per_page") public int perPage;
        @Key public int page;
        @Key public int pages;
    }
}
