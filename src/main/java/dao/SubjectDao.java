package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao{

	public Subject get(String cd,School school) throws Exception {
		Subject subject = new Subject();
		subject.setSchool(school);
		// データベースへのコネクションを確率
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from subject where school_cd=? and cd=?");
			// プリペアードステートメントに学生番号をバインド
			statement.setString(1, school.getCd());
			statement.setString(2, cd);
			// プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			if (rSet.next()){
				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
			}else {
				subject = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement !=null) {
				try{
					statement.close();
				}catch (SQLException sqle){
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null){
				try{
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return subject;
	}

	public List<Subject> filter(School school, boolean filter) throws Exception {

		List<Subject> list = new ArrayList<>();

		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;

		try {

			statement = connection.prepareStatement(
				"select * from subject where school_cd=?"
			);

			statement.setString(1, school.getCd());

			rSet = statement.executeQuery();

			while (rSet.next()) {

				Subject subject = new Subject();

				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
				subject.setSchool(school);

				list.add(subject);
			}

		} finally {

			if (rSet != null) rSet.close();
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}

		return list;
	}

	
	public boolean save(Subject subject) throws Exception {

	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    int count = 0;

	    try {

	        String sql =
	            "INSERT INTO subject " +
	            "(cd, name, school_cd) " +
	            "VALUES (?, ?, ?)";

	        statement = connection.prepareStatement(sql);

	        statement.setString(1, subject.getCd());
	        statement.setString(2, subject.getName());
	        statement.setString(3, subject.getSchool().getCd());

	        count = statement.executeUpdate();

	    } finally {

	        if (statement != null) statement.close();
	        if (connection != null) connection.close();
	    }

	    return count > 0;
	}
	public boolean update(String oldCd, Subject subject)
	        throws Exception {

	    Connection connection = getConnection();
	    PreparedStatement statement = null;

	    int count = 0;

	    try {
	        statement = connection.prepareStatement(
	            "update subject " +
	            "set cd=?, name=? " +
	            "where school_cd=? and cd=?"
	        );

	        statement.setString(1, subject.getCd());
	        statement.setString(2, subject.getName());
	        statement.setString(3, subject.getSchool().getCd());
	        statement.setString(4, oldCd);

	        count = statement.executeUpdate();

	    } finally {
	        if (statement != null) statement.close();
	        if (connection != null) connection.close();
	    }
	    return count > 0;
	}


	public boolean delete(Subject subject) throws Exception {

	    Connection connection = getConnection();
	    PreparedStatement statement = null;

	    int count = 0;

	    try {

	        statement = connection.prepareStatement(
	            "delete from subject where school_cd=? and cd=?"
	        );

	        statement.setString(1, subject.getSchool().getCd());
	        statement.setString(2, subject.getCd());

	        count = statement.executeUpdate();

	    } catch (Exception e) {

	        throw e;

	    } finally {

	        if (statement != null) {

	            try {
	                statement.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }

	        if (connection != null) {

	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	    }

	    return count > 0;
	}

	
	public boolean change(String cd,boolean back) throws Exception{
		Connection connection = getConnection();
		PreparedStatement statement = null;
		try{
			statement = connection.prepareStatement(
					"update subject set is_true = true where cd = ?");
			statement.setString(1, cd);
			statement.executeUpdate();
		}catch (Exception e) {
			throw e;
		}finally {
			statement.close();
			connection.close();
		}
		
		return true;
	}
}
