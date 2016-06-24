package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtils.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;

import bulletinBoard.dao.UserMessageDao;

/*古い日時の検索サービス*/
public class GetStartDayService {

	public String getStartDay(){
		Connection connection = null;
		try{
			connection = getConnection();

			UserMessageDao messageDao = new UserMessageDao();
			String ret = messageDao.getStartDay(connection);

			commit(connection);

			return ret ;

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

	public String getEndDay(){
		Connection connection = null;
		try{
			connection = getConnection();

			UserMessageDao messageDao = new UserMessageDao();
			String ret = messageDao.getEndDay(connection);

			commit(connection);

			return ret ;

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
