package scoremanager.main;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

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

        // 科目オブジェクト作成
        Subject subject = new Subject();

        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(school);

        // DAOで更新
        SubjectDao sDao = new SubjectDao();

        // 既存科目チェック
        Subject exists = sDao.get(cd, school);

        // 更新
        sDao.delete(subject);
        // 完了画面へ
        req.getRequestDispatcher("subject_delete_done.jsp")
                .forward(req, res);
    }
}