package bulletinBoard.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinBoard.beans.User;

@WebFilter(urlPatterns = { "/index.jsp", "/signup" , "/usermaintenance", "/newComment", "/setting" })
public class LoginFilter implements Filter{
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain)throws IOException, ServletException {
		try{
			User user =(User) ((HttpServletRequest) request).getSession().getAttribute("loginUser");
			if( user == null ){
				 System.out.println("不正なアクセス");

				((HttpServletResponse) response).sendRedirect("login");
				return;
			}

			chain.doFilter(request, response);
		}catch (ServletException se){

		}catch (IOException e){

		}

	}

	@Override
	public void destroy() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO 自動生成されたメソッド・スタブ

	}
}
