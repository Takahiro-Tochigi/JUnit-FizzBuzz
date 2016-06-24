package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtils.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;

import bulletinBoard.beans.User;
import bulletinBoard.dao.UserSearchDao;

public class UserSearchService {
	public User searchUser(String login_id){

		Connection connection = null;
		try{
			connection =getConnection();

			UserSearchDao userSearchDao =new UserSearchDao();

			User user = userSearchDao.getUser(connection, login_id);

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
