/*
 * Course: CSC-1120
 * Find Index Helper
 */
package week11;

import java.util.Scanner;

/**
 * A pretty awful tool that finds the index of a key
 */
public class FindIndex {
    public static void main() {
        Scanner in = new Scanner(System.in);
        boolean done;
        do {
            System.out.print("Enter a String to hash: ");
            String s = in.nextLine();
            System.out.print("Enter the length of the array: ");
            int length = in.nextInt();
            in.nextLine();
            int index = s.hashCode() % length;
            if (index < 0) {
                index += length;
            }
            System.out.println("Index for " + s + " is " + index);
            System.out.print("Enter 1 for another or 0 to quit: ");
            int more = in.nextInt();
            in.nextLine();
            done = more == 0;
        } while (!done);
        System.out.println("Thank goodness.");
        in.close();
    }
}
