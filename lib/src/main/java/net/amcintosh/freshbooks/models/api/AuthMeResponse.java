package net.amcintosh.freshbooks.models.api;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.Identity;
import net.amcintosh.freshbooks.models.Project;

public class AuthMeResponse {

    @Key("response") public Identity identity;

}
