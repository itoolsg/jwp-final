package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import next.model.Question;
import next.support.db.ConnectionManager;

public class QuestionDao {

	public void insert(Question question) throws SQLException {
		String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfComment) VALUES (?, ?, ?, ?, ?)";
		QueryTemplate.executeQuery(sql, question.getWriter(), question
				.getTitle(), question.getContents(),
				new Timestamp(question.getTimeFromCreateDate()), question
						.getCountOfComment());
	}

	public List<Question> findAll() throws SQLException {

		String sql = "SELECT questionId, writer, title, createdDate, countOfComment FROM QUESTIONS "
				+ "order by questionId desc";
		ReadTemplate<List<Question>> template = new ReadTemplate<List<Question>>(
				sql) {

			@Override
			public List<Question> read(ResultSet rs) throws SQLException {
				List<Question> questions = new ArrayList<Question>();
				Question question = null;
				while (rs.next()) {
					question = new Question(rs.getLong("questionId"),
							rs.getString("writer"), rs.getString("title"),
							null, rs.getTimestamp("createdDate"),
							rs.getInt("countOfComment"));
					questions.add(question);
				}

				return questions;
			}
		};

		return template.execute();
	}

	public Question findById(long questionId) throws SQLException {
		String sql = "SELECT questionId, writer, title, contents, createdDate, countOfComment FROM QUESTIONS "
				+ "WHERE questionId = ?";

		ReadTemplate<Question> template = new ReadTemplate<Question>(sql,
				questionId) {

			@Override
			public Question read(ResultSet rs) throws SQLException {
				Question question = null;
				if (rs.next()) {
					question = new Question(rs.getLong("questionId"),
							rs.getString("writer"), rs.getString("title"),
							rs.getString("contents"),
							rs.getTimestamp("createdDate"),
							rs.getInt("countOfComment"));
					return question;
				}
				return question;
			}
		};
		return template.execute();
	}
}
