package eStoreSearch;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class FileIO {
    private static final int NUM_BOOK_ARGS = 6;
    private static final int NUM_ELECTRONIC_ARGS = 5;

    /**
     * Description - Used to parse objects from input textfile
     * @param args - Array of command arguments, used to retrieve name of textfile
     * @param store - Used to search products in EStore
     * @return The name of the textfile if it was able to be parsed successfully
     */
    public static String loadInfo(String args[], EStoreSearch store) {
        // File reading variables
        String textfile;                                                        // Name of textfile

        // String tokenizer variables
        String tokens[];                                                        // Used to split file line into object attributes
        String delimiters = "[ ]+";     
        String temp;                                            

        // Object values
        String newBookValues[] = new String[NUM_BOOK_ARGS];                     // Used to initialize Book product 
        String newElectronicsValues[] = new String[NUM_ELECTRONIC_ARGS];        // Used to initialize Electronics product  

        // Boolean Control Flow Variables
        boolean isProductBook = false;
        boolean isProductElectronics = false;

        if (args.length > 0){
            try {
                textfile = args[0];                                                     // Retrieve option through command line
                File file = new File(textfile);                                         // Create new file object
                Scanner scanner = new Scanner(file);                                    // Scan file object

                while (scanner.hasNextLine()) {
                    tokens = scanner.nextLine().split(delimiters);                      // Split current line into object attributes

                    // Loop through each token
                    for (int i = 0; i < tokens.length; i++) {
                        // If the current token is "book", parse Book Object
                        if (tokens[i].equals("\"book\"")) {
                            isProductBook = true;
                        }
                        // If the current token is "electronics", parse Electronics Object
                        if (tokens[i].equals("\"electronics\"")) {
                            isProductElectronics = true;
                        }
                    }

                    // Parse Book Object
                    if (isProductBook == true) {
                        // Iterate the next #NUM_BOOK_ARGS lines in the text file
                        for (int i = 0; i < NUM_BOOK_ARGS; i++) {
                            temp = scanner.nextLine();
                            // Iterate over each character in the current line
                            for (int j = 0; j < temp.length(); j++) {
                                // If the current character is a quotation, extract string in between the quotations
                                if (temp.charAt(j) == '\"'){
                                    // Extract string in between the quotations
                                    newBookValues[i] = temp.substring(j + 1, temp.length() - 1);
                                    // If the quotation marks are empty, assign an empty string to the value
                                    if ((j == temp.length() - 2)){
                                        newBookValues[i] = "";

                                    }
                                    break;
                                }
                            }
                        }
                        // Add Book Product to ArrayList
                        store.addBookToList(Integer.parseInt(newBookValues[0]), newBookValues[1], Double.parseDouble(newBookValues[2]), Integer.parseInt(newBookValues[3]), newBookValues[4], newBookValues[5]);

                        isProductBook = false;
                    }

                    // Parse Electronics Object
                    if (isProductElectronics == true) {
                        // Iterate the next #NUM_ELECTRONIC_ARGS lines in the text file
                        for (int i = 0; i < NUM_ELECTRONIC_ARGS; i++) {
                            temp = scanner.nextLine();
                            // Iterate over each character in the current line
                            for (int j = 0; j < temp.length(); j++) {
                                // If the current character is a quotation, extract string in between the quotations
                                if (temp.charAt(j) == '\"'){
                                    // Extract string in between the quotations
                                    newElectronicsValues[i] = temp.substring(j + 1, temp.length() - 1);
                                    // If the quotation marks are empty, assign an empty string to the value
                                    if ((j == temp.length() - 2)){
                                        newElectronicsValues[i] = "";
                                    }
                                    break;
                                }
                            }
                        }
                 
                        // Add Electronics Product to ArrayList
                        store.addElectronicsToList(Integer.parseInt(newElectronicsValues[0]), newElectronicsValues[1], Double.parseDouble(newElectronicsValues[2]), Integer.parseInt(newElectronicsValues[3]), newElectronicsValues[4]);
                        isProductElectronics = false;
                    }
                }
                scanner.close();
                return textfile;
            } catch (Exception e) {
                System.out.println("Could not Open File :(");
                System.exit(0);
            }
        } 
        return null;
    }

    /**
     * Description - Used to store objects to output textfile
     * @param store - Used to search products in EStore
     * @return True - if the Product ArrayList was successfully stored in the input file, False - if otherwise
     */
    public static boolean storeInfo(String textfile, EStoreSearch store) {
        try {
            // FileOutput variables 
            PrintWriter outputStream = new PrintWriter(new FileOutputStream(textfile));
            // Object variables
            Product currentProduct;
            // Attribute Variables
            String info;
            // Store Product list
            ArrayList<Product> product_list = store.getProductList();

            // Iterate through Array Lust
            for (int i = 0; i < product_list.size(); i++) {
                // Retreive current student
                currentProduct = product_list.get(i);
                // Format string to store in text file

                info = formatProductString(currentProduct);
                
                outputStream.println(info);
            }

            outputStream.close();
            return true;
        } catch (Exception e) {
            System.out.println("");
            System.out.println("Failed to write!");
            System.out.println("");
            return false;
        }
    }

    /**
     * Description - Used to create a format product objects to insert in output file
     * @param newProduct - producted to be formatted for output
     */
    public static String formatProductString (Product newProduct) {
        Book bookProduct;
        Electronics electronicsProduct;
        String outputString = "";

        // If the product in the list was originally a Book, format a Book outputString
        if (newProduct instanceof Book) {
            bookProduct = (Book) newProduct;

            outputString = ("type = \"book\"" + "\n"
            + "productID = \"" + String.format("%06d", newProduct.getID()) + "\"" + "\n"
            + "description = \"" + newProduct.getDescription() + "\"" + "\n"
            + "price = \"" + newProduct.getPrice() + "\"" + "\n"
            + "year = \"" + newProduct.getYear() + "\"" + "\n"
            + "authors = \"" + bookProduct.getAuthors() + "\"" + "\n"
            + "publisher = \"" + bookProduct.getPublisher() + "\"");
        }

         // If the product in the list was originally an Electronics, format a Electronics outputString
        if (newProduct instanceof Electronics) {
            electronicsProduct = (Electronics) newProduct;

            outputString = ("type = \"electronics\"" + "\n"
            + "productID = \"" + String.format("%06d", newProduct.getID()) + "\"" + "\n"
            + "description = \"" + newProduct.getDescription() + "\"" + "\n"
            + "price = \"" + newProduct.getPrice() + "\"" + "\n"
            + "year = \"" + newProduct.getYear() + "\"" + "\n"
            + "maker = \"" + electronicsProduct.getMaker() + "\"");
        }

        // Return product outputString
        return outputString;
    }
}