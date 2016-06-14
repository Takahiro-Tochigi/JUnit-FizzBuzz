package bulletinBoard.dao;

import static  bulletinBoard.utils.CloseableUtils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.Message;
import bulletinBoard.exception.SQLRuntimeException;

public class UserMessageDao {
	public List<Message> getUserMessages(Connection connection, int num){
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("select * from posts ");
			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<Message> ret = toUserMessageList(rs);
			return ret;
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	private List<Message> toUserMessageList(ResultSet rs) throws SQLException{

		List<Message> ret = new ArrayList<Message>();
		try{
			while (rs.next()){
				int id = rs.getInt("id");
				String subject = rs.getString("subject");
				String body = rs.getString("body");
				String category =rs.getString("category");
				String name = rs.getString("name");
				Timestamp insert_date = rs.getTimestamp("insert_date");

				Message message = new Message();
				message.setId(id);
				message.setSubject(subject);
				message.setBody(body);
				message.setCategory(category);
				message.setName(name);
				message.setInsertdate(insert_date);

				ret.add(message);

			}return ret;
		}finally{
			close(rs);
		}

	}
}
