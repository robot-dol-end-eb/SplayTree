import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Iterator;

import static org.junit.Assert.*;

class SplayTreeTest {

    @Test
    void size() {
        SplayTree<Integer> a = new SplayTree<>();
        a.add(1);
        a.add(2);
        a.add(10);
        a.add(111);
        assertEquals(4,a.size());
    }

    @Test
    void isEmpty() {
        SplayTree<Integer> a = new SplayTree<>();
        a.add(1);
        assertEquals(a.isEmpty(),false);
    }

    @Test
    void contains() {
        SplayTree<Integer> a = new SplayTree<>();
        a.add(1);
        assertEquals(a.contains(1),true);
        a.add(2);
        a.add(3);
        assertEquals(a.contains(2),true);
        assertEquals(a.contains(4),false);
    }

    @Test
    public void next() {
        SplayTree<Integer> a = new SplayTree<>();
        a.add(1);
        a.add(2);
        a.add(10);
        a.add(111);
        Iterator<Integer> iterator = a.iterator();
        iterator.next();
        int b = iterator.next();
        assertEquals(b, 2);
        b = iterator.next();
        assertEquals(b, 10);
    }

    @Test
    public void hasNext() {
        SplayTree<Integer> a = new SplayTree<>();
        a.add(1);
        a.add(2);
        a.add(10);
        a.add(111);
        Iterator<Integer> iterator = a.iterator();
        iterator.next();
        assertTrue(iterator.hasNext());
        iterator.next();
        iterator.next();
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void removeIterator() {
        SplayTree<Integer> a = new SplayTree<>();
        a.add(1);
        a.add(2);
        a.add(10);
        a.add(111);
        assertEquals(a.size(),4);
        Iterator<Integer> iterator = a.iterator();
        iterator.next();
        iterator.next();
        iterator.remove();
        assertEquals(a.size(),3);
        assertEquals(a.contains(2),false);
        assertEquals(a.contains(111),true);
    }

    @Test
    void toArray() {
        SplayTree<Integer> a = new SplayTree<>();
        a.add(1);
        a.add(2);
        a.add(10);
        a.add(111);
        assertEquals(a.toArray()[0],1);
        assertEquals(a.toArray()[1],2);
        Integer[] b = a.toArray(new Integer[4]);
        assertEquals(b.length,a.size());
    }


    @Test
    void add() {
        SplayTree<Integer> a = new SplayTree<>();
        a.add(1);
        a.add(23);
        a.add(2);
        a.add(111);
        assertEquals(4,a.size());
    }

    @Test
    void remove() {
        SplayTree<Integer> a = new SplayTree<>();
        a.add(1);
        a.add(2);
        a.add(10);
        a.add(111);
        assertEquals(4,a.size());
        a.remove(1);
        assertEquals(3,a.size());
        a.remove(11);
        assertEquals(3,a.size());
    }

    @Test
    void containsAll() {
        SplayTree<Integer> a = new SplayTree<>();
        a.add(1);
        a.add(2);
        a.add(10);
        a.add(111);
        HashSet<Integer> b = new HashSet<>();
        b.add(1);
        b.add(2);
        b.add(10);
        b.add(111);
        assertEquals(a.containsAll(b),true);
    }

    @Test
    void addAll() {
        SplayTree<Integer> a = new SplayTree<>();
        HashSet<Integer> b = new HashSet<>();
        b.add(1);
        b.add(2);
        b.add(10);
        b.add(111);
        a.addAll(b);
        assertEquals(a.containsAll(b),true);
    }

    @Test
    void retainAll() {
        SplayTree<Integer> a = new SplayTree<>();
        a.add(1);
        a.add(2);
        a.add(10);
        SplayTree<Integer> b = new SplayTree<>();
        b.add(1);
        b.add(2);
        a.retainAll(b);
        assertEquals(2, a.size());
        assertEquals(a.contains(1), true);
        assertEquals(a.contains(10), false);
    }

    @Test
    void removeAll() {
        SplayTree<Integer> a = new SplayTree<>();
        a.add(1);
        a.add(2);
        a.add(10);
        a.add(111);
        HashSet<Integer> b = new HashSet<>();
        b.add(1);
        b.add(2);
        b.add(4);
        a.removeAll(b);
        SplayTree<Integer> c = new SplayTree<>();
        c.add(10);
        c.add(111);
        try {
            assertEquals(a.containsAll(c),true);
        }
        catch (NullPointerException e) {
            assertEquals(a.size(),2);
        }
    }

    @Test
    void clear() {
        SplayTree<Integer> a = new SplayTree<>();
        a.add(1);
        a.add(2);
        assertEquals(2, a.size());
        assertEquals(false, a.isEmpty());
        a.clear();
        assertEquals(0, a.size());
        assertEquals(true, a.isEmpty());
    }
}