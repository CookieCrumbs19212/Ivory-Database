package IvoryDBExceptions;

public class IvoryDatabaseNotFoundException extends Exception {
    public IvoryDatabaseNotFoundException(String location) {
        super("Ivory Database file (.ivry) is not found in: " + location);
    }
}