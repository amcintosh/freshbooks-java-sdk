package net.amcintosh.freshbooks.models.api;

import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.Identity;

public class AuthErrorResponse {
    @Key public String error;
    @Key("error_description") public String errorDescription;
}
