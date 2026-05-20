package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao {

    private String baseSql = "select * from student where school_cd = ? ";

    // ▼ 1件取得
    public Student get(String no) throws Exception {
        Student student = new Student();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                "select * from student where no=?"
            );
            statement.setString(1, no);

            ResultSet rSet = statement.executeQuery();
            SchoolDao schoolDao = new SchoolDao();

            if (rSet.next()) {
                student.setNo(rSet.getString("no"));
                student.setName(rSet.getString("name"));
                student.setEntYear(rSet.getInt("ent_year"));
                student.setClassNum(rSet.getString("class_num"));
                student.setAttend(rSet.getBoolean("is_attend"));
                student.setSchool(schoolDao.get(rSet.getString("school_cd")));
            } else {
                student = null;
            }

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return student;
    }

    // ▼ postFilter
    private List<Student> postFilter(ResultSet rSet, School school) throws Exception {
        List<Student> list = new ArrayList<>();

        while (rSet.next()) {
            Student student = new Student();
            student.setNo(rSet.getString("no"));
            student.setName(rSet.getString("name"));
            student.setEntYear(rSet.getInt("ent_year"));
            student.setClassNum(rSet.getString("class_num"));
            student.setAttend(rSet.getBoolean("is_attend"));
            student.setSchool(school);
            list.add(student);
        }

        return list;
    }

    // ▼ 成績登録用（在学中フラグを無視して全員取得）
    public List<Student> filterAll(School school, int entYear, String classNum) throws Exception {

        List<Student> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        String sql =
            "select * from student " +
            "where school_cd = ? " +
            "  and ent_year = ? " +
            "  and class_num = ? " +
            "order by no asc";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, school.getCd());
            statement.setInt(2, entYear);
            statement.setString(3, classNum);

            rSet = statement.executeQuery();
            list = postFilter(rSet, school);

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    // ▼ 在学中のみ（既存機能：学生管理画面用）
    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {

        List<Student> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        String condition = "and ent_year=? and class_num=? ";
        String order = " order by no asc ";
        String conditionIsAttend = "";

        if (isAttend) {
            conditionIsAttend = " and is_attend=true ";
        }

        try {
            statement = connection.prepareStatement(
                baseSql + condition + conditionIsAttend + order
            );
            statement.setString(1, school.getCd());
            statement.setInt(2, entYear);
            statement.setString(3, classNum);

            rSet = statement.executeQuery();
            list = postFilter(rSet, school);

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    // ▼ 在学中のみ（入学年度だけ）
    public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {

        List<Student> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        String condition = " and ent_year=? ";
        String order = " order by no asc ";
        String conditionIsAttend = "";

        if (isAttend) {
            conditionIsAttend = " and is_attend=true ";
        }

        try {
            statement = connection.prepareStatement(
                baseSql + condition + conditionIsAttend + order
            );
            statement.setString(1, school.getCd());
            statement.setInt(2, entYear);

            rSet = statement.executeQuery();
            list = postFilter(rSet, school);

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    // ▼ 在学中のみ（学校のみ）
    public List<Student> filter(School school, boolean isAttend) throws Exception {

        List<Student> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        String order = " order by no asc ";
        String conditionIsAttend = "";

        if (isAttend) {
            conditionIsAttend = " and is_attend=true ";
        }

        try {
            statement = connection.prepareStatement(
                baseSql + conditionIsAttend + order
            );
            statement.setString(1, school.getCd());

            rSet = statement.executeQuery();
            list = postFilter(rSet, school);

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    // ▼ 保存
    public boolean save(Student student) throws Exception {

        String sql =
            "INSERT INTO student " +
            "(no, name, ent_year, class_num, is_attend, school_cd) " +
            "VALUES (?, ?, ?, ?, ?, ?) " +
            "ON CONFLICT (no) DO UPDATE SET " +
            "name = EXCLUDED.name, " +
            "ent_year = EXCLUDED.ent_year, " +
            "class_num = EXCLUDED.class_num, " +
            "is_attend = EXCLUDED.is_attend, " +
            "school_cd = EXCLUDED.school_cd";

        try (
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, student.getNo());
            statement.setString(2, student.getName());
            statement.setInt(3, student.getEntYear());
            statement.setString(4, student.getClassNum());
            statement.setBoolean(5, student.isAttend());
            statement.setString(6, student.getSchool().getCd());

            return statement.executeUpdate() > 0;
        }
    }

    /// ▼ 削除
    public boolean delete(String no, String school_cd)
            throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {

            connection.setAutoCommit(false);

            statement = connection.prepareStatement("delete from test where student_no=? and school_cd=?");
            statement.setString(1,no);
            statement.setString(2,school_cd);
            statement.executeUpdate();

            statement = connection.prepareStatement(
                "delete from student where no=? and school_cd=?"
            );
            statement.setString(1,no);
            statement.setString(2,school_cd);

            count = statement.executeUpdate();

            connection.commit();

        } catch(Exception e) {

            connection.rollback();
            throw e;

        } finally {

            connection.close();

        }

        return count > 0;
    }
}
