package ba.unsa.etf.rpr.projekat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class ScientificDAO {
    private static ScientificDAO instance;
    private Connection conn;
    private PreparedStatement getUser;

    private void regenerisiBazu(){
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("korisnici.db.sql"));
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
            getUser = conn.prepareStatement("select * from users where username=? and password=?");
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
                    rs.getString(7), rs.getString(8));
        } catch (SQLException e) {
            e.printStackTrace();
        }return null;
    }
}
