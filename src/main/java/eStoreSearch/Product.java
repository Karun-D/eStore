package eStoreSearch;

/**
 * This class is a template to store all product relevant information
 */
public abstract class Product{
    
    ////////////////////////
    // Instance Variables //
    ////////////////////////

    /**
     * productID - Used to store Product ID #
     */
    private int productID;                                         
    /**
     * description - Used to store description of product
     */
    private String description;                                     
    /**
     * price - Used to store the price of the product
     */
    private double price;                                           
    /**
     * year - Used to store the year the product was released
     */
    private int year;         

    //////////////////
    // Constructors //
    //////////////////

    public Product (){
        productID = 0;
        description = " ";
        price = 0;
        this.year = 1000;
    }

    public Product (int productID, String description, double price, int year) throws Exception { 
        setID(productID);
        setDescription(description);
        setPrice(price);
        setYear(year);
    }

    public Product (Product original) throws Exception { 
        this(original.getID(), original.getDescription(), original.getPrice(),original.getYear());
    }
    
    /////////////////////
    // Mutator Methods //
    /////////////////////

    /**
     * Description: Mutator method for initializing the product's ID #
     * @param productID - Product ID #
     * @return True - if the ID is a 6 digit integer, False - if the ID is not a 6 digit integer
     */
    public void setID(int productID) throws Exception {
        if ((productID > 999999) || (productID < 0)){
            throw new Exception("Error: Product ID must be a valid 6 digit postive integer.");
        } else {
            this.productID = productID;
        }
    }

    /**
     * Description: Mutator method for initializing the product's description
     * @param description - description of product
     * @return 
     */
    public void setDescription(String description) throws Exception {
        if (description.length() == 0) {
            throw new Exception("Error: Description is a mandatory field.");
        } else {
            this.description = description;
        }
    }

    /**
     * Description: Mutator method for initializing the product's price
     * @param price - price of product
     * @return True - if the price is a postive floating point #, False - if the price is not a positive floating point #
     */
    public void setPrice(double price) throws Exception {
        if (price < 0) {
            throw new Exception("Error: Price must be a postive real number.");
        } else {
            this.price = price;
        }
    }

    /**
     * Description: Mutator method for initializing the product's year
     * @param year - year the product was released
     * @return True - if the year is a 4 digit integer, False - if the year is not a 4 digit integer
     */
    public void setYear(int year) throws Exception {
        if ((year < 1000) || (year > 9999)) {
            throw new Exception("Error: Year must be a postive 4 digit integer.");
        } else {
            this.year = year;
        }
    }

    //////////////////////
    // Accessor Methods //
    //////////////////////

    /**
     * Description: Accessor method for retrieving the product's product ID #
     */
    public int getID() {
        return productID;
    }

    /**
     * Description: Accessor method for retrieving the product's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Description: Accessor method for retrieving the product's price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Description: Accessor method for retrieving the product's year
     */
    public int getYear() {
        return year;
    }

    /**
     * Description: Equals method to check if two product objects are equal in attributes
     * @param other - second product object, used for comparison
     * @return
     */
    public abstract boolean equals(Object other);

    /**
     * Description: Method to output the details of the product object
     * @return
     */
    public abstract String toString();

    /*
    public boolean equals(Object other) {
        if (other == null){
           return false;
        } else if (getClass() != other.getClass()) {
            return false;
        } else {
            Product otherProduct = (Product)other;
            return (getID() == otherProduct.getID() && getDescription().equals(otherProduct.getDescription()) && getPrice() == otherProduct.getPrice() 
            && getYear() == otherProduct.getYear());
        }
    }
    */

    /*
    public String toString() {
        return ("Product #" + String.format("%06d", getID()) + "\n"
        + "Description: " + getDescription() + "\n"
        + "Price: $" + getPrice() + "\n"
        + "Year: " + getYear() + "\n");
    }
    */
}