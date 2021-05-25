import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import IvoryDBExceptions.*;

public class IvoryDatabase implements AutoCloseable{
    /**
     * Each Attribute object in attributes[] acts like a Column.
     * Each Attribute object holds the data values for an Attribute in an Object Array.
     * Hence, the attributes for each "Row Entry" is spread across the several 
     */
    Attribute[] attributes;
    int rows, columns; // total number of rows, total number of columns, in the table
    String file_location; // location of the .ivry file where the database table is stored

    // initializing an IvoryDatabase object and creating a new .ivry file at "file_location"
    public IvoryDatabase(int rows, int columns, String file_location) {
        this.rows = rows; // number of rows (also called "entries") in the database
        this.columns = columns; // number of columns (also called "attributes") in the database
        this.file_location = file_location; // file location of the database
        attributes = new Attribute[columns]; // creating an Attribute Object array (Attribute Objects not initialized yet)
    } // constructor IvoryDatabase(rows, columns)

    // initializing an IvoryDatabase object with an existing .ivry file at "file_location"
    public IvoryDatabase(String file_location) throws IvoryDatabaseNotFoundException{
        // validating file_location
        if (file_location == null || !file_location.endsWith(".ivry")) {
            throw new IvoryDatabaseNotFoundException(file_location);
            return;
        }
        this.file_location = file_location; // assigning to global variable
        // 
        try (BufferedReader br = new BufferedReader(new FileReader(file_location))) {
            String line;
            while ((line = br.readLine()) != null) {
                table[row++] = line.split(","); // split up values in the line and store in String array
            } // while

        } // try
        catch (FileNotFoundException e) { e.printStackTrace(); }

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