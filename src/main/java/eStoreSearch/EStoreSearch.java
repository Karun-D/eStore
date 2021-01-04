package eStoreSearch;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is used to manage multiple products through storing, searching, and filtering them
 */
public class EStoreSearch {

    ////////////////////////
    // Instance Variables //
    ////////////////////////

    /**
     * Used to store multiple product Objects
     */
    private ArrayList <Product> product_list;

    /**
     * HashMap used to efficiently search store for Product Objects
     */
    private HashMap<String, ArrayList <Integer>> index;

    //////////////////
    // Constructors //
    //////////////////

    public EStoreSearch() {
        product_list = new ArrayList<Product>();
        index = new HashMap<String, ArrayList <Integer>> ();
    }

    public EStoreSearch(ArrayList <Product> product_list, HashMap<String, ArrayList <Integer>> index ) {
        this.product_list = product_list;
        this.index = index;
    }

    //////////////////////
    // Accessor Methods //
    //////////////////////

    /**
     * Description: Accessor method for retrieving the Product ArrayList
     */
    public ArrayList <Product> getProductList() {
        return product_list;
    }

    /**
     * Description: Accessor method for retrieving the Product HashMap
     */
    public HashMap<String, ArrayList <Integer>> getHashMap() {
        return index;
    }

    /////////////////////
    // Mutator Methods //
    /////////////////////

    /**
     * Description: Accessor method for retrieving the Book ArrayList
     */
    public boolean setProductList(ArrayList <Product> product_list) {
        if (product_list == null){
            return false;
        } else {
            this.product_list = product_list;
            return true;
        }
    }

    /**
     * Description: Accessor method for retrieving the Book ArrayList
     */
    public boolean setHashMap(HashMap<String, ArrayList <Integer>> index) {
        if (index == null){
            return false;
        } else {
            this.index = index;
            return true;
        }
    }

    /////////////////////
    // Instance Methods //
    /////////////////////

    /**
     * Description - Used to add products to the "store"
     * @param keyboard - Scanner object (for user input)
     * @param book_list - ArrayList used to store Book products
     * @param electronic_list - ArrayList used to store Electronic products
     */
    public void addProduct(Product newProduct) {
        product_list.add(newProduct);
    }

    /**
     * Description - Used to search for products in the "store"
     * @param keyboard - Scanner object (for user input)
     */
    public ArrayList <Product> searchProducts(String keyWords, int productID, String period) {
        // Boolean variables, used to determine which filter block to perform based on user input
        boolean searchID = false;
        boolean searchKeyWords = false;
        boolean searchPeriod = false;

        // ArrayList, used to store a new list of products after filters have been applied
        ArrayList<Product> relevantProducts = new ArrayList<Product> ();

        // ArrayList, used to store the indices of products containing all the keywords entered by the user
        ArrayList<Integer> keywordProducts = new ArrayList<Integer> ();

        // Assigns boolean values to searchFilters depending on user input
        if (keyWords != null) {
            searchKeyWords = true;
        }

        if (productID > -1) {
            searchID = true;
        }

        if (period != null) {
            searchPeriod = true;
        }

        // If the searchKeyWords filter was activated
        if (searchKeyWords) {
            keywordProducts = searchhashMap(keyWords);                              // Obtain indices of products that match the keywords

            if (keywordProducts != null) {                                          // If there are products containing list of keywords
                for (int i = 0; i < keywordProducts.size(); i++){
                    relevantProducts.add(product_list.get(keywordProducts.get(i)));
                }
            }
        }

        // If the productID filter was activated
        if (searchID){
            // If the productID AND the searchKeyWords filter was activated
            if (searchKeyWords) {
                // Find the intersection of the filters by removing the Products that don't satisfy productID AND searchKeyWords
                for (int k = 0; k < relevantProducts.size(); k++){
                    if (relevantProducts.get(k).getID() != productID) {
                        relevantProducts.remove(k);
                    }
                }
                
                for (int j = 0; j < relevantProducts.size(); j++){
                    if (relevantProducts.get(j).getID() != productID) {
                        relevantProducts.remove(j);
                    } 
                }

            // If ONLY the productID was activated
            } else {
                for (int i = 0; i < product_list.size(); i++){
                    if (product_list.get(i).getID() == productID) {
                        relevantProducts.add(product_list.get(i));
                    }
                }
            }
        }

        // If the searchPeriod filter was activated
        if (searchPeriod) {
            // If the searchPeriod filter AND one/both of the previous filters were activated
            if (searchID || searchKeyWords){
                // Find the intersection of the filters by removing the Products that don't satisfy all the filters
                for (int i = 0; i < relevantProducts.size(); i++){
                    if (matchPeriod(period, relevantProducts.get(i).getYear()) == false) {
                        relevantProducts.remove(i);
                    }
                }
            // If only the searchPeriod filter was activated
            } else {
                // Add the Products that fit the filter
                for (int i = 0; i < product_list.size(); i++){
                    if (matchPeriod(period, product_list.get(i).getYear()) == true) {
                        relevantProducts.add(product_list.get(i));
                    }
                }
            }
        }

       // System.out.println("");

        // If none of the filters were activated, output every Product and exit function
        if (searchID == false && searchKeyWords == false && searchPeriod == false) {

            /*for (int i = 0; i < product_list.size(); i++){
                System.out.println("");
                System.out.println(product_list.get(i));
            }*/

            return product_list;
        } 

        // Output the filtered Products
        /*for (int i = 0; i < relevantProducts.size(); i++){
            System.out.println(relevantProducts.get(i));
        }*/

        return relevantProducts;
    }

