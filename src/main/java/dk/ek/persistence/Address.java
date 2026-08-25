package dk.ek.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String street;
    private int number;
    private int zip;
    private String city;

    @OneToMany(mappedBy = "address")
    private List<Student> students = new ArrayList<>();

    public Address(String street, int number, int zip, String city){
        this.street = street;
        this.number = number;
        this.zip = zip;
        this.city = city;
    }

    //TODO: remember to check bidirectional relationship.
    public void addStudent(Student student){
        students.add(student);
        student.setAddress(this);
    }

    public void removeStudent(Student student){
        if(students.contains(student)){
            students.remove(student);
            student.setAddress(null);
        }
    }

}
