package request;

/**
 * Request for the login operation.
 */
public class LoginRequest {
    /** User name */
    private String userName;
    /** Password */
    private String password;

    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
