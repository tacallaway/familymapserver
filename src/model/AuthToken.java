package model;

import java.util.UUID;

/**
 * Associates an auth token to a user.
 */
public class AuthToken {
    /** Token value */
    private String token;
    /** User object */
    private String username;

    public AuthToken(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public AuthToken(String username) {
        this(UUID.randomUUID().toString(), username);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }
}
