package Filters;

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
import Common.UrlConstant;
import DAO.IUserDAO;
import DAO.UserDAO_Impl;
import DTO.User;
import DTO.User.UserBuilder;
import Utils.CookieHelper;

@WebFilter(urlPatterns= {"/edit", "/search"})
public class AuthenFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse resq= (HttpServletResponse) response;
		User user = new UserBuilder()
						.setUserId(CookieHelper.getCookie("userId", req))
						.setUserName(CookieHelper.getCookie("userName", req))
						.setPsnCD(CookieHelper.getCookie("psnCD", req))
						.build();	
		
		IUserDAO iUserDAO = new UserDAO_Impl();
		
		try {
			if(iUserDAO.checkUserSession(user) == true) {
				chain.doFilter(request, response);
				return;
			} else {				
				resq.sendRedirect(req.getContextPath() + UrlConstant.URL_LOGIN);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
