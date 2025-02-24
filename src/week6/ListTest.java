package week6;

/// # Lab 5
/// You do not need to write comprehensive tests for lab, but
/// you should write at least two simple tests demonstrating
/// use of the following annotations: @Test, @BeforeEach, and @AfterEach

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import week5.SJArrayList;

public class ListTest {
    private List<String> list;

    @BeforeEach
    public void setUp() {
        list = new SubLinkedList<>();
    }

    @AfterEach
    public void tearDown() {
        list = null;
    }

    // Test isEmpty()
    @Test
    public void testIsEmpty() {
        Assertions.assertTrue(list.isEmpty());
        list.add("one");
        Assertions.assertFalse(list.isEmpty());
    }

    // Test size()
    // Test add()
    // Test get()
    @Test
    public void testGet() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        list.add("one");
        list.add("two");
        list.add("three");
        Assertions.assertEquals("one", list.get(0));
        Assertions.assertEquals("two", list.get(1));
        Assertions.assertEquals("three", list.get(2));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
    }

}
