package cs2114.restaurant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

// -------------------------------------------------------------------------
/**
 * A circular, doubly-linked list.
 * 
 * @param <E>
 *            the type of element stored in the list
 * @author Benjamin Robohn (brobohn)
 * @version 2013.10.30
 */
public class CircularLinkedList<E>
    implements CircularList<E>
{
    // ~ Fields ................................................................

    private List<Node<E>> list;
    private int           current = -1;
    private Node<E>       curr;
    private Node<E>       prev;
    private Node<E>       next;


    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    // ----------------------------------------------------------
    /**
     * Creates a new CircularLinkedList object.
     */
    public CircularLinkedList()
    {
        list = new ArrayList<Node<E>>(0);
    }


    // ~ Public methods ........................................................

    // ----------------------------------------------------------
    /**
     * Moves to the next node.
     */
    @Override
    public void next()
    {
        if (size() != 0)
        {
            current++;
            if (current == size())
            {
                current = 0;
            }

            int prevIndex = current - 1;
            int nextIndex = current + 1;
            if (nextIndex == size())
            {
                nextIndex = 0;
            }
            if (prevIndex < 0)
            {
                prevIndex = size() - 1;
            }

            next = list.get(nextIndex);
            curr = list.get(current);
            prev = list.get(prevIndex);
        }
    }


    // ----------------------------------------------------------
    /**
     * Moves to the previous node
     */
    @Override
    public void previous()
    {
        if (size() != 0)
        {
            current--;
            if (current < 0)
            {
                current = size() - 1;
            }

            int prevIndex = current - 1;
            int nextIndex = current + 1;
            if (nextIndex >= size())
            {
                nextIndex = 0;
            }
            if (prevIndex < 0)
            {
                prevIndex = size() - 1;
            }

            next = list.get(nextIndex);
            curr = list.get(current);
            prev = list.get(prevIndex);
        }
    }


    // ----------------------------------------------------------
    /**
     * Returns the data from the current node.
     * 
     * @return the data
     */
    @Override
    public E getCurrent()
    {
        if (size() == 0)
        {
            throw new NoSuchElementException("The list is currently empty.");
        }
        return curr.data();
    }


    // ----------------------------------------------------------
    /**
     * Returns the number of nodes in the list.
     * 
     * @return the number of nodes
     */
    @Override
    public int size()
    {
        return list.size();
    }


    // ----------------------------------------------------------
    /**
     * Adds a node in front of the current node.
     * 
     * @param item
     *            the item to be added
     */
    @Override
    public void add(E item)
    {
        if (size() == 0)
        {
            list.add(new Node<E>(item));
            curr = list.get(0);
            prev = list.get(0);
            next = list.get(0);
            current = 0;
        }
        else if (size() == 1)
        {
            list.add(0, new Node<E>(item));
            curr = list.get(0);
            prev = list.get(1);
            next = list.get(1);
            prev.join(curr);
            curr.join(next);
        }
        else if (size() == 2)
        {
            int prevIndex = current - 1;
            int nextIndex = current + 1;
            if (prevIndex < 0)
            {
                prevIndex = size();
            }
            list.add(current, new Node<E>(item));
            prev.split();
            curr.split();
            curr = list.get(current);
            prev = list.get(prevIndex);
            next = list.get(nextIndex);
            prev.join(curr);
            curr.join(next);
            next.join(prev);
        }
        else
        {
            int prevIndex = current - 1;
            int nextIndex = current + 1;
            if (prevIndex == -1)
            {
                prevIndex = size();
            }
            list.add(current, new Node<E>(item));
            prev.split();
            curr = list.get(current);
            prev = list.get(prevIndex);
            next = list.get(nextIndex);
            prev.join(curr);
            curr.join(next);
        }
    }


    // ----------------------------------------------------------
    /**
     * Removes the current node and returns the data stored in it.
     * 
     * @return the data removed
     */
    @Override
    public E removeCurrent()
    {
        if (size() == 0)
        {
            throw new NoSuchElementException("The list is currently empty.");
        }
        else if (size() == 1)
        {
            Node<E> toReturn = list.get(0);
            list.remove(0);
            current = -1;
            return toReturn.data();
        }
        else if (size() == 2)
        {
            Node<E> toReturn = list.get(current);
            curr.split();
            prev.split();
            next.split();
            list.remove(current);
            next = list.get(0);
            curr = list.get(0);
            prev = list.get(0);
            return toReturn.data();
        }
        else
        {
            Node<E> toReturn = new Node<E>(getCurrent());
            prev.split();
            curr.split();
            prev.join(next);
            int removeIndex = current;
            if (current == size() - 1)
            {
                current = 0;
            }
            int prevIndex = current - 1;
            int nextIndex = current + 1;
            if (prevIndex == -1)
            {
                prevIndex = size() - 2;
            }
            if (nextIndex == size() - 1)
            {
                nextIndex = 0;
            }
            list.remove(removeIndex);
            next = list.get(nextIndex);
            curr = list.get(current);
            prev = list.get(prevIndex);
            return toReturn.data();
        }
    }


    // ----------------------------------------------------------
    /**
     * Clears the list of all entries
     */
    @Override
    public void clear()
    {
        while (size() > 0)
        {
            removeCurrent();
        }
    }


    // ----------------------------------------------------------
    /**
     * Returns a human-readable String representation of the data.
     * 
     * @return the string of data
     */
    @Override
    public String toString()
    {
        if (size() == 0)
        {
            return "[]";
        }
        String str = "[";
        Iterator<E> iter = iterator();
        for (int i = 0; i < size() - 1; i++)
        {
            str += iter.next() + ", ";
        }
        str += iter.next() + "]";

        return str;
    }


    // ----------------------------------------------------------
    /**
     * Creates a new CircularLinkedListIterator
     * 
     * @return the iterator
     */
    @Override
    public Iterator<E> iterator()
    {
        return new CircularLinkedListIterator();
    }


    // ~ Inner classes .........................................................

    // ----------------------------------------------------------
    /**
     * An Iterator for the Circularly Linked List
     * 
     * @author Ben Robohn (brobohn)
     * @version 2013.10.30
     */
    private class CircularLinkedListIterator
        implements Iterator<E>
    {
        // ~ Fields ............................................................

        private int count;
        private int index;


        // ~ Constructors ......................................................

        // ----------------------------------------------------------
        /**
         * Creates a new CircularLinkedListIterator object
         */
        public CircularLinkedListIterator()
        {
            count = 0;
            index = current;
        }


        // ~ Public methods ....................................................

        // ----------------------------------------------------------
        /**
         * Returns false once the iterator has gone through every node
         * 
         * @return false if the iterator is done
         */
        @Override
        public boolean hasNext()
        {
            return (count < size());
        }


        // ----------------------------------------------------------
        /**
         * Gives the next item in the list
         * 
         * @return the next item
         */
        @Override
        public E next()
        {
            E item;
            if (hasNext())
            {
                item = list.get(index).data();
                count++;
            }
            else
            {
                throw new NoSuchElementException(
                    "The Iterator has returned to the first item.");
            }

            index++;
            if (index == size())
            {
                index = 0;
            }
            return item;
        }


        // ----------------------------------------------------------
        /**
         * Simply throws an exception.
         */
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException(
                "remove() is not supported.");
        }
    }
}
