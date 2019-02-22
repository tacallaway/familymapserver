package model;

/**
 * Represents a user of the family information service.
 */
public class User {
    /** User name */
    private String userName;
    /** Password */
    private String password;
    /** User email */
    private String email;
    /** User first name */
    private String firstName;
    /** User last name */
    private String lastName;
    /** User gender */
    private String gender;
    /** Link to person */
    private Person person;

    public User(String userName, String password, String email, Person person) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.gender = person.getGender();
        this.person = person;
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

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Person getPerson() {
        return person;
    }
}
