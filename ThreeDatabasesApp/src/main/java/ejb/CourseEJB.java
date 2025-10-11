package ejb;

import model.Course;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CourseEJB {

    @PersistenceUnit(unitName = "OraclePU")
    private EntityManagerFactory emf;

    // CREATE
    public void create(Course course) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(course);
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            em.close();
        }
    }

    // READ all
    public List<Course> findAll() {
        EntityManager em = emf.createEntityManager();
        try {

            System.out.println("inainte de query");
            return em.createQuery("SELECT c FROM Course c", Course.class).getResultList();
            //List<Course> courses = em.createNativeQuery("SELECT * FROM UNIVUSER.COURSES", Course.class).getResultList();
//            for (Course course : courses) {
//                System.out.println(course);
//            }


//            Course c = new Course();
//            c.setId(1L);
//            c.setName("dsadasd");
//            c.setTeacherId(1L);
//            List<Course> list = new ArrayList<>();
//            list.add(c);
//            System.out.println("dupa query si afisare");
//            return list;
        } finally {
            em.close();
        }
    }

    // READ by ID
    public Course findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Course.class, id);
        } finally {
            em.close();
        }
    }

    // UPDATE
    public void update(Course course) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(course);
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            em.close();
        }
    }

    // DELETE
    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Course c = em.find(Course.class, id);
            if (c != null) em.remove(c);
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            em.close();
        }
    }
}
