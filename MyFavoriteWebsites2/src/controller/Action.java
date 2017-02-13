/**
 * 08-672 Assignment 4.
 * @author Jiayi Xie
 * @id jiayix
 * 12/12/2016
 */
package controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract class Action {

    public abstract String getName();

    public abstract String perform(HttpServletRequest request);

    private static Map<String, Action> map = new HashMap<String, Action>();

    public static void add(Action a) {
        synchronized (map) {
            map.put(a.getName(), a);
        }
    }

    public static String perform(String name, HttpServletRequest request) {
        Action a;
        synchronized (map) {
            a = map.get(name);
        }

        if (a == null)
            return null;
        return a.perform(request);
    }
}
