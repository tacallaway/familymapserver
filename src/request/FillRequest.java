package request;

/**
 * Request for the fill operation.
 */
public class FillRequest {
    /** User name */
    private String userName;
    /** Num generations to populate */
    private int numGenerations;

    public FillRequest(String userName) {
        this(userName, 4);
    }

    public FillRequest(String userName, int numGenerations) {
        this.userName = userName;
        this.numGenerations = numGenerations;
    }

    public String getUserName() {
        return userName;
    }

    public int getNumGenerations() {
        return numGenerations;
    }
}
