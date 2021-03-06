CREATE DATABASE FujiTraining;

USE FujiTraining;

DROP TABLE FujiTraining.dbo.MSTUSER;

IF NOT EXISTS 
(
	SELECT [NAME] 
	FROM sys.tables 
	WHERE [NAME] = 'MSTUSER'
)
CREATE TABLE MSTUSER (
	PSN_CD NUMERIC(4) PRIMARY KEY IDENTITY(1,1),
	USERID VARCHAR(8),
	PASSWORD VARCHAR(8),
	USERNAME VARCHAR(40),
	DELETE_YMD DATETIME DEFAULT NULL,
	INSERT_YMD TIMESTAMP ,
	INSERT_PSN_CD NUMERIC(5) DEFAULT 0,
	UPDATE_YMD DATETIME DEFAULT NULL,
	UPDATE_PSN_CD NUMERIC(5) DEFAULT 0			
)

IF NOT EXISTS 
(
	SELECT [NAME] 
	FROM sys.tables 
	WHERE [NAME] = 'MSTCUSTOMER'
)
CREATE TABLE MSTCUSTOMER (
	CUSTOMER_ID NUMERIC(8) PRIMARY KEY DEFAULT(NEXT VALUE FOR dbo.CUSTOMER_SEQUENCE),
	CUSTOMER_NAME VARCHAR(50),
	SEX VARCHAR(1),
	BIRTHDAY VARCHAR(10),
	EMAIL VARCHAR(40),
	ADDRESS VARCHAR(256) ,
	DELETE_YMD DATETIME DEFAULT NULL,
	INSERT_YMD TIMESTAMP ,
	INSERT_PSN_CD NUMERIC(5) DEFAULT 0,
	UPDATE_YMD DATETIME DEFAULT NULL,
	UPDATE_PSN_CD NUMERIC(5) DEFAULT 0				
)
