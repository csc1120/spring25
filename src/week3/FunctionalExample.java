/*
 * Course: CSC1120
 * Functional Programming example
 */
package week3;

/**
 * Basic functional interface example
 */
public class FunctionalExample {
    public static void main(String[] args) {
        report(1, 2, Double::sum); // built-in functions
        report(2, 3, (a, b) -> a - b);
        report(3, 0, (a, _) -> a * a);
        report(2, 4, Math::pow); // built-in functions
    }

    private static void report(double a, double b, DoMath math) {
        System.out.println(math.apply(a, b));
    }

    @FunctionalInterface
    private interface DoMath {
        double apply(double a, double b);
    }
}
