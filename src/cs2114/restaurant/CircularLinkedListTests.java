package cs2114.restaurant;

import java.util.Iterator;
import java.util.NoSuchElementException;

// import java.util.Iterator;

// -------------------------------------------------------------------------
/**
 * Unit tests for the CircularLinkedList class.
 * 
 * @author Benjamin Robohn (brobohn)
 * @version 2013.10.30
 */
public class CircularLinkedListTests
    extends student.TestCase
{
    // ~ Fields ................................................................

    private CircularLinkedList<String> list;


    // ~ Public methods ........................................................

    // ----------------------------------------------------------
    /**
     * Creates a brand new, empty CircularLinkedList for each test method.
     */
    @Override
    public void setUp()
    {
        list = new CircularLinkedList<String>();
    }


    // ----------------------------------------------------------
    /**
     * Tests next() on an empty list
     */
    public void testNextEmpty()
    {
        Exception thrown = null;
        try
        {
            list.next();
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertNull(thrown);
    }


    /**
     * Tests previous() on an empty list
     */
    public void testPreviousEmpty()
    {
        Exception thrown = null;
        try
        {
            list.previous();
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertNull(thrown);
    }


    /**
     * Tests the add() method
     */
    public void testAdd1()
    {
        Exception thrown = null;
        try
        {
            list.getCurrent();
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertTrue(thrown instanceof NoSuchElementException);
        assertEquals("The list is currently empty.", thrown.getMessage());

        list.add("hi");
        assertEquals(list.getCurrent(), "hi");

        list.next();
        assertEquals(list.getCurrent(), "hi");

        list.previous();
        assertEquals(list.getCurrent(), "hi");
    }


    /**
     * Tests the add() method
     */
    public void testAdd2()
    {
        list.add("first");
        list.add("second");
        assertEquals(list.getCurrent(), "second");
        list.add("third");
        assertEquals(list.getCurrent(), "third");
        list.add("fourth");
        assertEquals(list.getCurrent(), "fourth");
        list.add("fifth");
        assertEquals(list.getCurrent(), "fifth");
    }


    /**
     * Tests the add() method
     */
    public void testAdd3()
    {
        list.add("first");
        list.add("second");
        list.next();
        assertEquals(list.getCurrent(), "first");
        list.add("third");
        assertEquals(list.getCurrent(), "third");
        list.previous();
        list.add("fourth");
        assertEquals(list.getCurrent(), "fourth");
    }


    /**
     * Tests the add() method
     */
    public void testAdd4()
    {
        list.add("first");
        list.add("second");
        list.next();
        assertEquals(list.getCurrent(), "first");
        list.add("third");
        assertEquals(list.getCurrent(), "third");
    }


    /**
     * Tests the previous() method
     */
    public void testPrevious()
    {
        list.add("first");
        list.add("second");
        list.add("third");
        list.add("fourth");
        assertEquals(list.getCurrent(), "fourth");
        list.previous();
        assertEquals(list.getCurrent(), "first");
        list.add("fifth");
        assertEquals(list.getCurrent(), "fifth");
        list.previous();
        assertEquals(list.getCurrent(), "second");
    }


    /**
     * Tests the next() method
     */
    public void testNext()
    {
        list.add("first");
        list.add("second");
        list.add("third");
        list.add("fourth");
        assertEquals(list.getCurrent(), "fourth");
        list.next();
        assertEquals(list.getCurrent(), "third");
        list.add("fifth");
        assertEquals(list.getCurrent(), "fifth");
    }


    /**
     * Tests the remove() method
     */
    public void testRemove1()
    {
        list.add("hi");
        assertEquals(list.removeCurrent(), "hi");
        Exception thrown = null;
        try
        {
            list.removeCurrent();
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertTrue(thrown instanceof NoSuchElementException);
        assertEquals("The list is currently empty.", thrown.getMessage());
    }


    /**
     * Tests the remove() method
     */
    public void testRemove2()
    {
        list.add("first");
        list.add("second");
        assertEquals(list.removeCurrent(), "second");
        assertEquals(list.getCurrent(), "first");
    }


    /**
     * Tests the remove() method
     */
    public void testRemove3()
    {
        list.add("first");
        list.add("second");
        list.add("third");
        assertEquals(list.removeCurrent(), "third");
        assertEquals(list.getCurrent(), "second");
    }


    /**
     * Tests the remove() method
     */
    public void testRemove4()
    {
        list.add("first");
        list.add("second");
        list.add("third");
        list.add("fourth");
        assertEquals(list.removeCurrent(), "fourth");
        assertEquals(list.getCurrent(), "third");
    }


    /**
     * Tests the remove() method
     */
    public void testRemove5()
    {
        list.add("first");
        list.add("second");
        list.add("third");
        list.add("fourth");
        list.add("fifth");
        assertEquals(list.removeCurrent(), "fifth");
        assertEquals(list.getCurrent(), "fourth");
    }


    /**
     * Tests the remove() method
     */
    public void testRemove6()
    {
        list.add("first");
        list.add("second");
        list.add("third");
        list.add("fourth");
        list.add("fifth");
        list.next();
        list.next();
        assertEquals(list.getCurrent(), "third");
        assertEquals(list.removeCurrent(), "third");
        assertEquals(list.getCurrent(), "second");
    }


    /**
     * Tests the remove() method
     */
    public void testRemove7()
    {
        list.add("first");
        list.add("second");
        list.add("third");
        list.previous();
        assertEquals(list.getCurrent(), "first");
        assertEquals(list.removeCurrent(), "first");
        assertEquals(list.getCurrent(), "third");
    }


    /**
     * Tests the remove() method
     */
    public void testRemove8()
    {
        list.add("first");
        list.add("second");
        list.add("third");
        list.add("fourth");
        list.previous();
        assertEquals(list.getCurrent(), "first");
        assertEquals(list.removeCurrent(), "first");
        assertEquals(list.getCurrent(), "fourth");
    }


    /**
     * Tests the remove() method on the second to last entry several times
     */
    public void testRemove9()
    {
        list.add("first");
        list.add("second");
        list.add("third");
        list.add("fourth");
        list.add("fifth");
        list.previous();
        list.previous();
        assertEquals(list.removeCurrent(), "second");
        list.previous();
        assertEquals(list.removeCurrent(), "third");
        list.previous();
        assertEquals(list.removeCurrent(), "fourth");
        list.previous();
        assertEquals(list.removeCurrent(), "fifth");
        list.previous();
        assertEquals(list.removeCurrent(), "first");
    }


    /**
     * Tests the clear() method
     */
    public void testClear()
    {
        list.add("first");
        list.add("second");
        list.add("third");
        list.add("fourth");
        list.add("fifth");
        list.clear();
        assertEquals(list.size(), 0);
    }


    /**
     * Tests the toString() method when empty
     */
    public void testToStringEmpty()
    {
        assertEquals(list.toString(), "[]");
    }


    /**
     * Tests the toString() method with one entry
     */
    public void testToStringOneEntry()
    {
        list.add("hi");
        assertEquals(list.toString(), "[hi]");
    }


    /**
     * Tests the toString() method
     */
    public void testToString1()
    {
        list.add("first");
        list.add("second");
        list.add("third");
        assertEquals(list.toString(), "[third, second, first]");
    }


    /**
     * Tests the toString() method
     */
    public void testToString2()
    {
        list.add("first");
        list.add("second");
        list.add("third");
        list.next();
        assertEquals(list.toString(), "[second, first, third]");
    }


    /**
     * Tests the toString() method
     */
    public void testToString3()
    {
        list.add("first");
        list.add("second");
        list.add("third");
        list.previous();
        assertEquals(list.toString(), "[first, third, second]");
    }


    /**
     * Tests the toString() method
     */
    public void testToString4()
    {
        list.add("first");
        list.add("second");
        list.add("third");
        list.add("fourth");
        list.add("fifth");
        assertEquals(list.toString(), "[fifth, fourth, third, second, first]");
    }


    /**
     * Tests the Iterator
     */
    public void testIterator()
    {
        list.add("first");
        list.add("second");
        list.add("third");
        list.add("fourth");
        list.add("fifth");
        Iterator<String> iter = list.iterator();
        assertEquals(iter.next(), "fifth");
        assertEquals(iter.next(), "fourth");
        assertEquals(iter.next(), "third");
        assertEquals(iter.next(), "second");
        assertEquals(iter.next(), "first");
        Exception thrown = null;
        try
        {
            iter.next();
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertTrue(thrown instanceof NoSuchElementException);
        assertEquals(
            "The Iterator has returned to the first item.",
            thrown.getMessage());
    }


    /**
     * Tests the Iterator's removeCurrent() method
     */
    public void testIteratorRemove()
    {
        list.add("first");
        Iterator<String> iter = list.iterator();
        Exception thrown = null;
        try
        {
            iter.remove();
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertTrue(thrown instanceof UnsupportedOperationException);
        assertEquals("remove() is not supported.", thrown.getMessage());
    }
}
