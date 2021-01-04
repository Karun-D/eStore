package eStoreSearch;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class GUI extends JFrame {
    // Constants for frame/panel sizes
    private static final int WIDTH = 200 * 3;
    private static final int HEIGHT = (int)(300 * 2.5);

    private static final int LINES = 15;
    private static final int CHAR_PER_LINE = 30;

    // Main Panels
    private JPanel welcomePanel;
    private JPanel addPanel;
    private JPanel searchPanel;

    // Text Consoles
    private JTextArea memoDisplay;
    private JTextArea memoDisplayTwo;

    // Text Fields for Add Panel
    private JComboBox<String> typeField;
    private JTextField idField;
    private JTextField descriptionField;
    private JTextField priceField;
    private JTextField yearField;
    private JTextField authorsField;
    private JTextField publisherField;
    private JTextField makerField;

    // Text Fields for Search Panel
    private JTextField productIDField;
    private JTextField keywordsField;
    private JTextField startYearField;
    private JTextField endYearField;
    
    // Panels that change visibility 
    private JPanel authorsPanel;
    private JPanel publisherPanel;
    private JPanel makerPanel;

    // Database variables
    private boolean isBookType = true;                                      // Determines if current product is of type "Book"                      
    private static EStoreSearch store = new EStoreSearch ();                // Used to add/search products in Database
    private static String textfile;                                         // Used for FileIO

    public static void main(String args[]){
        GUI window = new GUI();                                             // Create GUI 
        window.setVisible(true);                                            // Set GUI visible to user

        textfile = FileIO.loadInfo(args, store);                            // Compute name of textfile (if entered in command line)
        store.updateHashMap();                                              // Update hashMap for searching
    }

    /**
     * Used to addProducts to eStoreSearch using GUI user input and ActionListeners
     */
    private class addProductListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            // Product variables
            Product newProduct;                         
            Book newBookProduct;                                            
            Electronics newElectronicsProduct;
           
            // Product child class variables
            String authors = authorsField.getText();
            String publisher = publisherField.getText();
            String maker = makerField.getText();

            // Clear Text Console
            memoDisplay.setText("");

            // Determine if the product is of Book or Electronics type
            if (isBookType == true) {
                newProduct = new Book();
            } else {
                newProduct = new Electronics();
            }
            
            // Initialize Product Object with user input 
            try{
                newProduct.setDescription(descriptionField.getText());
                
                // If ProductID is not entered, output error message and exit method
                if (idField.getText().equals("")) {
                    memoDisplay.setText("Error, Product ID is required. ");
                    return;
                } else {
                    // If ID is taken, output error message and exit method
                    if (store.validID(Integer.parseInt(idField.getText())) == false) {
                        memoDisplay.setText("Error, Product ID is already taken, try again!");
                        return;
                    } else {
                        newProduct.setID(Integer.parseInt(idField.getText()));
                    }
                }

                // If Product Year is not entered, output error message and exit method
                if (yearField.getText().equals("")) {
                    memoDisplay.setText("Error, Product Year is required. ");
                    return;
                } else {
                    newProduct.setYear(Integer.parseInt(yearField.getText()));
                }

                // If Product Price is not entered, set default to 0, otherwise, set value to User Input
                if (priceField.getText().equals("")){
                    newProduct.setPrice(0);
                } else {
                    newProduct.setPrice(Double.parseDouble(priceField.getText()));
                }

                // If the product is a book ...
                if (isBookType == true){
                    // Initialize Book product, and add Book product to store
                    newBookProduct = new Book(newProduct, authors, publisher);
                    store.addProduct(newBookProduct);
                    // Update hashMap for searching
                    store.updateHashMap();
                    // Output product to Text Console
                    memoDisplay.setText(newBookProduct.toString());
                // If the product is an electronic product ...
                } else {
                    // Initialize Electronics product, and add Electronics product to store
                    newElectronicsProduct = new Electronics(newProduct, maker);
                    store.addProduct(newElectronicsProduct);
                    // Update hashMap for searching
                    store.updateHashMap();
                    // Output product to Text Console
                    memoDisplay.setText(newElectronicsProduct.toString());
                }

            } catch (Exception excep) {
                memoDisplay.setText(excep.getMessage());
                return;
            }
        }
    } 

    /**
     * Used to search Products to eStoreSearch using GUI user input and ActionListeners
     */
    private class searchProductListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            // User input variables
            int productID = -1;
            String keyWords = null;
            String period = null;

            ArrayList<Product> product_list;
            String productArray[];
            String result;

            // If the productIDField is not empty...
            if (productIDField.getText().length() != 0){
                // Error block, used to prevent invalid ProductID from being searched
                if (productIDField.getText().length() != 6) {
                    memoDisplayTwo.setText("Invalid input, Product ID is not a 6 digit integer, try again!");
                    return;
                } else {
                    // Error block, used to prevent invalid ProductID from being searched
                    try {
                        productID = Integer.parseInt(productIDField.getText());
                    } catch (Exception excep) {
                        memoDisplayTwo.setText("Invalid input, Product ID is not a 6 digit integer, try again!");
                        return;
                    }
                }
            }

            // If the keywordsField is not empty, initialize keyWords user input variable
            if (keywordsField.getText().length() != 0) {
                keyWords = keywordsField.getText();                                       
            }

            // If the startYearField is not empty...
            if (startYearField.getText().length() != 0) {
                // If the startYearField is not empty and endYearField is not empty, format = " startYear-endYear "
                if (endYearField.getText().length() != 0) {
                    period = (startYearField.getText() + "-" + endYearField.getText());
                // If the startYearField is not empty and endYearField is empty, format = " startYear- "
                } else {
                    period = (startYearField.getText() + "-");
                }

            }

            // If the endYearField is not empty...
            if (endYearField.getText().length() != 0) {
                // If the startYearField is not empty and endYearField is not empty, format = " startYear-endYear "
                if (startYearField.getText().length() != 0) {
                    period = (startYearField.getText() + "-" + endYearField.getText());
                // If the startYearField is empty and endYearField is not empty, format = " -endYear "
                } else {
                    period = ("-" + endYearField.getText());
                }
            }

            // If any yearField is not empty and the input is invalid, output error message and exit method
            if (((startYearField.getText().length() != 0) || (endYearField.getText().length() != 0)) && (store.validPeriod(period) == false) ){
                memoDisplayTwo.setText("Invalid input, time period input is incorrect, try again!");
                return;
            }

            // Return list of filtered products from eStore
            product_list = store.searchProducts(keyWords, productID, period);
            // Create a String array to store the filtered product output strings
            productArray = new String[product_list.size()];

            
            for (int i =0; i < product_list.size(); i++) {
                 // Add each filtered product output string to the String array
                productArray[i] = product_list.get(i).toString();
            }

            // Join the output strings of all filtered products into one string
            result = String.join("\n", productArray);

            // Print the single string to the text console
            memoDisplayTwo.setText(result);
        }
    }

    /**
     * Used to read the combo box in the Add Panel, allows the program to determine the type of the product
     */
    private class comboListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            // Cast actionEvent to type ComboBox
            JComboBox cb = (JComboBox) e.getSource();
            // Read the user's selected choice from the ComboBox
            String choice = (String)cb.getSelectedItem();

            // If the user chose the Book type, hide the maker field, set the boolean variable "isBookType" to true
            if(choice.equals("book")){
                authorsPanel.setVisible(true);
                publisherPanel.setVisible(true);
                makerPanel.setVisible(false);
                isBookType = true;
            // If the user chose the Electronics type, hide the book fields, set the boolean variable "isBookType" to false
            } else {
                authorsPanel.setVisible(false);
                publisherPanel.setVisible(false);
                makerPanel.setVisible(true);
                isBookType = false;
            }
        }
    }

    /**
     * Used to clear all input fields if the user hits the Reset Button in the Add Panel
     */
    private class resetListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            idField.setText("");
            descriptionField.setText("");
            priceField.setText("");
            yearField.setText("");
            authorsField.setText("");
            publisherField.setText("");
            makerField.setText("");
        }
    } 

    /**
     * Used to clear all input fields if the user hits the Reset Button in the Search Panel
     */
    private class resetListenerTwo implements ActionListener {
        public void actionPerformed(ActionEvent e){
            productIDField.setText("");
            keywordsField.setText("");
            startYearField.setText("");
            endYearField.setText("");
        }
    } 

    /**
     * Used to display the Add Panel
     */
    private class addListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            welcomePanel.setVisible(false);
            addPanel.setVisible(true);
            searchPanel.setVisible(false);
            setSize(WIDTH, HEIGHT);
        }
    }

    /**
     * Used to display the Search Panel
     */
    private class searchListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            welcomePanel.setVisible(false);
            addPanel.setVisible(false);
            searchPanel.setVisible(true);
            setSize(WIDTH, (int)(HEIGHT/1.5));
        }
    } 

    /**
     * Used to quit the program if the user hits the Quit Button
     */
    private class quitListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            // If user entered a textfile for input, store output in the textfile entered
            if (textfile != null) {
                FileIO.storeInfo(textfile, store);
            // If user did not enter a textfile for input, store output in the textfile "products.txt"
            } else {
                FileIO.storeInfo("products.txt", store);
            }

            System.exit(0);
        }
    } 

    public GUI() {
        super("eStoreSearch");
        setSize(WIDTH, HEIGHT);
        // Fix the size of the application
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLayout(new BorderLayout());
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        
        ////////////////
        // NORTH MENU //
        ////////////////  

        // Create Menu
        JMenu colorMenu = new JMenu("Commands");

        // Add MenuItems and Listeners for Add Command
        JMenuItem add = new JMenuItem("Add");
        add.addActionListener(new addListener());
        colorMenu.add(add);

        // Add MenuItems and Listeners for Search Command
        JMenuItem search = new JMenuItem("Search");
        search.addActionListener(new searchListener());
        colorMenu.add(search);

        // Add MenuItems and Listeners for Quit Command
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(new quitListener());
        colorMenu.add(quit);

        // Add Menu to MenuBar/Container
        JMenuBar bar = new JMenuBar( );
        bar.add(colorMenu);

        // Add MenuBar/Container to Window
        setJMenuBar(bar);

        ///////////////////
        // WELCOME PANEL //
        ///////////////////         

        // Initialize Panel
        welcomePanel = new JPanel();
        welcomePanel.setBackground(Color.BLACK);
        welcomePanel.setVisible(true);
        welcomePanel.setLayout(new GridLayout(3,1));

        // Create Labels
        JLabel welcomeMessageOne = new JLabel("Welcome to eStoreSearch");
        JLabel welcomeMessageTwo = new JLabel("Choose a command from the Commands menu above");
        JLabel welcomeMessageThree = new JLabel("for adding a product, searching products, or quitting the program.");
        
        // Change Font and Colour
        welcomeMessageOne.setForeground(Color.green);
        welcomeMessageOne.setFont(new Font("Calibri", Font.BOLD, 18));

        welcomeMessageTwo.setForeground(Color.green);
        welcomeMessageTwo.setFont(new Font("Calibri", Font.ITALIC, 18));
        
        welcomeMessageThree.setForeground(Color.green);
        welcomeMessageThree.setFont(new Font("Calibri", Font.ITALIC, 16));

        // Add Labels to Panel
        welcomePanel.add(welcomeMessageOne);
        welcomePanel.add(welcomeMessageTwo);
        welcomePanel.add(welcomeMessageThree);

        welcomeMessageOne.setHorizontalAlignment(JLabel.CENTER);
        welcomeMessageTwo.setHorizontalAlignment(JLabel.CENTER);
        welcomeMessageThree.setHorizontalAlignment(JLabel.CENTER);

        // Add Panel to Window
        add(welcomePanel, BorderLayout.CENTER);

        ///////////////
        // ADD PANEL //
        /////////////// 

        // Initialize ADD Panel
        addPanel = new JPanel();
        addPanel.setLayout(new BorderLayout());
        addPanel.setVisible(false);

        // Create North Panel
        JPanel northAddPanel = new JPanel();
        northAddPanel.setLayout(new BorderLayout());

        // Create Left Side of North Panel
        JPanel addProduct = new JPanel();
        addProduct.setBackground(Color.WHITE);
        addProduct.setLayout(new BorderLayout());

        // Create Header for LeftPanel, Add to LeftPanel
        JPanel northHeaderPanel = new JPanel();
        northHeaderPanel.setBackground(Color.BLACK);
        JLabel northHeader = new JLabel("  Adding a Product");
        northHeader.setForeground(Color.GREEN);
        northHeaderPanel.add(northHeader);

        addProduct.add(northHeaderPanel, BorderLayout.NORTH);

        JPanel textFields = new JPanel();
        textFields.setLayout(new GridLayout(8,1, 10, 20));
        textFields.setBackground(Color.darkGray);

        // Type Panel
        JPanel typePanel = new JPanel();
        typePanel.setLayout(new BorderLayout());
        
        JLabel type = new JLabel("    Type:                  ");
        typePanel.add(type, BorderLayout.WEST);

        String[] fields = {"book", "electronics"};
        typeField = new JComboBox<String>(fields);
        typeField.addActionListener(new comboListener());
        typePanel.add(typeField, BorderLayout.CENTER);

        textFields.add(typePanel);
        typePanel.setBackground(Color.darkGray);
        type.setForeground(Color.GREEN);
        
        // ID Panel
        JPanel idPanel = new JPanel();
        idPanel.setLayout(new BorderLayout());
        
        JLabel id = new JLabel("    ProductID: ");
        idPanel.add(id, BorderLayout.WEST);

        idField = new JTextField(20);
        idPanel.add(idField, BorderLayout.EAST);

        textFields.add(idPanel);
        idPanel.setBackground(Color.darkGray);
        idField.setBackground(Color.BLACK);
        idField.setForeground(Color.GREEN);
        id.setForeground(Color.GREEN);

        // Description Panel 
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BorderLayout());
        
        JLabel description = new JLabel("    Description: ");
        descriptionPanel.add(description, BorderLayout.WEST);

        descriptionField = new JTextField(20);
        descriptionPanel.add(descriptionField, BorderLayout.EAST);

        textFields.add(descriptionPanel);
        descriptionPanel.setBackground(Color.darkGray);
        descriptionField.setBackground(Color.BLACK);
        descriptionField.setForeground(Color.GREEN);
        description.setForeground(Color.GREEN);

        // Price Panel  
        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BorderLayout());
        
        JLabel price = new JLabel("    Price: ");
        pricePanel.add(price, BorderLayout.WEST);

        priceField = new JTextField(20);
        pricePanel.add(priceField, BorderLayout.EAST);

        textFields.add(pricePanel);
        pricePanel.setBackground(Color.darkGray);
        priceField.setBackground(Color.BLACK);
        priceField.setForeground(Color.GREEN);
        price.setForeground(Color.GREEN);

        // Year Panel   
        JPanel yearPanel = new JPanel();
        yearPanel.setLayout(new BorderLayout());
        
        JLabel year = new JLabel("    Year: ");
        yearPanel.add(year, BorderLayout.WEST);

        yearField = new JTextField(20);
        yearPanel.add(yearField, BorderLayout.EAST);

        textFields.add(yearPanel);
        yearPanel.setBackground(Color.darkGray);
        yearField.setBackground(Color.BLACK);
        yearField.setForeground(Color.GREEN);
        year.setForeground(Color.GREEN);

        // Authors Panel    
        authorsPanel = new JPanel();
        authorsPanel.setLayout(new BorderLayout());
        
        JLabel authors = new JLabel("    Authors: ");
        authorsPanel.add(authors, BorderLayout.WEST);

        authorsField = new JTextField(20);
        authorsPanel.add(authorsField, BorderLayout.EAST);

        textFields.add(authorsPanel);
        authorsPanel.setBackground(Color.darkGray);
        authorsField.setBackground(Color.BLACK);
        authorsField.setForeground(Color.GREEN);
        authors.setForeground(Color.GREEN);

        // Publisher Panel
        publisherPanel = new JPanel();
        publisherPanel.setLayout(new BorderLayout());
        
        JLabel publisher = new JLabel("    Publisher: ");
        publisherPanel.add(publisher, BorderLayout.WEST);

        publisherField = new JTextField(20);
        publisherPanel.add(publisherField, BorderLayout.EAST);

        textFields.add(publisherPanel);
        publisherPanel.setBackground(Color.darkGray);
        publisherField.setBackground(Color.BLACK);
        publisherField.setForeground(Color.GREEN);
        publisher.setForeground(Color.GREEN);

        // Maker Panel
        makerPanel = new JPanel();
        makerPanel.setLayout(new BorderLayout());
        
        JLabel maker = new JLabel("    Maker: ");
        makerPanel.add(maker, BorderLayout.WEST);

        makerField = new JTextField(20);
        makerPanel.add(makerField, BorderLayout.EAST);
        makerPanel.setVisible(false);

        textFields.add(makerPanel);
        makerPanel.setBackground(Color.darkGray);
        makerField.setBackground(Color.BLACK);
        makerField.setForeground(Color.GREEN);
        maker.setForeground(Color.GREEN);

        addProduct.add(textFields, BorderLayout.CENTER);

        JPanel blankPanel = new JPanel();
        blankPanel.setPreferredSize(new Dimension(75, 0));
        addProduct.add(blankPanel, BorderLayout.EAST);
        blankPanel.setBackground(Color.DARK_GRAY);

        // BlankPanel
        JPanel southBlankPanel = new JPanel();
        southBlankPanel.setPreferredSize(new Dimension(0, 10));
        addProduct.add(southBlankPanel, BorderLayout.SOUTH);
        southBlankPanel.setBackground(Color.DARK_GRAY);

        // Create Right Side of North Panel
        JPanel addProductButtons = new JPanel();
        addProductButtons.setBackground(Color.BLACK);
        addProductButtons.setLayout(new GridLayout(2,1));

        // Create Buttons for Right Side of North Panel
        JButton resetButton = new JButton("Reset");
        addProductButtons.add(resetButton);
        resetButton.setFont(new Font("Calibri", Font.BOLD, 26));
        resetButton.addActionListener(new resetListener());

        JButton addButton = new JButton("Add");
        addProductButtons.add(addButton);
        addButton.setFont(new Font("Calibri", Font.BOLD, 26));
        addButton.addActionListener(new addProductListener());

        // Add Left and Right Side to North Panel
        northAddPanel.add(addProduct, BorderLayout.WEST);
        northAddPanel.add(addProductButtons, BorderLayout.EAST);

        // Change Sizes of Left and Right Side of North Panel
        addProductButtons.setPreferredSize(new Dimension(150, 0));
        addProduct.setPreferredSize(new Dimension(WIDTH - 150, 0));

        // Add North Panel to Main Add Panel
        addPanel.add(northAddPanel, BorderLayout.CENTER);

        // Create South Panel
        JPanel southAddPanel = new JPanel();

        // Create Header for JTextArea, Add to SouthPanel
        JLabel southHeader = new JLabel("Messages");
        southAddPanel.add(southHeader);
        southAddPanel.setBackground(Color.darkGray);
        southHeader.setForeground(Color.green);

        // Create JTextArea
        memoDisplay = new JTextArea((int)(LINES/1.5), (int)(CHAR_PER_LINE * 1.5));
        memoDisplay.setBackground(Color.BLACK);
        memoDisplay.setForeground(Color.GREEN);
        memoDisplay.setEditable(false);

        // Create Scroll Bars
        JScrollPane scrolledText = new JScrollPane(memoDisplay);
        scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add JTextArea to South Panel. Change size,Add to SouthPanel
        southAddPanel.add(scrolledText);
        southAddPanel.setPreferredSize(new Dimension(WIDTH, 210));
        addPanel.add(southAddPanel, BorderLayout.SOUTH);

        // Add "addPanel" to GUI
        add(addPanel);   

        //////////////////
        // SEARCH PANEL //
        //////////////////

        // Initialize ADD Panel
        searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setVisible(false);

        // Create North Panel
        JPanel northSearchPanel = new JPanel();
        northSearchPanel.setLayout(new BorderLayout());

        // Create Left Side of North Panel
        JPanel searchProduct = new JPanel();
        searchProduct.setBackground(Color.WHITE);
        searchProduct.setLayout(new BorderLayout());

        // Create Header for LeftPanel, Add to LeftPanel
        JPanel northHeaderPanelTwo = new JPanel();
        northHeaderPanelTwo.setBackground(Color.BLACK);
        JLabel northHeaderTwo = new JLabel("  Searching Products");
        northHeaderTwo.setForeground(Color.GREEN);
        northHeaderPanelTwo.add(northHeaderTwo);

        searchProduct.add(northHeaderPanelTwo, BorderLayout.NORTH);

        JPanel textFieldsTwo = new JPanel();
        textFieldsTwo.setBackground(Color.darkGray);
        textFieldsTwo.setLayout(new GridLayout(4,1, 10, 20));
        
        // ID Panel
        JPanel productIDPanel = new JPanel();
        productIDPanel.setLayout(new BorderLayout());
        
        JLabel productID = new JLabel("    ProductID: ");
        productIDPanel.add(productID, BorderLayout.WEST);

        productIDField = new JTextField(20);
        productIDPanel.add(productIDField, BorderLayout.EAST);

        textFieldsTwo.add(productIDPanel);
        productIDPanel.setBackground(Color.darkGray);
        productIDField.setBackground(Color.BLACK);
        productIDField.setForeground(Color.GREEN);
        productID.setForeground(Color.GREEN);

        // Description Panel 
        JPanel keywordsPanel = new JPanel();
        keywordsPanel.setLayout(new BorderLayout());
        
        JLabel keywords = new JLabel("    Keywords: ");
        keywordsPanel.add(keywords, BorderLayout.WEST);

        keywordsField = new JTextField(20);
        keywordsPanel.add(keywordsField, BorderLayout.EAST);

        textFieldsTwo.add(keywordsPanel);
        keywordsPanel.setBackground(Color.darkGray);
        keywordsField.setBackground(Color.BLACK);
        keywordsField.setForeground(Color.GREEN);
        keywords.setForeground(Color.GREEN);

        // Price Panel  
        JPanel startYearPanel = new JPanel();
        startYearPanel.setLayout(new BorderLayout());
        
        JLabel startYear = new JLabel("    Start Year: ");
        startYearPanel.add(startYear, BorderLayout.WEST);

        startYearField = new JTextField(20);
        startYearPanel.add(startYearField, BorderLayout.EAST);

        textFieldsTwo.add(startYearPanel);
        startYearPanel.setBackground(Color.darkGray);
        startYearField.setBackground(Color.BLACK);
        startYearField.setForeground(Color.GREEN);
        startYear.setForeground(Color.GREEN);

        // Price Panel  
        JPanel endYearPanel = new JPanel();
        endYearPanel.setLayout(new BorderLayout());
        
        JLabel endYear = new JLabel("    End Year: ");
        endYearPanel.add(endYear, BorderLayout.WEST);

        endYearField = new JTextField(20);
        endYearPanel.add(endYearField, BorderLayout.EAST);

        textFieldsTwo.add(endYearPanel);
        endYearPanel.setBackground(Color.darkGray);
        endYearField.setBackground(Color.BLACK);
        endYearField.setForeground(Color.GREEN);
        endYear.setForeground(Color.GREEN);

        searchProduct.add(textFieldsTwo, BorderLayout.CENTER);

        JPanel blankPanelTwo = new JPanel();
        blankPanelTwo.setPreferredSize(new Dimension(75, 0));
        searchProduct.add(blankPanelTwo, BorderLayout.EAST);
        blankPanelTwo.setBackground(Color.darkGray);

        // BlankPanel
        JPanel southBlankPanelTwo = new JPanel();
        southBlankPanelTwo.setPreferredSize(new Dimension(0, 10));
        searchProduct.add(southBlankPanelTwo, BorderLayout.SOUTH);
        southBlankPanelTwo.setBackground(Color.darkGray);

        // Create Right Side of North Panel
        JPanel searchProductButtons = new JPanel();
        searchProductButtons.setBackground(Color.BLACK);
        searchProductButtons.setLayout(new GridLayout(2,1));

        // Create Buttons for Right Side of North Panel
        JButton resetButtonTwo = new JButton("Reset");
        searchProductButtons.add(resetButtonTwo);
        resetButtonTwo.setFont(new Font("Calibri", Font.BOLD, 26));
        resetButtonTwo.addActionListener(new resetListenerTwo());

        JButton searchButton = new JButton("Search");
        searchProductButtons.add(searchButton);
        searchButton.setFont(new Font("Calibri", Font.BOLD, 26));
        searchButton.addActionListener(new searchProductListener());

        // Add Left and Right Side to North Panel
        northSearchPanel.add(searchProduct, BorderLayout.WEST);
        northSearchPanel.add(searchProductButtons, BorderLayout.EAST);

        // Change Sizes of Left and Right Side of North Panel
        searchProductButtons.setPreferredSize(new Dimension(150, 0));
        searchProduct.setPreferredSize(new Dimension(WIDTH - 150, 0));

        // Add North Panel to Main Add Panel
        searchPanel.add(northSearchPanel, BorderLayout.CENTER);

        // Create South Panel
        JPanel southSearchPanel = new JPanel();

        // Create Header for JTextArea, Add to SouthPanel
        JLabel southSearchHeader = new JLabel("Search Results");
        southSearchPanel.add(southSearchHeader);
        southSearchPanel.setBackground(Color.darkGray);
        southSearchHeader.setForeground(Color.GREEN);

        // Create JTextArea
        memoDisplayTwo = new JTextArea((int)(LINES/1.5), (int)(CHAR_PER_LINE * 1.5));
        memoDisplayTwo.setBackground(Color.BLACK);
        memoDisplayTwo.setForeground(Color.GREEN);
        memoDisplayTwo.setEditable(false);

        // Create Scroll Bars
        JScrollPane scrolledTextTwo = new JScrollPane(memoDisplayTwo);
        scrolledTextTwo.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrolledTextTwo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add JTextArea to South Panel. Change size,Add to SouthPanel
        southSearchPanel.add(scrolledTextTwo);
        southSearchPanel.setPreferredSize(new Dimension(WIDTH, 210));
        searchPanel.add(southSearchPanel, BorderLayout.SOUTH);
    
        // Add "addPanel" to GUI
        add(searchPanel);   

    }
}



