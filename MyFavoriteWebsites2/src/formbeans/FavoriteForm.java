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

public class FavoriteForm {
    private String url;
    private String comment;

    public FavoriteForm(HttpServletRequest request) {
        url = request.getParameter("url");
        comment = request.getParameter("comment");
    }

    public String getUrl() {
        return url;
    }

    public String getComment() {
        return comment;
    }

    public boolean isPresent() {
        return url != null;
    }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();
        if (url == null || url.trim().length() == 0) {
            errors.add("URL is required");
        }
        if (comment == null || comment.trim().length() == 0) {
            errors.add("Comment is required");
        }
        return errors;
    }
}
