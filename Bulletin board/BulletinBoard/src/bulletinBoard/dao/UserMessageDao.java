package bulletinBoard.dao;

import static  bulletinBoard.utils.CloseableUtils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.UserMessage;
import bulletinBoard.exception.SQLRuntimeException;
/*ここに投稿に関するdaoメソッドを集約する*/
public class UserMessageDao {
	public List<UserMessage> getUserMessages(Connection connection, int num,
			String category, String startDay, String endDay){
		/*
		System.out.println(startDay);
		System.out.println(endDay);
		*/
		PreparedStatement ps = null;

		try{
			StringBuilder sql = new StringBuilder();
			sql.append(" select * from users_posts WHERE ");
			if(category != null && !("".equals(category))){
			sql.append(" category LIKE ?  AND");
			}
			sql.append(" insert_date BETWEEN  ? ");
			sql.append(" AND ? ");
			sql.append(" order by insert_date desc ");

			ps = connection.prepareStatement(sql.toString());
			if( category != null  && !("".equals(category))){
			ps.setString(1, category);
			ps.setString(2, startDay);
			ps.setString(3, endDay);
			}else{
			ps.setString(1, startDay);
			ps.setString(2, endDay);
			}
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
				String name = rs.getString("name");
				String subject = rs.getString("subject");
				String body = rs.getString("body");
				String category =rs.getString("category");
				int name_id = rs.getInt("name_id");
				Timestamp insert_date = rs.getTimestamp("insert_date");

				UserMessage message = new UserMessage();

				message.setId(id);
				message.setName(name);
				message.setSubject(subject);
				message.setBody(body);
				message.setCategory(category);
				message.setName_id(name_id);
				message.setInsert_date(insert_date);

				ret.add(message);

			}return ret;
		}finally{
			close(rs);
		}
	}

	public  String  getStartDay(Connection connection){
		PreparedStatement ps = null;

		try{
			StringBuilder sql = new StringBuilder();
			sql.append("select insert_date from users_posts");
			sql.append(" WHERE insert_date  limit 1");
			ps = connection.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			String startDay = null;
			while (rs.next()){
				startDay = rs.getString("insert_date");
			}
			return startDay;


		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}

	}
	public  String  getEndDay(Connection connection){
		PreparedStatement ps = null;

		try{
			StringBuilder sql = new StringBuilder();
			sql.append("select insert_date from users_posts");
			sql.append(" WHERE insert_date  ORDER BY insert_date desc limit 1");
			ps = connection.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			String startDay = null;
			while (rs.next()){
				startDay = rs.getString("insert_date");
			}
			return startDay;


		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}

	}

}
