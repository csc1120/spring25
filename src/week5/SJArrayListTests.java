/*
 * Course: CSC-1120
 */
package week5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;
import java.util.List;

class SJArrayListTests {
    private SJArrayList<String> list;

    @BeforeEach
    void setup() {
        list = new SJArrayList<>();
    }

    @Test
    void testNoArgConstructor() {
        Assertions.assertNotNull(list, "List should be constructed");
        Assertions.assertEquals(0, list.size(), "New list should have size 0");
    }

    @Test
    void testConstructorWithInitialCapacity() {
        final int capacity = 20;
        SJArrayList<Integer> list = new SJArrayList<>(capacity);
        Assertions.assertNotNull(list, "List should be constructed with initial capacity");
        Assertions.assertEquals(0, list.size(),
                "List should have size 0 even with initial capacity set");
    }

    @Test
    void testConstructorNegativeCapacity() {
        Assertions.assertThrows(NegativeArraySizeException.class, () -> new SJArrayList<>(-1),
                "Negative initial capacity should throw NegativeArraySizeException");
    }

    @Test
    void testAddNullElement() {
        list.add(null);
        Assertions.assertEquals(1, list.size(), "List should accept null elements");
        Assertions.assertTrue(list.contains(null), "List should report containing null");
    }

    @ParameterizedTest
    @ValueSource(strings = {"apple", "banana", "cherry"})
    void testParameterizedContains(String input) {
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        Assertions.assertTrue(list.contains(input), "List should contain: " + input);
    }

    @Test
    void testLargeListSizeAndContains() {
        SJArrayList<Integer> list = new SJArrayList<>();
        final int largeSize = 10_000;
        for (int i = 0; i < largeSize; i++) {
            list.add(i);
        }
        Assertions.assertEquals(largeSize, list.size(),
                "Large list should have correct size");
        Assertions.assertTrue(list.contains(largeSize - 1),
                "List should contain last added element");
        Assertions.assertFalse(list.contains(largeSize + 1),
                "List should not contain non-added element");
    }

    @Test
    void testSizeAfterAdds() {
        list.add("apple");
        list.add("banana");
        Assertions.assertEquals(2, list.size(), "List size should reflect added elements");
    }

    @Test
    void testSizeAfterRemove() {
        list.add("apple");
        list.add("banana");
        list.remove("apple");
        Assertions.assertEquals(1, list.size(), "List size should decrease after removal");
    }

    @Test
    void testContainsExistingElement() {
        list.add("apple");
        list.add("banana");
        Assertions.assertTrue(list.contains("apple"), "List should contain 'apple'");
        Assertions.assertTrue(list.contains("banana"), "List should contain 'banana'");
    }

    @Test
    void testContainsNonExistingElement() {
        list.add("apple");
        Assertions.assertFalse(list.contains("orange"), "List should not contain 'orange'");
    }

    @Test
    void testContainsOnEmptyList() {
        Assertions.assertFalse(list.contains("hello"), "Empty list should not contain any element");
    }

    @Test
    @SuppressWarnings("ConstantValue")
    void testSizeAfterClear() {
        list.add("one");
        list.add("two");
        list.clear();
        Assertions.assertEquals(0, list.size(),
                "List size should be zero after clear");
        Assertions.assertFalse(list.contains("one"),
                "List should not contain elements after clear");
    }

    @Test
    void testToArrayNoArg() {
        list.add("alpha");
        list.add("beta");
        list.add("gamma");

        Object[] array = list.toArray();
        Assertions.assertEquals(3, array.length,
                "toArray() should return array of correct size");
        Assertions.assertArrayEquals(new Object[] {"alpha", "beta", "gamma"}, array,
                "toArray() should match list elements");
    }

    @Test
    void testToArrayWithLargerProvidedArray() {
        list.add("one");
        list.add("two");

        String[] target = new String[4];  // larger than needed
        String[] result = list.toArray(target);

        Assertions.assertSame(target, result,
                "toArray(T[]) should return same array if large enough");
        Assertions.assertEquals("one", result[0]);
        Assertions.assertEquals("two", result[1]);
        Assertions.assertNull(result[2], "Remaining positions should be null");
    }

    @Test
    void testToArrayWithSmallerProvidedArray() {
        list.add("x");
        list.add("y");
        list.add("z");

        String[] target = new String[1];  // too small, should allocate new
        String[] result = list.toArray(target);

        Assertions.assertNotSame(target, result,
                "toArray(T[]) should create new array if too small");
        Assertions.assertArrayEquals(new String[] {"x", "y", "z"}, result,
                "New array should match list elements");
    }

