package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtils.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

	public List<Message> getMessage( String category, String startDay, String endDay){
		final String DATE_PATTERN ="yyyy/MM/dd HH:mm:ss";


		String iDate = startDay; // 比較対象
		String sDate = endDay; // 比較対象2

		boolean diff = checkDate(iDate);
		boolean diff2 = checkDate(sDate);
		if(diff==true || diff2 ==true){
			String startTime = "00:00:00";
			String endTime  = "23:59:59";
			startDay = startDay + " " + startTime;
			endDay = endDay + " " + endTime;
		}else{
			Date today = new Date();
			startDay ="2016/06/01 00:00:00";
			endDay = new SimpleDateFormat(DATE_PATTERN).format(today);
		}

		Connection connection = null;
		try{
		connection = getConnection();

		UserMessageDao messageDao = new UserMessageDao();
		List<Message> ret  = messageDao.getUserMessages(connection, LIMIT_NUM,  category,  startDay,  endDay);

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
	   /* if (strDate == null || strDate.length() != 10) {
	        throw new IllegalArgumentException(
	                "引数の文字列["+ strDate +"]" +
	                "は不正です。");
	    }
	    strDate = strDate.replace('-', '/');*/
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

}
