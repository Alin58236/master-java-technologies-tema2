package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Courses")
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "teacher_id")
    private Long teacherId; // legătură simplă cu Teacher

    // Constructor implicit
    public Course() {}

    public Course(String name, Long teacherId) {
        this.name = name;
        this.teacherId = teacherId;
    }

    // Getters și Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    @Override
    public String toString() {
        return "Course{id=" + id + ", name='" + name + "', teacherId=" + teacherId + "}";
    }
}
