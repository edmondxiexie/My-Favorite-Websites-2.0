/**
 * 08-672 Assignment 4.
 * @author Jiayi Xie
 * @id jiayix
 * 12/12/2016
 */

package databeans;

public class FavoriteBean {
    private int id;
    private int userId;
    private String comment;
    private String url;
    private int clickCount = 0;

    public FavoriteBean() {}

    public FavoriteBean(int userId, String url, String comment) {
        this.userId = userId;
        this.url = url;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getComment() {
        return comment;
    }

    public String getURL() {
        return url;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setURL(String uRL) {
        url = uRL;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }
}
