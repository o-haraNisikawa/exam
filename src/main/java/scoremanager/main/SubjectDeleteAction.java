package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        // セッション取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 未ログイン対策
        if (teacher == null) {

            res.sendRedirect("login.jsp");

            return;
        }

        // パラメータ取得
        String cd = req.getParameter("cd");

        // 未指定チェック
        if (cd == null || cd.isEmpty()) {
            req.setAttribute("error", "科目コードが指定されていません");
            req.getRequestDispatcher("SubjectList.action").forward(req, res);
            return;
        }

        // 科目取得
        SubjectDao sDao = new SubjectDao();
        Subject subject =sDao.get(cd, teacher.getSchool());

        // 存在チェック
        if (subject == null) {
            req.setAttribute("error", "科目が存在しません");
            req.getRequestDispatcher("SubjectList.action").forward(req, res);
            return;
        }

        // 論理削除
        sDao.delete(subject);

        // 一覧へ戻る
        res.sendRedirect("SubjectList.action");
    }
}