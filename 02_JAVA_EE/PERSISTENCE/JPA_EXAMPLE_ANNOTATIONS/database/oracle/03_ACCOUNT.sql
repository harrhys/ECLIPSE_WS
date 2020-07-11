-------------------------------------------------------------------------------------------
-- ACCOUNT
-------------------------------------------------------------------------------------------
CREATE TABLE ACCOUNT
(
  ACCOUNT_ID		        NUMBER		    NOT NULL,
  ACCOUNT_TYPE		        VARCHAR2(200)   NOT NULL,
  CREATION_DATE		        TIMESTAMP	    NOT NULL,
  BALANCE		            NUMBER		    NOT NULL,
  CHECK_STYLE				VARCHAR2(200),
  INTEREST_RATE				NUMBER,
  MATURITY_DATE				TIMESTAMP,  
  VERSION					NUMBER			NOT NULL
);


-------------
--PRIMARY KEY
-------------

ALTER TABLE ACCOUNT ADD CONSTRAINT ACCT_PK PRIMARY KEY
    (ACCOUNT_ID) ENABLE;
