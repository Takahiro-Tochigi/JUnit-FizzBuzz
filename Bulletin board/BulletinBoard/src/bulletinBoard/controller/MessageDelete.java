package bulletinBoard.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinBoard.service.MessageService;

@WebServlet(urlPatterns = { "/messageDelete" })

public class MessageDelete  extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{

	int messageId =Integer.parseInt(request.getParameter("message.id"));
	System.out.println(messageId);
	MessageService messageService = new MessageService();
	messageService.messageDelete(messageId);
	messageService.commentDelete(messageId);
	response.sendRedirect("./");
	}

}
