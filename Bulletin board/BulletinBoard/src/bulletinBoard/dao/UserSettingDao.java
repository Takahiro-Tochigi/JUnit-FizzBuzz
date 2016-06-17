package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.Branch;
import bulletinBoard.beans.Role;
import bulletinBoard.beans.User;
import bulletinBoard.exception.SQLRuntimeException;

public class UserSettingDao {
	public User userSetting(Connection connection, int id){
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("select * from users where id= ?");

			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();//sql文の実行

			List<User> userList = toSettingUserList(rs);
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
	private  List<User> toSettingUserList(ResultSet rs) throws SQLException {

		List <User> ret = new ArrayList<User>();
		try{
			//帰ってきた複数行のオブジェクトから取り出し
			while (rs.next()){
				int id=rs.getInt("id");
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
	public List<Branch> userBranch_id(Connection connection, int num){
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("select * from branches");
			ps = connection.prepareStatement(sql.toString());
			System.out.println(ps.toString());
			ResultSet rs = ps.executeQuery();//sql文の実行

			List<Branch> branchList =toBranchList(rs);

			return branchList;
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}
	private  List<Branch> toBranchList(ResultSet rs) throws SQLException {

		List <Branch> ret = new ArrayList<Branch>();

		try{
			//帰ってきた複数行のオブジェクトから取り出し
			while (rs.next()){
				int id = rs.getInt("id");
				String branchName = rs.getString("branchname");
				Branch branch = new Branch();
				branch.setId(id);
				branch.setBranchName(branchName);
				ret.add(branch);
			}
			System.out.println(ret);
			return ret;//取り出した情報をlistに保持
		}finally{
			close(rs);
		}
	}
	public List<Role> userRole_id(Connection connection, int num){
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("select * from roles");
			ps = connection.prepareStatement(sql.toString());
			System.out.println(ps.toString());
			ResultSet rs = ps.executeQuery();//sql文の実行

			List<Role> roleList =toRoleList(rs);

			return roleList;
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}
	private  List<Role> toRoleList(ResultSet rs) throws SQLException {

		List <Role> ret = new ArrayList<Role>();

		try{
			//帰ってきた複数行のオブジェクトから取り出し
			while (rs.next()){
				int id = rs.getInt("id");
				String roleName = rs.getString("rolename");
				Role role = new Role();
				role.setId(id);
				role.setRoleName(roleName);
				ret.add(role);
			}
			System.out.println(ret);
			return ret;//取り出した情報をlistに保持
		}finally{
			close(rs);
		}
	}
}
