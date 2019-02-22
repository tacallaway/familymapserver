package request;

/**
 * Request for the register operation.
 */
public class RegisterRequest {
    /** User name */
    private String userName;
    /** Password */
    private String password;
    /** Email address */
    private String email;
    /** First name */
    private String firstName;
    /** Last name */
    private String lastName;
    /** Gender */
    private String gender;

    public RegisterRequest(String userName, String password, String email, String firstName,
                           String lastName, String gender) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }
}
