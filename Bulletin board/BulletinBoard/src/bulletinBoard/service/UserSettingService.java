package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtils.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;

import bulletinBoard.beans.User;
import bulletinBoard.dao.UserSettingDao;


public class UserSettingService {

	public User userSetting(int id){

		Connection connection = null;
		try{
			connection =getConnection();

			UserSettingDao usersettingDao =new UserSettingDao();
			User user = usersettingDao.userSetting(connection, id);

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
