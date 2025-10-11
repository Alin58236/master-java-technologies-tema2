package ejb;

import model.Teacher;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Stateless
public class TeacherEJB {

    @PersistenceUnit(unitName = "MssqlPU")
    private EntityManagerFactory emf;

    // CREATE
    public void create(Teacher teacher) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(teacher);
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            em.close();
        }
    }

    // READ all
    public List<Teacher> findAll() {
        EntityManager em = emf.createEntityManager();
        System.out.println("TeacherEJB find teachers");
        try {
            List<Teacher> list = em.createQuery("SELECT t FROM Teacher t", Teacher.class).getResultList();
            for (Teacher teacher : list) {
                System.out.println(teacher);
            }
            return list;
        } finally {
            em.close();
            System.out.println("TeacherEJB after find teachers");
        }
    }

    // READ by ID
    public Teacher findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Teacher.class, id);
        } finally {
            em.close();
        }
    }

    // UPDATE
    public void update(Teacher teacher) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(teacher);
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
            Teacher t = em.find(Teacher.class, id);
            if (t != null) em.remove(t);
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            em.close();
        }
    }
}
