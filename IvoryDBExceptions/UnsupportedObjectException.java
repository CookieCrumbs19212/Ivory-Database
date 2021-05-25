package IvoryDBExceptions;

public class UnsupportedObjectException extends Exception {
    public UnsupportedObjectException(String type_name) {
        super("Attribute declaration with unsupported Object: \"" + type_name + "\".\n" + 
                "Supported Objects: String, Integer, Boolean, Float, Double, Character.");
    }
}