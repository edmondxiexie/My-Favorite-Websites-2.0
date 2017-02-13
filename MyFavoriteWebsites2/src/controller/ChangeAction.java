/**
 * 08-672 Assignment 4.
 * @author Jiayi Xie
 * @id jiayix
 * 12/12/2016
 */
package controller;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import databeans.UserBean;
import formbeans.ChangeForm;
import models.Model;
import models.MyDAOException;
import models.UserDAO;

public class ChangeAction extends Action {
    UserDAO userDAO;

    @Override
    public String getName() {
        return "change-pwd.do";
    }

    public ChangeAction(Model model) {
        userDAO = model.getUserDAO();
    }

    @Override
    public String perform(HttpServletRequest request) {
        ChangeForm form = new ChangeForm(request);
        request.setAttribute("form", form);
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);
        if (!form.isPresent()) {
            return "change-pwd.jsp";
        }
        errors.addAll(form.getValidationErrors());
        if (errors.size() != 0) {
            return "change-pwd.jsp";
        }
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("user");
        if (user != null) {
            try {
                userDAO.updatePassword(user.getId(), form.getPassword());
            } catch (MyDAOException e) {
                e.printStackTrace();
            }
        }
        request.setAttribute("message", "Password changed");
        return "success.jsp";
    }
}
