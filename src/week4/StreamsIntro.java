package week4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class StreamsIntro {
    public static void main(String[] args) {
        String[] w = {"Cow", "apple", "Cat", "Cat", "Cat", "Cat", "blank", "Dog", "Chicken",
                "Duck", "blue", "white", "run", "NetFlix", "black", "fridge"};
        List<String> words = new ArrayList<>(Arrays.asList(w));
        List<String> cats = words.stream()
                .distinct()
                .filter(s -> s.equals("cat"))
                .map(cat -> "orange " + cat).toList();
        System.out.println(cats);
        List<String> sorted = words.stream().map(String::toLowerCase).distinct().sorted().toList();
        List<String> unSorted = words.parallelStream().map(String::toLowerCase).distinct().toList();

//        List<Integer> list = words.stream().distinct().map(s -> s.length()).toList();
//        System.out.println(count);
//        System.out.println(list);
        System.out.println(sorted);
        System.out.println(unSorted);

        Consumer<String> prettyDisplay = word -> System.out.println(word + ": " + word.length());

        List<String> wordss = Arrays.asList("Gravity", "isn't", "just", "a", "good",
                "idea", "it's", "the", "law");
        wordss.stream().forEach(prettyDisplay);
        System.out.println();
        wordss.parallelStream().forEach(prettyDisplay);
    }
}
