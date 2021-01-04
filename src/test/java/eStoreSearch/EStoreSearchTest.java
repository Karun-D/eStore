package eStoreSearch;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import eStoreSearch.EStoreSearch;
import eStoreSearch.Book;
import eStoreSearch.Electronics;
import eStoreSearch.FileIO;

public class EStoreSearchTest {

    ////////////////////////////////////////////
    /// HASHMAP TESTS AT BOTTOM OF TEST FILE ///
    ////////////////////////////////////////////

    @Test
    public void testProductListSetAndGet(){
        ArrayList <Product> product_list = new ArrayList<Product>();
        EStoreSearch store = new EStoreSearch();
        Assert.assertTrue(store.setProductList(product_list));

        product_list = null;
        Assert.assertFalse(store.setProductList(product_list));
    }

    @Test
    public void hashMapSetAndGet(){
        HashMap<String, ArrayList <Integer>> index = new HashMap<String, ArrayList <Integer>> ();
        EStoreSearch store = new EStoreSearch();
        Assert.assertTrue(store.setHashMap(index));

        index = null;
        Assert.assertFalse(store.setHashMap(index));
    }

    @Test
    public void testValidityAddBook(){
        EStoreSearch store = new EStoreSearch();
        ArrayList <Product> product_list = new ArrayList<Product>();

        store.setProductList(product_list);
        
        int ID = 123456;
        String description = "sample description";
        int year = 2020;
        double price = 20;

        Assert.assertTrue(store.addBookToList(ID, description, price, year, "author", "publisher"));

        ID = 1234567;
        description = "";
        year = 20200;
        price = 20;

        Assert.assertFalse(store.addBookToList(ID, description, price, year, "author", "publisher"));
    }

    @Test
    public void testValidityAddElectronics(){
        EStoreSearch store = new EStoreSearch();
        ArrayList <Product> product_list = new ArrayList<Product>();

        store.setProductList(product_list);
        
        int ID = 123456;
        String description = "sample description";
        int year = 2020;
        double price = 20;

        Assert.assertTrue(store.addElectronicsToList(ID, description, price, year, "maker"));

        ID = 1234567;
        description = "";
        year = 20200;
        price = 20;

        Assert.assertFalse(store.addElectronicsToList(ID, description, price, year, "maker"));
    }

    @Test
    public void testAddUniquenessAndSuccessfulAdditions(){
        ArrayList <Product> product_list = new ArrayList<Product>();

        EStoreSearch store = new EStoreSearch(product_list, null);

        store.addElectronicsToList(123456, " ", 0, 1000, " ");
        store.addBookToList(123457, " ", 0, 1000, " ", " ");

        int ID = 123458;

        Assert.assertTrue(store.validID(ID));

        ID = 123456;

        Assert.assertFalse(store.validID(ID));

    }

    @Test
    public void testSearchProductID(){
        ArrayList <Product> product_list = new ArrayList<Product>();

        EStoreSearch store = new EStoreSearch(product_list, null);

        store.addElectronicsToList(123456, " ", 0, 1000, " ");
        store.addBookToList(123457, " ", 0, 1000, " ", " ");

        Assert.assertTrue(store.searchProductID(123456));
        Assert.assertTrue(store.searchProductID(123457));

        Assert.assertFalse(store.searchProductID(123458));

    }

    @Test
    public void testSearchDescription(){
        ArrayList <Product> product_list = new ArrayList<Product>();

        EStoreSearch store = new EStoreSearch(product_list, null);

        String description1 = "MacBook Air 11” Intel Dual-Core i5 1.6GHz";
        String description2 = "Absolute Java";

        Assert.assertTrue(store.matchKeyWords("aIr", description1));
        Assert.assertTrue(store.matchKeyWords("jAvA", description2));

        Assert.assertFalse(store.matchKeyWords("RANDOM WORD", description2));
    }

    @Test
    public void testSearchTimePeriod(){
        ArrayList <Product> product_list = new ArrayList<Product>();

        EStoreSearch store = new EStoreSearch(product_list, null);
        
        String timePeriod1 = "2020";
        String timePeriod2 = "-2020";
        String timePeriod3 = "2020-";
        String timePeriod4 = "2020-3020";

        Assert.assertTrue(store.matchPeriod(timePeriod1, 2020));
        Assert.assertFalse(store.matchPeriod(timePeriod1, 2021));

        Assert.assertTrue(store.matchPeriod(timePeriod2, 2019));
        Assert.assertFalse(store.matchPeriod(timePeriod2, 2021));

        Assert.assertTrue(store.matchPeriod(timePeriod3, 2021));
        Assert.assertFalse(store.matchPeriod(timePeriod3, 2019));

        Assert.assertTrue(store.matchPeriod(timePeriod4, 2021));
        Assert.assertFalse(store.matchPeriod(timePeriod4, 2019));
    }


