import java.io.FileNotFoundException;

public class Database{
    String[] headers;
    String[][] table;
    int rows, columns;
    String file_location;

    public Database(int rows, int columns, String file_location)throws FileNotFoundException{
        this.rows = rows;
        this.columns = columns;
        table = new String[rows][columns];
        this.file_location = file_location;
    } // constructor Database(rows, columns)

    // initialize a Database object 
    public Database(String file_location)throws FileNotFoundException {

    } // constructor Database(file_location)

    public void sortDataBy(String attribute_name, boolean ascending){

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