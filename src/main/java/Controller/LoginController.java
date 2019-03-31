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
		req.setAttribute("err", ""); // create err empty
		req.setAttribute("user", user); // create userid & password input empty
		// Navigate to login.jsp page
		req.getRequestDispatcher(UrlConstant.LOGIN).forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get all parameter from JSP form
		String userId = req.getParameter("userId").trim(); // get userid parameter
		String password = req.getParameter("password").trim(); // get password parameter
		// Log to screen, we will use AspectJ later
		//log("userId=" + userId);
		//log("password=" + password);
		
		// Storing error and url to navigate
		String err = "";
		
		// Check userId is null or empty and response the error
		if(userId == null || userId.equals("")) { // Beginning of If
			err = MessageConstant.USERID_ERR; // Add error userid not found
		} else if(password == null || password.equals("")) {  // Check password is null or empty and response the error
			err = MessageConstant.PASSWORD_ERR;	// Add error password is incorrect
		} else { // Check user hasn't existed in database or not
		    // Create user DAO layer connect database to get value
			IUserDAO iUserDAO = new UserDAO_Impl();
			try {
				// Go to databse and check user existed or not
				if(iUserDAO.checkUserExist(userId, password) == true) { // Beginning of If
					User user = iUserDAO.getUserInformation(userId, password); // start connect to databse to get user information
//		            log("ContextPath=" + req.getContextPath());
					// Add cookie to make sure user logined
		            CookieHelper.addCookie("userId", user.getUserId(), -1, resp); // add userid cookie
		            CookieHelper.addCookie("userName", user.getUserName(), -1, resp); // add username cookie
		            CookieHelper.addCookie("psnCD", user.getPsnCD(), -1, resp); // add psnCD cookie
					// Send it to UI , search.jsp
		            resp.sendRedirect(req.getContextPath() + UrlConstant.URL_SEARCH);
		            return;
				} else {
					err = MessageConstant.USER_ERR; // Show the error, user not existed in database
				} // Ending of If
			} catch (ClassNotFoundException e) { e.printStackTrace(); }			
		} // Ending of If
		
		//log("err=" + err);
		
		// If error happens, add it to request
		if(!err.equals("")) { // Beginning of If
			req.setAttribute("err", err); // Add error to send to login.jsp
			User user = new UserBuilder().setUserId(userId).setPassword(password).build(); // Create new user
			req.setAttribute("user", user); // Send userid & password input
			req.getRequestDispatcher(UrlConstant.LOGIN).forward(req,resp); // naviagte to login.jsp
		} // Ending of If
	}
}
