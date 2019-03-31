package DAO;

import DTO.User;

public interface IUserDAO {

	/*
	 * Get user information
	 * return user information if success
	 * return null if fail
	 **/
	public User getUserInformation(String userId, String password);

	/*
	* Check user existed in database or not
	* return true if found
	* return false if not found
	* */
	public boolean checkUserExist(String userId, String password) throws ClassNotFoundException;

	/*
	* Check user in session and user in databse is 1 or not
	* return true if it is 1
	* return false if it is not 1
	* */
	public boolean checkUserSession(User user) throws ClassNotFoundException;

}
