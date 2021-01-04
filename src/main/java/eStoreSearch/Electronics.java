package eStoreSearch;

/**
 * This class is a template to store all the relevant information of a electronic product
 */
public class Electronics extends Product {
    
    ////////////////////////
    // Instance Variables //
    ////////////////////////

    /**
     * Used to store the maker of the electronic product
     */
    private String maker;

    //////////////////
    // Constructors //
    //////////////////
    
    // No name constructor does not take on variable values, so catch block is implemented to prevent
    // the need for a try catch block in the main code
    public Electronics (){
        try {
            setID(0);
            setDescription(" ");
            setPrice(0);
            setYear(1000);
        } catch (Exception e) {
            setMaker("");
        }
    }

    public Electronics (int productID, String description, double price, int year, String maker) throws Exception {
        super(productID, description, price, year);
        setMaker(maker);
    }

    public Electronics (Product newProduct, String maker) throws Exception {
        super(newProduct.getID(), newProduct.getDescription(), newProduct.getPrice(), newProduct.getYear());
        setMaker(maker);
    }

    public Electronics (Electronics original, String maker) throws Exception {
        this(original.getID(), original.getDescription(), original.getPrice(), original.getYear(), original.getMaker());
    }

    //////////////////////
    // Accessor Methods //
    //////////////////////

    /**
     * Accessor method for retrieving the electronic's maker
     * @return
     */
    public String getMaker(){
        return maker;
    }

    /////////////////////
    // Mutator Methods //
    /////////////////////

    /**
     * Mutator method for initializing the electronic's maker
     * @return
     */
    public void setMaker(String maker) {
        this.maker = maker;
    }

    @Override
    /**
     * Description: Equals method to check if two Electronics objects are equal in attributes
     * @param other - second electronics object, used for comparison
     * @return
     */
    public boolean equals(Object other) {
        if (other == null){
            return false;
        } else if (getClass() != other.getClass()) {
            return false;
        } else {
            Electronics otherElectronics = (Electronics)other;

            return (getID() == otherElectronics.getID() && getDescription().equals(otherElectronics.getDescription()) && getPrice() == otherElectronics.getPrice() 
            && getYear() == otherElectronics.getYear() && getMaker().equals(otherElectronics.getMaker()));
        }
    }

    @Override
    /**
     * Description: Method to output the details of the Electronics object
     * @return
     */
    public String toString() {
        return ("Product #" + String.format("%06d", getID()) + "\n"
        + "Description: " + getDescription() + "\n"
        + "Price: $" + getPrice() + "\n"
        + "Year: " + getYear() + "\n"
        + "Maker: " + getMaker() + "\n");
    }
}
