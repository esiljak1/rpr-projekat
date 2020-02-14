package ba.unsa.etf.rpr.projekat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ScientificDAO {
    private static ScientificDAO instance;
    private Connection conn;
    private PreparedStatement getUserFromUsernamePassword, addUser, getUserId, getUserFromUsername, addPerson, getAuthorFromNameUni, getAllAuthors, addAuthor;

    private void regenerisiBazu(){
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("scientific.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void napuni(){
        try {
            addPerson.setInt(1, 1);
            addPerson.setString(2, "Emin");
            addPerson.setString(3, "Siljak");
            addPerson.setInt(4, 20);
            addPerson.setString(5, "m");
            addPerson.executeUpdate();
            addUser.setInt(1, 1);
            addUser.setString(2, "esiljak1");
            addUser.setString(3, "test");
            addUser.setString(4, "esiljak1@etf.unsa.ba");
            addUser.setString(5, "@/../Resources/images/default.jpg");
            addUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ScientificDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:scientific.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            getUserFromUsernamePassword = conn.prepareStatement("select p.*, u.username, u.password, u.mail, u.image from users u, person p where u.id = p.id and username=? and password=?");
        } catch (SQLException e) {
            regenerisiBazu();
        }
        try {
            getUserId = conn.prepareStatement("select max(id) + 1 from users");
            getUserFromUsernamePassword = conn.prepareStatement("select p.*, u.username, u.password, u.mail, u.image from users u, person p where u.id = p.id and username=? and password=?");
            getUserFromUsername = conn.prepareStatement("select * from users where username=?");
            getAuthorFromNameUni = conn.prepareStatement("select p.*, a.university from person p, author a where a.id = p.id and p.firstname=? and p.lastname=? and a.university=?");
            getAllAuthors = conn.prepareStatement("select p.*, a.university from person p, author a where a.id = p.id");

            addUser = conn.prepareStatement("insert into users values (?,?,?,?,?)");
            addPerson = conn.prepareStatement("insert into person values (?,?,?,?,?)");
            addAuthor = conn.prepareStatement("insert into author values (?,?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ScientificDAO getInstance() {
        if(instance == null) instance = new ScientificDAO();
        return instance;
    }

    public static void removeInstance(){
        instance = null;
    }

    public User getUser(String username, String password) throws IllegalUserException {
        try {
            getUserFromUsernamePassword.setString(1, username);
            getUserFromUsernamePassword.setString(2, password);

            ResultSet rs = getUserFromUsernamePassword.executeQuery();
            if(!rs.next()){
                throw new IllegalUserException("User not found");
            }
            return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
                    rs.getString(5).equals("m") ? Gender.MALE : Gender.FEMALE, rs.getString(6),
                    rs.getString(7), rs.getString(8), rs.getString(9));
        } catch (SQLException e) {
            e.printStackTrace();
        }return null;
    }
    public void addUser(User user){
        int id = 0;
        try {
            ResultSet rs = getUserId.executeQuery();
            id = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            addPerson.setInt(1, id);
            addPerson.setString(2, user.getFirstname());
            addPerson.setString(3, user.getLastname());
            addPerson.setInt(4, user.getAge());
            addPerson.setString(5, user.getGender().equals(Gender.MALE) ? "m" : "f");
            addPerson.executeUpdate();
            addUser.setInt(1, id);
            addUser.setString(2, user.getUsername());
            addUser.setString(3, user.getPassword());
            addUser.setString(4, user.getEmail());
            addUser.setString(5, user.getImage());
            addUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean existsUser(String username){
        try {
            getUserFromUsername.setString(1, username);
            ResultSet rs = getUserFromUsername.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }return false;
    }
    public boolean existsAuthor(String firstname, String lastname, String uni){
        try {
            getAuthorFromNameUni.setString(1, firstname);
            getAuthorFromNameUni.setString(2, lastname);
            getAuthorFromNameUni.setString(3, uni);
            ResultSet rs = getUserFromUsername.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }return false;
    }
    public ArrayList<Author> getAuthors(){
        ArrayList<Author> ret = new ArrayList<>();
        try {
            ResultSet rs = getAllAuthors.executeQuery();
            while(rs.next()){
                Author temp = new Author(rs.getString(2), rs.getString(3), rs.getInt(4),
                        rs.getString(5).equals("m") ? Gender.MALE : Gender.FEMALE, rs.getString(6));
                ret.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return ret;
    }
    public void addAuthor(Author author){
        int id = 0;
        try {
            ResultSet rs = getUserId.executeQuery();
            id = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            addPerson.setInt(1, id);
            addPerson.setString(2, author.getFirstname());
            addPerson.setString(3, author.getLastname());
            addPerson.setInt(4, author.getAge());
            addPerson.setString(5, author.getGender().equals(Gender.MALE) ? "m" : "f");
            addPerson.executeUpdate();

            addAuthor.setInt(1, id);
            addAuthor.setString(2, author.getUniversity());
            addAuthor.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
