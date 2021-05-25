package IvoryDBExceptions;

public class InvalidArgumentException extends Exception {
    public InvalidArgumentException(String Null) {
        super("Null value argument passed.");
    }
    public InvalidArgumentException() {
        super("Invalid argument passed.");
    }
}