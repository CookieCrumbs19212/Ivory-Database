import java.io.Serializable;

import IvoryDBExceptions.UnsupportedObjectException;

class Attribute implements Serializable{
    private String attribute_name; // name of the attribute.
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
     * @throws UnsupportedObjectException
     *         when parameter {@code data_type} is NOT one of the 9 supported Object Types.
     */
    public Attribute(String data_type, String attribute_name, int size) {
        // validating params, checking if any of the parameters are null.
        if(data_type == null){
            throw new IllegalArgumentException("data_type cannot be null.");
        }
        if(attribute_name == null){
            throw new IllegalArgumentException("attribute_name cannot be null.");
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
                    System.exit(-1); // unsuccessful termination
                }
                break;
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
    public <T extends Object> void setCell(int cell_index, T value){
        validateType(value);
        cells[cell_index] = value;
    } // setCell()

    /**
     * method to return the requested value from {@code cells[]}.
     * 
     * @param index
     *        the array index number of the value in {@code cells[]}.
     * 
     * @return the value at {@code index} of {@code cells[]} in the 
     *         same Object Type of the array elements in {@code cells[]};
     *         i.e. String, Integer, Long, Short, Byte, Boolean, Float, 
     *              Double, or Character Object Type.
     */
    public Object getValue(int index){
        return cells[index];
    } // getValue()

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
        Class<?> cls = value.getClass(); // getting and storing the Object Class of the 'value' object.

        if(cls == String.class){
            return true;
        }
        else if(cls == Integer.class){
            return true;
        }
        else if(cls == Boolean.class){
            return true;
        }
        else if(cls == Float.class){
            return true;
        }
        else if(cls == Double.class){
            return true;
        }
        else if(cls == Character.class){
            return true;
        }
        else {
            try {
                throw new UnsupportedObjectException(cls.toString());
            } catch (UnsupportedObjectException e) {
                e.printStackTrace();
                System.exit(-1); // unsuccessful termination
            }
            return false;
        }
    } // validateType()

} // Attribute class