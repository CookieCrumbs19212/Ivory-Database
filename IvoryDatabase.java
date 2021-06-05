import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.ArrayList;

import IvoryDBExceptions.*;

public class IvoryDatabase implements AutoCloseable, Serializable{
    /**
     * Each Attribute object in attributes[] acts like a Column in the Ivory Database.
     * Each Attribute object holds the column values for an Attribute in an Object Array.
     * Hence, an Ivory Database may have several Attributes associated to it and these
     * Attribute.java objects are kept track of using an Array of Attributes in the 
     * IvoryDatabase.java class: 'Attribute[] attributes'
     */
    private ArrayList<Column> columns; // ArrayList of all Column objects of this Ivory Database.
    private int no_of_columns; 
    private int no_of_rows; // total number of rows in the database.

    private File FILE_LOCATION = null; // the file where the Ivory Database is stored and saved to.

    private final String file_extension = ".ivry"; // the file extension of the IvoryDB file.
    private final String file_separator = File.separator; // the file separator of the System.


    /** 
     * rows 
     *        total number of rows in the database. "Rows" are also called "Entries".
     * 
     * columns
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


    /*** 
        *** Constructors and their helper methods ***
    ***/

    /** 
     * creating a new Ivory Database object.
     */
    public IvoryDatabase() {
        this.no_of_rows = 0;
        columns = new ArrayList<>();

        // creating default Attribute "ID" that every Ivory Database must contain.
        columns.add(new Column("ID"));
    } // constructor IvoryDatabase()


    /**
     * creating a new Ivory Database file at {@code file_path} 
     * from another Ivory Database object. 
     * 
     * @param ivoryDBObject
     *        IvoryDatabase object from which attributes are being copied.
     */
    public IvoryDatabase(IvoryDatabase ivoryDBObject) {
        // copying parameter's object variables to this object's variables.
        this.columns = ivoryDBObject.columns;
        this.no_of_rows = ivoryDBObject.no_of_rows;
    } // constructor IvoryDatabase(IvoryDatabase Object)


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
        FILE_LOCATION = new File(file_path);
        if(!FILE_LOCATION.exists()){
            throw new IvoryDatabaseNotFoundException(file_path);
        }

