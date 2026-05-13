package scoremanager.main;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        // パラメータ取得
        String cd = req.getParameter("cd");
        Teacher teacher = (Teacher) req.getSession().getAttribute("user");

        // 教師が所属する学校を取得
        School school = teacher.getSchool();

        // 科目取得
        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(cd, school);


        // JSP に渡す
        req.setAttribute("subject", subject);

        // 画面遷移
        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
    }
}
