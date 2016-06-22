package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtils.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulletinBoard.beans.Comment;
import bulletinBoard.beans.UserComment;
import bulletinBoard.dao.CommentDao;
import bulletinBoard.dao.UserCommentDao;

public class CommentService {

	public void register (Comment comment){

		Connection connection = null;
		try{
			connection = getConnection();

			CommentDao commentDao = new CommentDao();

			commentDao.insert(connection , comment);

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

	public List<UserComment> getComment(){
		Connection connection = null;
		try{
		connection = getConnection();

		UserCommentDao commentDao = new UserCommentDao();
		List<UserComment> ret  = commentDao.getUserComment(connection, LIMIT_NUM);

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
