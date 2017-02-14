/**
 * 08-672 Assignment 4.
 * @author Jiayi Xie
 * @id jiayix
 * 12/12/2016
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import databeans.FavoriteBean;
import databeans.UserBean;
import models.FavoriteDAO;
import models.Model;
import models.MyDAOException;
import models.UserDAO;

@SuppressWarnings("serial")
public class Controller extends HttpServlet {

    public void init() throws ServletException {
        Model model = new Model(getServletConfig());
        databaseInit(model);

        Action.add(new AddAction(model));
        Action.add(new DeleteAction(model));
        Action.add(new ListAction(model));
        Action.add(new LoginAction(model));
        Action.add(new LogoutAction(model));
        Action.add(new ManageAction(model));
        Action.add(new RegisterAction(model));
        Action.add(new UrlAction(model));
        Action.add(new WelcomeAction(model));
        Action.add(new ChangeAction(model));

    }

    private void databaseInit(Model model) {
        UserDAO userDAO = model.getUserDAO();
        try {
            if (!userDAO.tableExists()) {
                userDAO.createTable();
                addTestUsers(model);
            }
        } catch (MyDAOException e) {
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        sendToNextPage(performTheAction(request), request, response);
    }

    private String performTheAction(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String servletPath = request.getServletPath();
        UserBean user = (UserBean) session.getAttribute("user");
        String action = getActionName(servletPath);

        if (action.equals("register.do") 
                || action.equals("login.do")
                || action.equals("list.do") 
                || action.equals("url.do")
                || action.equals("welcome.do")) {
            return Action.perform(action, request);
        }

        if (user == null) {
            return Action.perform("welcome.do", request);
        }

        return Action.perform(action, request);
    }

    private void sendToNextPage(String nextPage, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        if (nextPage == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    request.getServletPath());
            return;
        }

        if (nextPage.endsWith(".do")) {
            response.sendRedirect(nextPage);
            return;
        }

        if (nextPage.endsWith(".jsp")) {
            RequestDispatcher d = request.getRequestDispatcher("WEB-INF/" + nextPage);
            d.forward(request, response);
            return;
        }

        if (nextPage.startsWith("http")) {
            response.sendRedirect(nextPage);
            return;
        }

        throw new ServletException(Controller.class.getName()
                + ".sendToNextPage(\"" 
                + nextPage 
                + "\"): invalid extension.");
    }

    private String getActionName(String path) {
        return path.substring(path.lastIndexOf('/') + 1);
    }
    
    private void addTestUsers(Model model) {
        UserDAO userDAO = model.getUserDAO();
        FavoriteDAO favoriteDAO = model.getFavoriteDAO();
        try {
            if (!favoriteDAO.tableExists()) {
                favoriteDAO.createTable();
            }

            UserBean user = new UserBean();

            user.setEmail("jiayixie@gmail.com");
            user.setFirstName("Jiayi");
            user.setLastName("Xie");
            user.setPassword("000000");
            userDAO.create(user);

            user.setEmail("donaldtrump@gmail.com");
            user.setFirstName("Donald");
            user.setLastName("Trump");
            user.setPassword("000000");
            userDAO.create(user);

            user.setEmail("barackobama@gmail.com");
            user.setFirstName("Barack");
            user.setLastName("Obama");
            user.setPassword("000000");
            userDAO.create(user);

            List<UserBean> users = new ArrayList<UserBean>();
            List<FavoriteBean> favorites = new ArrayList<FavoriteBean>();
            users = userDAO.getUsers();

            favorites.add(new FavoriteBean(users.get(0).getId(),
                    "http://www.cmu.edu", "Carnegie Mellon University."));
            favorites.add(new FavoriteBean(users.get(0).getId(),
                    "http://www.edmondbay.com", "My own website"));
            
            favorites.add(new FavoriteBean(users.get(1).getId(),
                    "https://www.facebook.com", "Facebook."));
            favorites.add(new FavoriteBean(users.get(1).getId(),
                    "http://ustoday.com", "News."));
            
            favorites.add(new FavoriteBean(users.get(2).getId(),
                    "http://www.president.com", "A website about president"));
            favorites.add(new FavoriteBean(users.get(2).getId(),
                    "http://www.twitter.com", "Twitter. It's what's happening."));

            for (FavoriteBean item : favorites) {
                favoriteDAO.create(item);
            }
        } catch (MyDAOException e) {
        }
    }
}
