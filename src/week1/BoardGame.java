/*
 * Course: CSC1120
 * Week 1 - OO Review
 *
 */
package week1;

/**
 * A simple board game
 */
public class BoardGame extends Game {
    private final int numMeeples;
    private final int numDice;

    /**
     * Constructor that sets the name, number of meeple and number of dice
     * @param name the name of the game
     * @param numMeeples the number of wooden bits in the game
     * @param numDice the number of dice in the game
     */
    public BoardGame(String name, int numMeeples, int numDice) {
        super(name);
        this.numMeeples = numMeeples;
        this.numDice = numDice;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nNumber of Meeples: " + numMeeples
                + "\nNumber of Dice: " + numDice;
    }
}
