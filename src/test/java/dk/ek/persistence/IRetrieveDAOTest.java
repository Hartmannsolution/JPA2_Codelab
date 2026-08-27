package dk.ek.persistence;

import dk.ek.utils.Populator;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class IRetrieveDAOTest {

    private static EntityManagerFactory emf;
    private static IRetrieveDAO retrieveDAO;
    private static Populator populator;
    private static Map<String, IEntity> entities;

    @BeforeAll
    static void setUp() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        retrieveDAO = new RetrieveDAO(emf);
        populator = new Populator(emf);
    }

    @BeforeEach
    void init() {

        entities = populator.populate();
    }

    @AfterAll
    static void tearDown() {
        HibernateConfig.shutdownTestEmf();
    }

    @Test
    @DisplayName("getAllStudentsInCourse(courseId) should return all students enrolled in that course")
    void getAllStudentsInCourse() {
        Course course = (Course) entities.get("course1"); // adjust key names to your Populator
        assertNotNull(course, "Populator must provide course1");

        Set<Student> studentsInCourse = retrieveDAO.getAllStudentsInCourse(course.getId());

        assertNotNull(studentsInCourse);
        assertFalse(studentsInCourse.isEmpty(), "Course should have students (based on Populator)");

        // every returned student must belong to the course
        studentsInCourse.forEach(s -> assertNotNull(s.getCourse()));
        assertTrue(studentsInCourse.stream().allMatch(s -> s.getCourse().getId().equals(course.getId())));
    }

    @Test
    @DisplayName("getAllCoursesForTeacher(teacherId) should return all courses taught by that teacher")
    void getAllCoursesForTeacher() {
        Teacher teacher = (Teacher) entities.get("teacher1"); // adjust key names
        assertNotNull(teacher, "Populator must provide teacher1");

        Set<Course> courses = retrieveDAO.getAllCoursesForTeacher(teacher.getId());

        assertNotNull(courses);
        assertFalse(courses.isEmpty(), "Teacher should have courses (based on Populator)");

        // every returned course must have that teacher
        courses.forEach(c -> assertNotNull(c.getTeacher()));
        assertTrue(courses.stream().allMatch(c -> c.getTeacher().getId().equals(teacher.getId())));
    }

    @Test
    @DisplayName("getAllStudentsForTeacher(teacherId) should return all students across that teacher's courses")
    void getAllStudentsForTeacher() {
        Teacher teacher = (Teacher) entities.get("teacher1"); // adjust key names
        assertNotNull(teacher, "Populator must provide teacher1");

        Set<Student> students = retrieveDAO.getAllStudentsForTeacher(teacher.getId());

        assertNotNull(students);
        assertFalse(students.isEmpty(), "Teacher should have students via courses (based on Populator)");

        // each student must have a course taught by this teacher
        students.forEach(s -> {
            assertNotNull(s.getCourse(), "Student must have a course");
            assertNotNull(s.getCourse().getTeacher(), "Course must have a teacher");
            assertEquals(teacher.getId(), s.getCourse().getTeacher().getId());
        });
    }

    @Test
    @DisplayName("getAllTeachersForCourse(courseName) should return all teachers connected to that course name")
    void getAllTeachersForCourse() {
        Course course = (Course) entities.get("course1"); // adjust key names
        assertNotNull(course, "Populator must provide course1");

        String courseName = course.getCourseName().toString();
        assertNotNull(courseName);

        Set<Teacher> teachers = retrieveDAO.getAllTeachersForCourse(courseName);

        assertNotNull(teachers);
        assertFalse(teachers.isEmpty(), "Course name should yield teacher(s)");

        // depending on model: if Course has a single teacher, expect exactly 1
        // If your domain allows multiple teachers per courseName, keep it as 'contains'.
        assertTrue(
                teachers.stream().anyMatch(t -> t.getId().equals(course.getTeacher().getId())),
                "Result should include the teacher for the course"
        );
    }

}
