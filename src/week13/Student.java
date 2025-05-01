/*
 * Course: CSC-1120
 * Clone and Object example
 */
package week13;

import java.util.ArrayList;
import java.util.List;

/**
 * A student class
 */
public class Student implements Cloneable {
    private final int id;
    private final String firstName;
    private String lastName;
    private List<Course> schedule;

    /**
     * Student constructor
     * @param id the student id
     * @param firstName the student's first name
     * @param lastName the student's last name
     */
    public Student(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.schedule = new ArrayList<>();
    }

    /**
     * Adds a course to the student's schedule
     * @param c the course to add
     */
    public void addCourse(Course c) {
        this.schedule.add(c);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return this.id + ": " + this.firstName + " " + this.lastName + ": " + this.schedule;
    }

    @Override
    public boolean equals(Object o) {
        // same memory location?
        if(this == o){
            return true;
        }
        // not null?
        // same type?
        if(!(o instanceof Student that)) {
            return false;
        }
        // Instance variables
        return this.id == that.id;
    }

    @Override
    public Object clone(){
        // create new Student instance in a new memory location on the heap
        Student result = new Student(this.id, this.firstName, this.lastName);
        // make a new instance variable with a new memory location
        result.schedule = new ArrayList<>();
        // copy all the elements from the list
        for(Course c : this.schedule) {
            // ensure any reference variable is also given a new memory location
            result.schedule.add(new Course(c.code(), c.name()));
        }
        return result;
    }

    @Override
    public int hashCode() {
        return (firstName + lastName).hashCode();
    }
}
