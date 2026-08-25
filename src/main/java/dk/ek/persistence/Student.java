package dk.ek.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<Long> courseIds = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private StudentGradeCard gradeCard = new StudentGradeCard();

    @ManyToOne@JoinColumn(name = "address_id")
    private Address address;

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    public void assignToCourse(Long courseId){
        if(courseId == null){
            throw new IllegalArgumentException("Course ID cannot be null");
        }
        if(courseIds.contains(courseId)){
            throw new IllegalArgumentException("Student is already assigned to course with ID: " + courseId);
        }
        courseIds.add(courseId);
    }
}