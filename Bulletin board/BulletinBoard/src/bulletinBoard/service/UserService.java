package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtils.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulletinBoard.beans.User;
import bulletinBoard.dao.UserDao;
import bulletinBoard.dao.UserMaintenanceDao;
import bulletinBoard.utils.CipherUtil;


public class UserService {

	public void register(User user) {

		Connection connection = null;
		try {
			connection = getConnection();

			String encPassword = CipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);

			UserDao userDao = new UserDao();
			userDao.insert(connection, user);

			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public void update(User user){
		Connection connection =null;
		try{
			connection=getConnection();

			if(!user.getPassword().isEmpty()){
				System.out.println("パスワード変更");
				String encPassword = CipherUtil.encrypt(user.getPassword());
				user.setPassword(encPassword);
				UserDao userDao =new UserDao();
				userDao.update(connection, user);
			}else{
				System.out.println("パスワード変更しない");
				UserDao userDao =new UserDao();
				userDao.update2(connection, user);
			}

			commit(connection);
		}catch (RuntimeException e){
			rollback(connection);
			throw e;
		}catch (Error e){
			rollback(connection);
			throw e;
		}finally{
			close(connection);
		}
	}
	private static final int LIMIT_NUM = 1000;

	public List<User> getUser(){
		Connection connection = null;
		try{
		connection = getConnection();

		UserMaintenanceDao userMaintenaceDao = new UserMaintenanceDao();
		List<User> ret  = userMaintenaceDao.getUser(connection, LIMIT_NUM);

		commit(connection);

		return ret;
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}catch(Error e){
			rollback(connection);
			throw e;
		}finally{
			close(connection);
		}
	}
}
