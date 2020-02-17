package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void testToString() {
        User user = new User("Emin", "Siljak", 20, Gender.MALE);
        user.setUsername("esiljak1");

        assertEquals("Emin Siljak (20) esiljak1", user.toString());
    }
}