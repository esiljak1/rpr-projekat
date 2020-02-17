package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ScientificDAOTest {
    ScientificDAO instance;
    @BeforeEach
    void before(){
        File dbFile = new File("scientific.db");
        dbFile.delete();
        instance = ScientificDAO.getInstance();
        instance.napuni();
    }

    @Test
    void getUser() {
        assertNotNull(instance);

        try {
            User user = instance.getUser("esiljak1", "test");
            assertEquals("Emin", user.getFirstname());
        } catch (IllegalUserException e) {
            e.printStackTrace();
        }
        assertThrows(IllegalUserException.class, () -> instance.getUser("esiljak2", "test"));
    }

    @Test
    void addUser() {
        User user2 = new User(2, "Adin", "Nesimi", 19, Gender.MALE, "anesimi1", "1234", "anesimi1@etf.unsa.ba", "");
        instance.addUser(user2);
        User dbUser = null;
        try {
            dbUser = instance.getUser("anesimi1", "1234");
        } catch (IllegalUserException e) {
            e.printStackTrace();
        }
        assertNotNull(dbUser);
        assertEquals("Adin", dbUser.getFirstname());
    }

    @Test
    void existsUser() {
        assertNotNull(instance);

        assertTrue(instance.existsUser("esiljak1"));
        assertFalse(instance.existsUser("Esiljak1"));
    }

    @Test
    void existsAuthor() {
        assertNotNull(instance);

        Author author = new Author("Nedim", "Bektas", 19, Gender.MALE, "UNSA");
        instance.addAuthor(author);

        assertTrue(instance.existsAuthor("Nedim", "Bektas", "UNSA") != -1);
        assertEquals(instance.existsAuthor("Emin", "Siljak", "UNSA"), -1);
    }

    @Test
    void addAuthor() {
        assertNotNull(instance);

        Author author = new Author("Branko", "Copic", 22, Gender.MALE, "IUT");
        instance.addAuthor(author);

        Author dbAuthor = instance.getAuthors().get(0);

        assertEquals(author.getFirstname(), dbAuthor.getFirstname());
    }

    @Test
    void updateUser() {
        assertNotNull(instance);
        User user = new User(2, "Amila", "Talic", 20, Gender.FEMALE, "atalic1", "12345", "atalic1@gmail.com", "");
        instance.addUser(user);
        user.setUsername("atalic5");
        instance.updateUser(user);

        assertTrue(instance.existsUser("atalic5"));
    }
}