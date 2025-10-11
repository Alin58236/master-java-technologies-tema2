import ejb.CourseEJB;
import model.Course;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class QueryUnitTest {

    @Test
    public void testCoursesExist() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OraclePU");
        EntityManager em = emf.createEntityManager();

        List<Course> courses = em.createQuery("SELECT c FROM Course c", Course.class)
                .getResultList();

        for (Course course : courses) {
            System.out.println(course);
        }
        //Assertions.assertFalse(courses.isEmpty(), "Tabela COURSES trebuie să conțină date");

        em.close();
        emf.close();
    }
}
