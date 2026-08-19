package dk.ek.utils;

import dk.ek.persistence.*;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.Map;

public class Populator {
    private final EntityManagerFactory emf;
    private final IDAO<Student> studentDAO;
    private final IDAO<Course> courseDAO;
    public Populator(EntityManagerFactory _emf){
        this.emf = _emf;
        this.studentDAO = new StudentDAO(emf);
        this.courseDAO = new CourseDAO(emf);
    }
    public void populate(){
        LocalDate start = LocalDate.of(2026, 2, 1);
        LocalDate end = start.plusMonths(6);
        Course course1 = new Course("MATH", "Mathematics course", start, end);
        Course course2 = new Course("ART", "Art course", start, end);
        Student student1 = new Student("Charlie", "char@college.com");
        Student student2 = new Student("Diana", "dian@college.com");
        Student student3 = new Student("Edward", "edw@college.com");
        Student student4 = new Student("Fiona", "fion@college.com");
        Student student5 = new Student("George", "geo@college.com");
        Student student6 = new Student("Hansi", "han@college.com");
        course1 = courseDAO.create(course1);
        course2 = courseDAO.create(course2);
        System.out.println("Created courses: " + course1.getId() + ", " + course2.getId());
        student1.assignToCourse(course1.getId());
        student2.assignToCourse(course1.getId());
        student1.assignToCourse(course2.getId());
        student2.assignToCourse(course2.getId());
        student3.assignToCourse(course1.getId());
        student4.assignToCourse(course1.getId());
        student5.assignToCourse(course1.getId());
        student6.assignToCourse(course1.getId());
        studentDAO.create(student1);
        studentDAO.create(student2);
        studentDAO.create(student3);
        studentDAO.create(student4);
        studentDAO.create(student5);
        studentDAO.create(student6);

    }

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        Populator populator = new Populator(emf);
        populator.populate();
    }
}
