package request;

/**
 * Request for the person operation.
 */
public class PersonRequest {
    /** Person ID */
    private String personID;

    public PersonRequest(String personID) {
        this.personID = personID;
    }

    public String getPersonID() {
        return personID;
    }
}
