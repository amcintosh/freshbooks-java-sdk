package net.amcintosh.freshbooks.models.api;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.Service;

import java.util.List;

public class ServiceListResponse {

    @Key public List<Service> services;
    @Key public Meta meta;

    public static class Meta {
        @Key public int total;
        @Key("per_page") public int perPage;
        @Key public int page;
        @Key public int pages;
    }
}
