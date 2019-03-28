package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Common.UrlConstant;
import DAO.CustomerDAO_Impl;
import DAO.ICustomerDAO;
import DTO.Customer;
import Utils.CookieHelper;

@WebServlet("/edit")
public class EditController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		if(id != null) {
			ICustomerDAO iCustomerDAO = new CustomerDAO_Impl();
			Customer customer = iCustomerDAO.getCustomerInformation(id);
			req.setAttribute("customer", customer);
			req.setAttribute("type", "edit");
			req.setAttribute("screenName", "Edit");

		} else  {
			ICustomerDAO iCustomerDAO = new CustomerDAO_Impl();

			req.setAttribute("type", "insert");
			req.setAttribute("screenName", "Add New");
			req.setAttribute("nextCusId", Integer.toString(iCustomerDAO.getNextCustomerId() + 1));
		}
		req.getRequestDispatcher(UrlConstant.EDIT).forward(req, resp);
	}
	
	@SuppressWarnings("Duplicates")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String type = req.getParameter("type");

		if(type.equals("edit")) {
			String email = req.getParameter("email");
			String sex = req.getParameter("sex");
			String address = req.getParameter("address");
			String id = req.getParameter("id");
			String name = req.getParameter("name");
			String bd = req.getParameter("bd");

			int psnCD = Integer.parseInt(CookieHelper.getCookie("psnCD",req));

			ICustomerDAO iCustomerDAO = new CustomerDAO_Impl();
			iCustomerDAO.updateCustomerInformation(psnCD,Integer.parseInt(id),name, sex, bd, email,address);
			resp.getWriter().write("Edit success");
		} else if(type.equals("insert")) {
			String email = req.getParameter("email");
			String sex = req.getParameter("sex");
			String address = req.getParameter("address");
			String name = req.getParameter("name");
			String bd = req.getParameter("bd");
			int psnCD = Integer.parseInt(CookieHelper.getCookie("psnCD",req));
			ICustomerDAO iCustomerDAO = new CustomerDAO_Impl();
			iCustomerDAO.insertCustomerInformation(psnCD,name, sex, bd, email,address);
			resp.getWriter().write("Insert success");
		}



	}
}
