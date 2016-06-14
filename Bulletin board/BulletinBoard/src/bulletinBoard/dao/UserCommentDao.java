package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.Comment;
import bulletinBoard.exception.SQLRuntimeException;

public class UserCommentDao {
	public List<Comment> getUserComment(Connection connection, int num){
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("select * from comments ");
			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<Comment> ret = toUserCommentList(rs);
			return ret;
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}
	private List<Comment> toUserCommentList(ResultSet rs) throws SQLException{

		List<Comment> ret = new ArrayList<Comment>();
		try{
			while (rs.next()){


				String body = rs.getString("body");
				String name = rs.getString("name");
				int post_id = rs.getInt("post_id");
				Timestamp insert_date = rs.getTimestamp("insert_date");

				Comment comment = new Comment();

				comment.setBody(body);
				comment.setName(name);
				comment.setPost_id(post_id);
				comment.setInsert_date(insert_date);

				ret.add(comment);

			}return ret;
		}finally{
			close(rs);
		}
	}
}
