package net.amcintosh.freshbooks.models.api;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.Project;

import java.util.ArrayList;

public class ProjectListResponse {

    @Key
    public ArrayList<Project> projects;

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