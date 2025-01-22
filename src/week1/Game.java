/*
 * Course: CSC1120
 * Week 1 - OO Review
 *
 */
package week1;

/**
 * A simple Game class
 */
public class Game implements PastTime {
    protected String name;

    /**
     * Constructor that defines the name of the game
     * @param name the name of the game
     */
    public Game(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
