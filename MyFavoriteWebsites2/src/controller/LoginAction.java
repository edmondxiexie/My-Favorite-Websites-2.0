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
import formbeans.LoginForm;
import models.Model;
import models.UserDAO;

public class LoginAction extends Action {
    private UserDAO userDAO;

    public LoginAction(Model model) {
        userDAO = model.getUserDAO();
    }

    public String getName() {
        return "login.do";
    }

    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        try {
            LoginForm form = new LoginForm(request);
            request.setAttribute("form", form);

            if (!form.isPresent()) {
                return "login.jsp";
            }

            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "login.jsp";
            }

            UserBean user = userDAO.read(form.getEmail());

            if (user == null) {
                errors.add("User not found");
                return "login.jsp";
            }

            if (!user.getPassword().equals(form.getPassword())) {
                errors.add("Incorrect password");
                return "login.jsp";
            }

            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "manage.do";
        } catch (Exception e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }
    }
}
