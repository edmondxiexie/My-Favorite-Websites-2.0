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
import formbeans.*;
import models.Model;
import models.MyDAOException;
import models.UserDAO;
import databeans.*;

public class RegisterAction extends Action {
    private UserDAO userDAO;

    public RegisterAction(Model model) {
        userDAO = model.getUserDAO();
    }

    @Override
    public String getName() {
        return "register.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);
        RegisterForm form = new RegisterForm(request);
        request.setAttribute("form", form);
        if (!form.isPresent()) {
            return "register.jsp";
        }
        errors.addAll(form.getValidationErrors());

        if (errors.size() != 0) {
            return "register.jsp";
        }

        try {
            if (userDAO.read(form.getEmail()) != null) {
                errors.add("User Existed");
            }

            if (errors.size() != 0) {
                return "register.jsp";
            }
            UserBean user = new UserBean();
            user.setEmail(form.getEmail());
            user.setFirstName(form.getFirstName());
            user.setLastName(form.getLastName());
            user.setPassword(form.getPassword());
            userDAO.create(user);
            List<UserBean> userList = new ArrayList<UserBean>();
            try {
                userList = userDAO.getUsers();
            } catch (MyDAOException e) {
                e.printStackTrace();
            }
            HttpSession session = request.getSession();
            session.setAttribute("userList", userList);
            session = request.getSession();
            session.setAttribute("user", user);
            return "manage.do";
        } catch (MyDAOException e) {
            return "register.jsp";
        }
    }
}
