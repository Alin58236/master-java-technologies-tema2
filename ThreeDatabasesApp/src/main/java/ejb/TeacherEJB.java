package ejb;

import model.Teacher;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class TeacherEJB {

    @PersistenceContext(unitName = "MSSQLPU")
    private EntityManager em;

    // CREATE
    public void create(Teacher teacher) {
        em.persist(teacher);
    }

    // READ all
    public List<Teacher> findAll() {
        return em.createQuery("SELECT t FROM Teacher t", Teacher.class).getResultList();
    }

    // READ by ID
    public Teacher findById(Long id) {
        return em.find(Teacher.class, id);
    }

    // UPDATE
    public void update(Teacher teacher) {
        em.merge(teacher);
    }

    // DELETE
    public void delete(Long id) {
        Teacher t = em.find(Teacher.class, id);
        if (t != null) em.remove(t);
    }
}
