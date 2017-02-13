/**
 * 08-672 Assignment 4.
 * @author Jiayi Xie
 * @id jiayix
 * 12/12/2016
 */
package controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import databeans.FavoriteBean;
import databeans.UserBean;
import formbeans.FavoriteForm;
import models.FavoriteDAO;
import models.Model;
import models.MyDAOException;

public class AddAction extends Action {
    FavoriteDAO favoriteDAO;

    public AddAction(Model model) {
        favoriteDAO = model.getFavoriteDAO();
    }

    @Override
    public String getName() {
        return "add.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        List<FavoriteBean> favorites = new LinkedList<FavoriteBean>();

        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);
        HttpSession session = request.getSession(false);
        UserBean user = (UserBean) session.getAttribute("user");
        try {
            favorites = favoriteDAO.getUserFavorites(user.getId());
            request.setAttribute("favList", favorites);
            FavoriteForm form = new FavoriteForm(request);
            if (!form.isPresent()) {
                return "manage.jsp";
            }
            errors.addAll(form.getValidationErrors());
            if (errors.size() > 0) {
                request.setAttribute("form", form);
                return "manage.jsp";
            }

            FavoriteBean favorite = new FavoriteBean(user.getId(), form.getUrl(),
                    form.getComment());

            favoriteDAO.create(favorite);
            favorites = favoriteDAO.getUserFavorites(user.getId());

        } catch (MyDAOException e) {
            try {
                throw new ServletException(e);
            } catch (ServletException e2) {
            }
        }
        request.setAttribute("favList", favorites);
        request.setAttribute("form", null);
        return "manage.do";
    }

}