    /**
     * Description - Helper method, used to determine if a set of keywords are present in a product's description
     * @param keywords - A set of keywords 
     * @param description - The product's description
     * @return True - if all the key words are found in the description, False - if otherwise
     */
    public boolean matchKeyWords (String keywords, String description) {
        String keyWordsList[] = keywords.split("[ ]+");
        String tokens[] = description.split("[ ]+");
        Boolean temp = false;

        for (int i = 0; i < keyWordsList.length; i++) {

            for (int j = 0; j < tokens.length; j++) {
                if (keyWordsList[i].equalsIgnoreCase(tokens[j])) {
                    temp = true;
                }
            }

            if (temp == false){
                return false;
            }

            temp = false;
        }

        return true;
    } 
    
    /**
     * Description - Helper method, used to determine if a product's year falls in certain time period range
     * @param period - The time period range 
     * @param year - The year the product was released
     * @return True - if all falls within the time period, False - if otherwise
     */
    public boolean matchPeriod (String period, int year) {
        // If the user enters a single year
        if (period.length() == 4) {
            // Checks if the year is in range
            if (Integer.parseInt(period) == year) {
                return true;
            } else {
                return false;
            }
        }

        // If the user enters a "-YEAR"
        if ((period.length() == 5) && (period.charAt(0) == '-')) {
            // Checks if the year is in range
            if (year <= Integer.parseInt(period.substring(1, 5))) {
                return true;
            } else {
                return false;
            }
        }

        // If the user enters a "YEAR-"
        if ((period.length() == 5) && (period.charAt(period.length() - 1) == '-')) {
            // Checks if the year is in range
            if (year >= Integer.parseInt(period.substring(0, 4))) {
                return true;
            } else {
                return false;
            }
        }

        // If the user enters a "YEAR1-YEAR2"
        if ((period.length() == 9) && (period.charAt(4) == '-')) {
            int year1 = Integer.parseInt(period.substring(0, 4));
            int year2 = Integer.parseInt(period.substring(5, 9));

            // Checks if the year is in range
            if ((year >= year1) && (year <= year2)) {
                return true;
            } else {
                return false;
            }
        }

        return false;                                               // Returns false if the format does not follow the 4 above
    } 

    /**
     * Description - Helper method, used to determine if an ID has been previously used before
     * @param productID - The product ID in question
     * @param book_list - Used to search all book products for the product ID in question
     * @param electronic_list - Used to search all electronic products for the product ID in question
     * @return True - if the product ID has not been used already, False - if otherwise
     */
    public boolean validID (int productID) {
        // Iterates over product list, checking if the product ID has been previously used
        for (int i = 0; i < product_list.size(); i++){
            // If the ID has been used, method returns false
            if (product_list.get(i).getID() == productID) {
                return false;
            }
        }

        return true;                    // Returns true if the ID has not been found in a previous product
    }

    /**
     * Description - Helper method, used to determine if format of time period range is valid for filtering products
     * @param period - The time period in question
     * @return True - if the time period range is valid, False - if otherwise
     */
    public boolean validPeriod (String period) {
        int year1;
        int year2;
        
        // If the user enters a single year
        if (period.length() == 4) {
            // Error handling
            try {
                year1 = Integer.parseInt(period);

                // Checks if the first year is a valid 4 digit year
                if ((year1 < 1000) || (year1 > 9999)) {
                    return false;
                } else {
                    return true;
                }
            // False, if the user enters in a value that is not a 4 digit integer
            } catch (Exception e) {
                return false;
            }
        }

        // If the user enters "-YEAR"
        if ((period.length() == 5) && (period.charAt(0) == '-')) {
            // Error handling
            try {
                year1 = Integer.parseInt(period.substring(1, 5));

                // Checks if the first year is a valid 4 digit year
                if ((year1 < 1000) || (year1 > 9999)) {
                    return false;
                } else {
                    return true;
                }
            // False, if the user enters in a value that is not a 4 digit integer
            } catch (Exception e) {
                return false;
            }

        }

        // If the user enters "YEAR-"
        if ((period.length() == 5) && (period.charAt(period.length() - 1) == '-')) {
            // Error handling
            try {
                year1 = Integer.parseInt(period.substring(0, 4));

                // Checks if the first year is a valid 4 digit year
                if ((year1 < 1000) || (year1 > 9999)) {
                    return false;
                } else {
                    return true;
                }
            // False, if the user enters in a value that is not a 4 digit integer
            } catch (Exception e) {
                return false;
            }
        }

        // If the user enters "YEAR1-YEAR2"
        if ((period.length() == 9) && (period.charAt(4) == '-')) {
            // Error handling
            try {
                year1 = Integer.parseInt(period.substring(0, 4));
                year2 = Integer.parseInt(period.substring(5, 9));

                // Checks if the first year is a valid 4 digit year
                if ((year1 < 1000) || (year1 > 9999)) {
                    return false;
                }

                // Checks if the second year is a valid 4 digit year
                if ((year2 < 1000) || (year2 > 9999)) {
                    return false;
                } 

                // Checks if the second year occurs after first year
                if (year2 < year1){
                    return false;
                } else {
                    return true;
                }

            // False, if the user enters in a value that is not a 4 digit integer
            } catch (Exception e) {
                return false;
            }
        }

        return false;                                               // Returns false if the format does not follow the 4 above
    }

