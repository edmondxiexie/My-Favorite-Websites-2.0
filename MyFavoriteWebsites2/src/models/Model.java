/**
 * 08-672 Assignment 4.
 * @author Jiayi Xie
 * @id jiayix
 * 12/12/2016
 */
package models;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class Model {
    private FavoriteDAO favoriteDAO;
    private UserDAO userDAO;

    public Model(ServletConfig config) throws ServletException {
        try {
            String jdbcDriver = config.getInitParameter("jdbcDriverName");
            String jdbcURL = config.getInitParameter("jdbcURL");
            ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL);
            userDAO = new UserDAO("jiayix_user", pool);
            favoriteDAO = new FavoriteDAO("jiayix_favorite", pool);
        } catch (MyDAOException e) {
            throw new ServletException(e);
        }
    }

    public FavoriteDAO getFavoriteDAO() {
        return favoriteDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }
}
