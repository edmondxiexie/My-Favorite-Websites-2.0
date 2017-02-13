/**
 * 08-672 Assignment 4.
 * @author Jiayi Xie
 * @id jiayix
 * 12/12/2016
 */

package models;

import databeans.UserBean;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private String tableName;
    ConnectionPool connectionPool;

    public UserDAO(String tableName, ConnectionPool connectionPool)
            throws MyDAOException {
        this.tableName = tableName;
        this.connectionPool = connectionPool;
    }

    public boolean tableExists() throws MyDAOException {
        Connection con = null;
        try {
            con = connectionPool.getConnection();
            con.setAutoCommit(false);
            DatabaseMetaData metaData = con.getMetaData();
            ResultSet rs = metaData.getTables(null, null, tableName, null);
            
            boolean answer = rs.next();
            
            rs.close();
            con.commit();
            con.setAutoCommit(true);
            connectionPool.releaseConnection(con);
            return answer;
        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e2) { /* ignore */
            }
            throw new MyDAOException(e);
        }
    }

    public void createTable() throws MyDAOException {
        Connection con = null;
        try {
            con = connectionPool.getConnection();
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName
                    + " (id INT NOT NULL AUTO_INCREMENT," 
                    + "email TEXT NULL,"
                    + "firstName TEXT NULL," 
                    + "lastName TEXT NULL,"
                    + "password TEXT NULL," 
                    + "PRIMARY KEY(id));");
            stmt.close();
            con.commit();
            con.setAutoCommit(true);
            connectionPool.releaseConnection(con);
        } catch (Exception e) {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e2) { /* ignore */
            }
            throw new MyDAOException(e);
        }
    }

    public void create(UserBean user) throws MyDAOException {
        Connection con = null;
        try {
            con = connectionPool.getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO " 
                    + tableName
                    + " (email,firstName,lastName,password) VALUES (?,?,?,?)");
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getPassword());
            pstmt.executeUpdate();
            pstmt.close();

            Statement stmt = con.createStatement();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
            rs.next();
            user.setId(rs.getInt("LAST_INSERT_ID()"));

            con.commit();
            con.setAutoCommit(true);
            connectionPool.releaseConnection(con);
        } catch (Exception e) {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e2) { /* ignore */ 
            }
            throw new MyDAOException(e);
        }
    }

    public UserBean read(String email) throws MyDAOException {
        Connection con = null;
        try {
            con = connectionPool.getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstmt = con.prepareStatement(
                    "SELECT * FROM " 
                    + tableName 
                    + " WHERE email=?");
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            UserBean user;
            if (!rs.next()) {
                user = null;
            } else {
                user = new UserBean();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setPassword(rs.getString("password"));
            }
            
            rs.close();
            pstmt.close();
            con.commit();
            con.setAutoCommit(true);
            connectionPool.releaseConnection(con);
            return user;
        } catch (Exception e) {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e2) { /* ignore */ 
            }
            throw new MyDAOException(e);
        }
    }

    public List<UserBean> getUsers() throws MyDAOException {
        Connection con = null;
        try {
            con = connectionPool.getConnection();
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " 
                    + tableName 
                    + " ORDER BY id DESC");
            List<UserBean> users = new ArrayList<>();
            UserBean user;
            while (rs.next()) {
                user = new UserBean();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
            rs.close();
            stmt.close();
            con.commit();
            con.setAutoCommit(true);
            connectionPool.releaseConnection(con);
            return users;
        } catch (Exception e) {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e2) { /* ignore */ 
            }
            throw new MyDAOException(e);
        }
    }

    public UserBean getUserById(String userId) throws MyDAOException {
        Connection con = null;
        try {
            con = connectionPool.getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstmt = con.prepareStatement(
                    "SELECT * FROM " 
                    + tableName 
                    + " WHERE id=?");
            pstmt.setInt(1, Integer.parseInt(userId));
            ResultSet rs = pstmt.executeQuery();
            UserBean user = new UserBean();
            while (rs.next()) {
                user = new UserBean();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setPassword(rs.getString("password"));
            }
            rs.close();
            pstmt.close();
            con.commit();
            con.setAutoCommit(true);
            connectionPool.releaseConnection(con);
            return user;
        } catch (Exception e) {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e2) { /* ignore */ 
            }
            throw new MyDAOException(e);
        }
    }

    public void updatePassword(int userId, String newPassword)
            throws MyDAOException {
        Connection con = null;
        try {
            con = connectionPool.getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstmt = con.prepareStatement(
                    "UPDATE " 
                    + tableName 
                    + " SET password=? WHERE id=?");
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
            pstmt.close();
            con.commit();
            con.setAutoCommit(true);
            connectionPool.releaseConnection(con);
            return;
        } catch (Exception e) {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new MyDAOException(e);
        }
    }
}
