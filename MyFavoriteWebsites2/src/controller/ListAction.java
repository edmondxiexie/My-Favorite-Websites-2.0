/**
 * 08-672 Assignment 4.
 * @author Jiayi Xie
 * @id jiayix
 * 12/12/2016
 */
package controller;

import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import databeans.FavoriteBean;
import databeans.UserBean;
import models.FavoriteDAO;
import models.Model;
import models.MyDAOException;
import models.UserDAO;

public class ListAction extends Action {
    UserDAO userDAO;
    FavoriteDAO favoriteDAO;

    public ListAction(Model model) {
        userDAO = model.getUserDAO();
        favoriteDAO = model.getFavoriteDAO();
    }

    @Override
    public String getName() {
        return "list.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        HttpSession session = request.getSession();

        UserBean user = (UserBean) session.getAttribute("listUser");
        List<FavoriteBean> favorites = new LinkedList<FavoriteBean>();
        String userId = request.getParameter("uid");
        try {
            if (userId == null) {
                if (user != null) {
                    userId = ((Integer) user.getId()).toString();
                }
            }

            if (userId == null || userId.length() == 0) {
                return "welcome.do";
            }
            if (userId.equals("-1")) {
                favorites = favoriteDAO.getAllFavorites();
                UserBean tmp = new UserBean();
                tmp.setId(-1);
                session.setAttribute("listUser", tmp);
            } else {
                try {
                    favorites = favoriteDAO.getUserFavorites(Integer.parseInt(userId));
                    user = userDAO.getUserById(userId);
                    session.setAttribute("listUser", user);
                } catch (NumberFormatException e) {
                    request.setAttribute("message", "Invalid user!");
                    return "success.jsp";
                }
            }
        } catch (MyDAOException e) {
            request.setAttribute("message", "Invalid Input");
            return "success.jsp";
        }
        request.setAttribute("favList", favorites);
        return "list.jsp";
    }
}
