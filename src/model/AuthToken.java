package model;

import java.util.UUID;

/**
 * Associates an auth token to a user.
 */
public class AuthToken {
    /** Token value */
    private String token;
    /** User object */
    private User user;

    public AuthToken(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public AuthToken(User user) {
        this(UUID.randomUUID().toString(), user);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
