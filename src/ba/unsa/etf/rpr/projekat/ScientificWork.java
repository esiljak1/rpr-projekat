package ba.unsa.etf.rpr.projekat;

import java.util.List;

public class ScientificWork {
    private int id;
    private List<Person> authors;
    private String name;
    private String[] tags;

    public ScientificWork(List<Person> authors, String name, String[] tags) {
        this.authors = authors;
        this.name = name;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Person> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Person> authors) {
        this.authors = authors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
