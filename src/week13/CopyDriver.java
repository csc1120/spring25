package week13;

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
        a.setLast("Gastro");
        System.out.println(a);
        System.out.println(b);
    }
}
