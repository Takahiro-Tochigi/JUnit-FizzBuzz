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

@WebFilter(urlPatterns = { "/usermaintenance", "/setting", "/signup" })
public class AccessFilter implements Filter{
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain)throws IOException, ServletException{
		try{
			/* フィルタで行う処理 */
			User user =(User) ((HttpServletRequest) request).getSession().getAttribute("loginUser");
			int role_id = user.getRole_id();
			if(role_id != 1){
				System.out.println("権限のないユーザのアクセス");
				((HttpServletResponse) response).sendRedirect("./");
				return;
			}
			chain.doFilter(request, response);
	    }catch (ServletException se){

	    }catch (IOException e){

	    }
  }
	@Override
  public void init(FilterConfig filterConfig){
  }
	@Override
  public void destroy(){
  }
}