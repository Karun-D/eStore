package eStoreSearch;

/**
 * This class is a template to store all the relevant information of a book
 */
public class Book extends Product {
   
    ////////////////////////
    // Instance Variables //
    ////////////////////////
   
    /**
     * authors - Used to store the authors of the book
     */
    private String authors;
    /**
     * authors - Used to store the publisher of the book
     */
    private String publisher; 

    //////////////////
    // Constructors //
    //////////////////

    // No name constructor does not take on variable values, so catch block is implemented to prevent
    // the need for a try catch block in the main code
    public Book () {
        try {
            setID(0);
            setDescription(" ");
            setPrice(0);
            setYear(1000);
        } catch (Exception e) {
            setAuthors("");
            setPublisher("");
        }
    }

    public Book (int productID, String description, double price, int year, String authors, String publisher) throws Exception{
        super(productID, description, price, year);
        setAuthors(authors);
        setPublisher(publisher);
    }

    public Book (Product newProduct, String authors, String publisher) throws Exception {
        super(newProduct.getID(), newProduct.getDescription(), newProduct.getPrice(), newProduct.getYear());
        setAuthors(authors);
        setPublisher(publisher);
    }

    public Book (Book original) throws Exception {
        this(original.getID(), original.getDescription(), original.getPrice(), original.getYear(), original.getAuthors(), original.getPublisher());

    }

    //////////////////////
    // Accessor Methods //
    //////////////////////

    /**
     * Description: Accessor method for retrieving the book's authors
     */
    public String getAuthors() {
        return authors;
    }

    /**
     * Description: Accessor method for retrieving the book's publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /////////////////////
    // Mutator Methods //
    /////////////////////

    /**
     * Description: Mutator method for initializing the book's authors
     * @param authors - authors of the book
     * @return 
     */
    public void setAuthors(String authors) {
        this.authors = authors;
    }

    /**
     * Description: Mutator method for initializing the book's publisher
     * @param authors - publisher of the book
     * @return 
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    /**
     * Description: Equals method to check if two Book objects are equal in attributes
     * @param other - second book object, used for comparison
     * @return
     */
    public boolean equals(Object other) {
        if (other == null){
            return false;
        } else if (getClass() != other.getClass()) {
            return false;
        } else {
            Book otherBook = (Book)other;
            return (getID() == otherBook.getID() && getDescription().equals(otherBook.getDescription()) && getPrice() == otherBook.getPrice() 
            && getYear() == otherBook.getYear() && getAuthors().equals(otherBook.getAuthors()) && getPublisher().equals(otherBook.getPublisher()));
        }
    }
    
    @Override
    /**
     * Description: Method to output the details of the Book object
     * @return
     */
    public String toString() {
        return ("Product #" + String.format("%06d", getID()) + "\n"
        + "Description: " + getDescription() + "\n"
        + "Price: $" + getPrice() + "\n"
        + "Year: " + getYear() + "\n"
        + "Authors: " + getAuthors() + "\n"
        + "Publisher: " + getPublisher() + "\n");
    }
}