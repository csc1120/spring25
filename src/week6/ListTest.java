/*
 * Course: CSC1120
 * List Test Examples
 *
 * Lab 5
 * You do not need to write comprehensive tests for lab, but
 * you should write at least two simple tests demonstrating
 * use of the following annotations: @Test, @BeforeEach, and @AfterEach
 */
package week6;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Example tests for the SubLinkedList class
 */
public class ListTest {
    private List<String> list;

    @BeforeEach
    void setUp() {
        list = new SubLinkedList<>();
    }

    @AfterEach
    void tearDown() {
        list = null;
    }

    // Test isEmpty()
    @Test
    void testIsEmpty() {
        Assertions.assertTrue(list.isEmpty());
        list.add("one");
        Assertions.assertFalse(list.isEmpty());
    }

    // Test size()
    @Test
    void testSize() {
        Assertions.assertEquals(0, list.size());
        list.add("one");
        Assertions.assertEquals(1, list.size());
        list.add("two");
        list.add("three");
        Assertions.assertEquals(3, list.size());
    }

    // Test add()
    @Test
    void testAdd() {
        Assertions.assertTrue(list.add("one"));
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals("one", list.getFirst());
    }

    // Test get()
    @Test
    void testGet() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.getFirst());
        list.add("one");
        list.add("two");
        list.add("three");
        Assertions.assertEquals("one", list.get(0));
        Assertions.assertEquals("two", list.get(1));
        Assertions.assertEquals("three", list.get(2));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
    }

    // Test contains()
    @Test
    void testContains() {
        list.add("One");
        list.add("Two");
        Assertions.assertTrue(list.contains("One"));
        Assertions.assertTrue(list.contains("Two"));
        Assertions.assertFalse(list.contains("Three"));
    }

    @Test
    void testRemove() {
        list.add("One");
        list.add("Two");
        list.add("Three");
        list.add("Four");
        list.add("Five");
        Assertions.assertTrue(list.remove("One"));
        Assertions.assertFalse(list.contains("One"));
        Assertions.assertTrue(list.remove("Three"));
        Assertions.assertFalse(list.contains("Three"));
        Assertions.assertTrue(list.remove("Five"));
        Assertions.assertFalse(list.contains("Five"));

    }
}
