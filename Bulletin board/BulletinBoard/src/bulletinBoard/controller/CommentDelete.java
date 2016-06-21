package bulletinBoard.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinBoard.service.MessageService;

@WebServlet(urlPatterns = { "/commentDelete" })

public class CommentDelete  extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{

	int commentId =Integer.parseInt(request.getParameter("comment.id"));
	System.out.println(commentId);
	MessageService messageService = new MessageService();
	messageService.commentOnlyDelete(commentId);
	response.sendRedirect("./");
	}

}