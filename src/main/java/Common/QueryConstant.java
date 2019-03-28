package Common;

public class QueryConstant {
	// ========================================
	public static final String GET_USER_INFO = "SELECT USERID, PSN_CD, USERNAME FROM MSTUSER WHERE USERID = ? AND PASSWORD = ? AND DELETE_YMD IS NULL";
	public static final String GET_USER = "SELECT count(*) FROM MSTUSER WHERE USERID = ? AND PASSWORD = ? AND DELETE_YMD IS NULL";
	public static final String CHECK_USER_SESSION = "SELECT count(*) FROM MSTUSER WHERE PSN_CD = ? AND USERID = ? AND USERNAME = ? AND DELETE_YMD IS NULL";
	public static final String GET_USER_SECRETKEY = "SELECT USER_KEY FROM SECRETKEY";
	// ========================================
	public static final String LOAD_CUSTOMER = "SELECT CUSTOMER_ID, CUSTOMER_NAME, BIRTHDAY, ADDRESS, (CASE SEX WHEN '0' THEN 'Male' WHEN '1' THEN 'Female' END) AS SEX, (SELECT COUNT(*) FROM MSTCUSTOMER ) AS COUNT_ALL FROM MSTCUSTOMER WHERE DELETE_YMD IS NULL ORDER BY CUSTOMER_ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
	public  static  final String SEARCH_CUSTOMERS = "EXEC dbo.SearchCustomer ?, ?, ?, ?, ?, ?";
	public  static  final String COUNT_CUSTOMERS = "EXEC dbo.CountCustomer  ?, ?, ?, ?";
	public  static  final String DELETE_CUSTOMER = "UPDATE MSTCUSTOMER SET DELETE_YMD = CURRENT_TIMESTAMP WHERE CUSTOMER_ID = ? ";
	public  static final String GET_CUSTOMER_INFO = "SELECT * FROM MSTCUSTOMER WHERE CUSTOMER_ID = ?";

	public  static final  String UPDATE_CUSTOMER = "UPDATE MSTCUSTOMER SET UPDATE_PSN_CD = ? , UPDATE_YMD = CURRENT_TIMESTAMP , CUSTOMER_NAME = ? , SEX = ? , BIRTHDAY = ? , EMAIL = ? , ADDRESS = ? WHERE CUSTOMER_ID = ?";

	public  static final  String INSERT_CUSTOMER = "INSERT INTO MSTCUSTOMER(CUSTOMER_NAME, SEX, BIRTHDAY, EMAIL, ADDRESS, INSERT_PSN_CD, UPDATE_PSN_CD, UPDATE_YMD) VALUES (?, ?, ? ,?, ?, ?, ?, CURRENT_TIMESTAMP)";

	public static final String GET_NEXT_CUSTOMER_ID = "SELECT cast(current_value as int) AS ID FROM sys.sequences WHERE name = ?";

}
