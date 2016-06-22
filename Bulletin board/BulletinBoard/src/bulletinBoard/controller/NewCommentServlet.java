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

import bulletinBoard.beans.Comment;
import bulletinBoard.beans.User;
import bulletinBoard.service.CommentService;

@WebServlet(urlPatterns ={ "/newComment" })
public class NewCommentServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<String> messages = new ArrayList<>();
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		if(isCommentValid(request,messages) == true) {
			User user = (User) session.getAttribute("loginUser");

			Comment comment = new Comment();
			comment.setBody(request.getParameter("body"));
			comment.setName_id(user.getId());
			comment.setPost_id(Integer.parseInt(request.getParameter("post_id")));

			new CommentService().register(comment);

			response.sendRedirect("./");
		}else{

			session.setAttribute("errormessages", messages);
			response.sendRedirect("./");
		}
	}

	private boolean isCommentValid(HttpServletRequest request, List<String> messages) {
		String body = request.getParameter("body");

		if (body.length() >500 ) {
			messages.add("コメントは500文字以下までです");
		}
		if (StringUtils.isNullOrEmpty(body) == true){
			messages.add("コメントが入力されていません");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
