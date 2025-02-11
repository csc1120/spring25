/*
 * Course: CSC1120
 * Streams Examples
 */
package week4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Simple Streams Examples
 */
public class Streams {
    public static void main(String[] args) {
        DoMath subtract = (a, b) -> a - b;
        DoMath multiply = (a, b) -> a * b;
        math(2, 3, subtract);
        math(3, 4, multiply);
        math(2, 3, (a, b) -> a + b + b * a);
        String[] words = {"my", "class", "seemed", "to", "like", "animals", "so", "cat", "cat",
                "cat", "dog", "cat", "duck", "llama", "and,", "NetFlix", "for", "some", "reason"};
        List<String> list = new ArrayList<>(Arrays.asList(words));
        long evens = list.stream().map(String::length).filter(i -> i % 2 == 0).count();
        System.out.println(evens);
        List<String> noE = list.parallelStream()
                .map(s -> s.replace('e', '*'))
                .collect(Collectors.toList());
        List<String> noDups = list.stream().distinct().toList();
        System.out.println(noE);
        System.out.println(noDups);

        // Parallel Example
        Consumer<String> prettyDisplay = word -> System.out.println(word + ": " + word.length());

        List<String> wordss = Arrays.asList("Gravity", "isn't", "just", "a", "good",
                "idea", "it's", "the", "law");
        wordss.parallelStream().forEach(prettyDisplay);
    }







    @FunctionalInterface
    private interface DoMath {
        int apply(int a, int b);
    }

    private static void math(int a, int b, DoMath dm) {
        System.out.println(dm.apply(a, b));
    }
}
