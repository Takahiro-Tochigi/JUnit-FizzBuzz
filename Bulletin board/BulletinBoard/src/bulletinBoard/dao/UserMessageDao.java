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
/*ここに投稿に関するdaoメソッドを集約する*/
public class UserMessageDao {
	public List<Message> getUserMessages(Connection connection, int num,
			String category, String startDay, String endDay){
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append(" select * from posts WHERE ");
			if(category != null && !("".equals(category))){
			sql.append(" category LIKE ? AND");
			}
			sql.append(" insert_date >= ? 'YYYY/MM/DD HH24:MI:SS' ");
			sql.append(" AND insert_date < ? 'YYYY/MM/DD HH24:MI:S' ");
			sql.append(" order by id desc");

			ps = connection.prepareStatement(sql.toString());
			if( category != null  && !("".equals(category))){
			ps.setString(1, category);
			ps.setString(2, startDay);
			ps.setString(3, endDay);
			}else{
			ps.setString(1, startDay);
			ps.setString(2, endDay);
			}

			//System.out.println(ps.toString());

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
				int name_id = rs.getInt("name_id");
				Timestamp insert_date = rs.getTimestamp("insert_date");

				Message message = new Message();
				message.setId(id);
				message.setSubject(subject);
				message.setBody(body);
				message.setCategory(category);
				message.setName_id(name_id);
				message.setInsertdate(insert_date);

				ret.add(message);

			}return ret;
		}finally{
			close(rs);
		}
	}
}
