package result;

/**
 * Result for the operations that just return a message.
 */
public class MessageResult {
    /** Success or error message */
    private String message;

    public MessageResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
