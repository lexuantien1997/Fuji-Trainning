package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import Common.QueryConstant;
import DTO.User;
import DTO.User.UserBuilder;
import Utils.SQLConnect;

public class UserDAO_Impl implements IUserDAO {

	public User getUserInformation(String userId, String password) {
		User user = null;
		try (ResultSet rs = SQLConnect.getInstance().select(QueryConstant.GET_USER_INFO, userId, password)) {
    		while(rs.next()) { // start loop to get each record
    			user = new UserBuilder()
    					.setUserId(rs.getString("USERID"))
    					.setUserName(rs.getString("USERNAME"))
    					.setPsnCD(rs.getString("PSN_CD"))
    					.build();
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
    			return rs.getInt(1) == 1 ? true: false;
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
										.select(QueryConstant.CHECK_USER_SESSION, 
												user.getPsnCD(),
												user.getUserId(), 
												user.getUserName())) {
    		while(rs.next()) { // start loop to get each record
    			return rs.getInt(1) == 1 ? true: false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
