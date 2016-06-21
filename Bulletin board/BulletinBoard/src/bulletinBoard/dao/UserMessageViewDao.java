package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.UserMessage;
import bulletinBoard.exception.SQLRuntimeException;

public class UserMessageViewDao {
	public List<UserMessage> getUserMessagesView(Connection connection, int num){
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("select *from users_posts ");
			sql.append("Order by insert_date desc limit "+ num);

			ps = connection.prepareStatement(sql.toString());


			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toUserMessageList(rs);
			return ret;
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	private List<UserMessage> toUserMessageList(ResultSet rs) throws SQLException{

		List<UserMessage> ret = new ArrayList<UserMessage>();
		try{
			while (rs.next()){
				int id = rs.getInt("id");
				String subject = rs.getString("subject");
				String name = rs.getString("name");
				String body = rs.getString("body");
				String category =rs.getString("category");
				int name_id = rs.getInt("name_id");
				Timestamp insert_date = rs.getTimestamp("insert_date");

				UserMessage messageView = new UserMessage();
				messageView.setId(id);
				messageView.setName(name);
				messageView.setSubject(subject);
				messageView.setBody(body);
				messageView.setCategory(category);
				messageView.setName_id(name_id);
				messageView.setInsert_date(insert_date);

				ret.add(messageView);

			}return ret;
		}finally{
			close(rs);
		}
	}
}
