/*
 * Course: CSC-1120
 * Practice Exam Q6
 */
package week10;

import week7a.SJQueue;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Reads in the text file sentences.txt.
 * Adds each sentence to a queue
 * Gets each sentence from the queue, counts the number of words, reports, and
 * puts the sentence back in the queue
 *
 */
public class PracticeExam6 {
    public static void main(String[] args) {
        try(Scanner in = new Scanner(Paths.get("data", "sentences.txt"))) {
            SJQueue<String> q = new SJQueue<>();
            int line = 0;
            while(in.hasNextLine()) {
                q.offer(in.nextLine());
                ++line;
            }
            System.out.println("""
                    Sample Output
                    =============""");
            for(int i = 0; i < line; ++i) {
                String sentence = q.poll();
                int count = countWords(sentence);
                System.out.println("Line " + (i + 1) + ": " + count
                        + (count > 1 ? " words" : " word"));
                q.offer(sentence);
            }
        } catch(IOException e) {
            System.out.println("Could not load file.");
        }
    }

    private static int countWords(String s) {
        int count = 0;
        try(Scanner in = new Scanner(s)) {
            while(in.hasNext()) {
                in.next();
                ++count;
            }
        }
        return count;
    }
}
