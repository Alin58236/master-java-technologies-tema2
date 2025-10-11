import org.junit.jupiter.api.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseConnectionTestJUNIT {

    @Test
    public void testPostgresConnection() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PostgresPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("SELECT 1").getSingleResult();
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("PostgresPU OK");
    }

    @Test
    public void testOracleConnection() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OraclePU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("SELECT 1 FROM DUAL").getSingleResult();
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("OraclePU OK");
    }

    @Test
    public void testMssqlConnection() {
        testConnection("MssqlPU");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MssqlPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("SELECT 1").getSingleResult();
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("MssqlPU OK");
    }

    private void testConnection(String pu) {

    }
}
