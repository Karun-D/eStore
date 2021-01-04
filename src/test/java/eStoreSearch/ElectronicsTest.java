package eStoreSearch;

import org.junit.Assert;
import org.junit.Test;

import eStoreSearch.Product;
import eStoreSearch.Electronics;

public class ElectronicsTest {

    @Test
    public void testProductToElectronicsConstructor() {
        try {
            Product prodOne = new Electronics(123456,"Hello World", 100, 2020, "");
            Electronics electronicsProduct = new Electronics (prodOne, "");
            Assert.assertTrue (prodOne.equals(electronicsProduct));
        } catch (Exception e) {
            return;
        }
    }

    @Test
    public void testMakerSetAndGet() {
        try{
            Electronics electronicsProduct = new Electronics();
            String maker = "Walter Savitch";
            electronicsProduct.setMaker(maker);

            Assert.assertEquals(maker, electronicsProduct.getMaker());
        } catch (Exception e) {
            return;
        }
    }

    @Test
    public void testEquals(){
        Electronics electronicsOne = new Electronics();
        Electronics electronicsTwo = new Electronics();

        /////////////
        // Case #1 //
        /////////////

        Assert.assertTrue(electronicsOne.equals(electronicsTwo));

        /////////////
        // Case #2 //
        /////////////

        try{
            electronicsOne.setID(123456);
        } catch (Exception e) {
            return;
        }

        Assert.assertFalse(electronicsOne.equals(electronicsTwo));
    }

    @Test
    public void testToString(){
        Electronics electronicsOne = new Electronics();

        Assert.assertNotNull(electronicsOne.toString());
    }

}