        // deserializing the file.
        try {
            FileInputStream fileIn = new FileInputStream(FILE_LOCATION);
            ObjectInputStream streamIn = new ObjectInputStream(fileIn);

            // copying the class variables.
            IvoryDatabase deserializedDB = (IvoryDatabase) streamIn.readObject();
            this.no_of_rows = deserializedDB.no_of_rows;
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
     * @param ivory_file
     *        the file where an existing Ivory Database file (.ivry) is stored.
     * 
     * @throws IvoryDatabaseNotFoundException
     *         when input file is null, or input file does not exist.
     */
    public IvoryDatabase(File ivory_file) 
        throws IvoryDatabaseNotFoundException {
        // validating ivory_file.
        if(ivory_file == null){
            throw new IllegalArgumentException("Ivory Database File cannot be null.");
        }
        // assigning ivory_file to FILE_LOCATION.
        FILE_LOCATION = ivory_file;
        if(!FILE_LOCATION.exists()){
            throw new IvoryDatabaseNotFoundException(ivory_file.getPath());
        }

        // deserializing the file.
        try {
            FileInputStream fileIn = new FileInputStream(FILE_LOCATION);
            ObjectInputStream streamIn = new ObjectInputStream(fileIn);

            // deserializing and casting Ivory Database Object
            IvoryDatabase deserializedDB = (IvoryDatabase) streamIn.readObject();

            // copying the class variables.
            this.no_of_rows = deserializedDB.no_of_rows;
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
     * method to set the Ivory Database save location
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
    public void setFileLocation(String file_path) 
        throws DirectoryNotFoundException, IllegalFileTypeException{
        // validating file_path
        if(file_path == null){
            throw new IllegalArgumentException();
        }

        // create a File object of the file path.
        File file = new File(file_path);

        // checking if parent directory exists.
        if (!file.getParentFile().exists()){
            throw new DirectoryNotFoundException(file.getParent());
        }
        // checking that the file is the correct file type (.ivry).
        else if (!file.getName().endsWith(file_extension)){
            throw new IllegalFileTypeException();
        }
        // checking if a file with the same file path already exists.
        else if (file.exists()) {
            // if true, rectify the filename collision.
            FILE_LOCATION = rectifyFileNameCollision(file);
        }
        // if there are no issues, straight away set the FILE_LOCATION.
        else {
            // set FILE_LOCATION.
            FILE_LOCATION = file;
        }
    } // setFileLocation(String)

    /**
     * Method to set the file location to a default file location.
     * 
     * The default file is placed inside the default directory "Local Ivory Databases"
     * 
     * If the "Local Ivory Databases" directory does not exist, it is created.
     */
    private void setDefaultFileLocation(){
        // the default name for the save file.
        String defaultFilename = "Ivory Database.ivry";

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
        File defaultFile = new File(defaultDirectoryPath + file_separator + defaultFilename);
        
        // check if a file with that name already exists in the same directory.
        if (defaultFile.exists()){
            // if true, rectify the file name.
            FILE_LOCATION = rectifyFileNameCollision(defaultFile);
        }
        else{
            // assigning the default file to FILE_LOCATION.
            FILE_LOCATION = defaultFile;
        }

    } // setDefaultFileLocation()

    /**
     * Method to rectify a filename collision when creating a new Ivory Database file. 
     * 
     * If a file with the same filename passed to the constructor already exists 
     * in that directory, the filename needs to be rectified to avoid a file collision.
     * 
     * This is done because a file collision would lead to the existing file being 
     * overwritten, which can lead to unintended consequences and is bad practice.
     * 
     * This method generates an alternate filename by appending a number to the given
     * filename. A File object is created with the rectified file path and returned.
     * 
     * @param file_path
     *        the path to the location where the 
     * 
     * @return The File object with the rectified file name.
     */
    private File rectifyFileNameCollision(File input_file){
        // getting the filename without the file extension.
        String database_name = input_file.getName().substring(0, input_file.getName().indexOf(file_extension));
        // creating partial file path with the directory and filename without file extension.
        String partial_path = input_file.getParent() + file_separator + database_name;

        // rectifying the filename.
        int number = 1; // database number concatenated to the end of the filename.
        String full_path; // stores the complete path string.
        do{
            // creating the filename by appending partial_path, number and file_extension.
            full_path = partial_path + "(" + number + ")" + file_extension;
            number++; // incrementing by 1
        }while(new File(full_path).exists()); // if the file exists, run loop again.

        // return the file path with the rectified filename.
        return new File(full_path);
    } // rectifyFileNameCollision()


    /*** 
        *** Object Attribute and related methods ***
    ***/

    
    /**
     * @return The name of the Ivory Database.
     */
    public String getName(){
        return FILE_LOCATION.getName();
    } // getName()
    

    /**
     * @return The file path of the file where the Ivory Database is saved.
     */
    public String getPath(){
        // if FILE_LOCATION has not already been set, set the default file location.
        if (FILE_LOCATION == null){
            setDefaultFileLocation();
        }
        return FILE_LOCATION.getPath();
    } // getPath()


    /**
     * @return The names of all the Columns in the Ivory Database.
     */
    public String[] getColumnNames(){
        // getting the number of columns in this database.
        int size = columns.size();

        // creating output String[].
        String output[] = new String[size];
        
        // running a loop through columns ArrayList.
        for(int index = 0 ; index < size ; index++){
            output[index] = columns.get(index).getName();
        }
        return output;
    } // getColumnNames()


    /**
     * @return The path to the parent directory of Ivory Database file.
     */
    public String getParentPath(){
        return FILE_LOCATION.getParent();
    } // getParentPath()


    /**
     * Method to rename the Ivory Database.
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
     * the current {@code FILE_LOCATION}.
     * 
     * Check if the new File object already exists and points to a file. This is done
     * to prevent accidentally overwriting any existing files.
     * 
     * Finally, File.renameTo(File) is invoked to rename the file.
     * 
     * @param new_filename
     *        the new filename that the current .ivry file has to be renamed to.
     * 
     * @return {@code true}, if file rename operation was successful. Otherwise, returns {@code false}.
     */
    public boolean renameDatabase(String new_filename){
        // validating param, checking if new_filename is null or contains any file_separators.
        if(new_filename != null && new_filename.indexOf(file_separator) == -1){
            // creating a File object with the new filename.
            File new_file = new File(FILE_LOCATION.getParent() + file_separator + new_filename);

            // if a file with the new filename already exists, abort file renaming operation.
            if(!new_file.exists()){
                return FILE_LOCATION.renameTo(new_file); // rename
            }
        }
        return false;
    } // renameDatabase()


    /**
     * Method to add a Column to the Ivory Database.
     * 
     * @param column_name
     *        The name of the Column to be added to the Database.
     * 
     * @return True if the Column addition was successful, otherwise, false.
     */
    public boolean ADD_COLUMN(String column_name){
        try{
            // adding new Column object with the same number of rows as the current database.
            columns.add(new Column(column_name, no_of_rows));

            // increment no_of_columns.
            no_of_columns++;
        }
        catch(Exception e){
            // return false if column addition failed
            return false;
        }
        // return true for successful column addition.
        return true;
    } // ADD_COLUMN()


    /**
     * Method to delete a Column from the Ivory Database.
     * 
     * @param column_name
     *        The name of the Column to be deleted.
     * 
     * @return True if the Column deletion was successful, otherwise, false.
     */
    public boolean DELETE_COLUMN(String column_name){
        try{
            // getting the column number of column_name.
            int col_num = getColumnNumberOf(column_name);

            @SuppressWarnings("unused") // suppress warning that 'column' is unused.
            // removing the column from columns.
            Column column = columns.remove(col_num);
            // set the column to null to effectively delete it.
            column = null;
        }
        catch (Exception e){
            // if column does not exist, an exception is thrown. Deletion failed.
            return false;
        }
        // return true for successful deletion.
        return true;
    } // DELETE_COLUMN


    /**
     * method to save changes made to the Ivory Database object and store 
     * it in FILE_LOCATION.
     * 
     * @see java.io.Serializable
     */
    public void SAVE(){
        // if the FILE_LOCATION has not already been set, set the default.
        if (FILE_LOCATION == null){
            setDefaultFileLocation();
        }

        boolean save_success; // true when the Ivory Database object is saved to file successfully.
        try {
            // creating FileOutputStream and ObjectOutputStream objects.
            FileOutputStream fileOut = new FileOutputStream(FILE_LOCATION);
            ObjectOutputStream streamOut = new ObjectOutputStream(fileOut);
            
            // writing IvoryDatabase object to FILE_LOCATION.
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
            System.out.println("IvoryDB Message : Operation Success : " + FILE_LOCATION.getName() + " has been saved successfully.");
        else
        // printing operation failure message.
            System.out.println("IvoryDB Message : Operation Failed : Changes made to " + FILE_LOCATION.getName() + " were not saved.");
    
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
        // saving all the changes made to the Database to FILE_LOCATION.
        this.SAVE(); 

        // setting all the columns to null.
        for(Column column : columns) {
            column = null; 
        }
        System.gc(); // invoking java garbage collector
    } // close()


    /*** 
        *** Regular Operation Methods ***
    ***/


    /**
     * Method to add a new row to the Database.
     * 
     * @param newEntry
     *        Object array containing the Column values for the new entry.
     */
    public boolean ADD(Object[] newEntry){
        // creating temporary Column reference.
        Column temp;
        // storing the id of the new Entry in a variable.
        String new_id = (String) newEntry[0];

        // run a loop to check if a ID already exists in the ID column with the same value as the 'id' parameter.
        temp = columns.get(0); // referring to the ID column.
        for(int index = 0 ; index < no_of_columns ; index++){
            if(new_id.equals(temp.get(index))){
                // if the new_id is equal to an existing id, return false and do not add the new Entry.
                return false;
            }
        }



        /** getting the index where the new entry needs to be inserted 
            to maintain alphabetical order in ID column. */
        int insert_index = findInsertIndex((String) newEntry[0]);

        // if insert_index is 0 then the column is empty. if it is -1 then entry needs to be inserted as the last row.
        if(insert_index == 0 || insert_index == -1){
            // loop going through every Column to add the attributes of this entry.
            for(int col_num = 0 ; col_num < no_of_columns ; col_num++){
                temp = columns.get(col_num);
                temp.add(newEntry[col_num]);
            }
        }
        else{
            // loop going through every column to add the attributes of this entry.
            for(int col_num = 0 ; col_num < no_of_columns ; col_num++){
                // inserting the new Entry's attribute to the corresponding Attribute column.
                temp = columns.get(col_num);
                temp.insert(insert_index, newEntry[col_num]);
            }
        }
        // incrementing rows to represent the new number of rows.
        no_of_rows++;
        return true;
    } // ADD()


    /**
     * Method to delete a row from the database.
     * 
     * @param id
     *        ID of the entry that needs to deleted.
     * 
     * @return True if the entry was successfully deleted. Otherwise false.
     */
    public boolean DELETE(String id){
        try{    
        // getting the row number of id.
            int row_num = getRowNumberOf(id);

            for(Column col : columns){
                // deleting values of the entry from each row
                col.delete(row_num);
            }
        }
        // if id does not exist, an exception will be thrown.
        catch(Exception e){
            return false;
        }
        return true;
    } // DELETE()


    /**
     * Method to get a value from the database.
     * 
     * @param id
     *        ID of the row entry whose value is requested.
     * 
     * @param column_name
     *        The name of the column value to returned
     * 
     * @return The object of {@code id} in {@code column_name}.
     */
    public Object GET(String id, String column_name){
        try{
            int col_num = getColumnNumberOf(column_name);
            int row_num = getRowNumberOf(id);

            return columns.get(col_num).get(row_num);
        }
        // if either id or column_name are invalid, an exception is thrown.
        catch (Exception e){
            return null;
        }
    } // GET()


    public Object[] GET_COLUMN(String column_name){
        // getting the column number of the column_name.
        int col_num = getColumnNumberOf(column_name);

        // converting Column to an Object Array and returning it.
        return columns.get(col_num).getArray();
    } // GET_COLUMN()


    /**
     * @return The Ivory Database as a comma separated table.
     */
    public String GET_AS_TABLE(){
        String output = "";
        Column currentCol;
        for(int row = 0 ; row < no_of_rows ; row++){
            for(int col = 0 ; col < no_of_columns ; col++){
                currentCol = columns.get(col);
                output = output + String.valueOf(currentCol.get(row)) + ", ";
            }
            output = output + "\b\n";
        }
        return output;
    } // GET_CONTENTS()



    /*** 
        *** Helper Methods ***
    ***/


    /**
     * @param id
     *        the id of the entry.
     * 
     * @return 0 if ID column is empty.
     *         -1 if entry needs to be added at the end of Column.
     *         Otherwise, the respective index value where the entry fits alphabetically.
     */
    private int findInsertIndex(String id){
        // making id uppercase.
        id = id.toUpperCase();
        // getting the ID attribute column.
        Column id_column = columns.get(0);

        // if ID column is empty, then return 0.
        if (no_of_rows == 0){
            return 0;
        }
        else{
            // running loop through ID column to find alphabetically correct index to insert the entry.
            for(int index = 0 ; index < no_of_rows ; index++){
                if(id.compareTo((String)id_column.get(index)) <= 0){
                    return index;
                }
            }
        }
        
        // if entry needs to be added to the end of the column.
        return -1;
    } // findInsertIndex()


    /**
     * Method to get the column number of a column.
     * 
     * @param column_name
     *        The name of the column.
     * 
     * @return The index number of {@code column_name} in {@code columns}. 
     */
    private int getColumnNumberOf(String column_name){
        for(int index = 0 ; index < no_of_columns ; index++){
            if(column_name.equals(columns.get(index).getName())){
                return index;
            }
        }
        return -1; // return -1 if column_name does not exist.
    } // getColumnNumberOf()


    /**
     * Method to get the column number of a column.
     * 
     * @param id_name
     *        The id of the row.
     * 
     * @return The row number of {@code id} in the Database. 
     */
    private int getRowNumberOf(String id){
        // making a reference to the ID column.
        Column id_columns = columns.get(0);
        for(int index = 0 ; index < no_of_rows ; index++){
            if(id.equals((String)id_columns.get(index))){
                return index;
            }
        }
        return -1; // return -1 if id does not exist.
    } // getRowNumberOf()

} // class