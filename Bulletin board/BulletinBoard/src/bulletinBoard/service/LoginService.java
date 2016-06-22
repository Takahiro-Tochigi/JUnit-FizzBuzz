package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtils.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;

import bulletinBoard.beans.User;
import bulletinBoard.dao.UserDao;
import bulletinBoard.utils.CipherUtil;


public class LoginService {
	public User login(String login_id, String password){

		Connection connection = null;
		try{
			connection =getConnection();

			UserDao userDao =new UserDao();
			String encPassword = CipherUtil.encrypt(password);

			User user = userDao.getUser(connection, login_id, encPassword);

			commit(connection);

			return user;
		}catch (RuntimeException e){
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
