import IvoryDBExceptions.NullArgumentException;
import IvoryDBExceptions.UnsupportedObjectException;

class Attribute {
    String attribute_name; // name of the attribute
    /**
     * Since we do not know the OBJECT TYPE of the attribute 
     * until it is initialized, we can declare 'cells[]' as 
     * a 'java.lang.Object' array.
     * 
     * Every object class in Java implements the 
     * 'java.lang.Object'. Therefore, by declaring 'cells[]' 
     * as a 'java.lang.Object' array, it can store objects of 
     * any class that implements 'java.lang.Object'.
     * 
     * For the Attribute object class, the 'cell[]' array
     * can be of Object Type String, Integer, Long, Short, 
     * Byte, Boolean, Float, Double or Character.
     * 
     * Hence, this gives us the freedom to declare the data 
     * type of the 'cells[]' array at a later time during 
     * initializing of an Attribute object. 
     */
    private Object cells[];

    /**
     * @param data_type 
     *        the data type with which the 'cells[]' array has to be initialized.
     * 
     * @param attribute_name
     *        the name of the Ivory Database attribute stored in this object.
     * 
     * @param size
     *        size of the 'cells[]' array.
     * 
     * @throws NullArgumentException
     *         when parameters {@code data_type} or {@code attribute_name} are null.
     * 
     * @throws UnsupportedObjectException
     *         when parameter {@code data_type} is NOT one of the 9 supported Object Types.
     */
    public Attribute(String data_type, String attribute_name, int size) {
        // validating params, checking if any of the parameters are null.
        try{
            if(data_type == null){
                throw new NullArgumentException("data_type");
            }
            else if(attribute_name == null){
                throw new NullArgumentException("attribute_name");
            }
        } catch(NullArgumentException e){
            e.printStackTrace();
        }

        this.attribute_name = attribute_name.toUpperCase();

        switch(data_type.toLowerCase()){
            case "string":
                cells = new String[size];
                break;

            case "integer":
            case "int":
                cells = new Integer[size];
                break;

            case "long":
                cells = new Long[size];
                break;
            
            case "short":
                cells = new Short[size];
                break;

            case "byte":
                cells = new Byte[size];
                break;
            
            case "boolean":
                cells = new Boolean[size];
                break;

            case "float":
                cells = new Float[size];
                break;

            case "double":
                cells = new Double[size];
                break;

            case "character":
            case "char":
                cells = new Character[size];
                break;
            default:
                try {
                    throw new UnsupportedObjectException(data_type);
                } catch (UnsupportedObjectException e) {
                    e.printStackTrace();
                }

        } // switch
    } // constructor

    /**
     * method to get the name of the attribute.
     * 
     * @return class variable String 'name' of the Attribute object.
     */
    public String getName(){
        return attribute_name;
    }

    /**
     * method for adding values to the class variable {@code cells[]}.
     * 
     * @param <T>
     *        the object type of the {@code value} that will be added to {@code cells[]}.
     * 
     * @param cell_index
     *        the index of {@code cells[]} where {@code value} is added.
     * 
     * @param value
     *        the value that is added to {@code cells[]}.
     * 
     * @see validateType(value)
     *      invoked to ensure that the Object Type of value is supported.
     */
    public <T extends Object> void addTo(int cell_index, T value){
        validateType(value);
        cells[cell_index] = value;
    }

    /**
     * method to validate the Object Type of the given parameter.
     * 
     * Only 9 Object Types are supported by Attribute.java.
     * Namely: String, Integer, Long, Short, Byte, Boolean, 
     *         Float, Double, and Character.
     * 
     * @param <T>
     *        can be any Object Type.
     * 
     * @param value
     *        the variable whose Object Type needs to be determined and validated.
     * 
     * @return boolean True if Object Type of {@code value} is one of the 9 
     *         supported Object Types.
     */
    private <T extends Object> boolean validateType(T value){
        if(value.getClass() == String.class){
            return true;
        }
        else if(value.getClass() == Integer.class){
            return true;
        }
        else if(value.getClass() == Boolean.class){
            return true;
        }
        else if(value.getClass() == Float.class){
            return true;
        }
        else if(value.getClass() == Double.class){
            return true;
        }
        else if(value.getClass() == Character.class){
            return true;
        }
        else {
            return false;
        }
    } // validateType()

} // Attribute class