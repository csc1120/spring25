/*
 * Course: CSC1120
 * Week 1 - OO Review
 *
 */
package week1;

/**
 * A simple card game
 */
public class CardGame extends Game {
    private final int numCards;

    /**
     * Constructor that sets the name and number of cards
     * @param name the name of the game
     * @param numCards the number of cards in the game
     */
    public CardGame(String name, int numCards) {
        super(name);
        this.numCards = numCards;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nNumber of Cards: " + numCards;
    }
}
