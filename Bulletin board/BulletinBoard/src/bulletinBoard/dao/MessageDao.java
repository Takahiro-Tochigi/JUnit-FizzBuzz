package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bulletinBoard.beans.Message;
import bulletinBoard.exception.SQLRuntimeException;

public class MessageDao {

	public void insert(Connection connection, Message message) {

		PreparedStatement ps =null;
		try{
			StringBuilder sql =new StringBuilder();
			sql.append("INSERT INTO posts ( ");
			sql.append(" subject");
			sql.append(", body");
			sql.append(", name_id");
			sql.append(", category");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(")VALUE(");
			sql.append("  ? ");
			sql.append(", ? ");
			sql.append(", ? ");
			sql.append(", ? ");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, message.getSubject());
			ps.setString(2, message.getBody());
			ps.setInt   (3, message.getName_id());
			ps.setString(4, message.getCategory());

			ps.executeUpdate();
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}
	public void  messageDelete(Connection connection, int id){
		PreparedStatement ps = null;
		try{

			StringBuilder sql = new StringBuilder();
			sql.append("delete from posts where id= ?");
			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, id);
			System.out.println(sql.toString());
			ps.executeUpdate();//sql文の実行

		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}
}
