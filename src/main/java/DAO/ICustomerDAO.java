package DAO;

import java.util.ArrayList;

import DTO.Customer;

public interface ICustomerDAO {
	public ArrayList<Customer> loadCustomer(int offset, int number);
	public ArrayList<Customer> searchCustomers(int offset, int number, String cusName, String Sex, String bdFrom, String bdTo);
	public int countCustomer(String cusName, String Sex, String bdFrom, String bdTo);
	public boolean deleteCustomer(String cusId);
	public Customer getCustomerInformation(String id);
	public void updateCustomerInformation(int psnCD , int id, String name, String sex, String bd, String email, String address);
	public void insertCustomerInformation(int psnCD , String name, String sex, String bd, String email, String address);

	public int getNextCustomerId ();

}
