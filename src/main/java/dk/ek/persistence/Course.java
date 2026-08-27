package dk.ek.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "course")
public class Course implements IEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING) // Store the enum as a string in the database rather than its ordinal value (which is default). This makes the database more readable and avoids issues if the enum order changes in the future.
    private CourseName courseName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime created;
    private LocalDateTime updated;

    @ManyToOne
    private Teacher teacher;
    public Course(CourseName courseName, String description, LocalDate startDate, LocalDate endDate, Teacher teacher) {
        this.courseName = courseName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teacher = teacher;
    }
    @PrePersist
    private void prePersist() {
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updated = LocalDateTime.now();
    }
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName=" + courseName +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", created=" + created +
                ", updated=" + updated +
                ", teacher=" + (teacher != null ? teacher.getName() : null) +
                '}';
    }
}