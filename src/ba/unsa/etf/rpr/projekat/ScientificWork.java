package ba.unsa.etf.rpr.projekat;

import java.util.List;

public class ScientificWork {
    private int id;
    private List<Author> authors;
    private String name;
    private String[] tags;
    private double rating;

    private void test(List<Person> list) throws IllegalUserException {
        for(Person p : list){
            if(!(p instanceof Author)){
                throw new IllegalUserException("The person is not a registered author");
            }
        }
    }

    public ScientificWork(int id, List<Author> authors, String name, String[] tags) {
        this.id = id;
        this.authors = authors;
        this.name = name;
        this.tags = tags;
        this.rating = 0;
    }

    public ScientificWork(List<Author> authors, String name, String[] tags) throws IllegalUserException {
        //test(authors);
        this.authors = authors;
        this.name = name;
        this.tags = tags;
        this.rating = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) throws IllegalUserException {
       // test(authors);
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
