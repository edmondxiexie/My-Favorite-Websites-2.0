/**
 * 08-672 Assignment 4.
 * @author Jiayi Xie
 * @id jiayix
 * 12/12/2016
 */
package models;

/**
 * @author Class Sample: ToDoList3.
 */
public class MyDAOException extends Exception {
    private static final long serialVersionUID = 1L;

    public MyDAOException(Exception e) {
        super(e);
    }

    public MyDAOException(String s) {
        super(s);
    }
}
