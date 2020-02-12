package ba.unsa.etf.rpr.projekat;

import java.util.List;

public class ScientificWork {
    private int id;
    private List<Person> authors;
    private String name;
    private String[] tags;

    private void test(List<Person> list) throws IllegalUserException {
        for(Person p : list){
            if(!(p instanceof User)){
                throw new IllegalUserException("Author must be a registered user!");
            }
        }
    }

    public ScientificWork(List<Person> authors, String name, String[] tags) throws IllegalUserException {
        test(authors);
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

    public void setAuthors(List<Person> authors) throws IllegalUserException {
        test(authors);
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
