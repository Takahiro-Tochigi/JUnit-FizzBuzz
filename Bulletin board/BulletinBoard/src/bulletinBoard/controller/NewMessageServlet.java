package bulletinBoard.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.StringUtils;

import bulletinBoard.beans.Message;
import bulletinBoard.beans.User;
import bulletinBoard.service.MessageService;

@WebServlet (urlPatterns = { "/newMessage" })
public class NewMessageServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

			request.getRequestDispatcher("newmessage.jsp").forward(request, response);
	}


	@Override
	protected void doPost (HttpServletRequest request, HttpServletResponse response)
			throws IOException,SecurityException, ServletException{

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		List<String> messages = new ArrayList<String>();

		if(isValid(request, messages) == true) {

			Message message = new Message();
			request.setCharacterEncoding("UTF-8");
			message.setSubject((String) request.getParameter("subject"));
			message.setBody((String) request.getParameter("body"));
			message.setCategory((String) request.getParameter("category"));
			message.setName_id(user.getId());


			new MessageService().register(message);

			response.sendRedirect("./");
		} else {

			Message message = new Message();
			message.setSubject((String) request.getParameter("subject"));
			message.setBody((String) request.getParameter("body"));
			message.setCategory((String) request.getParameter("category"));

			request.setAttribute("message",message);
			request.setAttribute("errorMessages",messages);
			request.getRequestDispatcher("/newmessage.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages){

		String subject = request.getParameter("subject");
		String body = request.getParameter("body");
		String category = request.getParameter("category");

		if (StringUtils.isNullOrEmpty(subject) == true){
			messages.add("件名を入力してください");
		}
		if (50 < subject.length()) {
			messages.add("50文字以下で入力してください");
		}
		if (StringUtils.isNullOrEmpty(body) == true){
			messages.add("本文を入力してください");
		}
		if (1000 < body.length()) {
			messages.add("1000文字以下で入力してください");
		}
		if (StringUtils.isNullOrEmpty(category) == true){
			messages.add("カテゴリーを入力してください");
		}
		if (10 < category.length()) {
			messages.add("10文字以下で入力してください");
		}
		if (messages.size() == 0){
			return true;
		}else{
			return false;
		}
	}
}
