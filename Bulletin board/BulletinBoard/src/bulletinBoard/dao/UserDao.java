package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.User;
import bulletinBoard.exception.SQLRuntimeException;

public class UserDao{

	public User getUser(Connection connection, String login_id, String password){

		PreparedStatement ps = null;
		try{
			String sql = "SELECT * FROM users WHERE login_id =? And password =?";

			ps =connection.prepareStatement(sql);
			ps.setString(1, login_id);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();//sql文の実行
			List<User> userList = toUserList(rs);
			if(userList.isEmpty() == true){
				return null;
			}else if (2 <= userList.size()){
				throw new IllegalStateException("2 <= userList.size()");
			}else{
				return userList.get(0);
			}
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	};

	private List<User> toUserList(ResultSet rs) throws SQLException {

		List <User> ret = new ArrayList<User>();
		try{
			//帰ってきた複数行のオブジェクトから取り出し
			while (rs.next()){
				int id  =rs.getInt("id");
				String login_id = rs.getString("login_id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				int branch_id =rs.getInt("branch_id");
				int role_id =rs.getInt("role_id");

				User user =new User();
				user.setId(id);
				user.setLogin_id(login_id);
				user.setPassword(password);
				user.setName(name);
				user.setBranch_id(branch_id);
				user.setRole_id(role_id);
				ret.add(user);
			}
			return ret;//取り出した情報をlistに保持
		}finally{
			close(rs);
		}
	}


	public void insert(Connection connection,User user){

		PreparedStatement ps =null;
 		try{
 			StringBuilder sql =new StringBuilder();
 			sql.append("INSERT INTO users( ");
 			sql.append(" login_id ");
 			sql.append(", password");
 			sql.append(", name");
 			sql.append(", branch_id");
 			sql.append(", role_id");
 			sql.append(")VALUES(");
 			sql.append("?");//logi_id
 			sql.append(",?");//password
 			sql.append(",?");//name
 			sql.append(",?");//branch_id
 			sql.append(",?");//role_id
 			sql.append(")");

 			ps =  connection.prepareStatement(sql.toString());

 			ps.setString(1, user.getLogin_id());
 			ps.setString(2, user.getPassword());
 			ps.setString(3, user.getName());
 			ps.setInt(4, user.getBranch_id());
 			ps.setInt(5, user.getRole_id());

 			ps.executeUpdate();
 		} catch (SQLException e){
 			throw new SQLRuntimeException(e);
 		} finally{
 			close(ps);
 		}
	}



	public void update(Connection connection, User user){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append(" login_id = ?");
			sql.append(", password = ?");
			sql.append(", name = ?");
			sql.append(", branch_id = ?");
			sql.append(", role_id = ?");
			sql.append(" WHERE");
			sql.append(" id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLogin_id());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setInt(4, user.getBranch_id());
			ps.setInt(5, user.getRole_id());
			ps.setInt(6, user.getId());

			ps.executeUpdate();

		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}
	public void update2(Connection connection, User user){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append(" login_id = ?");
			sql.append(", name = ?");
			sql.append(", branch_id = ?");
			sql.append(", role_id = ?");
			sql.append(" WHERE");
			sql.append(" id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLogin_id());
			ps.setString(2, user.getName());
			ps.setInt(3, user.getBranch_id());
			ps.setInt(4, user.getRole_id());
			ps.setInt(5, user.getId());

			ps.executeUpdate();

		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}
}