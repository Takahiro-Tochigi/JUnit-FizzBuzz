package bulletinBoard.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinBoard.service.UserSettingService;

@WebServlet(urlPatterns = { "/delete" })
public class UserDelete  extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{

	int userId =Integer.parseInt(request.getParameter("user.id"));
	UserSettingService usersettingService = new UserSettingService();
	usersettingService.userDelete(userId);
	response.sendRedirect("usermaintenance");
	}
}
