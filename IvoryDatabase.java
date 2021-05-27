import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
    private Attribute[] attributes;
    private int rows, columns; // total number of rows, total number of columns, in the database.

    private File IVORY_DB_FILE; // the file where the Ivory Database is stored and saved to.

    private final String file_extension = ".ivry"; // the file extension of the IvoryDB file.
    private final String file_separator = File.separator; // the file separator of the System.


    /** 
     * creating a new Ivory Database file in Default Directory 
     * (which is the current working directory).
     * 
     * @param rows 
     *        total number of rows in the database. "Rows" are also called "Entries".
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
     */
    public IvoryDatabase(int rows, int columns) {
        // set IVORY_DB_FILE to the default.
        setDefaultFile();

        this.rows = rows; // number of rows (also called "entries") in the database.
        this.columns = columns; // number of columns (also called "attributes") in the database.
        attributes = new Attribute[columns]; // creating an array of uninitialized Attribute objects.
    } // constructor IvoryDatabase(int, int)

    /** 
     * creating a new Ivory Database file at {@code file_path}.
     * 
     * @param file_path
     *        the file path in the computer where the Ivory Database file
     *        (.ivry) will be saved after creation of an Ivory Database by
     *        the following constructor.
     * 
     * @throws DirectoryNotFoundException
     *         when parent directory in file_path does not exist.
     * 
     * @throws IllegalFileTypeException
     *         when file extension is incorrect, i.e. file extension is not ".ivry".
     */
    public IvoryDatabase(int rows, int columns, String file_path) 
        throws DirectoryNotFoundException, IllegalFileTypeException {
        // validating file_path.
        if (file_path == null){
            throw new IllegalArgumentException("File path cannot be null.");
        }
        // set IVORY_DB_FILE.
        setIvoryDatabaseFile(new File(file_path));

        this.rows = rows;
        this.columns = columns;
        attributes = new Attribute[columns];
    } // constructor IvoryDatabase(int, int, String)

    /**
     * creating a new Ivory Database file at {@code input_file}.
     * 
     * @param input_file
     *        File object where the new Ivory Database will be stored.
     * 
     * @throws DirectoryNotFoundException
     *         when parent directory of input_file does not exist.
     * 
     * @throws IllegalFileTypeException
     *         when file extension is incorrect, i.e. file extension is not ".ivry".
     */
    public IvoryDatabase(int rows, int columns, File input_file) 
        throws DirectoryNotFoundException, IllegalFileTypeException {
        // validate input_file.
        if (input_file == null){
            throw new IllegalArgumentException("Ivory Database File cannot be null.");
        }
        // set IVORY_DB_FILE.
        setIvoryDatabaseFile(input_file);

        this.rows = rows; // number of rows (also called "entries") in the database.
        this.columns = columns; // number of columns (also called "attributes") in the database.
        attributes = new Attribute[columns]; // creating an array of uninitialized Attribute objects.
    } // constructor IvoryDatabase(int, int, File)

    /**
     * creating a new Ivory Database file at {@code file_path} 
     * from another Ivory Database object. 
     * 
     * @param ivoryDBObject
     *        IvoryDatabase object from which attributes are being copied.
     * 
     * @param file_path
     *        the file path in the computer where the Ivory Database file
     *        (.ivry) will be saved after creation of an Ivory Database by
     *        the following constructor.
     * 
     * @throws DirectoryNotFoundException
     *         when parent directory in file_path does not exist.
     * 
     * @throws IllegalFileTypeException
     *         when file extension is incorrect, i.e. file extension is not ".ivry".
     */
    public IvoryDatabase(IvoryDatabase ivoryDBObject, String file_path) 
        throws DirectoryNotFoundException, IllegalFileTypeException {
        // validating file_path.
        if (file_path == null){
            throw new IllegalArgumentException("File path cannot be null.");
        }
        // set IVORY_DB_FILE.
        setIvoryDatabaseFile(new File(file_path));

        // copying parameter's object variables to this object's variables.
        this.attributes = ivoryDBObject.attributes;
        this.rows = ivoryDBObject.rows;
        this.columns = ivoryDBObject.columns;
    } // constructor IvoryDatabase(IvoryDatabase Object, String)

    /**
     * creating a new Ivory Database in file {@code input_file} 
     * from another Ivory Database object. 
     * 
     * @param ivoryDBObject
     *        IvoryDatabase object from which attributes are being copied.
     * 
     * @param input_file
     *        File object where the new Ivory Database will be stored.
     * 
     * @throws DirectoryNotFoundException
     *         when parent directory of input_file does not exist.
     * 
     * @throws IllegalFileTypeException
     *         when file extension is incorrect, i.e. file extension is not ".ivry".
     */
    public IvoryDatabase(IvoryDatabase ivoryDBObject, File input_file) 
        throws DirectoryNotFoundException, IllegalFileTypeException {
        // validate input_file.
        if (input_file == null){
            throw new IllegalArgumentException("Ivory Database File cannot be null.");
        }
        // set IVORY_DB_FILE.
        setIvoryDatabaseFile(input_file);

        // copying parameter's object variables to this object's variables.
        this.attributes = ivoryDBObject.attributes;
        this.rows = ivoryDBObject.rows;
        this.columns = ivoryDBObject.columns;
    } // constructor IvoryDatabase(IvoryDatabase Object, String)

    /** 
     * opening an existng IvoryDatabase from file at {@code file_path}.
     * 
     * @param file_path
     *        the file path in the system where an existing Ivory Database
     *        file (.ivry) is stored.
     * 
     * @throws IvoryDatabaseNotFoundException
     *         when file_path is null, or File at file_path does not exist.
     */
    public IvoryDatabase(String file_path) 
        throws IvoryDatabaseNotFoundException {
        // validating file_path.
        if(file_path == null){
            throw new IllegalArgumentException("File path cannot be null.");
        }
        // assigning File object of file_path to IVORY_DB_FILE.
        IVORY_DB_FILE = new File(file_path);
        if(!IVORY_DB_FILE.exists()){
            throw new IvoryDatabaseNotFoundException(file_path);
        }

        // deserializing the file.
        try {
            FileInputStream fileIn = new FileInputStream(IVORY_DB_FILE);
            ObjectInputStream streamIn = new ObjectInputStream(fileIn);

            // copying the class variables.
            IvoryDatabase deserializedDB = (IvoryDatabase) streamIn.readObject();
            this.attributes = deserializedDB.attributes;
            this.rows = deserializedDB.rows;
            this.columns = deserializedDB.columns;

            // closing streams.
            streamIn.close();
            fileIn.close();

            // deleting deserializedDB object
            deserializedDB = null;

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    } // constructor IvoryDatabase(String)

    /** 
     * opening an existng Ivory Database from {@code input_file}.
     * 
     * @param input_file
     *        the file where an existing Ivory Database file (.ivry) is stored.
     * 
     * @throws IvoryDatabaseNotFoundException
     *         when input_file is null, or input_file does not exist.
     */
    public IvoryDatabase(File input_file) 
        throws IvoryDatabaseNotFoundException {
        // validating input_file.
        if(input_file == null){
            throw new IllegalArgumentException("Ivory Database File cannot be null.");
        }
        // assigning input_file to IVORY_DB_FILE.
        IVORY_DB_FILE = input_file;
        if(!IVORY_DB_FILE.exists()){
            throw new IvoryDatabaseNotFoundException(input_file.getPath());
        }

        // deserializing the file.
        try {
            FileInputStream fileIn = new FileInputStream(IVORY_DB_FILE);
            ObjectInputStream streamIn = new ObjectInputStream(fileIn);

            // copying the class variables.
            IvoryDatabase deserializedDB = (IvoryDatabase) streamIn.readObject();
            this.attributes = deserializedDB.attributes;
            this.rows = deserializedDB.rows;
            this.columns = deserializedDB.columns;

            // closing streams.
            streamIn.close();
            fileIn.close();

            // deleting deserializedDB object
            deserializedDB = null;

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    } // constructor IvoryDatabase(File)

    /**
     * validates File objects and sets IVORY_DB_FILE.
     * 
     * @param input_file
     *        File object to be validated.
     * 
     * @throws IllegalArgumentException
     *         when input_file is null.
     * 
     * @throws DirectoryNotFoundException
     *         when parent directory of input_file does not exist.
     * 
     * @throws IllegalFileTypeException
     *         when file extension is incorrect, i.e. file extension is not ".ivry".
     */
    private void setIvoryDatabaseFile(File input_file) 
        throws DirectoryNotFoundException, IllegalFileTypeException{
        // 
        if (!input_file.getParentFile().exists()){
            throw new DirectoryNotFoundException(input_file.getParent());
        }
        else if (!input_file.getName().endsWith(file_extension)){
            throw new IllegalFileTypeException();
        }
        
        if (input_file.exists()){
            IVORY_DB_FILE = rectifyDatabaseNameCollision(input_file);
        }
        else{ 
            // set IVORY_DB_FILE
            IVORY_DB_FILE = input_file;
        }
    } // setIvoryDatabaseFile(File)

    /**
     * method to assign a default file to IVORY_DB_FILE.
     * 
     * The default file is placed inside the default directory "Local Ivory Databases"
     * 
     * If the "Local Ivory Databases" directory does not exist, create it.
     */
    private void setDefaultFile(){

        /** CREATING Local Ivory Databases DIRECTORY, IF IT DOESNT ALREADY EXIST **/
        // getting the Path string of the current working directory.
        String workingDirectory = System.getProperty("user.dir");

        // creating File object for "Local Ivory Databases" to check if it already exists.
        String defaultDirectoryPath = workingDirectory + file_separator + "Local Ivory Databases";
        File defaultDirectory = new File(defaultDirectoryPath);

        // if the default directory "Local Ivory Databases" does not already exist: create the directory.
        if(!defaultDirectory.exists()){
            if(defaultDirectory.mkdir()) // creating the "Local Ivory Databases" directory.
                System.out.println("\n\"Local Ivory Databases\" directory successfully created in working directory: " + workingDirectory + "\n");
            else 
                System.out.println("\n\"Local Ivory Databases\" directory creation failed.\n"); 
        }

        /** CREATING THE DEFAULT FILE **/
        // creating a default .ivry file in the "Local Ivory Databases" directory.
        int DB_number = 1; // database number concatenated to the end of the defaultFilename.
        String defaultFile; // stores the name of the Ivory Database file.
        do{
            // creating the filename by appending the file_extension to the database_name.
            defaultFile = defaultDirectoryPath + file_separator + 
                                "Ivory Database (" + DB_number + ")" + file_extension;
            DB_number++; // incrementing by 1
        }while(new File(defaultFile).exists()); // if the file exists, run loop again.

        // creating the default file and assigning it to IVORY_DB_FILE.
        IVORY_DB_FILE = new File(defaultFile);
    } // setDefaultFile()

    /**
     * method to rectify a filename collision when creating a new Ivory Database file. 
     * 
     * If a file with the same filename is passed to the constructor already exists 
     * in that directory, the filename needs to be rectified to avoid a file collision.
     * 
     * This is done because a file collision would lead to existing file to be overwritten
     * which is bad practice.
     * 
     * This method generates an alternate database filename by appending a number to the 
     * given filename. A File object is created with the rectified file path and returned.
     * 
     * @param file_path
     *        the path to the location where the 
     */
    private File rectifyDatabaseNameCollision(File input_file){
        // getting the filename without the file extension.
        String database_name = input_file.getName().substring(0, input_file.getName().indexOf(file_extension));
        // creating partial file path.
        String partial_path = input_file.getParent() + file_separator + database_name;

        // rectifying the filename.
        int DB_number = 1; // database number concatenated to the end of the filename.
        String full_path; // stores the complete path string.
        do{
            // creating the filename by appending database_name, DB_number and file_extension.
            full_path = partial_path + "(" + DB_number + ")" + file_extension;
            DB_number++; // incrementing by 1
        }while(new File(full_path).exists()); // if the file exists, run loop again.

        // return the file path with the rectified filename.
        return new File(full_path);
    } // rectifyDatabaseNameCollision()

    
    /**
     * @return - database name (same as the IVORY_DB_FILE filename).
     */
    public String getDatabaseName(){
        return IVORY_DB_FILE.getName();
    }
    
    /**
     * @return - the file path of IVORY_DB_FILE.
     */
    public String getPath(){
        return IVORY_DB_FILE.getPath();
    } // getFilePathAsString()

    /**
     * @return - parent directory path of IVORY_DB_FILE.
     */
    public String getParentPath(){
        return IVORY_DB_FILE.getParent();
    }

    /**
     * method to rename the Ivory Database.
     * 
     * First, the method checks if the new filename contains any File Separators.
     * Presence of File Separators in the new filename will cause a change of the 
     * file directory, which would effectively move the file to a different directory.
     * 
     * To prevent this, ensure the new filename does not contain File Separators using
     * {@code new_filename.indexOf(file_separator)}, which returns -1 if no File 
     * Separators are found in the new filename.
     * 
     * Then a new File object is created with the new filename and the parent path of 
     * the current IVORY_DB_FILE.
     * 
     * Check if the new File object already exists and points to a file. This is done
     * to prevent accidentally overwriting any existing files.
     * 
     * Finally, File.renameTo(File) is invoked to rename the file.
     * 
     * @param new_filename
     *        the new filename that the .ivry file has to be renamed to.
     * 
     * @return - true, if file rename operation was successful. Otherwise, returns false.
     */
    public boolean renameDatabase(String new_filename){
        // validating param, checking if new_filename is null or contains any file_separators.
        if(new_filename != null && new_filename.indexOf(file_separator) == -1){
            // creating a File object with the new filename.
            File new_file = new File(IVORY_DB_FILE.getParent() + file_separator + new_filename);

            // if a file with the new filename already exists, abort file renaming operation.
            if(!new_file.exists()){
                return IVORY_DB_FILE.renameTo(new_file); // rename
            }
        }
        return false;
    } // renameDatabase()

    /**
     * method to save changes made to the IvoryDatabase object and store 
     * it in the .ivry file at DB_SAVE_LOCATION.
     * 
     * @see java.io.Serializable
     */
    public void SAVE(){
        boolean save_success; // true when the Ivory Database object is saved to file successfully.
        try {
            // creating FileOutputStream and ObjectOutputStream objects.
            FileOutputStream fileOut = new FileOutputStream(IVORY_DB_FILE);
            ObjectOutputStream streamOut = new ObjectOutputStream(fileOut);
            
            // writing IvoryDatabase object to DB_SAVE_LOCATION.
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
            System.out.println("IvoryDB Message : Operation Success : " + IVORY_DB_FILE.getName() + " has been saved successfully.");
        else
        // printing operation failure message.
            System.out.println("IvoryDB Message : Operation Failed : Changes made to " + IVORY_DB_FILE.getName() + " were not saved.");
    
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
    @SuppressWarnings("unused") // suppressing the warning that 'attribute' is unused.
    public void close() {
        this.SAVE(); // saving all the changes made and creating output file.
        for(Attribute attribute:attributes) {
            attribute = null; 
        }
        System.gc(); // invoking java garbage collector
    } // close()
} // class