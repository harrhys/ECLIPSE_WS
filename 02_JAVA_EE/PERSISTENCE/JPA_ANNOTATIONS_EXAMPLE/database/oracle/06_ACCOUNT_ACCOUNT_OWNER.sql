-------------------------------------------------------------------------------------------
-- ACCOUNT_ACCOUNT_OWNER
-------------------------------------------------------------------------------------------
CREATE TABLE ACCOUNT_ACCOUNT_OWNER
(
  ACCOUNT_ID          		NUMBER          NOT NULL,
  ACCOUNT_OWNER_ID          NUMBER          NOT NULL
);


-------------
--PRIMARY KEY
-------------
ALTER TABLE ACCOUNT_ACCOUNT_OWNER ADD CONSTRAINT ACCTACCTOWNR_PK PRIMARY KEY
    (ACCOUNT_ID, ACCOUNT_OWNER_ID) ENABLE;


--------------
--FOREIGN KEYS
--------------
ALTER TABLE ACCOUNT_ACCOUNT_OWNER ADD CONSTRAINT ACCTACCTOWNR_ACCT_FK FOREIGN KEY
    (ACCOUNT_ID)
    REFERENCES ACCOUNT (ACCOUNT_ID) ENABLE;
ALTER TABLE ACCOUNT_ACCOUNT_OWNER ADD CONSTRAINT ACCTACCTOWNR_ACCTOWNR_FK FOREIGN KEY
    (ACCOUNT_OWNER_ID)
    REFERENCES ACCOUNT_OWNER (ACCOUNT_OWNER_ID) ENABLE;
    

