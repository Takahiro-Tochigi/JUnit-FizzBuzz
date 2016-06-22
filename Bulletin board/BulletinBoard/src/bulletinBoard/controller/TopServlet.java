package bulletinBoard.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinBoard.beans.UserComment;
import bulletinBoard.beans.UserMessage;
import bulletinBoard.service.CommentService;
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

		String start = startYear +'/'+ startMonth +'/'+ startDay;//+" "+ startTime;
		String end = endYear +'/'+ endMonth +'/'+ endDay; //+ " " +endTime;

		String category = request.getParameter("category");

		List<UserMessage> messages = new MessageService().getMessage(category, start, end);
		List<UserComment> comment = new CommentService().getComment();



		request.setAttribute("category",category);
		request.setAttribute("startYear",startYear);
		request.setAttribute("startMonth",startMonth);
		request.setAttribute("startDay",startDay);
		request.setAttribute("endYear",endYear);
		request.setAttribute("endMonth",endMonth);
		request.setAttribute("endDay",endDay);

		request.setAttribute("messages",messages);
		request.setAttribute("comment",comment);

		request.getRequestDispatcher("/top.jsp").forward(request, response);
	}

}
