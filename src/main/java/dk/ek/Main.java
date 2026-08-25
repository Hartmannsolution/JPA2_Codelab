package dk.ek;

import dk.ek.persistence.Address;
import dk.ek.persistence.HibernateConfig;
import dk.ek.persistence.Student;
import dk.ek.persistence.StudentDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Student s").executeUpdate();
            em.createQuery("DELETE FROM Address a").executeUpdate();
            em.createQuery("DELETE FROM StudentGradeCard sg").executeUpdate();
            em.getTransaction().commit();
        }
        StudentDAO studentDAO = new StudentDAO(emf);

        Student s1 = new Student("Anne", "anne@mail.dk");
        Student s2 = new Student("Bert", "bert@mail.dk");

        s1 = studentDAO.create(s1);
        s2 = studentDAO.create(s2);
        studentDAO.get().forEach(s-> System.out.println(s));
        s1.getGradeCard().addGrade(10);
        Address address = new Address("Hovedgaden", 34, 2100, "Rønne");
        address.addStudent(s1);
        studentDAO.update(s1);
        System.out.println("Student: "+s1.getName()+" now has a grade: "+s1.getGradeCard().getGrades());

        }
    }
