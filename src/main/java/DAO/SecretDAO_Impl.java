package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import Common.QueryConstant;
import Utils.SQLConnect;

public class SecretDAO_Impl implements ISecretDAO{

	@Override
	public String getUserSecretKey() {
		try (ResultSet rs = SQLConnect.getInstance().select(QueryConstant.GET_USER_SECRETKEY)) {
    		while(rs.next()) { // start loop to get each record
    			return rs.getString("USER_KEY");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
