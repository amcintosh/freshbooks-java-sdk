package net.amcintosh.freshbooks.models;

import com.google.api.client.util.Key;

public class Client {

    @Key
    public long id;

    @Key
    public String organization;
}
