package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bulletinBoard.beans.Comment;
import bulletinBoard.exception.SQLRuntimeException;

public class CommentDao {
	public void insert(Connection connection, Comment comment) {

		PreparedStatement ps =null;
		try{
			StringBuilder sql =new StringBuilder();
			sql.append("INSERT INTO comments ( ");
			sql.append(" body");
			sql.append(", name_id");
			sql.append(", post_id");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(")VALUE(");
			sql.append("  ? ");
			sql.append(", ? ");
			sql.append(", ? ");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, comment.getBody());
			ps.setInt(2, comment.getName_id());
			ps.setInt(3, comment.getPost_id());

			ps.executeUpdate();
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}
	public void  commentDelete(Connection connection, int id){
		PreparedStatement ps = null;
		try{

			StringBuilder sql = new StringBuilder();
			sql.append("delete from comments where post_id= ?");

			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, id);
			System.out.println(ps.toString());
			ps.executeUpdate();//sql文の実行

		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}
	public void  commentOnlyDelete(Connection connection, int id){
		PreparedStatement ps = null;
		try{

			StringBuilder sql = new StringBuilder();
			sql.append("delete from comments where id= ?");

			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, id);
			System.out.println(ps.toString());
			ps.executeUpdate();//sql文の実行

		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}
}
