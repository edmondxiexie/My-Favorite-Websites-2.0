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

public class ManageAction extends Action {
    UserDAO userDAO;
    FavoriteDAO favoriteDAO;

    public ManageAction(Model model) {
        userDAO = model.getUserDAO();
        favoriteDAO = model.getFavoriteDAO();
    }

    @Override
    public String getName() {
        return "manage.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("user");
        if (user == null) {
            return "login.do";
        }
        List<FavoriteBean> favorites = new LinkedList<FavoriteBean>();
        try {
            favorites = favoriteDAO.getUserFavorites(user.getId());
        } catch (NumberFormatException e) {
        } catch (MyDAOException e) {
        }
        request.setAttribute("favList", favorites);
        return "manage.jsp";
    }

}
