import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;

import IvoryDBExceptions.*;

public class IvoryDatabase implements AutoCloseable, Serializable{
    /**
     * Each Attribute object in attributes[] acts like a Column in the Ivory Database.
     * Each Attribute object holds the column values for an Attribute in an Object Array.
     * Hence, an Ivory Database may have several Attributes associated to it and these
     * Attribute.java objects are kept track of using an Array of Attributes in the 
     * IvoryDatabase.java class: 'Attribute[] attributes'
     */
    Attribute[] attributes;
    int rows, columns; // total number of rows, total number of columns, in the table
    String file_location; // location of the .ivry file where the database table is stored

    /** 
     * initializing an IvoryDatabase object and creating a new .ivry file at "file_location"
     * 
     * @param rows - total number of rows in the database. "Rows" are also called "Entries"
     * 
     * @param columns - total number of columns in the database. "Columns" are also called 
     *                  "Attributes" and they are implemented using Attribute objects.
     * 
     *                  This is done because Java does not natively support multidimensional 
     *                  arrays of different Types. Hence the Attribute.java object is 
     *                  utilized to realize 'columns' in the Ivory Database and the
     *                  IvoryDatabase.java class contains the class variable attributes 
     *                  which is an Array of Attribute.java objects. 
     * 
     *                  Hence, we are able to create a 2 Dimensional data structure that is
     *                  capable of storing data of different data types in each column.
     * 
     * @param file_location - the location in the computer where the Ivory Database file
     *                        (.ivry) will be stored after creation of an Ivory Database by
     *                        the following constructor.
     *                        Should never be null
     * 
     * @throws 
     */
    public IvoryDatabase(int rows, int columns, String file_location) {
        if(file_location == null)
            throw new NullArgumentException("file_location");
        this.rows = rows; // number of rows (also called "entries") in the database
        this.columns = columns; // number of columns (also called "attributes") in the database
        this.file_location = file_location; // file location of the database
        attributes = new Attribute[columns]; // creating an Attribute Object array (Attribute Objects not initialized yet)

        try(BufferedWriter br = new 
    } // constructor IvoryDatabase(rows, columns)

    /** 
     * initializing an IvoryDatabase object with an existing .ivry file at "file_location"
     * 
     * @param file_location - the location in the computer where an existing Ivory Database
     *                        file (.ivry) is stored.
     * 
     * @throws IvoryDatabaseNotFoundException
     */
    public IvoryDatabase(String file_location) throws IvoryDatabaseNotFoundException{
        // validating file_location
        if (file_location == null || !file_location.endsWith(".ivry")) {
            throw new IvoryDatabaseNotFoundException(file_location);
        }

        this.file_location = file_location; // assigning to global variable

    } // constructor IvoryDatabase(file_location)

    public void sortDataBy(String attribute_name, boolean ascending){
        
    }

    public void getCell(int row, int column){

    } // getCell()

    public void addColumn(String attribute_name){

    }

    public void deleteColumn(String attribute_name){

    }

    public String findAllElementsWithAttribute(String attribute_name, String attribute_value){
        return "";
    }

    public void addRow(String row_attributes[]){

    } // addRow()

    public void deleteRow(int row_number){

    } // deleteRow(int)

    public void deleteRow(String attribute_name, String attribute_value){

    } // deleteRow(String, String)

    public boolean EXISTS(String attribute_name, String attribute_value){
        return false;
    } // FIND()

    /**
     * The close() method will run a loop through the attributes[] array and
     * assign every Attribute object as 'null'. This will be mark the objects 
     * for garbage collection. 
     * Then System.gc() is invoked to call the JVM Garbage Collector which
     * will clean up the objects and free up system resources.
     */
    @Override
    public void close() { 
        for(Attribute attribute:attributes) {
            attribute = null; 
        }
        System.gc(); // invoking java garbage collector
    } // close()
} // class