import IvoryDBExceptions.InvalidArgumentException;
import IvoryDBExceptions.UnsupportedObjectException;

class Attribute {
    String name; // name of the attribute
    /**
     * Since we do not know the DATA TYPE of the attribute 
     * until it is initialized, we can declare 'cells[]' as 
     * a 'java.lang.Object' array.
     * 
     * Every primitive data type has a Wrapper class that
     * implements 'java.lang.Object'. Therefore, by declaring
     * 'cell[]' as a 'java.lang.Object' array, it can store 
     * objects of any class that implements the 
     * 'java.lang.Object' interface.
     * 
     * For the Attribute object class, the 'cell[]' array
     * can be of type String, Integer, Boolean, Float, 
     * Double or Character.
     * 
     * Hence, this gives us the freedom to declare the data 
     * type of the 'cells[]' array at a later time during 
     * initializing of an Attribute object. 
     */
    private Object column_cells[];

    /**
     * @param data_type
     * @param attribute_name
     * @param size
     * @throws UnsupportedObjectException
     */
    public Attribute(String data_type, String attribute_name, int size) 
        throws UnsupportedObjectException, InvalidArgumentException{
        // validating params
        if(data_type == null || attribute_name == null){
            throw new InvalidArgumentException("null");
        }
        this.name = attribute_name.toUpperCase(); 
        switch(data_type.toLowerCase()){
            case "string":
                column_cells = new String[size];
                break;
            case "integer":
            case "int":
                column_cells = new Integer[size];
                break;
            case "boolean":
                column_cells = new Boolean[size];
                break;
            case "float":
                column_cells = new Float[size];
                break;
            case "double":
                column_cells = new Double[size];
                break;
            case "character":
            case "char":
                column_cells = new Character[size];
                break;
            default:
                throw new UnsupportedObjectException(data_type);

        } // switch
    } // constructor

    public String getName(){
        return name;
    }
} // Attribute class