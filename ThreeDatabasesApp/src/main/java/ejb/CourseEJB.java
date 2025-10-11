package ejb;

import model.Course;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CourseEJB {

    @PersistenceContext(unitName = "OraclePU")
    private EntityManager em;

    // CREATE
    public void create(Course course) {
        em.persist(course);
    }

    // READ all
    public List<Course> findAll() {
        return em.createQuery("SELECT c FROM Course c", Course.class).getResultList();
    }

    // READ by ID
    public Course findById(Long id) {
        return em.find(Course.class, id);
    }

    // UPDATE
    public void update(Course course) {
        em.merge(course);
    }

    // DELETE
    public void delete(Long id) {
        Course c = em.find(Course.class, id);
        if (c != null) em.remove(c);
    }
}
