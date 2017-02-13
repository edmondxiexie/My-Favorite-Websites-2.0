/**
 * 08-672 Assignment 4.
 * @author Jiayi Xie
 * @id jiayix
 * 12/12/2016
 */
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private List<Connection> connectionPool = new ArrayList<Connection>();
    private String jdbcDriver;
    private String jdbcURL;

    public ConnectionPool(String jdbcDriver, String jdbcURL) {
        this.jdbcDriver = jdbcDriver;
        this.jdbcURL = jdbcURL;
    }

    public synchronized Connection getConnection() throws MyDAOException {

        if (connectionPool.size() > 0) {
            return connectionPool.remove(connectionPool.size() - 1);
        }

        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            throw new MyDAOException(e);
        }
        
        try {
            return DriverManager.getConnection(jdbcURL);
        } catch (SQLException e) {
            throw new MyDAOException(e);
        }
    }

    public synchronized void releaseConnection(Connection con) {
        connectionPool.add(con);
    }
}
