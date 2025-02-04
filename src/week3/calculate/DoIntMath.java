/*
 * Course: CSC1120
 * Functional Interface example
 */
package week3.calculate;

/**
 * A Function Interface that does integer math
 */
@FunctionalInterface
public interface DoIntMath {
    /**
     * Does the Math
     * @param a the first operand
     * @param b the second operand
     * @return the result of the math done
     */
    int doMath(int a, int b);
}
