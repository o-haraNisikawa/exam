package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

    // ▼ 1件取得（Student / Subject を渡す従来版）
    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        Test test = new Test();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "select * from test where student_no=? and subject_cd=? and school_cd=? and no=?"
            );
            statement.setString(1, student.getNo());
            statement.setString(2, subject.getCd());
            statement.setString(3, school.getCd());
            statement.setInt(4, no);

            ResultSet rSet = statement.executeQuery();

            if (rSet.next()) {
                test.setStudent(student);
                test.setClassNum(rSet.getString("class_num"));
                test.setSubject(subject);
                test.setSchool(school);
                test.setNo(no);
                test.setPoint(rSet.getInt("point"));
            } else {
                test = null;
            }

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return test;
    }

    // ★★★ 追加：簡易 get（TestRegistAction から使う用）★★★
    public Test get(String studentNo, String subjectCd, School school, int no) throws Exception {

        Test test = null;

        String sql =
            "select point " +
            "from test " +
            "where student_no=? and subject_cd=? and school_cd=? and no=?";

        try (
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, studentNo);
            ps.setString(2, subjectCd);
            ps.setString(3, school.getCd());
            ps.setInt(4, no);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                test = new Test();
                test.setPoint(rs.getInt("point"));
            }
        }

        return test;
    }

    // ▼ postFilter（成績参照用）
    private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
        List<Test> list = new ArrayList<>();

        while (rSet.next()) {
            Test test = new Test();
            Student student = new Student();
            Subject subject = new Subject();

            student.setNo(rSet.getString("student_no"));
            student.setName(rSet.getString("student_name"));
            student.setEntYear(rSet.getInt("ent_year"));
            student.setClassNum(rSet.getString("class_num"));
            student.setSchool(school);

            subject.setCd(rSet.getString("subject_cd"));
            subject.setName(rSet.getString("subject_name"));

            test.setStudent(student);
            test.setSubject(subject);
            test.setSchool(school);
            test.setClassNum(rSet.getString("class_num"));
            test.setNo(rSet.getInt("num"));
            test.setPoint(rSet.getInt("point"));

            list.add(test);
        }

        return list;
    }

    // ▼ 成績参照（GRMR001〜003）専用
    public List<Test> filter(int entYear, String classNum, Subject subject,
                             int num, School school) throws Exception {

        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        String sql =
            "select " +
            " t.student_no, " +
            " st.ent_year, " +
            " st.name as student_name, " +
            " t.subject_cd, " +
            " s.name as subject_name, " +
            " t.no as num, " +
            " t.point, " +
            " t.class_num " +
            "from test t " +
            "join student st on t.student_no = st.no " +
            "               and t.school_cd = st.school_cd " +
            "join subject s on t.subject_cd = s.cd " +
            "               and t.school_cd = s.school_cd " +
            "where st.ent_year = ? " +
            "  and t.class_num = ? " +
            "  and t.subject_cd = ? " +
            "  and t.no = ? " +
            "  and t.school_cd = ? " +
            "order by st.no";

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, entYear);
            statement.setString(2, classNum);
            statement.setString(3, subject.getCd());
            statement.setInt(4, num);
            statement.setString(5, school.getCd());

            rSet = statement.executeQuery();
            list = postFilter(rSet, school);

            if (list.isEmpty()) list = null;

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    // ▼ 保存（MERGE）
    public boolean save(List<Test> list) throws Exception {
        Connection connection = getConnection();

        for (Test test : list) {
            save(test, connection);
        }

        connection.close();
        return true;
    }

    private boolean save(Test test, Connection connection) throws Exception {

        PreparedStatement statement = null;
        int count = 0;

        try {
            statement = connection.prepareStatement(
                "INSERT INTO test " +
                "(student_no, subject_cd, school_cd, no, point, class_num) " +
                "VALUES (?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (student_no, subject_cd, school_cd, no) DO UPDATE SET " +
                "point = EXCLUDED.point, " +
                "class_num = EXCLUDED.class_num"
            );

            statement.setString(1, test.getStudent().getNo());
            statement.setString(2, test.getSubject().getCd());
            statement.setString(3, test.getSchool().getCd());
            statement.setInt(4, test.getNo());
            statement.setInt(5, test.getPoint());
            statement.setString(6, test.getClassNum());

            count = statement.executeUpdate();

        } finally {
            if (statement != null) statement.close();
        }

        return count > 0;
    }

}
