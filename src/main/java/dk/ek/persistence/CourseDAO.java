package dk.ek.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CourseDAO implements IDAO<Course>, IRetrieveCourseData  {
    EntityManagerFactory emf;
    public CourseDAO(EntityManagerFactory _emf){
        this.emf = _emf;
    }
    @Override
    public Course create(Course e) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();
            return e;
        }
    }

    @Override
    public Set<Course> get() {
        try(EntityManager em = emf.createEntityManager()){
            return new HashSet(em.createQuery("SELECT e FROM Course e").getResultList());
        }
    }

    @Override
    public Course getByID(Long id) {
        try(EntityManager em = emf.createEntityManager()){
            Course employee = em.find(Course.class, id);
            if(employee == null)
                throw new EntityNotFoundException("No entity found with id: "+id);
            return employee;
        }
    }

    @Override
    public Course update(Course e) {
        try(EntityManager em = emf.createEntityManager()){
            Course foundEmployee = em.find(Course.class, e.getId());
            if(foundEmployee == null)
                throw new EntityNotFoundException("No entity found with id: "+e.getId());
            em.getTransaction().begin();
            Course employee = em.merge(e);
            em.getTransaction().commit();
            return employee;
        }
    }

    @Override
    public Long delete(Course e) {
        try(EntityManager em = emf.createEntityManager()){
            Course employee = em.find(Course.class, e.getId());
            if(employee == null)
                throw new EntityNotFoundException("No entity found with id: "+e.getId());
            em.getTransaction().begin();
            em.remove(employee);
            em.getTransaction().commit();
            return employee.getId();
        }
    }
    @Override
    public Set<Course> getCoursesByStudent(Long studentId) {
        try(EntityManager em = emf.createEntityManager()){
            Student student = em.find(Student.class, studentId);
            if(student == null){
                throw new IllegalArgumentException("No student found with that id");
            }
            return student.getCourseIds()
                    .stream()
                    .map(id -> em.find(Course.class, id))
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public Set<Student> getByCourseId(Long courseId) {
        try(EntityManager em = emf.createEntityManager()){
            Course course = em.find(Course.class, courseId);
            if(course == null)
                throw new EntityNotFoundException("No entity found with id: "+courseId);
            Set<Student> allStudents = new HashSet<>(em.createQuery("SELECT s FROM Student s", Student.class).getResultList());
            return allStudents
                    .stream()
                    .filter(s->s.getCourseIds().contains(courseId))
                    .collect(Collectors.toSet());
        }
    }
}
