-------------------------------------------------------------------------------------------
-- ACCOUNT_OWNER
-------------------------------------------------------------------------------------------
CREATE TABLE ACCOUNT_OWNER
(
  ACCOUNT_OWNER_ID          NUMBER		    NOT NULL,
  LAST_NAME                 VARCHAR2(50)    NOT NULL,
  FIRST_NAME                VARCHAR2(50)    NOT NULL,
  SOCIAL_SECURITY_NUMBER    VARCHAR2(50)    NOT NULL,
  STREET_ADDRESS            VARCHAR2(50)    NOT NULL,
  CITY                   	VARCHAR2(50)    NOT NULL,
  STATE                  	VARCHAR2(50)    NOT NULL,
  ZIP_CODE                	VARCHAR2(10)    NOT NULL,
  HOME_PHONE            	VARCHAR2(20),
  CELL_PHONE           		VARCHAR2(20)
);


-------------
--PRIMARY KEY
-------------

ALTER TABLE ACCOUNT_OWNER ADD CONSTRAINT ACCTOWNER_PK PRIMARY KEY
    (ACCOUNT_OWNER_ID) ENABLE;

-------------
--UNIQUE KEYS
-------------

ALTER TABLE ACCOUNT_OWNER ADD CONSTRAINT ACCTOWNER_SOCSECNUM_UK UNIQUE
    (SOCIAL_SECURITY_NUMBER) ENABLE;