    /**
     * Description - Used to check if bookProduct is a valid object, and adds the product to the list
     * @param bookProduct - Object used to add to arraylist
     * @return True - if product is added successfully, False if otherwise
     */
    public boolean addBookToList (int productID, String description, double price, int year, String authors, String publisher) {
        Book newBook;
        try {
            newBook = new Book();

            newBook.setID(productID);
            newBook.setDescription(description);
            newBook.setPrice(price);
            newBook.setYear(year);
            newBook.setAuthors(authors);
            newBook.setPublisher(publisher);
        } catch (Exception e) {
            return false;
        }

        product_list.add(newBook);
        return true;
    }

    /**
     * Description - Used to check if electronicsProduct is a valid object, and adds the product to the list
     * @param electronicsProduct - Object used to add to arraylist
     * @return True - if product is added successfully, False if otherwise
     */
    public boolean addElectronicsToList (int productID, String description, double price, int year, String maker) {
        Electronics newElectronics;

        try {
            newElectronics = new Electronics();

            newElectronics.setID(productID);
            newElectronics.setDescription(description);
            newElectronics.setPrice(price);
            newElectronics.setYear(year);
            newElectronics.setMaker(maker);
        } catch (Exception e) {
            return false;
        }

        product_list.add(newElectronics);
        return true;
    }

    /**
     * Description - Used to check if productiD has been found in array list
     * @param productID - ID to be checked
     * @return True - if product is foound successfully, False if otherwise
     */
    public boolean searchProductID (int productID){
        for (int i = 0; i < product_list.size(); i++){
            if (product_list.get(i).getID() == productID) {
                return true;
            }
        }
        return false;
    }

    /**
     * Description - Used to map each word in a product description to a set of indices of products that contain the word
     */
    public void updateHashMap () {
        String description;
        String tokens[];
        ArrayList <Integer> list;

        index = new HashMap<String, ArrayList <Integer>> ();                            // Reset HashMap
        
        // Iterate over product list, get the product description
        for (int i = 0; i < product_list.size(); i++) {
            description = product_list.get(i).getDescription();
            tokens = description.split("[ ]+");                                         // Tokenize description into single words

            // Iterate tokens
            for (int j = 0; j < tokens.length; j++) {
                list = new ArrayList<Integer> ();                                       // Reset arraylist for next iteration
                // Finds if current token is a keyword in another product's description
                for (int k = 0; k < product_list.size(); k++){
                    // If the keyword is found in another product's description, save the products index in an array list
                    if (matchKeyWords(tokens[j], product_list.get(k).getDescription()) == true) {
                        list.add(k);
                    } 
                }
                // Hashmap current token in product with an arraylist of indices
                index.put(tokens[j].toLowerCase(), list);
            }
        }
    }

    /**
     * Description - Used to find indices of products that contain all keywords entered by user
     * @param keywords - String of keywords used to find matching products
     * @return intList - an integer ArrayList containing the indices of products that match all of the keywords
     */
    public ArrayList <Integer> searchhashMap (String keywords){
        String keyWordsList[] = keywords.split("[ ]+");                                            // Tokenize keywords 
        ArrayList <ArrayList <Integer>> intList = new ArrayList <ArrayList<Integer>> ();
        updateHashMap();

        // Iterate keywords
        for (int i = 0; i < keyWordsList.length; i++) {
            // If the hashMap contains the keyword, add its mapped list of indices to an array list
            if (index.containsKey(keyWordsList[i].toLowerCase())) {
                intList.add(index.get(keyWordsList[i].toLowerCase()));
            // If the hashMap does not contain the keyword, no product in the store contains the keyword, so return an empty list
            } else {
                return null;
            }
        }

        // Product must contain all the keywords, so loop through the ArrayList of indices and compute the intersection
        for (int i = 1; i < intList.size(); i++) {
            intList.get(i).retainAll(intList.get(i - 1));
        }

        // Return the intersection of all products that contain all the keywords
        return intList.get(intList.size() - 1);
    }
}
