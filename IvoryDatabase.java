import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
    public String database_name; // name of the Ivory Database object.
    private Attribute[] attributes;
    private int rows, columns; // total number of rows, total number of columns, in the database.
    private String file_location; // location of the .ivry file where the database table is stored.

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
    public IvoryDatabase(String database_name, int rows, int columns, String file_location) {
        // validating params, checking if any of the parameters are null.
        try{
            if(database_name == null){
                throw new NullArgumentException("database_name");
            }
            else if(file_location == null){
                throw new NullArgumentException("file_location");
            }
        } catch(NullArgumentException e){
            e.printStackTrace();
        }

        if(database_name == null || file_location == null){
            try {
                throw new NullArgumentException("file_location");
            } catch (NullArgumentException nae) {
                createDirectoryAndIVRYFile();
            }
        }
        this.rows = rows; // number of rows (also called "entries") in the database
        this.columns = columns; // number of columns (also called "attributes") in the database
        this.file_location = file_location; // file location of the database
        attributes = new Attribute[columns]; // creating an Attribute Object array (Attribute Objects not initialized yet)

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

    /**
     * Explanation of how the following createDirectoryAndIVRYFile() method works.
     * 
     * ******** START OF METHOD ********
     * 
     * check if "Ivory_Databases" folder exists in current directory 
     * (the current directory is where the IvoryDatabase.java file exists):
     * |--> if False:
     * |    |   F.1. create an "Ivory_Databases" folder in current directory.
     * |    |   F.2. create an "Ivory_Database_(1).ivry" file.
     * |    |   F.3. assign path of "Ivory_Database_1.ivry" to (file_location).
     * |     
     * |--> if True:
     * |    |   T.1. go into "Ivory_Databases" directory by creating new File reference.
     * |    |   T.2. check if File "Ivory_Database_({DB_number}).ivry" already 
     * |             exists in "Ivory_Databases" directory. 
     * |             (where DB_number is an int variable starting at 1)
     * |             |--> if True:
     * |                  |   increment DB_number by 1 and go back to Step T.2.
     * |             |--> if False:
     * |                  |   create an "Ivory_Database_{DB_number}.ivry" file
     * |                  |   in the "Ivory_Databases" directory.
     * |    |   T.3. assign path of "Ivory_Database_{DB_number}.ivry" to (file_location).
     *     
     *  ******** END OF METHOD ********
     */
    private void createDirectoryAndIVRYFile() {
        
        try{
            // getting the Path of the current directory where the IvoryDatabase.java file is located.
            String currentDirectory = (new File(IvoryDatabase.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath())).getParent();
            
            // checking if "Ivory_Databases" folder exists in the current directory.
            if((new File(currentDirectory + File.separator + "Ivory_Databases")).exists()){
                
                // assigning the Path String of "Ivory_Databases" directory to String currentDirectory.
                currentDirectory = currentDirectory + File.separator + "Ivory_Databases" + File.separator;
                
                // creating a .ivry file in the "Ivory_Databases" directory.
                int DB_number = 1; // database number concatenated to the end of the .ivry filename.
                String filename; // stores the name of the Ivory Database file.
                do{
                    // connecting different parts of the filename together.
                    filename = "Ivory_Database(" + DB_number + ").ivry"; 
                    DB_number++; // incrementing by 1
                }while((new File(currentDirectory + filename)).exists());

                
            } // if
            else{

            } // else
        }catch(Exception ioe){
            ioe.printStackTrace();
            System.exit(-1); // unsuccessful termination
        }
    } // createDirectoryAndIVRYFile()

    /**
     * method to save changes made to the IvoryDatabase object and store 
     * it in the .ivry file at (file_location).
     * 
     * @see java.io.Serializable
     */
    public void SAVE(){
        boolean save_success; // true when the Ivory Database object is saved to file successfully.
        try {
            // creating FileOutputStream and ObjectOutputStream objects.
            FileOutputStream fileOut = new FileOutputStream(file_location);
            ObjectOutputStream streamOut = new ObjectOutputStream(fileOut);
            
            // writing IvoryDatabase.java object to (file_location).
            streamOut.writeObject(this);

            // closing streams
            streamOut.close();
            fileOut.close();

            save_success = true;
        } catch (IOException e) {
            save_success = false;
            e.printStackTrace();
        }

        if(save_success)
        // printing operation success message.
            System.out.println("IvoryDB Message : Operation Success : " + database_name + " has been saved successfully.");
        else
        // printing operation failure message.
            System.out.println("IvoryDB Message : Operation Failed : Changes made to " + database_name + " were not saved.");
    } // SAVE()

    /**
     * The close() method will run a loop through the attributes[] array and
     * assign every Attribute object as 'null'. This will be mark the objects 
     * for garbage collection.
     * 
     * Then System.gc() is invoked to call the JVM Garbage Collector which
     * will clean up the objects and free up system resources.
     */
    @Override
    public void close() {
        this.SAVE(); // saving all the changes made and creating output file.
        for(Attribute attribute:attributes) {
            attribute = null; 
        }
        System.gc(); // invoking java garbage collector
    } // close()
} // class