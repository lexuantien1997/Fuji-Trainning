package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import Common.QueryConstant;
import DTO.User;
import DTO.User.UserBuilder;
import Utils.SQLConnect;

public class UserDAO_Impl implements IUserDAO {

	public User getUserInformation(String userId, String password) {
		User user = null; // return null if can not get user information
		try (ResultSet rs = SQLConnect.getInstance().select(QueryConstant.GET_USER_INFO, userId, password)) {
    		while(rs.next()) { // start loop to get each record
    			user = new UserBuilder() // using builder pattern
    					.setUserId(rs.getString("USERID")) // get USERID value in database
    					.setUserName(rs.getString("USERNAME")) // get USERNAME value in database
    					.setPsnCD(rs.getString("PSN_CD")) // get PSN_CD value in database
    					.build(); // start create user
    			return user;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	public boolean checkUserExist(String userId, String password) {		
		try (ResultSet rs = SQLConnect.getInstance().select(QueryConstant.GET_USER, userId,password)) {
    		while(rs.next()) { // start loop to get each record
    			return rs.getInt(1) == 1 ? true: false; // check user existed in database or not
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkUserSession(User user) throws ClassNotFoundException {
		try (ResultSet rs = SQLConnect .getInstance()
										.select(QueryConstant.CHECK_USER_SESSION, // query
												user.getPsnCD(),// psn para
												user.getUserId(),  // userid para
												user.getUserName())) { // username para
    		while(rs.next()) { // start loop to get each record
    			return rs.getInt(1) == 1 ? true: false; // check user existed in database or not
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false; // return false if fail
	}
}
