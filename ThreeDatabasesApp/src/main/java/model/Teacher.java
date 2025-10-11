package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Teachers")
public class Teacher implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String department;

    // Constructor implicit
    public Teacher() {}

    public Teacher(String name, String department) {
        this.name = name;
        this.department = department;
    }

    // Getters și Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return "Teacher{id=" + id + ", name='" + name + "', department='" + department + "'}";
    }
}
