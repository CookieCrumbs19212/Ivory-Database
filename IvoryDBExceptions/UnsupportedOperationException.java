package IvoryDBExceptions;

public class UnsupportedOperationException extends Exception {
    public UnsupportedOperationException(String DB_name) {
        super("Unsupported Operation attempted on Ivory Database \"" + DB_name + "\"");
    }
}
