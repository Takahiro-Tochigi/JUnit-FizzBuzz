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
import bulletinBoard.exception.NoRowsUpdatedRuntimeException;
import bulletinBoard.service.UserService;
import bulletinBoard.service.UserSettingService;


@WebServlet(urlPatterns = { "/setting" })
public class SettingsServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet (HttpServletRequest request,
		HttpServletResponse response) throws ServletException ,IOException{

		int id =Integer.parseInt(request.getParameter("user.id"));
		UserSettingService usersettingService = new UserSettingService();
		User user =usersettingService.userSetting(id);

		HttpSession session = request.getSession();

		session.setAttribute("user",user);

		request.getRequestDispatcher("/setting.jsp").forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{

		List<String> messages = new ArrayList<String>();
		HttpSession session =request.getSession();
		request.setCharacterEncoding("UTF-8");

		User user = getEditUser(request);
		session.setAttribute("user", user);

		if(isValid(request,messages) == true ){
			try{
				//System.out.println("update");
				new UserService().update(user);
			} catch (NoRowsUpdatedRuntimeException e){
				session.removeAttribute("editUser");
				messages.add("他の人によって更新されています。最新データを表示しました。データを確認してください");
				session.setAttribute("errorMessages", messages);
				response.sendRedirect("setting");
			}
			session.removeAttribute("user");
			response.sendRedirect("usermaintenance");
		}else{
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("setting.jsp");
		}
	}
	private User getEditUser(HttpServletRequest request)
		throws IOException, ServletException{
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		user.setLogin_id(request.getParameter("login_id"));
		user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		user.setBranch_id(Integer.parseInt(request.getParameter("branch_id")));
		user.setRole_id(Integer.parseInt(request.getParameter("role_id")));

		return user;
	}

	private boolean isValid(HttpServletRequest request, List<String> messages){
		String login_id = request.getParameter("login_id");;
		String name = request.getParameter("name");
		String branch_id = request.getParameter("branch_id");
		String role_id = request.getParameter("role_id");

		if (StringUtils.isNullOrEmpty(login_id) == true){
			messages.add("ログインIDを入力してください");
		}
		if (StringUtils.isNullOrEmpty(name)==true){
			messages.add("名前を入力してください");
		}
		if (StringUtils.isNullOrEmpty(branch_id)==true){
			messages.add("支店番号を入力してください");
		}
		if (StringUtils.isNullOrEmpty(role_id)==true){
			messages.add("部署・役職を入力してください");
		}
		if (login_id.length() < 6 || login_id.length() > 21 ) {
			messages.add("ログインIDは6文字以上20文字以下で入力してください");
		}
		if(!request.getParameter("password").isEmpty()){
			String password = request.getParameter("password");

			if (password.length() < 6 || password.length() > 255)
				messages.add("パスワードは6文字以上255文字以下で入力してください");
		}
		/*if (!password.equals(checkPassword)){
			messages.add("パスワードが確認用パスワードと一致しません");
		}*/
		if (name.length() > 11){
			messages.add("個人名は10文字以下にしてください。");
		}


		if( messages.size() == 0){
			return true;
		}else{
			return false;
		}
	}
}