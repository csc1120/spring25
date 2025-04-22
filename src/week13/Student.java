package week13;

import java.util.ArrayList;
import java.util.List;

public class Student implements Cloneable {
    private int id;
    private String first;
    private String last;
    private List<Course> schedule;

    public Student(int id, String first, String last) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.schedule = new ArrayList<>();
    }

    public void addCourse(Course c) {
        this.schedule.add(c);
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public String toString() {
        return id + ": " + first + " " + last + ": " + schedule;
    }

    @Override
    public boolean equals(Object o) {
        // not null
        // same type
        // instance variables
        // same memory location?
        if(this == o) {
            return true;
        }
        if(!(o instanceof Student)) {
            return false;
        }
        return this.id == ((Student) o).id;
    }

    @Override
    public Object clone() {
        // clone stuff and return a new instance
        Student result = new Student(id, first, last);
        result.schedule = new ArrayList<>();
        for(Course c : this.schedule) {
            result.schedule.add(new Course(c.code(), c.name()));
        }
        return result;
    }

    @Override
    public int hashCode() {
        return (first + last).hashCode();
    }
}
