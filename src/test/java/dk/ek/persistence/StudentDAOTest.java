package dk.ek.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StudentDAOTest {
    private static EntityManagerFactory emf;
    private static IDAO<Student> studentDAO;
    private static IRetrieveCourseData retrieveCourseData;
    private Student s1, s2, s3;
    private Course c1, c2;

    @BeforeAll
    static void beforeAll(){
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        studentDAO = new StudentDAO(emf);
        retrieveCourseData = new CourseDAO(emf);

    }

    @BeforeEach
    void setUp() {
        s1 = new Student("Holger", "ho@mail.com");
        s2 = new Student("Henriette", "he@mail.com");
        s3 = new Student("Hassan", "ha@mail.com");
        LocalDate start = LocalDate.of(2026, 2, 1);
        LocalDate end = start.plusMonths(6);
        c1 = new Course("MATH", "Mathematics course", start, end);
        c2 = new Course("ART", "Art course", start, end);

        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Student s").executeUpdate();
            em.persist(c1);
            em.persist(c2);
            em.getTransaction().commit();

            em.getTransaction().begin();
            s1.assignToCourse(c1.getId());
            s2.assignToCourse(c1.getId());
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

    @Test
    @DisplayName("Test assignment by getting students from specific course")
    void assignmentTest(){
        Set<Student> studentsInCourse1 = retrieveCourseData.getByCourseId(c1.getId());
        assertTrue(studentsInCourse1.size() == 2);

    }
    @Test
    @DisplayName("Test courses by a students")
    void assignmentTest2(){
        Set<Course> coursesByStudent2 = retrieveCourseData.getCoursesByStudent(s2.getId());
        assertTrue(coursesByStudent2.size() == 1);
    }
}