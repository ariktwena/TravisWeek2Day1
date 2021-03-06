package facades;

import dtos.MovieDTO;
import utils.EMF_Creator;
import entities.Movie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class MovieFacadeTest {

    private static EntityManagerFactory emf;
    private static MovieFacade facade;

    public MovieFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = MovieFacade.getMovieFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.persist(new Movie("TitleTest1", "Des1", 34, 1999, new String[] {"Actor1", "Actor2", "Actor3"}, "Private message1"));
            em.persist(new Movie("TitleTest2", "Des2", 50, 2010, new String[] {"Actor4", "Actor5", "Actor6"}, "Private message2"));

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void getObjectCount() {
        assertEquals(2, facade.getMovieCount(), "Expects two rows in the database");
    }
    
    @Test
    public void getYear(){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.persist(new Movie("TitleTest1", "Des1", 34, 1999, new String[] {"Actor1", "Actor2", "Actor3"}, "Private message1"));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        List<MovieDTO> movieDTOs = facade.getMovieDTOByYear(1999);
        assertEquals("TitleTest1", movieDTOs.get(0).getTitleDTO(), "Expects title TitleTest1");
    }
}
