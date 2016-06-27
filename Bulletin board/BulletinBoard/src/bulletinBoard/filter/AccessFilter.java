package bulletinBoard.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import bulletinBoard.beans.User;

@WebFilter(urlPatterns = { "/usermaintenance", "/setting", "/signup" })
public class AccessFilter implements Filter{
	public void doFilter(ServletRequest request, ServletResponse response,
    FilterChain chain)throws IOException, ServletException{
		List<String> messages = new ArrayList<String>();
		try{
			/* フィルタで行う処理 */
			User user =(User) ((HttpServletRequest) request).getSession().getAttribute("loginUser");
			if(user!= null){
				int role_id = user.getRole_id();
				int branch_id =user.getBranch_id();
				if(role_id != 1 || branch_id != 1){
					System.out.println("権限のないユーザのアクセス");
					messages.add("権限がありません");
					request.setAttribute("errormessages",messages);
					request.getRequestDispatcher("./").forward(request, response);
					return;
				}
			}else{
				 System.out.println("不正なアクセス2");
				 messages.add("ログインしてください");
				 request.setAttribute("errorMessages",messages);
				 request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			chain.doFilter(request, response);
	    }catch (ServletException se){

	    }catch (IOException e){

	    }
  }

  public void init(FilterConfig filterConfig){
  }

  public void destroy(){
  }
}