-------------------------------------------------------------------------------------------
-- ACCOUNT_TRANSACTION
-------------------------------------------------------------------------------------------
CREATE TABLE ACCOUNT_TRANSACTION
(
  ACCOUNT_TRANSACTION_ID	NUMBER		    NOT NULL,
  ACCOUNT_ID			    NUMBER          NOT NULL,
  TRANSACTION_TYPE		    VARCHAR2(200)	NOT NULL,
  TXDATE			        TIMESTAMP	    NOT NULL,
  AMOUNT			        NUMBER(10,2)	NOT NULL
);


-------------
--PRIMARY KEY
-------------

ALTER TABLE ACCOUNT_TRANSACTION ADD CONSTRAINT TX_PK PRIMARY KEY
    (ACCOUNT_TRANSACTION_ID) ENABLE;


--------------
--FOREIGN KEYS
--------------
ALTER TABLE ACCOUNT_TRANSACTION ADD CONSTRAINT TX_ACCTID_ACCT_FK FOREIGN KEY
    (ACCOUNT_ID)
    REFERENCES ACCOUNT (ACCOUNT_ID) ENABLE;
    
