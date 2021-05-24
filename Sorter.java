class Sorter {

    private int input[][];
    private int length;

    public void quickSort(String[] input, String attribute_name, boolean ascending) {

        // validating input
        if (input == null || input.length == 0) {
            return;
        }
        this.input = input;
        length = input.length;

        // ascending or descending
        if(ascending)
            quickSortAscending(0, length - 1);
        else
            quickSortDescending(0, length - 1);
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
} // class Sorter