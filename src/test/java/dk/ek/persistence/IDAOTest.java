package dk.ek.persistence;

import dk.ek.utils.Populator;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class IDAOTest {
    private static EntityManagerFactory emf;
    private static IDAO<Student> studentDAO;
    private static IRetrieveCourseData retrieveCourseDataDAO;
    private static IDAO<Course> courseDAO;
    private static Populator populator;

    @BeforeAll
    static void setUp() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        studentDAO = new StudentDAO(emf);
        courseDAO = new CourseDAO(emf);
        retrieveCourseDataDAO = (IRetrieveCourseData) courseDAO;
        populator = new Populator(emf);
    }

    @BeforeEach
    void init() {
        populator.populate();
    }

    @AfterAll
    static void tearDown() {
        HibernateConfig.shutdownTestEmf();
    }


    @Test
    @DisplayName("Test if we can get all students by a course")
    void getStudentsByCourseTest() {
        Course firstCourse = courseDAO.get().stream().findFirst().get();
        System.out.println("first course: "+firstCourse);
        Set<Student> students = retrieveCourseDataDAO.getByCourseId(firstCourse.getId());
        students.forEach(System.out::println);
        assertFalse(students.isEmpty());
    }

    @Test
    @DisplayName("Test if we can get all courses by a student")
    void getCoursesByStudentTest() {
        Student firstStudent = studentDAO.get().stream().findFirst().get();
        System.out.println("first student: "+firstStudent);
        Set<Course> courses = retrieveCourseDataDAO.getCoursesByStudent(firstStudent.getId());
        courses.forEach(System.out::println);
        assertFalse(courses.isEmpty());
    }
}
