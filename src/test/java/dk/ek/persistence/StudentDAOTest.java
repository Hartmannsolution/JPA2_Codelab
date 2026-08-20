package dk.ek.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StudentDAOTest {
    private static EntityManagerFactory emf;
    private static IDAO<Student> studentDAO;
    private Student s1, s2, s3;

    @BeforeAll
    static void beforeAll(){
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        studentDAO = new StudentDAO(emf);
    }

    @BeforeEach
    void setUp() {
        s1 = new Student("Holger", "ho@mail.com");
        s2 = new Student("Henriette", "he@mail.com");
        s3 = new Student("Hassan", "ha@mail.com");
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Student s").executeUpdate();
            em.persist(s1);
            em.persist(s2);
            em.persist(s3);
            em.getTransaction().commit();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Create student has ID after creation")
    void create() {
        Student student = studentDAO.create(new Student());
        assertTrue(student.getId()!=null);
    }

    @Test
    @DisplayName("Test setup")
    void setupTest(){
        Set<Student> students = studentDAO.get();
        System.out.println("Student SIZE: "+students.size());
        assertTrue(students.size()==3);
    }
}