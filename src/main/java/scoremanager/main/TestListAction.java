package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import bean.TestListSubject;
import dao.StudentDao;
import dao.TestListStudentDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
import tool.Util;

public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        Teacher teacher = Util.getUser(req);
        if (teacher == null) {
            res.sendRedirect("../Login.action");
            return;
        }

        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");
        String studentNo = req.getParameter("student_no");
        String btn = req.getParameter("btn");

        int entYear = 0;
        if (entYearStr != null && !entYearStr.isEmpty()) {
            entYear = Integer.parseInt(entYearStr);
        }

        // ▼ 科目別（btn=31）
        if ("31".equals(btn)) {

            // ▼ 条件不足チェック
            if (entYear == 0 || "0".equals(classNum) || "0".equals(subjectCd)) {

                req.setAttribute("error_subject", true);

                // ▼ プルダウンセット
                Util.setEntYearSet(req);
                Util.setClassNumSet(req);
                Util.setSubjects(req);

                // ▼ 入力保持
                req.setAttribute("f1", entYear);
                req.setAttribute("f2", classNum);
                req.setAttribute("f3", subjectCd);
                req.setAttribute("student_no", studentNo);

                req.getRequestDispatcher("/scoremanager/main/test_list.jsp")
                   .forward(req, res);
                return;
            }

            // ▼ 条件が揃っている場合は検索実行
            Subject subject = new Subject();
            subject.setCd(subjectCd);

            TestListSubjectDao dao = new TestListSubjectDao();
            List<TestListSubject> list =
                    dao.filter(entYear, classNum, subject, teacher.getSchool());

            req.setAttribute("list", list);

            // ▼ 検索フォームの値を渡す
            req.setAttribute("f1", entYear);
            req.setAttribute("f2", classNum);
            req.setAttribute("f3", subjectCd);
            req.setAttribute("student_no", studentNo);

            // 科目名取得
            Util.setSubjects(req);
            String subjectName = Util.getSubjectName(req, subjectCd);
            req.setAttribute("subject_name", subjectName);

            // 回数セット
            Util.setNumSet(req);

            // ▼ プルダウンセット
            Util.setEntYearSet(req);
            Util.setClassNumSet(req);
            Util.setSubjects(req);

            req.getRequestDispatcher("/scoremanager/main/test_list_subject.jsp")
               .forward(req, res);
            return;
        }

        // ▼ 学生別（btn=32）
        if ("32".equals(btn)) {

            // ▼ 学生番号未入力エラー
            if (studentNo == null || studentNo.isEmpty()) {

                req.setAttribute("error_student_no", true);

                // ▼ プルダウンセット
                Util.setEntYearSet(req);
                Util.setClassNumSet(req);
                Util.setSubjects(req);

                // ▼ 入力保持
                req.setAttribute("f1", entYear);
                req.setAttribute("f2", classNum);
                req.setAttribute("f3", subjectCd);
                req.setAttribute("student_no", studentNo);

                req.getRequestDispatcher("/scoremanager/main/test_list.jsp")
                   .forward(req, res);
                return;
            }

            // ▼ 学生番号が入力されている場合は検索実行
            StudentDao sDao = new StudentDao();
            Student st = sDao.get(studentNo);

            if (st != null) {
                req.setAttribute("student_name", st.getName());
                req.setAttribute("ent_year", st.getEntYear());
                req.setAttribute("class_num", st.getClassNum());
            }

            Student student = new Student();
            student.setNo(studentNo);
            student.setSchool(teacher.getSchool());

            TestListStudentDao dao = new TestListStudentDao();
            List<TestListStudent> list = dao.filter(student);

            req.setAttribute("list", list);

            // ▼ 検索フォームの値を渡す
            req.setAttribute("f1", entYear);
            req.setAttribute("f2", classNum);
            req.setAttribute("f3", subjectCd);
            req.setAttribute("student_no", studentNo);

            // ▼ プルダウンセット
            Util.setEntYearSet(req);
            Util.setClassNumSet(req);
            Util.setSubjects(req);

            req.getRequestDispatcher("/scoremanager/main/test_list_student.jsp")
               .forward(req, res);
            return;
        }

        // ▼ 初期表示（検索画面）
        Util.setEntYearSet(req);
        Util.setClassNumSet(req);
        Util.setSubjects(req);

        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("student_no", studentNo);

        req.getRequestDispatcher("/scoremanager/main/test_list.jsp").forward(req, res);
    }
}
