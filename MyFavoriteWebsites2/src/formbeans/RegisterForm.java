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

public class RegisterForm {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String confirm;

    public RegisterForm(HttpServletRequest request) {
        email = request.getParameter("email");
        firstName = request.getParameter("firstName");
        lastName = request.getParameter("lastName");
        password = request.getParameter("password");
        confirm = request.getParameter("confirm");
    }

    public String getEmail() {
        return email;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirm() {
        return confirm;
    }

    public boolean isPresent() {
        return (email != null 
                || firstName != null 
                || lastName != null
                || password != null 
                || confirm != null);
    }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();
        
        if (email == null || email.trim().length() == 0) {
            errors.add("E-mail Address is required");
        }
        
        if (firstName == null || firstName.length() == 0) {
            errors.add("First Name is required");
        }
        
        if (lastName == null || lastName.length() == 0) {
            errors.add("Last Name is required");
        }
        
        if (password == null || password.length() == 0) {
            errors.add("Password is required");
        }

        if (confirm == null || confirm.length() == 0) {
            errors.add("Please Confirm your password");
        }
        
        if (!password.equals(confirm)) {
            errors.add("Password does not match the confirm password");
        }
        
        if (errors.size() > 0) {
            return errors;
        }

        return errors;
    }

}
