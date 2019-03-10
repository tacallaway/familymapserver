package model;

import java.util.UUID;

/**
 * Represents a person.
 */
public class Person {
    /** Person primary key */
    private String personID;
    /** Descendant */
    private String descendant;
    /** First name */
    private String firstName;
    /** Last name */
    private String lastName;
    /** Person gender */
    private String gender;
    /** Father name */
    private String father;
    /** Mother name */
    private String mother;
    /** Spouse name */
    private String spouse;

    public Person(String personID, String firstName, String lastName, String gender) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public Person(String firstName, String lastName, String gender) {
        this(UUID.randomUUID().toString(), firstName, lastName, gender);
    }

    public String getPersonID() {
        return personID;
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

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }
}
