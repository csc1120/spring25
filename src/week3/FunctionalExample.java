package week3;

public class FunctionalExample {
    public static void main(String[] args) {
        report(1, 2, (a, b) -> a + b);
        report(2, 3, (a, b) -> a - b);
        report(3, 0, (a, _) -> a * a);
        report(2, 4, (a, b) -> Math.pow(a, b));
    }

    private static void report(double a, double b, DoMath math) {
        System.out.println(math.apply(a, b));
    }

    private interface DoMath {
        double apply(double a, double b);
    }
}
