class Attribute {
    String dataType;
    String name;
    Object[] cells;

    public Attribute(String attribute_name, int size, Class<?> data_type){
        this.name = attribute_name;
        if(data_type == String.class){
            cells = new String[size];
        }
        else if(data_type == Integer.class){
            cells = new Integer[size];
        }
        else{
            throw new UnsupportedOperationException();
        }
    } // constructor
} // Attribute class