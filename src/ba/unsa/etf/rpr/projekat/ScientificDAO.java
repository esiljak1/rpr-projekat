package ba.unsa.etf.rpr.projekat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScientificDAO {
    private static ScientificDAO instance;
    private Connection conn;
    private PreparedStatement getUserFromUsernamePassword, addUser, getUserId, getUserFromUsername, addPerson, getAuthorFromNameUni, getAllAuthors, addAuthor, getPersonId,
                                addAuthorForScWork, addScWork, getPaperId, getPaperFromName, getPaperFromAuthor, getPaperFromTags, getAuthorsFromPaperId, updateUser, updatePerson, addGrade, getGrade;

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
    private String getString (String[] arr){
        String ret = "";
        for(int i = 0; i < arr.length; i++){
            ret += arr[i];
            ret += ",";
        }return ret;
    }
    private ScientificWork getPaperFromResultSet(ResultSet rs) throws SQLException, IllegalUserException {
        return new ScientificWork(rs.getInt(1), null, rs.getString(2), (rs.getString(3).split(",")));
    }
    private Author getAuthorFromResultSet(ResultSet rs) throws Exception {
        Gender g = rs.getString(5).equals("m") ? Gender.MALE : Gender.FEMALE;
        return new Author(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), g, rs.getString(6));
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
            getPersonId = conn.prepareStatement("select max(id) + 1 from person");
            getUserId = conn.prepareStatement("select max(id) + 1 from users");
            getUserFromUsernamePassword = conn.prepareStatement("select p.*, u.username, u.password, u.mail, u.image from users u, person p where u.id = p.id and username=? and password=?");
            getUserFromUsername = conn.prepareStatement("select * from users where username=?");
            getAuthorFromNameUni = conn.prepareStatement("select p.*, a.university from person p, author a where a.id = p.id and p.firstname=? and p.lastname=? and a.university=?");
            getAllAuthors = conn.prepareStatement("select p.*, a.university from person p, author a where a.id = p.id");
            getPaperId = conn.prepareStatement("select max (id) + 1 from scworks");
            getPaperFromTags = conn.prepareStatement("select * from ScWorks where tags like ?");
            getPaperFromName = conn.prepareStatement("select * from ScWorks sc where sc.name like ?");
            getPaperFromAuthor = conn.prepareStatement("select s.* from scworks s, ScWorksAuthors sa, Author a, Person p where s.id = sa.sc_id and a.id = sa.author_id and p.id = a.id and p.firstname || ' ' || p.lastname like ?");
            getAuthorsFromPaperId = conn.prepareStatement("select p.*, a.university from person p, author a, ScWorksAuthors sa where sa.sc_id=? and sa.author_id=a.id and a.id=p.id");
            getGrade = conn.prepareStatement("select avg(grade) from Rating where paper_id=?");

            addUser = conn.prepareStatement("insert into users values (?,?,?,?,?)");
            addPerson = conn.prepareStatement("insert into person values (?,?,?,?,?)");
            addAuthor = conn.prepareStatement("insert into author values (?,?)");
            addAuthorForScWork = conn.prepareStatement("insert into ScWorksAuthors values (?,?)");
            addScWork = conn.prepareStatement("insert into ScWorks values (?,?,?)");
            addGrade = conn.prepareStatement("insert into Rating values (?,?,?)");

            updateUser = conn.prepareStatement("UPDATE Users set username=?, password=?, mail=? where id = ?");
            updatePerson = conn.prepareStatement("UPDATE Person set firstname=?, lastname=?, age=?, gender=? where id = ?");
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
            ResultSet rs = getPersonId.executeQuery();
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
    public int existsAuthor(String firstname, String lastname, String uni){
        try {
            getAuthorFromNameUni.setString(1, firstname);
            getAuthorFromNameUni.setString(2, lastname);
            getAuthorFromNameUni.setString(3, uni);
            ResultSet rs = getAuthorFromNameUni.executeQuery();
            if(!rs.next()) return -1;
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }return -1;
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
    public int addAuthor(Author author){
        int id = 0;
        try {
            ResultSet rs = getPersonId.executeQuery();
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
        }return id;
    }
    public int addPaperWork(ScientificWork paper){
        int id = -1;
        try {
            ResultSet rs = getPaperId.executeQuery();
            id = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            addScWork.setInt(1, id);
            addScWork.setString(2, paper.getName());
            addScWork.setString(3, getString(paper.getTags()));
            addScWork.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }return id;
    }
    public void addAuthorForScWork(int authorId, int paperId){
        try {
            addAuthorForScWork.setInt(1, paperId);
            addAuthorForScWork.setInt(2, authorId);
            addAuthorForScWork.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Author> getAuthorsFromPaperId(int id){
        List<Author> list = new ArrayList<>();
        try {
            getAuthorsFromPaperId.setInt(1, id);
            ResultSet rs = getAuthorsFromPaperId.executeQuery();
            while(rs.next()){
                list.add(getAuthorFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }return list;
    }
    public List<ScientificWork> getWorksByName(String name){
        List<ScientificWork> ret = new ArrayList<>();
        try {
            getPaperFromName.setString(1, "%" + name + "%");
            ResultSet rs = getPaperFromName.executeQuery();
            while(rs.next()){
                ScientificWork temp = getPaperFromResultSet(rs);
                List<Author> list = getAuthorsFromPaperId(rs.getInt(1));
                temp.setAuthors(list);
                temp.setRating(getGrade(temp));
                ret.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }return ret;
    }
    public List<ScientificWork> getWorksByAuthor(String authorName){
        List<ScientificWork> ret = new ArrayList<>();

        try {
            getPaperFromAuthor.setString(1, "%" + authorName + "%");
            ResultSet rs = getPaperFromAuthor.executeQuery();
            while(rs.next()){
                ScientificWork temp = getPaperFromResultSet(rs);
                List<Author> list = getAuthorsFromPaperId(rs.getInt(1));
                temp.setAuthors(list);
                temp.setRating(getGrade(temp));
                ret.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }return ret;
    }
    public List<ScientificWork> getWorksByTag(String tag){
        List<ScientificWork> ret = new ArrayList<>();
        try {
            getPaperFromTags.setString(1, "%" + tag + "%");
            ResultSet rs = getPaperFromTags.executeQuery();
            while(rs.next()){
                ScientificWork temp = getPaperFromResultSet(rs);
                List<Author> list = getAuthorsFromPaperId(rs.getInt(1));
                temp.setRating(getGrade(temp));
                temp.setAuthors(list);
                ret.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }return ret;
    }
    public void updateUser(User user){
        try {
            updatePerson.setString(1, user.getFirstname());
            updatePerson.setString(2, user.getLastname());
            updatePerson.setInt(3, user.getAge());
            updatePerson.setString(4, user.getGender().equals(Gender.MALE) ? "m" : "f");
            updatePerson.setInt(5, user.getId());
            updatePerson.executeUpdate();

            updateUser.setString(1, user.getUsername());
            updateUser.setString(2, user.getPassword());
            updateUser.setString(3, user.getEmail());
            updateUser.setInt(4, user.getId());
            updateUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addGrade(ScientificWork paper, User user, int grade){
        try {
            addGrade.setInt(1, paper.getId());
            addGrade.setInt(2, user.getId());
            addGrade.setInt(3, grade);
            addGrade.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public double getGrade(ScientificWork paper){
        try {
            getGrade.setInt(1, paper.getId());
            ResultSet rs = getGrade.executeQuery();
            return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }return 0;
    }
}
