
package scoremanager.main;

import bean.Teacher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ★元コードと同じ構造に合わせて「初期値」をセット
        // 入力値が残っている場合はそのまま使う
        if (req.getAttribute("cd") == null) {
            req.setAttribute("cd", "");
        }
        if (req.getAttribute("name") == null) {
            req.setAttribute("name", "");
        }

        // ★元コードと同じように JSP をフォワード
        req.getRequestDispatcher("subject_create.jsp").forward(req, res);
    }
}