    @Test
    public void testSearchMultipleFilters(){
        ArrayList <Product> product_list = new ArrayList<Product>();

        EStoreSearch store = new EStoreSearch(product_list, null);
        
        String description2 = "Absolute Java";
        int ID = 123456;
        int year = 2020;

        try {
            store.addProduct(new Electronics(123456,description2, 100, year, " "));
        } catch (Exception e) {
            return;
        }
        
        String timePeriod1 = "2020-3020";

        Assert.assertTrue(store.matchPeriod(timePeriod1, year));
        Assert.assertTrue(store.matchKeyWords("jAvA", description2));
        Assert.assertTrue(store.searchProductID(ID));

        Assert.assertFalse(store.matchPeriod(timePeriod1, year - 1));
        Assert.assertFalse(store.matchKeyWords("RANDOM WORD", description2));
        Assert.assertFalse(store.searchProductID(ID + 1));
        
    }

    @Test
    public void testHashMapSingleSearch() {

        EStoreSearch store = new EStoreSearch();
        
        String description1 = "Absolute Java";
        String search = "jaVA";
        int ID = 123456;
        int year = 2020;

        try{
            store.addProduct(new Electronics(ID,description1, 100, year, " "));
        } catch (Exception e) {
            return;
        }
        store.updateHashMap();

        HashMap<String, ArrayList <Integer>> index = store.getHashMap();

        ArrayList<Integer> templist = index.get(search);
        int listValue = templist.get(0);

        Assert.assertEquals(0, listValue);
        Assert.assertNotEquals(1, listValue);
    }

    @Test
    public void testHashMapMultipleSearch() {

        EStoreSearch store = new EStoreSearch();
        
        String description1 = "Absolute Java";
        String description2 = "JAVA PROGRAMMING";
        String search = "jaVA";

        int ID = 123456;
        int year = 2020;

        try {
            store.addProduct(new Electronics(ID,description1, 100, year, " "));
            store.addProduct(new Electronics(ID + 1,description2, 100, year, " "));
        } catch (Exception e) {
            return;
        }
        store.updateHashMap();

        HashMap<String, ArrayList <Integer>> index = store.getHashMap();

        ArrayList<Integer> templist = index.get(search);
        int listValue = templist.get(0);

        Assert.assertEquals(0, listValue);

        listValue = templist.get(1);

        Assert.assertEquals(1, listValue);

        Assert.assertNotEquals(2, listValue);

    }

    @Test
    public void testHashMapCombinedSearch() {

        EStoreSearch store = new EStoreSearch();
        
        String description1 = "Absolute Java";
        String description2 = "JAVA PROGRAMMING";
        String keyWords = "jaVA";

        Product finalProduct;
        Product fakeProduct;

        int ID = 123456;
        int year = 2020;

        try{
            store.addProduct(new Electronics(ID,description1, 100, year, " "));
            store.addProduct(new Electronics(ID + 1,description2, 100, year + 1, " "));
            finalProduct = new Electronics(ID,description1, 100, year, " ");
            fakeProduct = new Electronics(ID,description2 , 100, year, " ");
        } catch (Exception e) {
            return;
        }
        store.updateHashMap();

        ArrayList<Integer> keywordProducts = store.searchhashMap(keyWords);                              // Obtain indices of products that match the keywords
        ArrayList<Product> relevantProducts = new ArrayList<Product> ();

        if (keywordProducts != null) {                                                      // If there are products containing list of keywords
            for (int i = 0; i < keywordProducts.size(); i++){
                relevantProducts.add(store.getProductList().get(keywordProducts.get(i)));
            }
        }

        for (int i = 0; i < relevantProducts.size(); i++){
            if (store.matchPeriod("2020", relevantProducts.get(i).getYear()) == false) {
                relevantProducts.remove(i);
            }
        }

        Assert.assertEquals(finalProduct, relevantProducts.get(0));
        Assert.assertNotEquals(fakeProduct, relevantProducts.get(0));
    }
}