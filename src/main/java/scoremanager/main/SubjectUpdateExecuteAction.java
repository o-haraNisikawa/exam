package scoremanager.main;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        HttpSession session = req.getSession();

        // ログイン中の教師を取得
        Teacher teacher = (Teacher) session.getAttribute("user");

        // セッション切れ対策
        if (teacher == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        // 教師が所属する学校を取得
        School school = teacher.getSchool();

        // パラメータ取得
        String cd = req.getParameter("cd");
        String name = req.getParameter("name");

        // 入力チェック（任意）
        if (cd == null || cd.isEmpty() || name == null || name.isEmpty()) {
            req.setAttribute("error", "科目名または科目コードが未入力です");
            req.getRequestDispatcher("subject_update.jsp").forward(req, res);
            return;
        }

        // 科目オブジェクト作成
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(school);

        // DAOで保存（更新 or 追加）
        SubjectDao sDao = new SubjectDao();
<<<<<<< HEAD
        sDao.save(subject);
        sDao.delete(subject);
=======
        sDao.update(subject);

>>>>>>> branch 'master' of https://github.com/o-haraNisikawa/exam
        // 完了画面へ
        req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
    }
}
