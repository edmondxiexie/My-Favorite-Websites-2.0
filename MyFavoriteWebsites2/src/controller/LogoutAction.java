/**
 * 08-672 Assignment 4.
 * @author Jiayi Xie
 * @id jiayix
 * 12/12/2016
 */
package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import models.Model;

public class LogoutAction extends Action {

    public LogoutAction(Model model) {
    }

    public String getName() {
        return "logout.do";
    }

    public String perform(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setAttribute("user", null);
        session.setAttribute("listUser", null);
        return "welcome.do";
    }
}
