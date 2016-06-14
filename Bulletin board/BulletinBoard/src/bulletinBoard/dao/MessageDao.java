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
			sql.append(", category");
			sql.append(", name");
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
			ps.setString(3, message.getCategory());
			ps.setString(4, message.getName());

			ps.executeUpdate();
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

}
