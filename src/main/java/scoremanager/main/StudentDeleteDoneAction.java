package scoremanager.main;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentDeleteDoneAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        HttpSession session = req.getSession();

        Teacher teacher = (Teacher) session.getAttribute("user");

        // セッション切れ対策
        if (teacher == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        // パラメータ取得
        String no = req.getParameter("no");
        String sc_cd = req.getParameter("school_cd");

        // 科目オブジェクト作成
        Student student = new Student();

		student.setNo(no);
        student.setName(sc_cd);

        StudentDao sDao = new StudentDao();
        
        // 削除
        sDao.delete(no,sc_cd);
        // JSPにフォワード
        req.getRequestDispatcher("student_delete_done.jsp").forward(req, res);
    }
}