/*
 * Course: CSC-1120
 * Clone and Object example
 */
package week13;

/**
 * Driver class for the clone/object example
 */
public class CopyDriver {
    public static void main(String[] args) {
        Student a = new Student(1, "Ashtin", "Vassar");
        a.addCourse(new Course(1120, "Data Structures"));
        Student b = (Student) a.clone();
        System.out.println(a);
        System.out.println(b);
        b.addCourse(new Course(1110, "Software Development"));
        System.out.println(a);
        System.out.println(b);
        // hashCode will only reference names, so will be the same
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        b.setLastName("Gastro");
        // different names = different hashCode
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        String s1 = "hi";
        String s2 = "hi";
        String s3 = new String("hi");
        System.out.println(s1 == s2); // same location in String pool
        System.out.println(s1 == s3); // s3 is stored on the heap
    }


}
