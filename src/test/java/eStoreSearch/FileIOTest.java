package eStoreSearch;

import org.junit.Assert;
import org.junit.Test;

import eStoreSearch.FileIO;

public class FileIOTest {

    @Test
    public void testFileInput(){
        EStoreSearch store = new EStoreSearch();
        String args[] = new String[1];
        
        /////////////////
        // Valid Input //
        /////////////////

        args[0] = "out.txt";

        // Method returns the name of the textfile if it was able to be parsed successfully
        Assert.assertNotNull(FileIO.loadInfo(args, store));
        Assert.assertEquals(args[0], FileIO.loadInfo(args, store));

        /////////////////////
        // Incorrect Input //
        /////////////////////

        args[0] = "outtt.txt";

        // Method returns null if the textfile was not able to be parsed successfully
        Assert.assertNull(FileIO.loadInfo(args, store));
        Assert.assertNotEquals(args[0], FileIO.loadInfo(args, store));

    }

    @Test
    public void testFileOutput(){
        EStoreSearch store = new EStoreSearch();
        String textfile;
        
        /////////////////
        // Valid Input //
        /////////////////

        textfile = "out.txt";

        // Method returns True if the Product ArrayList was successfully stored in the input file,
        Assert.assertTrue(FileIO.storeInfo(textfile, store));
        

        /////////////////////
        // Incorrect Input //
        /////////////////////

        textfile = "outtt.txt";

        // Method returns False if the Product ArrayList was not successfully stored in the input file,
        Assert.assertFalse(FileIO.storeInfo(textfile, store));

    }
}