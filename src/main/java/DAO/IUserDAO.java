package DAO;

		import DTO.User;

public interface IUserDAO {

	public User getUserInformation(String userId, String password);
	public boolean checkUserExist(String userId, String password) throws ClassNotFoundException;
	public boolean checkUserSession(User user) throws ClassNotFoundException;

}
