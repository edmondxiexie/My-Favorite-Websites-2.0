/**
 * 08-672 Assignment 4.
 * @author Jiayi Xie
 * @id jiayix
 * 12/12/2016
 */
package controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import databeans.FavoriteBean;
import databeans.UserBean;
import models.FavoriteDAO;
import models.Model;

public class DeleteAction extends Action {
    FavoriteDAO favoriteDAO;

    public DeleteAction(Model model) {
        favoriteDAO = model.getFavoriteDAO();
    }

    @Override
    public String getName() {
        return "delete.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        try {
            UserBean user = (UserBean) request.getSession(false).getAttribute("user");
            int favoriteId = Integer.parseInt(request.getParameter("id"));
            if (user != null) {
                favoriteDAO.delete(user.getId(), favoriteId);
                List<FavoriteBean> favorites = favoriteDAO.getUserFavorites(user.getId());
                request.setAttribute("favList", favorites);
            }
            return "manage.do";
        } catch (Exception e) {
            request.setAttribute("message", "Invalid Operation");
            return "success.jsp";
        }
    }
}
