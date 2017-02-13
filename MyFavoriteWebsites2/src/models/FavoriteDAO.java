/**
 * 08-672 Assignment 4.
 * @author Jiayi Xie
 * @id jiayix
 * 12/12/2016
 */
package models;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import databeans.FavoriteBean;


public class FavoriteDAO {
    private String tableName;
    private ConnectionPool connectionPool;

    public FavoriteDAO(String tableName, ConnectionPool connectionPool)
            throws MyDAOException {
        this.tableName = tableName;
        this.connectionPool = connectionPool;
    }

    public void create(FavoriteBean favorite) throws MyDAOException {
        Connection con = null;
        try {
            con = connectionPool.getConnection();
            con.setAutoCommit(false);
            
            PreparedStatement pstmt = con.prepareStatement(
                            "INSERT INTO " 
                            + tableName
                            + " (userId,url,comment,clickCount) VALUES (?,?,?,?)");
            
            pstmt.setInt(1, favorite.getUserId());
            pstmt.setString(2, favorite.getURL());
            pstmt.setString(3, favorite.getComment());
            pstmt.setInt(4, favorite.getClickCount());
            pstmt.executeUpdate();
            pstmt.close();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
            rs.next();
            favorite.setId(rs.getInt("LAST_INSERT_ID()"));

            con.commit();
            con.setAutoCommit(true);
            connectionPool.releaseConnection(con);
        } catch (Exception e) {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e2) {
            }
            throw new MyDAOException(e);
        }
    }

    public List<FavoriteBean> getUserFavorites(int userId)
            throws MyDAOException {
        Connection con = null;
        try {
            con = connectionPool.getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstmt = con.prepareStatement(
                    "SELECT * FROM " 
                    + tableName
                    + " WHERE userId=? ORDER BY id DESC");
            pstmt.setInt(1, userId);
            // rs stores the query results
            ResultSet rs = pstmt.executeQuery();
            FavoriteBean favorite;
            List<FavoriteBean> favorites = new LinkedList<FavoriteBean>();
            while (rs.next()) {
                favorite = new FavoriteBean();
                favorite.setUserId(rs.getInt("userId"));
                favorite.setURL(rs.getString("url"));
                favorite.setComment(rs.getString("comment"));
                favorite.setClickCount(rs.getInt("clickCount"));
                favorite.setId(rs.getInt("id"));
                favorites.add(favorite);
            }
            rs.close();
            pstmt.close();
            con.commit();
            con.setAutoCommit(true);
            connectionPool.releaseConnection(con);
            return favorites;
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

    public FavoriteBean getFavoriteById(int favoriteId) throws MyDAOException {
        Connection con = connectionPool.getConnection();
        try {
            con.setAutoCommit(false);

            PreparedStatement pstmt = con.prepareStatement(
                                            "SELECT * FROM " 
                                            + tableName 
                                            + " WHERE id=?");
            pstmt.setInt(1, favoriteId);

            ResultSet rs = pstmt.executeQuery();
            FavoriteBean favorite = new FavoriteBean();
            while (rs.next()) {
                favorite = new FavoriteBean();
                favorite.setUserId(rs.getInt("userId"));
                favorite.setURL(rs.getString("url"));
                favorite.setComment(rs.getString("comment"));
                favorite.setClickCount(rs.getInt("clickCount"));
                favorite.setId(rs.getInt("id"));
            }
            rs.close();
            pstmt.close();
            con.commit();
            con.setAutoCommit(true);
            connectionPool.releaseConnection(con);
            return favorite;
        } catch (Exception e) {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e1) {
            }
            throw new MyDAOException(e);
        }
    }

    public List<FavoriteBean> getAllFavorites() throws MyDAOException {
        Connection con = null;
        try {
            con = connectionPool.getConnection();
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT * FROM " 
                    + tableName 
                    + " ORDER BY id DESC");
            FavoriteBean favorite;
            List<FavoriteBean> favorites = new LinkedList<FavoriteBean>();
            while (rs.next()) {
                favorite = new FavoriteBean();
                favorite.setUserId(rs.getInt("userId"));
                favorite.setURL(rs.getString("url"));
                favorite.setComment(rs.getString("comment"));
                favorite.setClickCount(rs.getInt("clickCount"));
                favorite.setId(rs.getInt("id"));
                favorites.add(favorite);
            }
            rs.close();
            stmt.close();
            con.commit();
            con.setAutoCommit(true);
            connectionPool.releaseConnection(con);
            return favorites;
        } catch (Exception e) {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e1) { /* ignore */ 
            }
            throw new MyDAOException(e);
        }
    }

    public void updateClickCount(String itemId) throws MyDAOException {
        int item = Integer.parseInt(itemId);
        Connection con = null;
        try {
            con = connectionPool.getConnection();
            con.setAutoCommit(false);
            PreparedStatement pstmt = con.prepareStatement(
                    "SELECT clickCount from " 
                    + tableName 
                    + " WHERE id=? ");
            pstmt.setInt(1, item);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                pstmt = con.prepareStatement(
                        "UPDATE " 
                        + tableName 
                        + " SET clickCount=? WHERE id=?");
                pstmt.setInt(1, rs.getInt("clickCount") + 1);
                pstmt.setInt(2, item);
                pstmt.executeUpdate();
                rs.close();
                pstmt.close();
                con.commit();
                con.setAutoCommit(true);
                connectionPool.releaseConnection(con);
                return;
            }
        } catch (Exception e) {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e2) {
            }
            throw new MyDAOException(e);
        }
    }

    public void delete(int userId, int favoriteId) throws MyDAOException {
        Connection con = null;
        try {
            con = connectionPool.getConnection();
            con.setAutoCommit(false);

            PreparedStatement pstmt = con.prepareStatement(
                                            "DELETE FROM " 
                                            + tableName
                                            + " WHERE id=? AND userId=?");
            pstmt.setInt(1, favoriteId);
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
            } catch (SQLException e2) { /* ignore */ 
            }
            throw new MyDAOException(e);
        }
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
            } catch (SQLException e2) {
            }
            throw new MyDAOException(e);
        }
    }

    public void createTable() throws MyDAOException {
        Connection con = connectionPool.getConnection();
        try {
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS " 
                    + tableName
                    + " (id INT NOT NULL AUTO_INCREMENT," 
                    + "url TEXT NULL,"
                    + "comment TEXT NULL," 
                    + "clickCount INT NULL,"
                    + "userId INT NOT NULL," 
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
}
