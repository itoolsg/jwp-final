package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import next.model.Answer;

public class AnswerDao {

	public void insert(Answer answer) throws SQLException {
		String sql = "INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";
		boolean is_success = QueryTemplate.executeQuery(sql,
				answer.getWriter(), answer.getContents(),
				new Timestamp(answer.getTimeFromCreateDate()),
				answer.getQuestionId());

		if (!is_success)
			return;

		String countplussql = "update QUESTIONS set countOfComment = countOfComment + 1 where questionId = ?";
		QueryTemplate.executeQuery(countplussql, answer.getQuestionId());

	}

	public List<Answer> findAllByQuestionId(final long questionId)
			throws SQLException {

		String sql = "SELECT answerId, writer, contents, createdDate FROM ANSWERS WHERE questionId = ? "
				+ "order by answerId desc";

		ReadTemplate<List<Answer>> template = new ReadTemplate<List<Answer>>(
				sql, questionId) {
			@Override
			public List<Answer> read(ResultSet rs) throws SQLException {
				List<Answer> answers = new ArrayList<Answer>();
				Answer answer = null;
				while (rs.next()) {
					answer = new Answer(rs.getLong("answerId"),
							rs.getString("writer"), rs.getString("contents"),
							rs.getTimestamp("createdDate"), questionId);
					answers.add(answer);
				}
				return answers;
			}

		};

		return template.execute();
	}
}
