package IvoryDBExceptions;

public class IvoryDatabaseNotFoundException extends Exception {
    public IvoryDatabaseNotFoundException(String file_path) {
        super("Ivory Database file (.ivry) is not found in: " + file_path);
    }
}