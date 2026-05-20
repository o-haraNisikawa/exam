package scoremanager.main;

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
    	Teacher teacher = (Teacher)session.getAttribute("user");

    	String no = req.getParameter("no");

    	// school_cdは教師から取得
    	String sc_cd = teacher.getSchool().getCd();

    	StudentDao sDao = new StudentDao();
    	sDao.delete(no, sc_cd);
    	
        // JSPにフォワード
        req.getRequestDispatcher("student_delete_done.jsp").forward(req, res);
    }
}