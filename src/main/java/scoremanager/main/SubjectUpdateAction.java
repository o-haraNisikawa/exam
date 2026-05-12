package scoremanager.main;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        // パラメータ取得
        String cd = req.getParameter("cd");
        Teacher teacher = (Teacher) req.getSession().getAttribute("user");

        // 教師が所属する学校を取得
        School school = teacher.getSchool();
     // ★ 学校コードを取得（追加）
        String schoolCd = school.getCd();
        
        // パラメータ未指定なら一覧へ戻す
        if (cd == null || cd.isEmpty()) {
            req.setAttribute("error", "科目コードが指定されていません");
            req.getRequestDispatcher("SubjectList.action").forward(req, res);
            return;
        }

        // 科目取得
        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(cd, school);

        // 科目が存在しない場合
        if (subject == null) {
            req.setAttribute("error", "指定された科目は存在しません");
            req.getRequestDispatcher("SubjectList.action").forward(req, res);
            return;
        }

        // JSP に渡す
        req.setAttribute("subject", subject);
        req.setAttribute("schoolCd", schoolCd);

        // 画面遷移
        req.getRequestDispatcher("subject_update.jsp").forward(req, res);
    }
}
