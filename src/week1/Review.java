/*
 * Course: CSC-1120
 * Week 1 Review
 */
package week1;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * CSC1110 Review
 */
public class Review {
    public static void main(String[] args) {
        final int numUnoCards = 108;
        final int numPokemonCards = 200_000; // ...ish see -> https://shorturl.at/1mLfU
        final int numCatanMeeples = 97; // just the wooden bits
        ArrayList<PastTime> list = new ArrayList<>();
        list.add(new CardGame("Uno", numUnoCards));
        list.add(new CardGame("Pokemon", numPokemonCards));
        list.add(new BoardGame("Catan", numCatanMeeples, 2));
        System.out.println(list.getFirst());
        System.out.println(list.getLast());
        for(PastTime p : list) {
            try (PrintWriter pw = new PrintWriter(p.getName() + ".txt")) {
                if(p instanceof CardGame) {
                    pw.println(p);
                } else if(p instanceof BoardGame) {
                    pw.println(p);
                }
            } catch (FileNotFoundException e) {
                System.err.println("Could not create the file.");
            }
        }
    }
}
