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

        /* ============================================================
         * ▼ 科目別検索（btn=31）
         * ============================================================ */
        if ("31".equals(btn)) {

            req.setAttribute("btn", "31");

            // 入力チェック（科目検索）
            if (entYear == 0 || "0".equals(classNum) || "0".equals(subjectCd)) {

                req.setAttribute("error_subject", true);

                Util.setEntYearSet(req);
                Util.setClassNumSet(req);
                Util.setSubjects(req);

                req.setAttribute("f1", entYear);
                req.setAttribute("f2", classNum);
                req.setAttribute("f3", subjectCd);
                req.setAttribute("student_no", studentNo);

                req.getRequestDispatcher("/scoremanager/main/test_list.jsp")
                   .forward(req, res);
                return;
            }

            Subject subject = new Subject();
            subject.setCd(subjectCd);

            TestListSubjectDao dao = new TestListSubjectDao();
            List<TestListSubject> list =
                    dao.filter(entYear, classNum, subject, teacher.getSchool());

            req.setAttribute("list", list);

            req.setAttribute("f1", entYear);
            req.setAttribute("f2", classNum);
            req.setAttribute("f3", subjectCd);
            req.setAttribute("student_no", studentNo);

            Util.setSubjects(req);
            req.setAttribute("subject_name", Util.getSubjectName(req, subjectCd));
            Util.setNumSet(req);

            Util.setEntYearSet(req);
            Util.setClassNumSet(req);
            Util.setSubjects(req);

            req.getRequestDispatcher("/scoremanager/main/test_list_subject.jsp")
               .forward(req, res);
            return;
        }

        /* ============================================================
         * ▼ 学生別検索（btn=32）
         * ============================================================ */
        if ("32".equals(btn)) {

            req.setAttribute("btn", "32");

            // ★ required を削除したので、空欄チェックを Action 側で行う
            if (studentNo == null || studentNo.isEmpty()) {

                req.setAttribute("error_student", true);

                Util.setEntYearSet(req);
                Util.setClassNumSet(req);
                Util.setSubjects(req);

                req.setAttribute("f1", entYear);
                req.setAttribute("f2", classNum);
                req.setAttribute("f3", subjectCd);
                req.setAttribute("student_no", studentNo);

                req.getRequestDispatcher("/scoremanager/main/test_list.jsp")
                   .forward(req, res);
                return;
            }

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

            req.setAttribute("f1", entYear);
            req.setAttribute("f2", classNum);
            req.setAttribute("f3", subjectCd);
            req.setAttribute("student_no", studentNo);

            Util.setEntYearSet(req);
            Util.setClassNumSet(req);
            Util.setSubjects(req);

            req.getRequestDispatcher("/scoremanager/main/test_list_student.jsp")
               .forward(req, res);
            return;
        }

        /* ============================================================
         * ▼ 初期表示
         * ============================================================ */
        req.setAttribute("btn", null);

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
