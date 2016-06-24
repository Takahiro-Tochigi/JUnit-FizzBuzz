package bulletinBoard.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinBoard.beans.UserComment;
import bulletinBoard.beans.UserMessage;
import bulletinBoard.service.CommentService;
import bulletinBoard.service.GetStartDayService;
import bulletinBoard.service.MessageService;

@WebServlet(urlPatterns = { "/index.jsp"})
public class TopServlet extends HttpServlet  {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet (HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException{
		/*日付検索*/

		String startYear = request.getParameter("startYear");
		String startMonth = request.getParameter("startMonth");
		String startDay = request.getParameter("startDay");
		String endYear = request.getParameter("endYear");
		String endMonth = request.getParameter("endMonth");
		String endDay = request.getParameter("endDay");



		String start = startYear +'-'+ startMonth +'-'+ startDay;//+" "+ startTime;
		String end = endYear +'-'+ endMonth +'-'+ endDay; //+ " " +endTime;
		String category = request.getParameter("category");


		boolean diff = checkDate(start);
		boolean diff2 = checkDate(end);

		if(diff==true || diff2 ==true){
			//検索が押されたら
			request.setAttribute("startYear",startYear);
			request.setAttribute("startMonth",startMonth);
			request.setAttribute("startDay",startDay);
			request.setAttribute("endYear",endYear);
			request.setAttribute("endMonth",endMonth);
			request.setAttribute("endDay",endDay);

			String startTime = "00:00:00";
			String endTime  = "23:59:59";
			startDay = start + " " + startTime;
			endDay = end + " " + endTime;
			List<UserMessage> messages = new MessageService().getMessage(category, startDay, endDay);
			List<UserComment> comment = new CommentService().getComment();
			request.setAttribute("messages",messages);
			request.setAttribute("comment",comment);
			request.setAttribute("category",category);
		}else{
			/*ログインした最初の処理*/

			startDay =new GetStartDayService().getStartDay();//ここの修正
			endDay = new GetStartDayService().getEndDay();

			String baseStartYear = startDay.substring(0,4);
			String baseStartMonth = startDay.substring(5,7);
			String baseStartDay = startDay.substring(8,10);
			String baseEndYear = endDay.substring(0,4);
			String baseEndMonth = endDay.substring(5,7);
			String baseEndDay = endDay.substring(8,10);

			request.setAttribute("startYear",baseStartYear);
			request.setAttribute("startMonth",baseStartMonth);
			request.setAttribute("startDay",baseStartDay);
			request.setAttribute("endYear",baseEndYear);
			request.setAttribute("endMonth",baseEndMonth);
			request.setAttribute("endDay",baseEndDay);

			List<UserMessage> messages = new MessageService().getMessage(category, startDay, endDay);
			List<UserComment> comment = new CommentService().getComment();
			request.setAttribute("messages",messages);
			request.setAttribute("comment",comment);
			request.setAttribute("category",category);
		}
		request.getRequestDispatcher("/top.jsp").forward(request, response);
	}
	public static boolean checkDate(String strDate) {

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
