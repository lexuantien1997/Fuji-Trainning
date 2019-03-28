package Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CookieHelper {
	// create default cookie time can alive
		// use double * will make application decreases performance but make it easy to understand
		private static int defaultMaxAge = 60 * 60 * 24 ; 
		
		public static void addCookie(String name, String value, int maxAge, HttpServletResponse response) throws ServletException, UnsupportedEncodingException {
			
			Cookie cookie = new Cookie(name, URLEncoder.encode( value, "UTF-8"));		

			cookie.setMaxAge(maxAge< 0  ? defaultMaxAge : maxAge);
			
			response.addCookie(cookie);
		}
		
		public static String getCookie(String name, HttpServletRequest request) {
			Cookie[] cookies = request.getCookies();
			
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals(name)) {
					return cookie.getValue();
				}
			}
			
			return null;
		}
		
		public static boolean isCookie(String name, HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        // Get an array of Cookies associated with this domain
	        Cookie[] cookies = request.getCookies();

	        // Set response content type
	        response.setContentType("text/html");
	        if (cookies != null) {
	            for (Cookie cookie : cookies) {
	                if (cookie.getName().equals(name)) {
	                    return true;
	                }
	            }
	        }
	        return false;
	    }
		
		public static void deleteCookies(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        // Get an array of Cookies associated with this domain
	        Cookie[] cookies = request.getCookies();

	        // Set response content type
	        response.setContentType("text/html");

	        for (Cookie cookie : cookies) {
	            cookie.setMaxAge(0);
	            response.addCookie(cookie);
	        }
	    }

	public static String disableButtonNext(HttpServletRequest req) {
		HttpSession session = req.getSession();
		if(session.getAttribute("count") != null && session.getAttribute("current") != null ) {
			int count  = (Integer)session.getAttribute("count");
			int current = (Integer)session.getAttribute("current");
			if(count < 1 && current >= count)
				return " disabled";
		}
		return "";
	}
}
