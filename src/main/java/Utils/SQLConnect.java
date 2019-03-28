package Utils;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class SQLConnect {
	private String hostName;
	private String database;
	private String userName;
	private String password;
	
    private ArrayList<Object> params = new ArrayList<Object>();
	
	private static SQLConnect instance = null; 
	
	private SQLConnect() {
		hostName = "localhost";
		database = "FujiTraining";
		userName = "sa";
		password = "root";
	}
	
	// static method to create instance of Singleton class 
    public static SQLConnect getInstance() 
    { 
        // To ensure only one instance is created 
        if (instance == null) 
        { 
        	instance = new SQLConnect(); 
        } 
        return instance; 
    } 

    public Connection getConnection() throws ClassNotFoundException, SQLException {

    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
 
        String connectionURL = "jdbc:sqlserver://" + hostName + ":1433;" + "databaseName=" + database + ";integratedSecurity=false";
 
        Connection conn = DriverManager.getConnection(connectionURL, userName, password);
        
        return conn;
    }
    
 
    
    public ResultSet select(String query, Object ...params) throws ClassNotFoundException, SQLException {

    	Connection cnn = getConnection();    	
    	PreparedStatement ptm = cnn.prepareStatement(query);
    	this.params.clear();
    	this.params.addAll(Arrays.asList(params));      	
    	int count = 1;
    	for (Object param : this.params) {
            if (param instanceof String) 
            	ptm.setString(count, (String) param);
            else if (param instanceof Integer) 
            	ptm.setInt(count, (Integer) param);
            else if (param instanceof Long) 
            	ptm.setLong(count, (Long) param);
            else if (param instanceof Float) 
            	ptm.setFloat(count, (Float) param);
            else if (param instanceof Double) 
            	ptm.setDouble(count, (Double) param);
            else if (param instanceof Date)
            	ptm.setDate(count, (Date) param);
            count++;         
        }
    	  	  	
    	return ptm.executeQuery();
    }
}
