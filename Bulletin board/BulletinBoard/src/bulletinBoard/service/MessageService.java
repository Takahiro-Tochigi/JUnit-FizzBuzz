package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtils.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;
import java.text.DateFormat;
import java.util.List;

import bulletinBoard.beans.Message;
import bulletinBoard.beans.UserComment;
import bulletinBoard.beans.UserMessage;
import bulletinBoard.dao.CommentDao;
import bulletinBoard.dao.MessageDao;
import bulletinBoard.dao.UserCommentViewDao;
import bulletinBoard.dao.UserMessageDao;
import bulletinBoard.dao.UserMessageViewDao;

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

	public List<UserMessage> getMessage( String category, String startDay, String endDay){
		Connection connection = null;
		try{
		connection = getConnection();

		UserMessageDao messageDao = new UserMessageDao();
		List<UserMessage> ret  = messageDao.getUserMessages(connection, LIMIT_NUM,  category,  startDay,  endDay);

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

	public static boolean checkDate(String strDate) {

	    DateFormat format = DateFormat.getDateInstance();
	    // 日付/時刻解析を厳密に行うかどうかを設定する。
	    format.setLenient(false);
	    try {
	        format.parse(strDate);
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}
	public void messageDelete(int id){

		Connection connection = null;
		try{
			connection =getConnection();

			MessageDao messageDao =new MessageDao();
			messageDao.messageDelete(connection, id);

			commit(connection);

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
	public void commentDelete(int id){

		Connection connection = null;
		try{
			connection =getConnection();

			CommentDao commentDao =new CommentDao();
			commentDao.commentDelete(connection, id);

			commit(connection);

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

	public void commentOnlyDelete(int id){

		Connection connection = null;
		try{
			connection =getConnection();

			CommentDao commentDao =new CommentDao();
			commentDao.commentOnlyDelete(connection, id);

			commit(connection);

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

	public List<UserMessage> getUserMessage(){
		Connection connection = null;
		try{
			connection = getConnection();
			UserMessageViewDao userMessageDao =new UserMessageViewDao();
			List<UserMessage> ret = userMessageDao.getUserMessagesView(connection,LIMIT_NUM);

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

	public List<UserComment> getUserComment(){
		Connection connection = null;
		try{
			connection = getConnection();
			UserCommentViewDao userCommentDao =new UserCommentViewDao();
			List<UserComment> ret = userCommentDao.getUserCommentView(connection,LIMIT_NUM);

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
