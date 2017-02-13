/**
 * 08-672 Assignment 4.
 * @author Jiayi Xie
 * @id jiayix
 * 12/12/2016
 */
package controller;

import javax.servlet.http.HttpServletRequest;
import databeans.FavoriteBean;
import models.FavoriteDAO;
import models.Model;
import models.MyDAOException;

public class UrlAction extends Action {
    FavoriteDAO favoriteDAO;

    public UrlAction(Model model) {
        favoriteDAO = model.getFavoriteDAO();
    }

    @Override
    public String getName() {
        return "url.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        String favoriteId = request.getParameter("id");
        try {
            favoriteDAO.updateClickCount(favoriteId);
            FavoriteBean bean = favoriteDAO.getFavoriteById(Integer.parseInt(favoriteId));
            String url = bean.getURL();
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            return url;
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid Favorite Id!");
            return "success.jsp";
        } catch (MyDAOException e) {
            request.setAttribute("message", "Data Connection Error!");
            return "success.jsp";
        }

    }
}
