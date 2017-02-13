/**
 * 08-672 Assignment 4.
 * @author Jiayi Xie
 * @id jiayix
 * 12/12/2016
 */
package formbeans;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class LoginForm {
    private String email;
    private String password;

    public LoginForm(HttpServletRequest request) {
        email = request.getParameter("email");
        password = request.getParameter("password");
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isPresent() {
        return (email != null || password != null);
    }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (email == null || email.length() == 0) {
            errors.add("E-mail Address is required");
        }
        if (password == null || password.length() == 0) {
            errors.add("Password is required");
        }

        if (errors.size() != 0) {
            return errors;
        }

        return errors;
    }

}
