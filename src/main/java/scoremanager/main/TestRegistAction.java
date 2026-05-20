package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
import tool.Util;

public class TestRegistAction extends Action {

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
        String noStr = req.getParameter("f4");
        String btn = req.getParameter("btn");

        int entYear = 0;
        int no = 0;

        if (entYearStr != null && !entYearStr.isEmpty()) entYear = Integer.parseInt(entYearStr);
        if (noStr != null && !noStr.isEmpty()) no = Integer.parseInt(noStr);

        // ▼ 保存処理
        if ("save".equals(btn)) {

            entYear = Integer.parseInt(req.getParameter("f1"));
            classNum = req.getParameter("f2");
            subjectCd = req.getParameter("f3");
            no = Integer.parseInt(req.getParameter("f4"));

            String[] studentNos = req.getParameterValues("student_no");
            String[] points = req.getParameterValues("point");

            List<Test> list = new ArrayList<>();
            List<Integer> errorIndexList = new ArrayList<>();

            for (int i = 0; i < studentNos.length; i++) {

                int p = -1;

                if (points == null || i >= points.length) {
                    errorIndexList.add(i);
                } else {
                    String pointStr = points[i];

                    if (pointStr == null || pointStr.trim().isEmpty()) {
                        errorIndexList.add(i);
                    } else {
                        try {
                            p = Integer.parseInt(pointStr);
                            if (p < 0 || p > 100) {
                                errorIndexList.add(i);
                            }
                        } catch (Exception e) {
                            errorIndexList.add(i);
                        }
                    }
                }

                Test test = new Test();

                Student st = new Student();
                st.setNo(studentNos[i]);
                st.setSchool(teacher.getSchool());
                test.setStudent(st);

                Subject sub = new Subject();
                sub.setCd(subjectCd);
                test.setSubject(sub);

                test.setSchool(teacher.getSchool());
                test.setClassNum(classNum);
                test.setNo(no);
                test.setPoint(p);

                list.add(test);
            }

            if (!errorIndexList.isEmpty()) {

                StudentDao sDao = new StudentDao();
                List<Student> studentListForJsp = new ArrayList<>();

                for (Test t : list) {
                    Student st = sDao.get(t.getStudent().getNo());
                    st.setPoint(t.getPoint());
                    studentListForJsp.add(st);
                }

                req.setAttribute("errorIndexList", errorIndexList);
                req.setAttribute("student_list", studentListForJsp);

                Util.setSubjects(req);
                req.setAttribute("subject_name", Util.getSubjectName(req, subjectCd));

                Util.setEntYearSet(req);
                Util.setClassNumSet(req);
                Util.setSubjects(req);
                Util.setNumSet(req);

                req.setAttribute("f1", entYear);
                req.setAttribute("f2", classNum);
                req.setAttribute("f3", subjectCd);
                req.setAttribute("f4", no);

                req.getRequestDispatcher("/scoremanager/main/test_regist.jsp")
                   .forward(req, res);
                return;
            }

            TestDao tDao = new TestDao();
            tDao.save(list);

            res.sendRedirect("/exam/scoremanager/main/test_regist_done.jsp");
            return;
        }

        // ▼ 検索処理
        if (entYear > 0 && !classNum.equals("0") && !subjectCd.equals("0") && no > 0) {

            StudentDao sDao = new StudentDao();
            TestDao tDao = new TestDao();

            List<Student> students =
                sDao.filterAll(teacher.getSchool(), entYear, classNum);

            if (students == null || students.isEmpty()) {

                req.setAttribute("error_student_notfound", true);

                Util.setEntYearSet(req);
                Util.setClassNumSet(req);
                Util.setSubjects(req);
                Util.setNumSet(req);

                req.setAttribute("f1", entYear);
                req.setAttribute("f2", classNum);
                req.setAttribute("f3", subjectCd);
                req.setAttribute("f4", no);

                req.getRequestDispatcher("/scoremanager/main/test_regist.jsp")
                   .forward(req, res);
                return;
            }

            // ★ 既存点数を取得して Student にセット
            for (Student st : students) {
                Test test = tDao.get(st.getNo(), subjectCd, teacher.getSchool(), no);
                if (test != null) {
                    st.setPoint(test.getPoint());
                }
            }

            req.setAttribute("student_list", students);

            Util.setSubjects(req);
            req.setAttribute("subject_name", Util.getSubjectName(req, subjectCd));
        }

        Util.setEntYearSet(req);
        Util.setClassNumSet(req);
        Util.setSubjects(req);
        Util.setNumSet(req);

        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", no);

        req.getRequestDispatcher("/scoremanager/main/test_regist.jsp").forward(req, res);
    }
}
