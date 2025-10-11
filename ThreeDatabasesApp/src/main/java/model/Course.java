package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "COURSES", schema="UNIVUSER")
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
    @SequenceGenerator(name = "course_seq", sequenceName = "COURSES_SEQ", allocationSize = 1)
    private Long id;


    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "TEACHER_ID")
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
