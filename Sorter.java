class Sorter {

    private String[] attributes; // contains the column header names
    private int attributes_len; // stores the number of columns, equal to the number of attributes in the table
    private String[][] table; // table that contains the organized data
    private int rows; // stores the number of rows in the table, this includes the column header row
    private int attribute_index; // the attribute according to which the table is going to be organized
    
    public void sort(String[][] table, String attribute_name, boolean ascending) {

        // validating table
        if (table == null || table.length == 0){
            return;
        }
        
        this.table = table;
        this.rows = table.length;
        this.attributes = table[0]; // 0th row is column headers, attribute names are the column headers
        this.attributes_len = attributes.length;

        // validating and getting attribute_index
        this.attribute_index = getIndexOfAttribute(attribute_name.toUpperCase());
        if(attribute_index == -1){ // getAttributeIndex() returns -1 if the attribute_name does not exist for this table
            return;
        }

        /** 
         * following if statment decides sorting in ascending or descending, the starting index is passed as [1] 
         * because the index [0] contains the column headers(a.k.a. the attribute names).
        **/
        if(ascending)
            quickSortAscending(1, rows-1); // table sorted in ascending order
        else
            quickSortDescending(1, rows-1); // table sorted in descending order
    } // sort()



    /*
     * START OF STRING DATA TYPE SORTING SECTION
     *   
     * This method implements in-place quicksort algorithm recursively.
     */
    private void quickSortAscending(int low, int high) {
        int index1 = low;
        int index2 = high;

        // pivot is middle index
        String pivot = table[low + (high-low)/2][attribute_index];

        // Divide into two arrays
        while (index1 <= index2) {
            /**
             * As shown in above image, In each iteration, we will identify a
             * number from left side which is greater then the pivot value, and
             * a number from right side which is less then the pivot value. Once
             * search is complete, we can swap both numbers.
             */
            while (table[index1][attribute_index].compareTo(pivot) < 0) {
                index1++;
            }
            while (table[index2][attribute_index].compareTo(pivot) > 0) {
                index2--;
            }
            if (index1 <= index2) {
                swap(index1, index2);
                // move index to next position on both sides
                index1++;
                index2--;
            }
        } // while loop

        // calls quickSortAscending() method recursively
        if (low < index2) {
            quickSortAscending(low, index2);
        }

        if (index1 < high) {
            quickSortAscending(index1, high);
        }
    } // quickSortAscending()

    /*
     * This method implements in-place quicksort algorithm recursively.
     */
    private void quickSortDescending(int low, int high) {
        int index1 = low;
        int index2 = high;

        // pivot is middle index
        String pivot = table[low + (high-low)/2][attribute_index];

        // Divide into two arrays
        while (index1 <= index2) {
            /**
             * In each iteration, we will identify a number from left side which is 
             * less than the pivot value, and a number from right side which is greater 
             * than the pivot value. Once search is complete, we can swap both numbers.
             */
            while (table[index1][attribute_index].compareTo(pivot) > 0) {
                index1++;
            }
            while (table[index2][attribute_index].compareTo(pivot) < 0) {
                index2--;
            }
            if (index1 <= index2) {
                swap(index1, index2);
                // move index to next position on both sides
                index1++;
                index2--;
            }
        } // while loop

        // calls quickSortDescending() method recursively
        if (low < index2)
            quickSortDescending(low, index2);

        if (index1 < high)
            quickSortDescending(index1, high);
    } // quickSortDescending()
    /**
     * END OF STRING DATA TYPE SORTING SECTION
     */


    /**
     * START OF INTEGER DATA TYPE SORTING SECTION
     */
    private void quickSortAscending(int low, int high, int[] attribute_array) {
        int index1 = low;
        int index2 = high;

        // pivot is middle index
        String pivot = table[low + (high-low)/2][attribute_index];

        // Divide into two arrays
        while (index1 <= index2) {
            /**
             * As shown in above image, In each iteration, we will identify a
             * number from left side which is greater then the pivot value, and
             * a number from right side which is less then the pivot value. Once
             * search is complete, we can swap both numbers.
             */
            while (table[index1][attribute_index].compareTo(pivot) < 0) {
                index1++;
            }
            while (table[index2][attribute_index].compareTo(pivot) > 0) {
                index2--;
            }
            if (index1 <= index2) {
                swap(index1, index2);
                // move index to next position on both sides
                index1++;
                index2--;
            }
        } // while loop

        // calls quickSortAscending() method recursively
        if (low < index2) {
            quickSortAscending(low, index2);
        }

        if (index1 < high) {
            quickSortAscending(index1, high);
        }
    } // quickSortAscending()

    /*
     * This method implements in-place quicksort algorithm recursively.
     */
    private void quickSortDescending(int low, int high) {
        int index1 = low;
        int index2 = high;

        // pivot is middle index
        String pivot = table[low + (high-low)/2][attribute_index];

        // Divide into two arrays
        while (index1 <= index2) {
            /**
             * In each iteration, we will identify a number from left side which is 
             * less than the pivot value, and a number from right side which is greater 
             * than the pivot value. Once search is complete, we can swap both numbers.
             */
            while (table[index1][attribute_index].compareTo(pivot) > 0) {
                index1++;
            }
            while (table[index2][attribute_index].compareTo(pivot) < 0) {
                index2--;
            }
            if (index1 <= index2) {
                swap(index1, index2);
                // move index to next position on both sides
                index1++;
                index2--;
            }
        } // while loop

        // calls quickSortDescending() method recursively
        if (low < index2)
            quickSortDescending(low, index2);

        if (index1 < high)
            quickSortDescending(index1, high);
    } // quickSortDescending()
    /**
     * END OF INTEGER DATA TYPE SORTING SECTION
     */

    

    // method to swap the elements in index1 and index2
    private void swap(int index1, int index2) {
        String[] temp = table[index1];
        table[index1] = table[index2];
        table[index2] = temp;
    } // swap()

    // method to check if attribute_name exists in this table and return its index value in attributes[]
    private int getIndexOfAttribute(String attribute_name){
        for(int index = 0 ; index < attributes_len ; index++){
            if(attribute_name.equalsIgnoreCase(attributes[index]))
                return index;
        }
        return -1; // attribute_name does not exist in this table
    } // getIndexOfAttribute()

} // class Sorter