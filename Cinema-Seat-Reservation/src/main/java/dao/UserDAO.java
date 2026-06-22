package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Admin;
import model.Customer;
import model.User;
import util.DBConnection;

public class UserDAO {

    // Register New User
    public boolean registerUser(User user) {

        String sql = "INSERT INTO users "
                + "(name,email,username,password,role) "
                + "VALUES (?,?,?,?,?)";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)
        ) {

            pst.setString(1, user.getName());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getUsername());
            pst.setString(4, user.getPassword());

            if (user instanceof Admin) {
                pst.setString(5, "ADMIN");
            } else {
                pst.setString(5, "CUSTOMER");
            }

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Register Error : "
                    + e.getMessage());

            return false;
        }
    }

    // Login User
    public User login(String username,
                      String password) {

        String sql =
        "SELECT user_id, name, email, username, password, role "
        + "FROM users "
        + "WHERE username = ? "
        + "AND password = ?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                String role =
                        rs.getString("role");

                User user;

                if (role.equalsIgnoreCase(
                        "ADMIN")) {

                    user = new Admin();

                } else {

                    user = new Customer();
                }

                user.setUserId(
                        rs.getInt("user_id"));

                user.setName(
                        rs.getString("name"));

                user.setEmail(
                        rs.getString("email"));

                user.setUsername(
                        rs.getString("username"));

                user.setPassword(
                        rs.getString("password"));

                return user;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Login Error : "
                            + e.getMessage());
        }

        return null;
    }

    // Check Username Exists
    public boolean usernameExists(
            String username) {

        String sql =
                "SELECT user_id,name,email,username,password,role\n" + "FROM users"
                + "WHERE username=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setString(1, username);

            ResultSet rs =
                    pst.executeQuery();

            return rs.next();

        } catch (SQLException e) {

            System.out.println(
                    "Username Check Error : "
                            + e.getMessage());

            return false;
        }
    }

    // Get User By ID
    public User getUserById(
            int userId) {

        String sql =
                "SELECT user_id,name,email,username,password,role\n" + "FROM users"
                + "WHERE user_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(1, userId);

            ResultSet rs =
                    pst.executeQuery();

            if (rs.next()) {

                String role =
                        rs.getString("role");

                User user;

                if (role.equalsIgnoreCase(
                        "ADMIN")) {

                    user = new Admin();

                } else {

                    user = new Customer();
                }

                user.setUserId(
                        rs.getInt("user_id"));

                user.setName(
                        rs.getString("name"));

                user.setEmail(
                        rs.getString("email"));

                user.setUsername(
                        rs.getString("username"));

                user.setPassword(
                        rs.getString("password"));

                return user;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Get User Error : "
                            + e.getMessage());
        }

        return null;
    }

    // Delete User
    public boolean deleteUser(
            int userId) {

        String sql =
                "DELETE FROM users "
                + "WHERE user_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(1, userId);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Delete Error : "
                            + e.getMessage());

            return false;
        }
    }
    
    public boolean updateUser(User user){

    String sql =
            "UPDATE users SET "
                    + "name=?,"
                    + "email=?,"
                    + "username=?,"
                    + "password=? "
                    + "WHERE user_id=?";

    try(
        Connection con =
                DBConnection.getConnection();

        PreparedStatement pst =
                con.prepareStatement(sql)
    ){

        pst.setString(1,user.getName());
        pst.setString(2,user.getEmail());
        pst.setString(3,user.getUsername());
        pst.setString(4,user.getPassword());
        pst.setInt(5,user.getUserId());

        return pst.executeUpdate() > 0;

        }catch(SQLException e){

            System.out.println(
                    "Update User Error : "
                            + e.getMessage());

            return false;
        }
    }
    
        public List<User> getAllUsers() {

        java.util.List<User> users =
                new ArrayList<>();

        String sql = "SELECT user_id,name,email,username,password,role\n" + "FROM users"
;

        try(
            Connection con =
                    DBConnection.getConnection();

            Statement st =
                    con.createStatement();

            ResultSet rs =
                    st.executeQuery(sql)
        ){

            while(rs.next()){

                User user;

                if(rs.getString("role")
                        .equalsIgnoreCase("ADMIN")){

                    user = new Admin();

                }else{

                    user = new Customer();
                }

                user.setUserId(
                        rs.getInt("user_id"));

                user.setName(
                        rs.getString("name"));

                user.setEmail(
                        rs.getString("email"));

                user.setUsername(
                        rs.getString("username"));

                user.setPassword(
                        rs.getString("password"));

                users.add(user);
            }

        }catch(SQLException e){

            System.out.println(e.getMessage());
        }

        return users;
    }
        
        
        public java.util.List<User> searchUsers(
            String keyword){

        java.util.List<User> users =
                new java.util.ArrayList<>();

        String sql =
                "SELECT user_id,name,email,username,password,role\n" + "FROM users"
                + "WHERE name LIKE ?";

        try(
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql)
        ){

            pst.setString(
                    1,
                    "%" + keyword + "%");

            ResultSet rs =
                    pst.executeQuery();

            while(rs.next()){

                User user;

                if(rs.getString("role")
                        .equalsIgnoreCase("ADMIN")){

                    user = new Admin();

                }else{

                    user = new Customer();
                }

                user.setUserId(
                        rs.getInt("user_id"));

                user.setName(
                        rs.getString("name"));

                user.setEmail(
                        rs.getString("email"));

                user.setUsername(
                        rs.getString("username"));

                users.add(user);
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return users;
    }
        
        public int getTotalUsers(){

    String sql =
            "SELECT COUNT(*) FROM users";

    try(
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery()
    ){

        if(rs.next()){
            return rs.getInt(1);
        }

    }catch(Exception e){

    System.out.println(
            "Search User Error : "
                    + e.getMessage());
}

    return 0;
}
}