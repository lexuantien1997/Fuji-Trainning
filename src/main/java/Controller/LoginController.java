package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Common.MessageConstant;
import Common.UrlConstant;
import DAO.IUserDAO;
import DAO.UserDAO_Impl;
import DTO.User;
import DTO.User.UserBuilder;
import Utils.CookieHelper;

@WebServlet("/login")
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		// Get cookie to check user logined or not
		boolean ckeckCookie = CookieHelper.isCookie("userId", req, resp)
							&& CookieHelper.isCookie("userName", req, resp) 
							&& CookieHelper.isCookie("psnCD", req, resp); 
		// Check user logined ?
		if (ckeckCookie) { // Beginning of If statement
//			System.out.print("user da login");
			resp.sendRedirect(req.getContextPath() + UrlConstant.URL_SEARCH);
			return;
		} // Ending of If statement
		// Create empty user
		User user = new UserBuilder().setUserId("").setPassword("").build();					
		// Pass all data to JSP
		// We will user JavaBean later
		req.setAttribute("err", "");
		req.setAttribute("user", user);			
		// Navigate to login.jsp page
		req.getRequestDispatcher(UrlConstant.LOGIN).forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get all parameter from JSP form
		String userId = req.getParameter("userId").trim();
		String password = req.getParameter("password").trim();		
		// Log to screen, we will use AspectJ later
		//log("userId=" + userId);
		//log("password=" + password);
		
		// Storing error and url to navigate
		String err = "";
		
		// Check userId is null or empty and response the error
		if(userId == null || userId.equals("")) { // Beginning of If
			err = MessageConstant.USERID_ERR;
		} 
		// Check password is null or empty and response the error
		else if(password == null || password.equals("")) { 
			err = MessageConstant.PASSWORD_ERR;						
		} 
		// Check user hasn't existed in database or not
		else { 			
			IUserDAO iUserDAO = new UserDAO_Impl();
			try {
				// Go to databse and check user existed or not
				if(iUserDAO.checkUserExist(userId, password) == true) {				
					User user = iUserDAO.getUserInformation(userId, password);
//		            log("ContextPath=" + req.getContextPath());
					// Add cookie to make sure user logined
		            CookieHelper.addCookie("userId", user.getUserId(), -1, resp);
		            CookieHelper.addCookie("userName", user.getUserName(), -1, resp);
		            CookieHelper.addCookie("psnCD", user.getPsnCD(), -1, resp);
					// Send it to UI
		            resp.sendRedirect(req.getContextPath() + UrlConstant.URL_SEARCH);
		            return;
				} else {
					// Show the error, user not existed in database
					err = MessageConstant.USER_ERR;
				}
			} catch (ClassNotFoundException e) { e.printStackTrace(); }			
		} // Ending of If
		
		log("err=" + err);
		
		// If error happens, add it to request
		if(!err.equals("")) { // Beginning of If
			req.setAttribute("err", err);
			User user = new UserBuilder().setUserId(userId).setPassword(password).build();			
			req.setAttribute("user", user);
			req.getRequestDispatcher(UrlConstant.LOGIN).forward(req,resp);
		} 
	}
}
