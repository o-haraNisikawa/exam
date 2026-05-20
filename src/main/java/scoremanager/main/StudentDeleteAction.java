
package scoremanager.main;

import bean.Student;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        // パラメータ取得
        String no = req.getParameter("no");

        // 学生取得
        StudentDao sDao = new StudentDao();
        Student student = sDao.get(no);

        // JSPへ渡す
        req.setAttribute("student", student);

        // 画面表示
        req.getRequestDispatcher("student_delete.jsp")
           .forward(req, res);
    }
}