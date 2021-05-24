class Sorter {

    private String[] attributes; // contains the column header names
    private String[][] table; // table that contains the organized data
    private int rows; // stores the number of rows in the table, this includes the column header row
    private int attributes_len; // stores the number of columns, equal to the number of attributes in the table

    public void quickSort(String[][] table, String attribute_name, boolean ascending) {

        // validating table
        if (table == null || table.length == 0){
            return;
        }
        
        this.table = table;
        this.attributes = table[0]; // 0th row is column headers, these are stored in attributes
        rows = table.length;
        attributes_len = attributes.length;
        attribute_name = attribute_name.toUpperCase();

        //validating attribute_name
        validateAttribute(attribute_name);

        // ascending or descending
        if(ascending)
            quickSortAscending(0, length - 1); // table sorted in ascending order
        else
            quickSortDescending(0, length - 1); // table sorted in descending order
    } // sort()

    /*
     * This method implements in-place quicksort algorithm recursively.
     */
    private void quickSortAscending(int low, int high) {
        int i = low;
        int j = high;

        // pivot is middle index
        int pivot = input[low + (high - low) / 2];

        // Divide into two arrays
        while (i <= j) {
            /**
             * As shown in above image, In each iteration, we will identify a
             * number from left side which is greater then the pivot value, and
             * a number from right side which is less then the pivot value. Once
             * search is complete, we can swap both numbers.
             */
            while (input[i] < pivot) {
                i++;
            }
            while (input[j] > pivot) {
                j--;
            }
            if (i <= j) {
                swap(i, j);
                // move index to next position on both sides
                i++;
                j--;
            }
        }

        // calls quickSort() method recursively
        if (low < j) {
            quickSortAscending(low, j);
        }

        if (i < high) {
            quickSortAscending(i, high);
        }
    }

    private void quickSortDescending(int low, int high) {
        int i = low;
        int j = high;

        // pivot is middle index
        int pivot = input[low + (high - low) / 2];

        // Divide into two arrays
        while (i <= j) {
            /**
             * As shown in above image, In each iteration, we will identify a
             * number from left side which is greater then the pivot value, and
             * a number from right side which is less then the pivot value. Once
             * search is complete, we can swap both numbers.
             */
            while (input[i] < pivot) {
                i++;
            }
            while (input[j] > pivot) {
                j--;
            }
            if (i <= j) {
                swap(i, j);
                // move index to next position on both sides
                i++;
                j--;
            }
        }

        // calls quickSort() method recursively
        if (low < j) {
            quickSortDescending(low, j);
        }

        if (i < high) {
            quickSortDescending(i, high);
        }
    }

    // method to swap the elements in index1 and index2
    private void swap(int index1, int index2) {
        int temp = input[index1];
        input[index1] = input[index2];
        input[index2] = temp;
    }

    // method to check if attribute_name exists in this table
    private boolean validateAttribute(String attribute_name){
        for(int index = 0 ; index < attributes_len ; index++){
            if(attribute_name.equals(attributes[index]))
                return true;
        }
        return false;
    } // validateAttribute()

} // class Sorter