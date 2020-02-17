package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {
    Author author = null;

    @BeforeEach
    void before(){
        author = new Author("Adin", "Nesimi", 19, Gender.MALE, "UNSA");
    }

    @org.junit.jupiter.api.Test
    void getUniversity() {
        assertNotNull(author);
        assertEquals("UNSA", author.getUniversity());
        author.setUniversity("UNZE");

        assertEquals("UNZE", author.getUniversity());
    }

    @org.junit.jupiter.api.Test
    void setUniversity() {
        assertNotNull(author);
        author.setUniversity("");
        assertTrue(author.getUniversity().isEmpty());
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        assertNotNull(author);
        assertEquals("UNSA", author.getUniversity());

        assertEquals("Adin Nesimi UNSA", author.toString());

        author.setFirstname("Nedim");
        assertEquals("Nedim Nesimi UNSA", author.toString());
    }

    @org.junit.jupiter.api.Test
    void testEquals() {
        Author a = new Author("adin", "Nesimi", 19, Gender.MALE, "UNSA");
        assertNotNull(author);
        assertNotEquals(author, a);

        a.setFirstname("Adin");
        assertEquals(author, a);

    }

    @org.junit.jupiter.api.Test
    void getId() {
        assertNotNull(author);
        assertEquals(0, author.getId());
    }

    @org.junit.jupiter.api.Test
    void getGender() {
        assertNotNull(author);
        assertEquals(Gender.MALE, author.getGender());
    }
}