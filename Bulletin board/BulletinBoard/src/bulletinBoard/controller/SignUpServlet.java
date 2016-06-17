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

import bulletinBoard.beans.Branch;
import bulletinBoard.beans.Role;
import bulletinBoard.beans.User;
import bulletinBoard.service.UserService;
import bulletinBoard.service.UserSettingService;

@WebServlet(urlPatterns = { "/signup" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		UserSettingService usersettingService = new UserSettingService();
		List<Branch> branch =usersettingService.userBranch();
		List<Role> role =usersettingService.userRole();
		request.setAttribute("branch_name", branch);
		request.setAttribute("role_name", role);


			request.getRequestDispatcher("signup.jsp").forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		List<String> messages = new ArrayList<>();

		HttpSession session = request.getSession();
		if(isValid(request,messages) == true) {

			User user = new User();
			user.setLogin_id(request.getParameter("login_id"));
			user.setPassword(request.getParameter("password"));
			user.setName(request.getParameter("name"));
			user.setBranch_id(Integer.parseInt(request.getParameter("branch_id")));
			user.setRole_id(Integer.parseInt(request.getParameter("role_id")));

			new UserService().register(user);

			response.sendRedirect("usermaintenance");
		}else{
			session.setAttribute("errormessages", messages);
			System.out.println(messages);
			response.sendRedirect("signup");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String login_id = request.getParameter("login_id");
		String password = request.getParameter("password");
		String checkPassword =request.getParameter("checkpassword");
		String name = request.getParameter("name");
		/*String branch_id = request.getParameter("branch_id");
		String role_id = request.getParameter("role_id");*/

		if (login_id.length() < 6 || login_id.length() > 21 ) {
			messages.add("ログインIDは6文字以上20文字以下で入力してください");
		}
		if (password.length() < 6 || password.length() > 255) {
			messages.add("パスワードは6文字以上255文字以下で入力してください");
		}
		if (!password.equals(checkPassword)){
			messages.add("パスワードが確認用パスワードと一致しません");
		}
		if (name.length() > 11){
			messages.add("個人名は10文字以下にしてください。");
		}
		/*if(!isNumeric(branch_id)){

		}
		if*/
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
