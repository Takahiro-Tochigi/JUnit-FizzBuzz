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

public class UserCommentViewDao {
	public List<UserComment> getUserCommentView(Connection connection, int num){
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("select *from users_comments ");
			sql.append("Order by insert_date desc limit "+ num);

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
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int name_id = rs.getInt("name_id");
				String body = rs.getString("body");
				int post_id = rs.getInt("post_id");
				Timestamp insert_date = rs.getTimestamp("insert_date");

				UserComment messageView = new UserComment();
				messageView.setId(id);
				messageView.setName(name);
				messageView.setBody(body);
				messageView.setPost_id(post_id);
				messageView.setName_id(name_id);
				messageView.setInsert_date(insert_date);

				ret.add(messageView);

			}return ret;
		}finally{
			close(rs);
		}
	}
}
