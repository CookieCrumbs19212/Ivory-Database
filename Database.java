import java.io.FileNotFoundException;

public class Database{
    String[] attributes; // the column headers are called attributes eg. name, ID no., address, etc.
    String[][] table; // the 2 Dimensional array where the data is stored in a structured manner
    int rows, columns; // total number of rows, total number of columns, in the table
    String file_location; // location of the .csv file where the database table is stored

    public Database(int rows, int columns, String file_location)throws FileNotFoundException{
        this.rows = rows;
        this.columns = columns;
        table = new String[rows][columns];
        this.file_location = file_location;
    } // constructor Database(rows, columns)

    // initialize a Database object 
    public Database(String file_location)throws FileNotFoundException {
        this.file_location = file_location;

    } // constructor Database(file_location)

    public void sortDataBy(String attribute_name, boolean ascending){
        Sorter.quickSort(table, attribute_name, ascending);
    }

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
} // class