    @Test
    @SuppressWarnings("ConstantValue")
    void testAddSingleElement() {
        boolean added = list.add("hello");
        Assertions.assertTrue(added, "add(E) should return true");
        Assertions.assertEquals(1, list.size(), "List size should increase after add");
        Assertions.assertEquals("hello", list.getFirst(), "Element should be added to list");
    }

    @Test
    void testAddMultipleElementsAndOrder() {
        list.add("first");
        list.add("second");
        list.add("third");

        Assertions.assertEquals(3, list.size(), "List should reflect added elements");
        Assertions.assertEquals("first", list.get(0), "First element should be correct");
        Assertions.assertEquals("second", list.get(1), "Second element should be correct");
        Assertions.assertEquals("third", list.get(2), "Third element should be correct");
    }

    @Test
    void testRemoveExistingElement() {
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        boolean removed = list.remove("banana");
        Assertions.assertTrue(removed,
                "remove(Object) should return true when element is found");
        Assertions.assertEquals(2, list.size(),
                "List size should decrease after removal");
        Assertions.assertFalse(list.contains("banana"),
                "Removed element should no longer be in the list");
        Assertions.assertEquals("apple", list.get(0),
                "First element should remain");
        Assertions.assertEquals("cherry", list.get(1),
                "Second element should shift correctly after removal");
    }

    @Test
    void testRemoveNonExistingElement() {
        list.add("apple");
        list.add("banana");
        boolean removed = list.remove("orange");
        Assertions.assertFalse(removed,
                "remove(Object) should return false when element is not found");
        Assertions.assertEquals(2, list.size(),
                "List size should remain unchanged");
    }

    @Test
    void testRemoveFirstElement() {
        list.add("a");
        list.add("b");
        list.add("c");

        boolean removed = list.remove("a");

        Assertions.assertTrue(removed, "Should remove first element");
        Assertions.assertEquals(2, list.size(), "Size should decrease");
        Assertions.assertEquals("b", list.get(0), "Elements should shift left after removal");
        Assertions.assertEquals("c", list.get(1));
    }

    @Test
    void testRemoveLastElement() {
        list.add("a");
        list.add("b");
        list.add("c");

        boolean removed = list.remove("c");

        Assertions.assertTrue(removed, "Should remove last element");
        Assertions.assertEquals(2, list.size(), "Size should decrease");
        Assertions.assertEquals("a", list.get(0));
        Assertions.assertEquals("b", list.get(1));
    }

    @Test
    void testRemoveNullElement() {
        list.add(null);
        list.add("test");

        boolean removed = list.remove(null);

        Assertions.assertTrue(removed, "Should remove null element");
        Assertions.assertEquals(1, list.size(), "Size should decrease");
        Assertions.assertFalse(list.contains(null),
                "Null should no longer be in list");
        Assertions.assertEquals("test", list.getFirst(),
                "Remaining element should shift correctly");
    }

    @Test
    void testContainsAllWithAllPresent() {
        list.add("apple");
        list.add("banana");
        list.add("cherry");

        boolean result = list.containsAll(List.of("apple", "banana"));

        Assertions.assertTrue(result,
                "containsAll should return true when all elements are present");
    }

    @Test
    void testContainsAllWithSomeMissing() {
        list.add("apple");
        list.add("banana");

        boolean result = list.containsAll(List.of("apple", "orange"));

        Assertions.assertFalse(result,
                "containsAll should return false when some elements are missing");
    }

    @Test
    void testContainsAllWithEmptyCollection() {
        list.add("apple");
        list.add("banana");
        boolean result = list.containsAll(Collections.<String>emptyList());
        Assertions.assertTrue(result,
                "containsAll should return true for empty input collection");
    }

    @Test
    void testContainsAllOnEmptyList() {
        boolean result = list.containsAll(List.of("apple", "banana"));
        Assertions.assertFalse(result,
                "containsAll should return false when list is empty and input is not");
    }

    @Test
    void testContainsAllWithDuplicatesInInput() {
        list.add("a");
        list.add("b");
        list.add("c");
        boolean result = list.containsAll(List.of("b", "b", "c"));
        Assertions.assertTrue(result,
                "containsAll should handle duplicate elements in the input collection");
    }

    @Test
    void testAddAllToEnd() {
        list.add("apple");

        boolean result = list.addAll(List.of("banana", "cherry"));

        Assertions.assertTrue(result,
                "addAll(Collection) should return true when elements are added");
        Assertions.assertEquals(3, list.size(),
                "List size should increase after addAll");
        Assertions.assertEquals("apple", list.get(0));
        Assertions.assertEquals("banana", list.get(1));
        Assertions.assertEquals("cherry", list.get(2));
    }

