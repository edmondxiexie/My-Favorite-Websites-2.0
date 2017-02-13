/**
 * 08-672 Assignment 4.
 * @author Jiayi Xie
 * @id jiayix
 * 12/12/2016
 */

package formbeans;

import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

public class ChangeForm {
    private String password;
    private String confirm;

    public ChangeForm(HttpServletRequest request) {
        this.password = request.getParameter("password");
        this.confirm = request.getParameter("confirm");
    }

    public boolean isPresent() {
        return password != null || confirm != null;
    }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();
        if (password == null || password.trim().length() == 0) {
            errors.add("Password is missing");
        }
        if (confirm == null || confirm.trim().length() == 0) {
            errors.add("Please confirm your new password");
        }
        if (errors.size() > 0) {
            return errors;
        }
        if (!password.equals(confirm)) {
            errors.add("Password does not match the confirm password");
        }
        return errors;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirm() {
        return confirm;
    }
}
