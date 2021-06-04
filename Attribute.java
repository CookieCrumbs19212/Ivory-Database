import java.io.Serializable;
import java.util.ArrayList;

import IvoryDBExceptions.UnsupportedObjectException;

class Attribute implements Serializable{
    private String attribute_name; // name of the attribute.
    /**
     * Since we do not know the OBJECT TYPE of the attribute 
     * until it is initialized, we can declare 'cells' as 
     * a generic ArrayList.
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
    private ArrayList<Object> cells;


    /**
     * @param attribute_name
     *        the name of the Ivory Database attribute stored in this object.
     * 
     * @param size
     *        size of the 'cells[]' array.
     */
    public Attribute(String attribute_name, int size){
        this.attribute_name = attribute_name.toUpperCase();
        this.cells = new ArrayList<>(size); // initializing ArrayList()
    } // constructor
    

    /**
     * method to get the name of the attribute.
     * 
     * @return class variable String 'name' of the Attribute object.
     */
    public String getName(){
        return attribute_name;
    } // getName()


    /**
     * method for adding values to the class variable {@code cells}.
     * 
     * @param value
     *        the value that is added to {@code cells}.
     */
    public void add(Object value){
        cells.add(value);
    } // add()


    /**
     * method for changing values in the class variable {@code cells}.
     * 
     * @param index
     *        the index in {@code cells} where {@code value} is added.
     * 
     * @param value
     *        the value that is added to {@code cells}.
     */
    public void set(int index, Object value){
        cells.set(index, value);
    } // set()


    /**
     * method to return the requested value from {@code cells}.
     * 
     * @param index
     *        the index of the value in {@code cells}.
     * 
     * @return the value at {@code index} of {@code cells}.         
     */
    public Object getValueAt(int index){
        return cells.get(index);
    } // getValueAt()


    /**
     * method to delete a value from {@code cells}.
     * 
     * @param index
     *        the index of the value to be deleted in {@code cells}.
     * 
     * @return the true if object the object at {@code index} in {@code cells} is successfully deleted.         
     */
    public boolean delete(int index){
        try{
            cells.remove(index); // if index is out of bounds, an exception is thrown
        }
        catch (Exception e){
            return false;
        }
        return true;
    } // delete()

} // Attribute class