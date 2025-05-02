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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

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

    @Test
    void testGetValidIndex() {
        list.add("one");
        list.add("two");
        list.add("three");
        Assertions.assertEquals("one", list.get(0), "Element at index 0 should be 'one'");
        Assertions.assertEquals("two", list.get(1), "Element at index 1 should be 'two'");
        Assertions.assertEquals("three", list.get(2), "Element at index 2 should be 'three'");
    }

    @Test
    void testGetInvalidIndex() {
        list.add("one");
        list.add("two");
        list.add("three");
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1),
                "Getting a negative index should throw IndexOutOfBoundsException");

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(3),
                "Getting an index equal to size should throw IndexOutOfBoundsException");
    }

    @Test
    void testSetValidIndex() {
        list.add("one");
        list.add("two");
        list.add("three");
        String old = list.set(1, "TWO");
        Assertions.assertEquals("two", old, "set should return the old value at index");
        Assertions.assertEquals("TWO", list.get(1), "Element at index 1 should now be 'TWO'");
    }

    @Test
    void testSetInvalidIndex() {
        list.add("one");
        list.add("two");
        list.add("three");
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "negative"),
                "Setting a negative index should throw IndexOutOfBoundsException");

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.set(3, "outOfBounds"),
                "Setting an index equal to size should throw IndexOutOfBoundsException");
    }

    @Test
    void testAddAtIndexBeginning() {
        list.add("one");
        list.add("two");
        list.add("three");
        list.addFirst("zero");
        Assertions.assertEquals(4, list.size(),
                "List size should increase after add at index");
        Assertions.assertEquals("zero", list.get(0),
                "Element at index 0 should be 'zero'");
        Assertions.assertEquals("one", list.get(1),
                "Original first element should shift to index 1");
    }

    @Test
    void testAddAtIndexMiddle() {
        list.add("one");
        list.add("two");
        list.add("three");
        list.add(1, "one-and-a-half");
        Assertions.assertEquals(4, list.size(),
                "List size should increase after add at middle index");
        Assertions.assertEquals("one-and-a-half", list.get(1),
                "Element at index 1 should be the newly added item");
        Assertions.assertEquals("two", list.get(2),
                "Original element at index 1 should shift to index 2");
    }

    @Test
    void testAddAtIndexEnd() {
        list.add("one");
        list.add("two");
        list.add("three");
        list.add(list.size(), "four");
        Assertions.assertEquals(4, list.size(),
                "List size should increase after add at end");
        Assertions.assertEquals("four", list.get(3),
                "Newly added element should be at the end");
    }

    @Test
    void testAddAtInvalidIndex() {
        list.add("one");
        list.add("two");
        list.add("three");
        Assertions.assertThrows(IndexOutOfBoundsException.class,
                () -> list.add(-1, "negative"),
                "Adding at negative index should throw IndexOutOfBoundsException");

        Assertions.assertThrows(IndexOutOfBoundsException.class,
                () -> list.add(list.size() + 1, "too far"),
                "Adding beyond size should throw IndexOutOfBoundsException");
    }

    @Test
    void testRemoveAtIndexBeginning() {
        list.add("one");
        list.add("two");
        list.add("three");
        String removed = list.removeFirst();
        Assertions.assertEquals("one", removed,
                "Removed element should be 'one'");
        Assertions.assertEquals(2, list.size(),
                "List size should decrease after removal");
        Assertions.assertEquals("two", list.getFirst(),
                "Remaining elements should shift left after removal");
    }

    @Test
    void testRemoveAtIndexMiddle() {
        list.add("one");
        list.add("two");
        list.add("three");
        String removed = list.remove(1);
        Assertions.assertEquals("two", removed,
                "Removed element should be 'two'");
        Assertions.assertEquals(2, list.size(),
                "List size should decrease after removal");
        Assertions.assertEquals("three", list.get(1),
                "Elements after removed index should shift left");
    }

    @Test
    void testRemoveAtIndexEnd() {
        list.add("one");
        list.add("two");
        list.add("three");
        String removed = list.removeLast();
        Assertions.assertEquals("three", removed, "Removed element should be 'three'");
        Assertions.assertEquals(2, list.size(), "List size should decrease after removal");
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void testRemoveAtInvalidIndex() {
        list.add("one");
        list.add("two");
        list.add("three");
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1),
                "Removing at negative index should throw IndexOutOfBoundsException");

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.remove(list.size()),
                "Removing at size index should throw IndexOutOfBoundsException");
    }

    @Test
    void testIndexOfExistingElements() {
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        Assertions.assertEquals(0, list.indexOf("apple"), "Index of 'apple' should be 0");
        Assertions.assertEquals(1, list.indexOf("banana"), "Index of first 'banana' should be 1");
        Assertions.assertEquals(2, list.indexOf("cherry"), "Index of 'cherry' should be 2");
    }

    @Test
    void testIndexOfNonExistingElement() {
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        Assertions.assertEquals(-1, list.indexOf("durian"),
                "Index of non-existing element should be -1");
    }

    @Test
    void testLastIndexOfExistingElements() {
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        list.add("banana");
        Assertions.assertEquals(0, list.lastIndexOf("apple"),
                "Last index of 'apple' should be 0");
        Assertions.assertEquals(3, list.lastIndexOf("banana"),
                "Last index of 'banana' should be 3");
        Assertions.assertEquals(2, list.lastIndexOf("cherry"),
                "Last index of 'cherry' should be 2");
    }

    @Test
    void testLastIndexOfNonExistingElement() {
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        Assertions.assertEquals(-1, list.lastIndexOf("durian"),
                "Last index of non-existing element should be -1");
    }

    @Test
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    void testIndexOfAndLastIndexOfOnEmptyList() {
        SJArrayList<String> emptyList = new SJArrayList<>();
        Assertions.assertEquals(-1, emptyList.indexOf("apple"),
                "Index of any element in empty list should be -1");
        Assertions.assertEquals(-1, emptyList.lastIndexOf("apple"),
                "Last index of any element in empty list should be -1");
    }

    @Test
    void testIteratorGoesOverAllElements() {
        list.add("one");
        list.add("two");
        list.add("three");
        Iterator<String> iterator = list.iterator();
        Assertions.assertTrue(iterator.hasNext(),
                "Iterator should initially have elements");
        StringBuilder result = new StringBuilder();
        while (iterator.hasNext()) {
            result.append(iterator.next()).append(" ");
        }
        Assertions.assertEquals("one two three ", result.toString(),
                "Iterator should return elements in insertion order");
    }

    @Test
    void testIteratorNoSuchElementException() {
        list.add("one");
        list.add("two");
        list.add("three");
        Iterator<String> iterator = list.iterator();
        iterator.next();
        iterator.next();
        iterator.next();
        Assertions.assertFalse(iterator.hasNext(),
                "Iterator should be exhausted");
        Assertions.assertThrows(NoSuchElementException.class, iterator::next,
                "Calling next() after elements are exhausted should throw NoSuchElementException");
    }

    @Test
    void testIteratorRemoveWithoutNextThrows() {
        list.add("one");
        list.add("two");
        list.add("three");
        Iterator<String> iterator = list.iterator();
        Assertions.assertThrows(IllegalStateException.class, iterator::remove,
                "Calling remove() before next() should throw IllegalStateException");
    }

    @Test
    void testIteratorRemoveAfterNext() {
        list.add("one");
        list.add("two");
        list.add("three");
        Iterator<String> iterator = list.iterator();
        iterator.next();
        iterator.remove();
        Assertions.assertEquals(2, list.size(), "List size should decrease after remove");
        Assertions.assertEquals("two", list.getFirst(), "First element should now be 'two'");
        iterator.next();
        iterator.remove();
        Assertions.assertEquals(1, list.size(), "List size should decrease again after remove");
        Assertions.assertEquals("three", list.getFirst(), "First element should now be 'three'");
    }

    @Test
    void testIteratorRemoveTwiceWithoutNextThrows() {
        list.add("one");
        list.add("two");
        list.add("three");
        Iterator<String> iterator = list.iterator();
        iterator.next();
        iterator.remove();
        Assertions.assertThrows(IllegalStateException.class, iterator::remove,
                "Calling remove() twice in a row without next() " +
                        "should throw IllegalStateException");
    }

    @Test
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    void testIteratorOnEmptyList() {
        list.add("one");
        list.add("two");
        list.add("three");
        SJArrayList<String> emptyList = new SJArrayList<>();
        Iterator<String> iterator = emptyList.iterator();
        Assertions.assertFalse(iterator.hasNext(), "Empty list iterator should have no elements");
        Assertions.assertThrows(NoSuchElementException.class, iterator::next,
                "Calling next() on empty iterator should throw NoSuchElementException");
    }

    @Test
    void testForwardAndBackwardTraversal() {
        SJArrayList<String> list = new SJArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");

        ListIterator<String> iterator = list.listIterator();

        Assertions.assertTrue(iterator.hasNext(), "Should have next at start");
        Assertions.assertEquals("one", iterator.next());
        Assertions.assertEquals("two", iterator.next());
        Assertions.assertEquals("three", iterator.next());

        Assertions.assertFalse(iterator.hasNext(), "Should not have next at end");

        Assertions.assertTrue(iterator.hasPrevious(), "Should have previous at end");
        Assertions.assertEquals("three", iterator.previous());
        Assertions.assertEquals("two", iterator.previous());
        Assertions.assertEquals("one", iterator.previous());

        Assertions.assertFalse(iterator.hasPrevious(), "Should not have previous at start");
    }

    @Test
    void testNextIndexAndPreviousIndex() {
        SJArrayList<String> list = new SJArrayList<>();
        list.add("A");
        list.add("B");

        ListIterator<String> iterator = list.listIterator();

        Assertions.assertEquals(0, iterator.nextIndex());
        Assertions.assertEquals(-1, iterator.previousIndex());

        iterator.next();
        Assertions.assertEquals(1, iterator.nextIndex());
        Assertions.assertEquals(0, iterator.previousIndex());

        iterator.next();
        Assertions.assertEquals(2, iterator.nextIndex());
        Assertions.assertEquals(1, iterator.previousIndex());
    }

    @Test
    void testAddInMiddle() {
        SJArrayList<String> list = new SJArrayList<>();
        list.add("X");
        list.add("Y");

        ListIterator<String> iterator = list.listIterator(1);
        iterator.add("Z");

        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals("X", list.get(0));
        Assertions.assertEquals("Z", list.get(1));
        Assertions.assertEquals("Y", list.get(2));
    }

    @Test
    void testSetAfterNext() {
        SJArrayList<String> list = new SJArrayList<>();
        list.add("alpha");
        list.add("beta");

        ListIterator<String> iterator = list.listIterator();
        iterator.next();
        iterator.set("ALPHA");

        Assertions.assertEquals("ALPHA", list.getFirst());
    }

    @Test
    void testSetWithoutNextOrPreviousThrows() {
        SJArrayList<String> list = new SJArrayList<>();
        list.add("item");

        ListIterator<String> iterator = list.listIterator();

        Assertions.assertThrows(IllegalStateException.class, () -> iterator.set("NEW"),
                "Calling set() without next() or previous() should throw IllegalStateException");
    }

    @Test
    void testRemoveAfterNext() {
        SJArrayList<String> list = new SJArrayList<>();
        list.add("1");
        list.add("2");

        ListIterator<String> iterator = list.listIterator();
        iterator.next();
        iterator.remove();

        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals("2", list.getFirst());
    }

    @Test
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    void testRemoveWithoutNextOrPreviousThrows() {
        SJArrayList<String> list = new SJArrayList<>();
        list.add("item");

        ListIterator<String> iterator = list.listIterator();

        Assertions.assertThrows(IllegalStateException.class, iterator::remove,
                "Calling remove() before next() or previous() should throw IllegalStateException");
    }

    @Test
    void testNextOnEmptyListThrows() {
        SJArrayList<String> list = new SJArrayList<>();

        ListIterator<String> iterator = list.listIterator();

        Assertions.assertThrows(NoSuchElementException.class, iterator::next,
                "Calling next() on empty list should throw NoSuchElementException");
    }

    @Test
    void testPreviousOnEmptyListThrows() {
        SJArrayList<String> list = new SJArrayList<>();

        ListIterator<String> iterator = list.listIterator();

        Assertions.assertThrows(NoSuchElementException.class, iterator::previous,
                "Calling previous() on empty list should throw NoSuchElementException");
    }

    @Test
    void testSubListSizeAndContent() {
        SJArrayList<String> list = new SJArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");

        List<String> sub = list.subList(1, 3);  // should cover B, C

        Assertions.assertEquals(2, sub.size(), "Sublist should have size 2");
        Assertions.assertEquals("B", sub.get(0), "First element should be B");
        Assertions.assertEquals("C", sub.get(1), "Second element should be C");
    }

    @Test
    void testSubListModificationAffectsParent() {
        SJArrayList<String> list = new SJArrayList<>();
        list.add("X");
        list.add("Y");
        list.add("Z");

        List<String> sub = list.subList(1, 3);
        sub.set(0, "Y2");

        Assertions.assertEquals("Y2", list.get(1),
                "Parent list should reflect sublist modification");
    }

    @Test
    void testSubListAddAndRemove() {
        SJArrayList<String> list = new SJArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        List<String> sub = list.subList(1, 3);  // 2, 3
        sub.add("4");

        Assertions.assertEquals(4, list.size(),
                "Parent list size should increase after sublist add");
        Assertions.assertEquals("4", list.get(3),
                "New element should be added to parent list");

        sub.remove("2");

        Assertions.assertEquals(3, list.size(),
                "Parent list size should decrease after sublist remove");
        Assertions.assertFalse(list.contains("2"),
                "Removed element should be gone from parent list");
    }

    @Test
    void testSubListClear() {
        SJArrayList<String> list = new SJArrayList<>();
        list.add("alpha");
        list.add("beta");
        list.add("gamma");

        List<String> sub = list.subList(1, 3);
        sub.clear();

        Assertions.assertEquals(1, list.size(),
                "Parent list size should shrink after sublist clear");
        Assertions.assertEquals("alpha", list.getFirst(),
                "Remaining element should be alpha");
    }

    @Test
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    void testSubListIndexBounds() {
        SJArrayList<String> list = new SJArrayList<>();
        list.add("A");
        list.add("B");

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.subList(-1, 1),
                "Negative fromIndex should throw IndexOutOfBoundsException");
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.subList(0, 3),
                "toIndex beyond size should throw IndexOutOfBoundsException");
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.subList(2, 1),
                "fromIndex greater than toIndex should throw IndexOutOfBoundsException");
    }

    @Test
    void testSubListIterator() {
        SJArrayList<String> list = new SJArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");

        List<String> sub = list.subList(1, 3);  // two, three
        var iterator = sub.iterator();

        Assertions.assertTrue(iterator.hasNext(),
                "Iterator should have next element");
        Assertions.assertEquals("two", iterator.next());
        Assertions.assertTrue(iterator.hasNext(),
                "Iterator should still have next");
        Assertions.assertEquals("three", iterator.next());
        Assertions.assertFalse(iterator.hasNext(),
                "Iterator should be exhausted after two elements");
    }
}
