package tool;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class Util {

    // ▼ ログインユーザ取得（Teacher）
    public static Teacher getUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false); // ★ 安全な取得
        if (session == null) return null;
        return (Teacher) session.getAttribute("user");
    }

    // ▼ クラス番号セット
    public static void setClassNumSet(HttpServletRequest req) throws Exception {
        Teacher teacher = getUser(req);
        if (teacher == null) return;

        ClassNumDao cDao = new ClassNumDao();
        List<String> list = cDao.filter(teacher.getSchool());
        req.setAttribute("class_num_set", list);
    }

    // ▼ 入学年度セット（現在年 +1 ～ -5）
    public static void setEntYearSet(HttpServletRequest req) {
        int year = LocalDate.now().getYear();
        List<Integer> entYearSet = new ArrayList<>();

        for (int i = year + 1; i >= year - 5; i--) {
            entYearSet.add(i);
        }

        req.setAttribute("ent_year_set", entYearSet);
    }

    // ▼ 科目セット（subject_set）
    public static void setSubjects(HttpServletRequest req) throws Exception {
        Teacher teacher = getUser(req);
        if (teacher == null) return;

        SubjectDao sDao = new SubjectDao();
        List<Subject> list = sDao.filter(teacher.getSchool(), true);

        req.setAttribute("subject_set", list);
    }

    // ▼ 科目名取得（TestListAction で使用）
    public static String getSubjectName(HttpServletRequest req, String subjectCd) {
        List<Subject> list = (List<Subject>) req.getAttribute("subject_set");
        if (list == null) return null;

        for (Subject s : list) {
            if (s.getCd().equals(subjectCd)) {
                return s.getName();
            }
        }
        return null;
    }

    // ▼ 回数セット（1回目・2回目）
    public static void setNumSet(HttpServletRequest req) {
        List<Integer> numSet = new ArrayList<>();
        numSet.add(1);
        numSet.add(2);
        req.setAttribute("num_set", numSet);
    }
}

