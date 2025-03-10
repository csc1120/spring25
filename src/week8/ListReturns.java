/*
 * Course: CSC-1120
 * Types of Lists returned from methods
 */
package week8;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Examples of List "Factory" methods (methods that return Lists)
 */
public class ListReturns {
    public static void main(String[] args) {
        String[] words = {"we", "are", "words", "hear", "us", "roar"};
        // fixed size, but not immutable
        List<String> asList = Arrays.asList(words);
        System.out.println(asList.getClass());

        // immutable
        List<String> toList = Arrays.stream(words).toList();
        System.out.println(toList.getClass());

        // ArrayList - Note IntelliJ is wrong here. toList() does NOT return the same List type
        List<String> collectors = Arrays.stream(words).collect(Collectors.toList());
        System.out.println(collectors.getClass());

        // immutable
        List<String> listOf = List.of("we", "are", "words", "hear", "us", "roar");
        System.out.println(listOf.getClass());

        // Empty
        List<String> emptyList = Collections.emptyList();
        System.out.println(emptyList.getClass());
    }
}
