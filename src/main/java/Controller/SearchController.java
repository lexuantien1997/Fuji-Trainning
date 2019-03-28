package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Common.UrlConstant;
import Common.VariableConstant;
import DAO.CustomerDAO_Impl;
import DAO.ICustomerDAO;
import DTO.Customer;
import com.sun.deploy.net.HttpResponse;

@SuppressWarnings("ALL")
@WebServlet("/search")
public class SearchController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(UrlConstant.SEARCH).forward(req, resp);
    }

	private void setSessionValue(int count,int current,String cusName, String sex, String bdFrom, String bdTo, HttpServletRequest req) throws ServletException, IOException {
		// Add to session:
		HttpSession session = req.getSession();

		session.setAttribute("cusName", cusName);
		session.setAttribute("sex", sex);
		session.setAttribute("bdFrom", bdFrom);
		session.setAttribute("bdTo", bdTo);
		session.setAttribute("count", count);
		session.setAttribute("current", current);
	}

	void searchWithConditon(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get parameter
		String cusName = req.getParameter("cusName");
		String sex = req.getParameter("sex");
		String bdFrom = req.getParameter("bdFrom");
		String bdTo = req.getParameter("bdTo");

		// Query data in database
		ICustomerDAO iCustomerDAO = new CustomerDAO_Impl();
		ArrayList<Customer> customers  = iCustomerDAO.searchCustomers(0, -1, cusName, sex, bdFrom, bdTo);
		int count  = customers.size() != 0 ? 1 + iCustomerDAO.countCustomer(cusName,sex,bdFrom, bdTo)/VariableConstant.NUMBR_SEARCH : 0;
		// Send data to JSP
		req.setAttribute("customers", customers);

		// Add search value to session
		setSessionValue(count,1,cusName,sex,bdFrom,bdTo,req);
		// Navigate to jsp
		req.getRequestDispatcher(UrlConstant.SEARCH_TABLE).forward(req, resp);
	}

	void searchAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Query data in database
		ICustomerDAO iCustomerDAO = new CustomerDAO_Impl();
		ArrayList<Customer> customers  = iCustomerDAO.searchCustomers(0, -1, "","", "", "");
		int count  = customers.size() != 0 ? 1 + iCustomerDAO.countCustomer("","","", "")/VariableConstant.NUMBR_SEARCH : 0;
		// Send data to JSP
		req.setAttribute("customers", customers);
		// Add search value to session
		setSessionValue(count,1,"","","","",req);
		// Navigate to jsp
		req.getRequestDispatcher(UrlConstant.SEARCH_TABLE).forward(req, resp);
	}

	void pargination(String type,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String cusName = (String) session.getAttribute("cusName");
		String sex = (String) session.getAttribute("sex");
		String bdFrom = (String) session.getAttribute("bdFrom");
		String bdTo = (String) session.getAttribute("bdTo");
		int current = (Integer) session.getAttribute("current");

		if(type.equals("btnPrev")) {
			current--; // 2
		} else if(type.equals("btnNext")) {
			current ++;
		} else if(type.equals("btnFirst")) {
			current = 1;
		} else {
			current = (Integer) session.getAttribute("count");
		}

		session.setAttribute("current", current);

		ICustomerDAO iCustomerDAO = new CustomerDAO_Impl();
		ArrayList<Customer> customers
				= iCustomerDAO.searchCustomers(
				current*VariableConstant.NUMBR_SEARCH == VariableConstant.NUMBR_SEARCH ? 1 : current*VariableConstant.NUMBR_SEARCH - VariableConstant.NUMBR_SEARCH,
				-1,
				cusName,
				sex,
				bdFrom,
				bdTo);
		req.setAttribute("customers", customers);
		req.getRequestDispatcher(UrlConstant.SEARCH_TABLE).forward(req, resp);
	}

	void deleteCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ids = req.getParameter("ids");
		log("ids=" + ids);

		List<String> items = new ArrayList<>(Arrays.asList(ids.split(",")));
		log("items=" + items);

		ICustomerDAO iCustomerDAO = new CustomerDAO_Impl();
		for (String item: items) {
			iCustomerDAO.deleteCustomer(item);
		}

		HttpSession session = req.getSession();
		String cusName = (String) session.getAttribute("cusName");
		String sex = (String) session.getAttribute("sex");
		String bdFrom = (String) session.getAttribute("bdFrom");
		String bdTo = (String) session.getAttribute("bdTo");

		ArrayList<Customer> customers  = iCustomerDAO.searchCustomers(1, -1, cusName, sex, bdFrom, bdTo);
		int count = 1 + iCustomerDAO.countCustomer(cusName,sex,bdFrom, bdTo)/VariableConstant.NUMBR_SEARCH;
		// Send data to JSP
		req.setAttribute("customers", customers);

		// Add search value to session
		setSessionValue(count,1,cusName,sex,bdFrom,bdTo,req);
		// Navigate to jsp
		req.getRequestDispatcher(UrlConstant.SEARCH_TABLE).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String type = req.getParameter("type");

		switch (type) {
			case "search": {
				searchWithConditon(req, resp);
				break;
			}
			case "all": {
				searchAll(req, resp);
				break;
			}
			case "delete": {
				deleteCustomer(req, resp);
				break;
			}
			default: {
				pargination(type, req, resp);
				break;
			}
		}
	}
}
// 1 2  3  4
// 0 15 30 45