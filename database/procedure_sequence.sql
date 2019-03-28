--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
CREATE PROCEDURE dbo.SearchCustomer 
	@Offset int,
	@Number int,
	@CustomerName  VARCHAR(50) = NULL,
	@Gender VARCHAR(1) = NULL,
	@BirthdayFrom NVARCHAR(10) = NULL,
	@BirthdayTo NVARCHAR(10) = NULL 
AS 
BEGIN
DECLARE 
	@Sql_Search NVARCHAR(MAX),
	@Sql_Query NVARCHAR(MAX)
SET @Sql_Query = 'SELECT CUSTOMER_ID, CUSTOMER_NAME, BIRTHDAY, ADDRESS, (CASE SEX WHEN ''0'' THEN ''Male'' WHEN ''1'' THEN ''Female'' END) AS SEX FROM MSTCUSTOMER '
SET @Sql_Search = 'WHERE DELETE_YMD IS NULL '

IF @CustomerName IS NOT NULL 	
	SET @Sql_Search = @Sql_Search + 'AND (CUSTOMER_NAME LIKE + ''%'' + @CustomerName + ''%'') '
IF @Gender IS NOT NULL 	
	SET @Sql_Search = @Sql_Search + 'AND SEX LIKE + ''%'' + @Gender + ''%'' '
	
IF @BirthdayFrom IS NOT NULL AND @BirthdayFrom <> 'null' AND @BirthdayFrom <> 'NULL' 
	SET @Sql_Search = @Sql_Search + 'AND CONVERT(datetime , BIRTHDAY , 111)  >= CONVERT(datetime , @BirthdayFrom , 111) '

IF @BirthdayTo IS NOT NULL AND @BirthdayTo <> 'null' AND @BirthdayTo <> 'NULL'
	SET @Sql_Search = @Sql_Search + 'AND CONVERT(datetime , BIRTHDAY , 111)  <= CONVERT(datetime , @BirthdayTo , 111) '

SET @Sql_Query = @Sql_Query + @Sql_Search + ' ORDER BY CUSTOMER_ID OFFSET @Offset ROWS FETCH NEXT @Number ROWS ONLY';

EXEC SP_EXECUTESQL @Sql_Query, 
	N'@Offset int, 
	@Number int, 
	@CustomerName VARCHAR(50), 
	@Gender VARCHAR(1), 
	@BirthdayFrom NVARCHAR(10), 
	@BirthdayTo NVARCHAR(10)', 
	@Offset,
	@Number,
	@CustomerName,
	@Gender,
	@BirthdayFrom, 
	@BirthdayTo;
END

--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------

CREATE PROCEDURE dbo.CountCustomer 
	@CustomerName  VARCHAR(50) = NULL,
	@Gender VARCHAR(1) = NULL,
	@BirthdayFrom NVARCHAR(10) = NULL,
	@BirthdayTo NVARCHAR(10) = NULL 
AS
BEGIN
DECLARE 
	@Sql_Search NVARCHAR(MAX),
	@Sql_Query NVARCHAR(MAX)
SET @Sql_Query = 'SELECT COUNT(*) AS COUNT_ALL FROM MSTCUSTOMER '
SET @Sql_Search = 'WHERE DELETE_YMD IS NULL '

IF @CustomerName IS NOT NULL 	
	SET @Sql_Search = @Sql_Search + 'AND (CUSTOMER_NAME LIKE + ''%'' + @CustomerName + ''%'') '
IF @Gender IS NOT NULL 	
	SET @Sql_Search = @Sql_Search + 'AND SEX LIKE + ''%'' + @Gender + ''%'' '
	
IF @BirthdayFrom IS NOT NULL AND @BirthdayFrom <> 'null' AND @BirthdayFrom <> 'NULL' 
	SET @Sql_Search = @Sql_Search + 'AND CONVERT(datetime , BIRTHDAY , 111)  >= CONVERT(datetime , @BirthdayFrom , 111) '

IF @BirthdayTo IS NOT NULL AND @BirthdayTo <> 'null' AND @BirthdayTo <> 'NULL'
	SET @Sql_Search = @Sql_Search + 'AND CONVERT(datetime , BIRTHDAY , 111)  <= CONVERT(datetime , @BirthdayTo , 111) '

SET @Sql_Query = @Sql_Query + @Sql_Search; 

EXEC SP_EXECUTESQL @Sql_Query, 
	N'@CustomerName VARCHAR(50), 
	@Gender VARCHAR(1), 
	@BirthdayFrom NVARCHAR(10), 
	@BirthdayTo NVARCHAR(10)', 
	@CustomerName,
	@Gender,
	@BirthdayFrom, 
	@BirthdayTo;
END

--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
DROP PROCEDURE SearchCustomer;  
DROP PROCEDURE CountCustomer;  

EXEC  dbo.SearchCustomer  0,15,'', '', null, null;
EXEC  dbo.CountCustomer  '', '', null, null;


--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------

CREATE SEQUENCE CUSTOMER_SEQUENCE
start with 10000000
increment by 1
minvalue 0
maxvalue 99999999
cycle;