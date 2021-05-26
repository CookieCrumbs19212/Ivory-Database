package IvoryDBExceptions;

public class NullArgumentException extends Exception {
    public NullArgumentException(String var_name) {
        super("Null value argument passed for String \"" + var_name + "\".");
    }
}