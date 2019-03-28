package DAO;

import Common.QueryConstant;
import Common.VariableConstant;
import DTO.Customer;
import DTO.Customer.CustomerBuilder;
import Utils.SQLConnect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAO_Impl implements ICustomerDAO {

	@Override
	public ArrayList<Customer> loadCustomer(int offset, int number) {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		try (ResultSet rs = SQLConnect.getInstance().select(QueryConstant.LOAD_CUSTOMER, offset, number == -1 ? VariableConstant.NUMBR_SEARCH : number )) {
    		while(rs.next()) { // start loop to get each record
				Customer customer = createCustomer(rs, false);
				customers.add(customer);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customers;		
	}

	private Customer createCustomer(ResultSet rs, boolean email) throws SQLException {
        Customer customer;
	    if(email) {
	        customer = new CustomerBuilder()
                    .setCustomerId(rs.getString("CUSTOMER_ID"))
                    .setCustomerName(rs.getString("CUSTOMER_NAME"))
                    .setBirthday(rs.getString("BIRTHDAY"))
                    .setAddress(rs.getString("ADDRESS"))
                    .setSex(rs.getString("SEX"))
                    .setEmail(rs.getString("EMAIL"))
                    .build();
        } else {
            customer = new CustomerBuilder()
                    .setCustomerId(rs.getString("CUSTOMER_ID"))
                    .setCustomerName(rs.getString("CUSTOMER_NAME"))
                    .setBirthday(rs.getString("BIRTHDAY"))
                    .setAddress(rs.getString("ADDRESS"))
                    .setSex(rs.getString("SEX"))
                    .build();
        }

		return customer;
	}

	@Override
	public ArrayList<Customer> searchCustomers(int offset, int number, String cusName, String sex, String bdFrom, String bdTo) {
		if(bdFrom.length() == 0) bdFrom = "null";
		if(bdTo.length() == 0) bdTo = "null";
	    ArrayList<Customer> customers = new ArrayList<Customer>();
		try (ResultSet rs = SQLConnect
                                .getInstance()
                                .select(QueryConstant.SEARCH_CUSTOMERS,
                                        offset,
                                        number == -1 ? VariableConstant.NUMBR_SEARCH : number ,
                                        cusName,
                                        sex,
                                        bdFrom,
                                        bdTo)) {
			while(rs.next()) { // start loop to get each record
                Customer customer = createCustomer(rs, false);
                customers.add(customer);
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return customers;

	}

    @Override
    public int countCustomer(String cusName, String sex, String bdFrom, String bdTo) {
        if(bdFrom.length() == 0) bdFrom = "null";
        if(bdTo.length() == 0) bdTo = "null";
	    try (ResultSet rs = SQLConnect
                .getInstance()
                .select(QueryConstant.COUNT_CUSTOMERS,
                        cusName,
                        sex,
                        bdFrom,
                        bdTo)) {
            while(rs.next()) { // start loop to get each record
                return rs.getInt("COUNT_ALL");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	    return 0;
    }

    @Override
    public boolean deleteCustomer(String cusId) {
        try {
            SQLConnect
                .getInstance()
                .select(QueryConstant.DELETE_CUSTOMER, cusId);
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Customer getCustomerInformation(String id) {

        try (ResultSet rs = SQLConnect
                .getInstance()
                .select(QueryConstant.GET_CUSTOMER_INFO,
                        id)) {
            while(rs.next()) { // start loop to get each record
                return createCustomer(rs, true);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    return null;
    }

    @Override
    public void updateCustomerInformation(int psnCD , int id, String name, String sex, String bd, String email, String address) {
        try {
            SQLConnect
                    .getInstance()
                    .select(QueryConstant.UPDATE_CUSTOMER, psnCD, name, sex, bd, email, address, id);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void insertCustomerInformation(int psnCD, String name, String sex, String bd, String email, String address) {
        try {
            SQLConnect
                    .getInstance()
                    .select(QueryConstant.INSERT_CUSTOMER, name, sex, bd, email, address, psnCD, psnCD);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public int getNextCustomerId() {
        try (ResultSet rs = SQLConnect
                .getInstance()
                .select(QueryConstant.GET_NEXT_CUSTOMER_ID, "CUSTOMER_SEQUENCE")) {
            while(rs.next()) { // start loop to get each record
                return rs.getInt("ID");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }

}
