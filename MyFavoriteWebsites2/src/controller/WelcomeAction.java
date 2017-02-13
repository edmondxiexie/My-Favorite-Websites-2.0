/**
 * 08-672 Assignment 4.
 * @author Jiayi Xie
 * @id jiayix
 * 12/12/2016
 */
package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import databeans.UserBean;
import models.Model;
import models.MyDAOException;
import models.UserDAO;

public class WelcomeAction extends Action {
    UserDAO userDAO;

    public WelcomeAction(Model model) {
        userDAO = model.getUserDAO();
    }

    @Override
    public String getName() {
        return "welcome.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (request.getAttribute("userList") == null) {
            List<UserBean> userList = new ArrayList<UserBean>();
            try {
                userList = userDAO.getUsers();
            } catch (MyDAOException e) {
            }
            session.setAttribute("userList", userList);
        }
        return "welcome.jsp";
    }

}
