package ejb;

import model.Student;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class StudentEJB {

    @PersistenceContext(unitName = "PostgresPU")
    private EntityManager em;

    public void create(Student student) {
        em.persist(student);
    }

    public List<Student> findAll() {
        return em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
    }

    public void update(Student student) {
        em.merge(student);
    }

    public void delete(Long id) {
        Student s = em.find(Student.class, id);
        if (s != null) em.remove(s);
    }

    public Student findById(Long id) {
        return em.find(Student.class, id);
    }
}
