package dk.ek.persistence;

import java.util.Set;

public interface IRetrieveCourseData {
    Set<Student> getByCourseId(Long courseId);
    Set<Course> getCoursesByStudent(Long studentId);
}
