-------------------------------------------------------------------------------------------
-- ACCOUNT_EBILLER
-------------------------------------------------------------------------------------------
CREATE TABLE ACCOUNT_EBILLER
(
  ACCOUNT_EBILLER_ID		NUMBER	        NOT NULL,
  ACCOUNT_ID          		NUMBER          NOT NULL,
  EBILLER_ID                NUMBER          NOT NULL,
  ACCOUNT_OWNER_ID          NUMBER          NOT NULL,
  ACCOUNT_NUMBER			VARCHAR2(50)    NOT NULL,
  DATE_ADDED				TIMESTAMP       NOT NULL
);


-------------
--PRIMARY KEY
-------------

ALTER TABLE ACCOUNT_EBILLER ADD CONSTRAINT ACCTEBILLER_PK PRIMARY KEY
    (ACCOUNT_EBILLER_ID) ENABLE;


--------------
--FOREIGN KEYS
--------------

ALTER TABLE ACCOUNT_EBILLER ADD CONSTRAINT ACCTEBILLER_ACCT_FK FOREIGN KEY
    (ACCOUNT_ID)
    REFERENCES ACCOUNT (ACCOUNT_ID) ENABLE;


ALTER TABLE ACCOUNT_EBILLER ADD CONSTRAINT ACCTEBILLER_EBILLER_FK FOREIGN KEY
    (EBILLER_ID)
    REFERENCES EBILLER (EBILLER_ID) ENABLE;

ALTER TABLE ACCOUNT_EBILLER ADD CONSTRAINT ACCTEBILLER_ACCTOWNR_FK FOREIGN KEY
    (ACCOUNT_OWNER_ID)
    REFERENCES ACCOUNT_OWNER (ACCOUNT_OWNER_ID) ENABLE;