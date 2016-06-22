package bulletinBoard.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinBoard.beans.User;
import bulletinBoard.service.UserService;

@WebServlet(urlPatterns = { "/usermaintenance"} )
public class UserMaintenanceServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{

			List<User> users = new UserService().getUser();
			request.setAttribute("user",users);

			request.getRequestDispatcher("/usermaintenance.jsp").forward(request, response);

	}
}
