import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Locale;

import IvoryDBExceptions.*;

public class IvoryDatabase implements AutoCloseable, Serializable{
    /**
     * Each Attribute object in attributes[] acts like a Column in the Ivory Database.
     * Each Attribute object holds the column values for an Attribute in an Object Array.
     * Hence, an Ivory Database may have several Attributes associated to it and these
     * Attribute.java objects are kept track of using an Array of Attributes in the 
     * IvoryDatabase.java class: 'Attribute[] attributes'
     */
    private Attribute[] attributes;
    private int rows, columns; // total number of rows, total number of columns, in the database.

    private String file_directory; // directory where the .ivry file is stored.
    private String database_name; // name of the .ivry file.

    /** 
     * initializing an IvoryDatabase object and creating a new .ivry file at "file_location"
     * 
     * @param database_name
     *        the name of the IvoryDatabase object. Used as filename when saving 
     *        into .ivry file.
     *        Should not be null. If null, the class variable database_name will
     *        be assigned "Ivory_Database_({num})", where num is any integer number.
     * 
     * @param rows 
     *        total number of rows in the database. "Rows" are also called "Entries"
     * 
     * @param columns
     *        total number of columns in the database. "Columns" are also called 
     *        "Attributes" and they are implemented using Attribute objects.
     *              
     *        This is done because Java does not natively support multidimensional 
     *        arrays of different Types. Hence the Attribute.java object is 
     *        utilized to realize 'columns' in the Ivory Database and the
     *        IvoryDatabase.java class contains the class variable attributes 
     *        which is an Array of Attribute.java objects. 
     *           
     *        Hence, we are able to create a 2 Dimensional data structure that is
     *        capable of storing data of different data types in each column.
     * 
     * @param file_location
     *        the location in the computer where the Ivory Database file
     *        (.ivry) will be stored after creation of an Ivory Database by
     *        the following constructor.
     *        Should never be null. If null, the output file of this IvoryDB will 
     *        be saved at the default save location (differs for different OSes).
     */
    public IvoryDatabase(int rows, int columns, String file_location) {
        // setting file_directory and database_name.
        setDirectoryAndDBName(file_location);

        this.rows = rows; // number of rows (also called "entries") in the database
        this.columns = columns; // number of columns (also called "attributes") in the database
        this.file_directory = file_location; // file location of the database
        attributes = new Attribute[columns]; // creating an Attribute Object array (Attribute Objects not initialized yet)

    } // constructor IvoryDatabase(rows, columns, file_location)


    /** 
     * initializing an IvoryDatabase object with an existing .ivry file at "file_location"
     * 
     * @param file_location
     *        the location in the computer where an existing Ivory Database
     *        file (.ivry) is stored.
     * 
     * @throws IvoryDatabaseNotFoundException
     */
    public IvoryDatabase(String file_location) throws IvoryDatabaseNotFoundException{
        // validating file_location
        if (file_location == null || !file_location.endsWith(".ivry")) {
            throw new IvoryDatabaseNotFoundException(file_location);
        }

        // setting file_directory and database_name.
        setDirectoryAndDBName(file_location);

        // deserializing the file.
        try {
            FileInputStream fileIn = new FileInputStream(file_directory + database_name + ".ivry");
            ObjectInputStream streamIn = new ObjectInputStream(fileIn);

            // copying the class variables.
            IvoryDatabase deserializedDB = (IvoryDatabase) streamIn.readObject();
            this.attributes = deserializedDB.attributes;
            this.rows = deserializedDB.rows;
            this.columns = deserializedDB.columns;

            // closing streams.
            streamIn.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    } // constructor IvoryDatabase(file_location)

    public IvoryDatabase(IvoryDatabase ivoryDBObject, String file_location){
        this.attributes = ivoryDBObject.attributes;
        this.rows = ivoryDBObject.rows;
        this.columns = ivoryDBObject.columns;
        
        // setting file_directory and database_name.
        setDirectoryAndDBName(file_location);
    } // constructor IvoryDatabase(IvoryDBObject)


    private void setDirectoryAndDBName(String file_location){
        // checking if it with the correct file extension (.ivry).
        if(file_location.endsWith(".ivry")){
            // starting and ending index values of the "file name" contained within the file_location string.
            int startingIndex = file_location.lastIndexOf(File.separator) + 1;
            int endingIndex = file_location.lastIndexOf(".ivry");

            // taking the substring of "file_location" that contains only the file name without the file extension.
            this.database_name = file_location.substring(startingIndex, endingIndex);

            // check if the file location does exists.
            if(new File(file_location).exists()){
                this.file_directory = file_location.substring(0, startingIndex);
            }
            else{
                // set the file_directory to default directory.
                setDefaultFileDirectory();
            }
        }
        else{
            // set file_directory to default directory. 
            setDefaultFileDirectory();
            // set database_name to a default database name.
            setDefaultDatabaseName();
        }
    } // setDirectoryAndDBName()

    private void setDefaultDatabaseName(){
        // creating a .ivry file in the "Ivory_Databases" directory.
        int DB_number = 1; // database number concatenated to the end of the .ivry filename.
        String filename; // stores the name of the Ivory Database file.
        do{
            // connecting different parts of the filename together.
            database_name = "Ivory_Database(" + DB_number + ")";
            filename = database_name + ".ivry"; 
            DB_number++; // incrementing by 1
        }while((new File(file_directory + filename)).exists());
    } // setDefaultDatabaseName()

    private void setDefaultFileDirectory(){
        // getting the Path string of the current working directory.
        String workingDirectory = System.getProperty("user.dir");

        // creating an "Ivory_Databases" directory inside the current working directory.
        file_directory = workingDirectory + File.separator + "Ivory_Databases";
        File ivoryDBFolder = new File(file_directory);

        // concatenating the File.separator, for convienience later on.
        file_directory = file_directory + File.separator;

        // checking if the "Ivory_Databases" directory already exists, if it does not: create the directory.
        if(ivoryDBFolder.exists() == false){
            if(ivoryDBFolder.mkdir()) // creating the "Ivory_Databases" directory
                System.out.println("Ivory_Databases directory successfully created in working directory: " + workingDirectory);
            else 
                System.out.println("Ivory_Databases directory creation failed."); 
        }
    } // setDefaultFileDirectory()

    public String getPathAsString(){
        return (file_directory + database_name + ".irvy");
    } // getPathAsString()


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
            FileOutputStream fileOut = new FileOutputStream(file_directory + database_name + ".ivry");
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