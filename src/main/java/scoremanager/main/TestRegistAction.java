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

        // ▼ ログインチェック
        Teacher teacher = Util.getUser(req);
        if (teacher == null) {
            res.sendRedirect("../Login.action");
            return;
        }

        // ▼ パラメータ取得
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

            // POST 時に f1〜f4 を再取得
            entYearStr = req.getParameter("f1");
            classNum   = req.getParameter("f2");
            subjectCd  = req.getParameter("f3");
            noStr      = req.getParameter("f4");

            entYear = Integer.parseInt(entYearStr);
            no      = Integer.parseInt(noStr);

            String[] studentNos = req.getParameterValues("student_no");
            String[] points = req.getParameterValues("point");

            List<Test> list = new ArrayList<>();
            List<Integer> errorIndexList = new ArrayList<>();

            // ▼ 入力チェック（0～100）
            for (int i = 0; i < studentNos.length; i++) {

                int p = -1;
                try { p = Integer.parseInt(points[i]); } catch (Exception e) {}

                if (p < 0 || p > 100) {
                    errorIndexList.add(i);
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

            // ▼ エラーがある場合は JSP に戻す
            if (!errorIndexList.isEmpty()) {

                req.setAttribute("errorIndexList", errorIndexList);
                req.setAttribute("student_list", list); // 入力値保持のため

                // ▼ 科目名セット（検索結果表示用）
                Util.setSubjects(req);
                String subjectName = Util.getSubjectName(req, subjectCd);
                req.setAttribute("subject_name", subjectName);

                // ▼ プルダウンセット
                Util.setEntYearSet(req);
                Util.setClassNumSet(req);
                Util.setSubjects(req);
                Util.setNumSet(req);

                // ▼ 検索条件保持
                req.setAttribute("f1", entYear);
                req.setAttribute("f2", classNum);
                req.setAttribute("f3", subjectCd);
                req.setAttribute("f4", no);

                req.getRequestDispatcher("/scoremanager/main/test_regist.jsp")
                   .forward(req, res);
                return;
            }

            // ▼ 正常保存
            TestDao tDao = new TestDao();
            tDao.save(list);

            res.sendRedirect("/exam/scoremanager/main/test_regist_done.jsp");
            return;
        }

        // ▼ 検索処理（学生一覧）
        if (entYear > 0 && !classNum.equals("0") && !subjectCd.equals("0") && no > 0) {

            StudentDao sDao = new StudentDao();
            List<Student> students =
                sDao.filterAll(teacher.getSchool(), entYear, classNum);

            // ★ 学生情報が存在しない場合のエラー
            if (students == null || students.isEmpty()) {

                req.setAttribute("error_student_notfound", true);

                // ▼ プルダウンセット
                Util.setEntYearSet(req);
                Util.setClassNumSet(req);
                Util.setSubjects(req);
                Util.setNumSet(req);

                // ▼ 検索条件保持
                req.setAttribute("f1", entYear);
                req.setAttribute("f2", classNum);
                req.setAttribute("f3", subjectCd);
                req.setAttribute("f4", no);

                req.getRequestDispatcher("/scoremanager/main/test_regist.jsp")
                   .forward(req, res);
                return;
            }

            req.setAttribute("student_list", students);

            // ▼ 科目名セット（検索結果表示用）
            Util.setSubjects(req);
            String subjectName = Util.getSubjectName(req, subjectCd);
            req.setAttribute("subject_name", subjectName);
        }

        // ▼ プルダウンセット
        Util.setEntYearSet(req);
        Util.setClassNumSet(req);
        Util.setSubjects(req);
        Util.setNumSet(req);

        // ▼ 検索条件保持
        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", no);

        req.getRequestDispatcher("/scoremanager/main/test_regist.jsp").forward(req, res);
    }
}
