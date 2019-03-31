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
        req.getRequestDispatcher(UrlConstant.SEARCH).forward(req, resp); // send to search.jsp
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

    /**
     * This function will search with some condition from UI
     * @param req request from search.jsp contains some conditions like customer name, sex, birthday from, birthday to
     * @param resp response will send some data from here to search.jsp
     * @return list customer fit with condition
     * @throws ServletException handle serclet exception
     * @throws IOException handle some req, res execption
     */
	void searchWithConditon(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get parameter from search.jsp
		String cusName = req.getParameter("cusName"); // get customer name
		String sex = req.getParameter("sex"); // get sex
		String bdFrom = req.getParameter("bdFrom"); // get birthday from
		String bdTo = req.getParameter("bdTo"); // get birthday to

		// Query data in database
		ICustomerDAO iCustomerDAO = new CustomerDAO_Impl();
		// start connect to databse to get data
		ArrayList<Customer> customers  = iCustomerDAO.searchCustomers(0, -1, cusName, sex, bdFrom, bdTo);

		// if customer is existed, start counting customer with fit condition in database and plus 1
        // we plus with 1 because we start paginating from 1, if from 0 we don't nedd plus 1
        // else if customer not found , mean 0 page -> count = 0
		int count  = customers.size() != 0 ? 1 + iCustomerDAO.countCustomer(cusName,sex,bdFrom, bdTo)/VariableConstant.NUMBR_SEARCH : 0;

		// Send data to JSP
		req.setAttribute("customers", customers); // add to req

		// Add search value to session
		setSessionValue(count,1,cusName,sex,bdFrom,bdTo,req);
		// Navigate to jsp
		req.getRequestDispatcher(UrlConstant.SEARCH_TABLE).forward(req, resp);
	}

    /**
     * This function will search all customers, don't care about the condition
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
	void searchAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Query data in database
		ICustomerDAO iCustomerDAO = new CustomerDAO_Impl();
        // start connect to databse to get data
		ArrayList<Customer> customers  = iCustomerDAO.searchCustomers(0, -1, "","", "", "");
        // if customer is existed, start counting customer in database and plus 1
        // we plus with 1 because we start paginating from 1, if from 0 we don't nedd plus 1
        // else if customer empty, mean 0 page -> count = 0
		int count  = customers.size() != 0 ? 1 + iCustomerDAO.countCustomer("","","", "")/VariableConstant.NUMBR_SEARCH : 0;
		// Send data to JSP
		req.setAttribute("customers", customers);
		// Add search value to session to reuse
		setSessionValue(count,1,"","","","",req);
		// Navigate to jsp
		req.getRequestDispatcher(UrlConstant.SEARCH_TABLE).forward(req, resp);
	}

    /**
     * This function will handle pargination
     * @param type the type of next, first, last, previous
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
	void pargination(String type,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Query data in database
		HttpSession session = req.getSession();
		String cusName = (String) session.getAttribute("cusName"); // get customer name condition
		String sex = (String) session.getAttribute("sex"); // get sex condition
		String bdFrom = (String) session.getAttribute("bdFrom"); // get virthday from condition
		String bdTo = (String) session.getAttribute("bdTo"); // get birthday to condition
		int current = (Integer) session.getAttribute("current"); // get the current page

		if(type.equals("btnPrev")) { // Beginning of If
			current--; // previous means decrease the current page by 1
		} else if(type.equals("btnNext")) {
			current ++; // next means incerase the current page by 1
		} else if(type.equals("btnFirst")) {
			current = 1; // first means current page is first -> 1
		} else { // get last page
			current = (Integer) session.getAttribute("count"); // last means current page is final -> count
		} // Ending of If

        // add current page to session
		session.setAttribute("current", current);
        // start connect to databse to get data
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

    /**
     * This function will delete list customer
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
	void deleteCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ids = req.getParameter("ids");
	//	log("ids=" + ids);

		List<String> items = new ArrayList<>(Arrays.asList(ids.split(",")));
	//	log("items=" + items);

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
		// get type parameter response from search.jsp
	    String type = req.getParameter("type");

		switch (type) {
			case "search": { // meaning search with some condition like customer name, gender, Birthday from, Birthday to
				searchWithConditon(req, resp);
				break;
			}
			case "all": { // meaning search all, don't care about condition
				searchAll(req, resp);
				break;
			}
			case "delete": { // meaning delete customer(s)
				deleteCustomer(req, resp);
				break;
			}
			default: { // meaning when user click button next, first, last, previous, we will start sending fit data to user
				pargination(type, req, resp);
				break;
			}
		}
	}
}
// 1 2  3  4
// 0 15 30 45