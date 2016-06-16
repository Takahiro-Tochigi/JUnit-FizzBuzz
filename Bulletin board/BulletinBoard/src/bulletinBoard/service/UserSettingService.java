package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtils.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulletinBoard.beans.Branch;
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
	private static final int LIMIT_NUM = 1000;
	public List<Branch> userBranch(){

		Connection connection = null;
		try{
			connection =getConnection();

			UserSettingDao usersettingDao =new UserSettingDao();
			List<Branch> branch = usersettingDao.userBranch_id(connection, LIMIT_NUM);

			commit(connection);

			return branch;
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
