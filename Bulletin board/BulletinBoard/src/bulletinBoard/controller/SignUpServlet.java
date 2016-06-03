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

import bulletinBoard.beans.User;
import bulletinBoard.service.UserService;

@WebServlet(urlPatterns = { "/signup" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<String> messages = new ArrayList<>();
		HttpSession session = request.getSession();
		User registUser = getUser(request);
		session.setAttribute("registUser", registUser);

		if (isValid(request, messages) == true) {

			new UserService().register(registUser);
			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("signup");
		}
	}

	private User getUser(HttpServletRequest request) throws IOException, ServletException{

			User registUser =  new User();

			registUser.setLogin_id(request.getParameter("login_id"));
			registUser.setPassword(request.getParameter("password"));
			registUser.setName(request.getParameter("name"));
			registUser.setgetBranch_id(request.getParameter("branch_id"));
			registUser.setRole_id(request.getParameter("role_id"));

			return registUser;
		}

	private boolean isValid(HttpServletRequest request, List<Object> messages) {
		String account = request.getParameter("account");
		String password = request.getParameter("password");

		if (StringUtils.isNullOrEmpty(account) == true) {
			messages.add("アカウント名を入力してください");
		}
		if (StringUtils.isNullOrEmpty(password) == true) {
			messages.add("パスワードを入力してください");
		}
		// TODO アカウントが既に利用されていないか、メールアドレスが既に登録されていないかなどの確認も必要
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
