package result;

/**
 * Result for the person operation.
 */
public class PersonResult {
    /** Name of descendant */
    private String descendant;
    /** Person ID */
    private String personID;
    /** First name */
    private String firstName;
    /** Last name */
    private String lastName;
    /** Gender */
    private String gender;
    /** Name of father */
    private String father;
    /** Name of mother */
    private String mother;
    /** Name of spouse */
    private String spouse;
    /** Error message */
    private String message;

    public PersonResult(String descendant, String personID, String firstName, String lastName,
                        String gender, String father, String mother, String spouse) {
        this.descendant = descendant;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }

    public PersonResult(String message) {
        this.message = message;
    }

    public String getDescendant() {
        return descendant;
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

    public String getFather() {
        return father;
    }

    public String getMother() {
        return mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public String getMessage() {
        return message;
    }
}
