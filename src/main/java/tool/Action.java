package tool;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class Action {

    public abstract void execute(HttpServletRequest req, HttpServletResponse res) throws Exception;

    // JSP へフォワードする共通メソッド
    protected void forward(HttpServletRequest req, HttpServletResponse res, String path) throws Exception {
        RequestDispatcher rd = req.getRequestDispatcher("/scoremanager/main/" + path);
        rd.forward(req, res);
    }
}