    @Test
    void testAddAllToEndWithEmptyCollection() {
        list.add("apple");

        boolean result = list.addAll(List.of());

        Assertions.assertFalse(result,
                "addAll(Collection) should return false when input collection is empty");
        Assertions.assertEquals(1, list.size(),
                "List size should remain unchanged");
        Assertions.assertEquals("apple", list.getFirst());
    }

    @Test
    void testAddAllAtIndex() {
        list.add("apple");
        list.add("cherry");

        boolean result = list.addAll(1, List.of("banana", "blueberry"));

        Assertions.assertTrue(result,
                "addAll(int, Collection) should return true when elements are inserted");
        Assertions.assertEquals(4, list.size(),
                "List size should increase after addAll at index");
        Assertions.assertEquals("apple", list.get(0));
        Assertions.assertEquals("banana", list.get(1));
        Assertions.assertEquals("blueberry", list.get(2));
        Assertions.assertEquals("cherry", list.get(3));
    }

    @Test
    void testAddAllAtIndexWithEmptyCollection() {
        list.add("apple");
        list.add("banana");

        boolean result = list.addAll(1, List.of());

        Assertions.assertFalse(result,
                "addAll(int, Collection) should return false when input is empty");
        Assertions.assertEquals(2, list.size(),
                "List size should remain unchanged");
        Assertions.assertEquals("apple", list.get(0));
        Assertions.assertEquals("banana", list.get(1));
    }

    @Test
    void testAddAllAtInvalidIndex() {
        list.add("apple");

        Assertions.assertThrows(IndexOutOfBoundsException.class,
                () -> list.addAll(-1, List.of("banana")),
                "addAll at negative index should throw IndexOutOfBoundsException");

        Assertions.assertThrows(IndexOutOfBoundsException.class,
                () -> list.addAll(4, List.of("banana")),
                "addAll beyond size should throw IndexOutOfBoundsException");
    }

    @Test
    public void testRemoveAllWithMatchingElements() {
        list.add("apple");
        list.add("banana");
        list.add("cherry");

        boolean changed = list.removeAll(List.of("banana", "cherry"));

        Assertions.assertTrue(changed, "removeAll should return true when elements are removed");
        Assertions.assertEquals(1, list.size(), "List size should decrease after removeAll");
        Assertions.assertEquals("apple", list.getFirst(), "Remaining element should be 'apple'");
    }

    @Test
    public void testRemoveAllWithNoMatchingElements() {
        list.add("apple");
        list.add("banana");

        boolean changed = list.removeAll(List.of("cherry", "date"));

        Assertions.assertFalse(changed, "removeAll should return false when no elements match");
        Assertions.assertEquals(2, list.size(), "List size should remain unchanged");
    }

    @Test
    public void testRemoveAllWithEmptyCollection() {
        list.add("apple");

        boolean changed = list.removeAll(Collections.<String>emptyList());

        Assertions.assertFalse(changed, "removeAll should return false for empty input collection");
        Assertions.assertEquals(1, list.size(), "List size should remain unchanged");
    }

    @Test
    public void testRetainAllWithMatchingElements() {
        list.add("apple");
        list.add("banana");
        list.add("cherry");

        boolean changed = list.retainAll(List.of("banana", "cherry"));

        Assertions.assertTrue(changed,
                "retainAll should return true when elements are removed");
        Assertions.assertEquals(2, list.size(),
                "List size should reflect retained elements");
        Assertions.assertEquals("banana", list.get(0),
                "First retained element should be 'banana'");
        Assertions.assertEquals("cherry", list.get(1),
                "Second retained element should be 'cherry'");
    }

    @Test
    public void testRetainAllWithNoMatchingElements() {
        list.add("apple");
        list.add("banana");

        boolean changed = list.retainAll(List.of("cherry"));

        Assertions.assertTrue(changed,
                "retainAll should return true when all elements are removed");
        Assertions.assertEquals(0, list.size(),
                "List should be empty after retaining no matches");
    }

    @Test
    public void testRetainAllWithEmptyCollection() {
        list.add("apple");
        list.add("banana");

        boolean changed = list.retainAll(Collections.<String>emptyList());

        Assertions.assertTrue(changed,
                "retainAll should return true when clearing the list");
        Assertions.assertEquals(0, list.size(),
                "List should be empty after retaining no elements");
    }

    @Test
    public void testRetainAllWithAllElements() {
        list.add("apple");
        list.add("banana");

        boolean changed = list.retainAll(List.of("apple", "banana"));

        Assertions.assertFalse(changed,
                "retainAll should return false when no elements are removed");
        Assertions.assertEquals(2, list.size(),
                "List size should remain unchanged");
    }
}
