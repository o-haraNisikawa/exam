package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        String cd = req.getParameter("no");
        String name = req.getParameter("name");

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // バリデーション例
        Map<String, String> errors = new HashMap<>();
        if (cd == null || cd.isEmpty()) {
            errors.put("f1", "科目コードを入力してください");
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);
            req.getRequestDispatcher("SubjectCreate.action").forward(req, res);
            return;
        }

        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool());

        SubjectDao dao = new SubjectDao();
        dao.save(subject);

        res.sendRedirect("SubjectList.action");
    }
}
