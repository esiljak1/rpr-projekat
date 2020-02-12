package ba.unsa.etf.rpr.projekat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class ScientificDAO {
    private static ScientificDAO instance;
    private Connection conn;
    private PreparedStatement getUser, addUser, getUserId;

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
            addUser.setInt(1, 1);
            addUser.setString(2, "");
            addUser.setString(3, "");
            addUser.setInt(4, 20);
            addUser.setString(5, "m");
            addUser.setString(6, "esiljak1");
            addUser.setString(7, "test");
            addUser.setString(8, "");
            addUser.setString(9, "default.jpg");
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
            getUser = conn.prepareStatement("select * from users where username=? and password=?");
        } catch (SQLException e) {
            regenerisiBazu();
        }
        try {
            getUserId = conn.prepareStatement("select max(id) + 1 from users");
            getUser = conn.prepareStatement("select * from users where username=? and password=?");

            addUser = conn.prepareStatement("insert into users values (?,?,?,?,?,?,?,?,?)");
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
            getUser.setString(1, username);
            getUser.setString(2, password);

            ResultSet rs = getUser.executeQuery();
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
}
