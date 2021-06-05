package IvoryDatabase.IvoryDBExceptions;

public class ColumnNotFoundException extends Exception {
    public ColumnNotFoundException(String attribute_name, String DB_name) {
        super("The attribute \"" + attribute_name + "\" is not found in Ivory Database \"" + DB_name + "\".");
    }
}