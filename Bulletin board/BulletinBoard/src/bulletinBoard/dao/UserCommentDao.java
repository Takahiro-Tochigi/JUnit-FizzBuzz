package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.UserComment;
import bulletinBoard.exception.SQLRuntimeException;

public class UserCommentDao {
	public List<UserComment> getUserComment(Connection connection, int num){
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("select * from users_comments ");
			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserComment> ret = toUserCommentList(rs);
			return ret;
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}
	private List<UserComment> toUserCommentList(ResultSet rs) throws SQLException{

		List<UserComment> ret = new ArrayList<UserComment>();
		try{
			while (rs.next()){

				String name = rs.getString("name");
				String body = rs.getString("body");
				int name_id = rs.getInt("name_id");
				int post_id = rs.getInt("post_id");
				Timestamp insert_date = rs.getTimestamp("insert_date");

				UserComment comment = new UserComment();


				comment.setName(name);
				comment.setBody(body);
				comment.setName_id(name_id);
				comment.setPost_id(post_id);
				comment.setInsert_date(insert_date);

				ret.add(comment);

			}return ret;
		}finally{
			close(rs);
		}
	}
}
