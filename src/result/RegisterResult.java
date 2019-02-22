package result;

/**
 * Result for the register operation.
 */
public class RegisterResult {
    /** Auth token */
    private String authToken;
    /** User name */
    private String userName;
    /** Person ID */
    private String personID;
    /** Error message */
    private String message;

    public RegisterResult(String authToken, String userName, String personID) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }

    public RegisterResult(String message) {
        this.message = message;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUserName() {
        return userName;
    }

    public String getPersonID() {
        return personID;
    }

    public String getMessage() {
        return message;
    }
}
