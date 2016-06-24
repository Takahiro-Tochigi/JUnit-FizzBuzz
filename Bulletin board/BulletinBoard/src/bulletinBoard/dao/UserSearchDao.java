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

public class UserSearchDao {
	public User getUser(Connection connection, String login_id){
	PreparedStatement ps = null;
		try{
			String sql = "SELECT * FROM users WHERE login_id =? ";

			ps =connection.prepareStatement(sql);
			ps.setString(1, login_id);

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
	}

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

}
