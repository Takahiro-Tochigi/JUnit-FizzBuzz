package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtils.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;
//import java.util.List;
import java.util.List;

import bulletinBoard.beans.Message;
import bulletinBoard.dao.MessageDao;
import bulletinBoard.dao.UserMessageDao;

public class MessageService {

	public void register (Message message){

		Connection connection = null;
		try{
			connection = getConnection();

			MessageDao messageDao = new MessageDao();

			messageDao.insert(connection , message);

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

	public List<Message> getMessage(){
		Connection connection = null;
		try{
		connection = getConnection();

		UserMessageDao messageDao = new UserMessageDao();
		List<Message> ret  = messageDao.getUserMessages(connection, LIMIT_NUM);

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
