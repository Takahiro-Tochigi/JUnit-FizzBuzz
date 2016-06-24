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


//ユーザー管理画面に登録者一覧を作るためのDao
public class UserMaintenanceDao {
	public List<User> getUser(Connection connection, int num){
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("select * from users ");
			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<User> ret = toUsersList(rs);
			return ret;
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}


	private List<User> toUsersList(ResultSet rs) throws SQLException{

		List<User> ret = new ArrayList<User>();
		try{
			while (rs.next()){
				int id =rs.getInt("id");
				String login_id = rs.getString("login_id");
				String name = rs.getString("name");

				User user = new User();
				user.setId(id);
				user.setLogin_id(login_id);
				user.setName(name);

				ret.add(user);

			}return ret;
		}finally{
			close(rs);
		}

	}

}
