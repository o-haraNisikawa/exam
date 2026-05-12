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

        String cd = req.getParameter("cd");
        String name = req.getParameter("name");

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        Map<String, String> errors = new HashMap<>();

        // 入力チェック
        if (cd == null || cd.isEmpty()) {
            errors.put("cd", "科目コードを入力してください");
        }

        if (name == null || name.isEmpty()) {
            errors.put("name", "科目名を入力してください");
        }

        SubjectDao dao = new SubjectDao();

        // 重複チェック
        Subject exists = dao.get(cd, teacher.getSchool());

        if (exists != null) {
            errors.put("cd", "その科目コードは既に登録されています");
        }

        // エラー時
        if (!errors.isEmpty()) {

            req.setAttribute("errors", errors);
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);

            req.getRequestDispatcher("SubjectCreate.action")
               .forward(req, res);

            return;
        }

        // 保存
        Subject subject = new Subject();

        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool());

        dao.save(subject);

        // 一覧へ
        res.sendRedirect("SubjectList.action");
    }
}