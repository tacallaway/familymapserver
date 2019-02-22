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
    /** Link to person */
    private Person person;

    public User(String userName, String password, String email, Person person) {
        this.userName = userName;
        this.password = password;
        this.email = email;
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
        return person.getFirstName();
    }

    public String getLastName() {
        return person.getLastName();
    }

    public String getGender() {
        return person.getGender();
    }

    public Person getPerson() {
        return person;
    }
}
