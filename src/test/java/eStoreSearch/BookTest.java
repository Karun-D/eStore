package eStoreSearch;

import org.junit.Assert;
import org.junit.Test;

import eStoreSearch.Product;
import eStoreSearch.Book;

public class BookTest {

    @Test
    public void testProductToBookConstructor() {
        try{
            Product prodOne = new Book(123456,"Hello World", 100, 2020, "", "");
            Book bookProduct = new Book (prodOne, "", "");
            Assert.assertTrue (prodOne.equals(bookProduct));
        } catch (Exception e) {
            return;
        }
    }

    @Test
    public void testAuthorsSetAndGet() {
        Book bookOne = new Book();
        String authors = "Walter Savitch, Kenrick Mock";
        bookOne.setAuthors(authors);

        Assert.assertEquals(authors, bookOne.getAuthors());
    }

    @Test
    public void testPublisherSetAndGet() {
        Book bookOne = new Book();
        String publisher = "Pearson";
        bookOne.setPublisher(publisher);

        Assert.assertEquals(publisher, bookOne.getPublisher());
    }

    @Test
    public void testEquals(){
        Book bookOne = new Book();
        Book bookTwo = new Book();

        /////////////
        // Case #1 //
        /////////////

        Assert.assertTrue(bookOne.equals(bookTwo));

        /////////////
        // Case #2 //
        /////////////

        try{
            bookOne.setID(123456);
        } catch (Exception e) {
            return;
        }

        Assert.assertFalse(bookOne.equals(bookTwo));
    }

    @Test
    public void testToString(){
        Book bookOne = new Book();

        Assert.assertNotNull(bookOne.toString());
    }
}