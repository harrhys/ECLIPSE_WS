-------------------------------------------------------------------------------------------
-- EBILLER
-------------------------------------------------------------------------------------------
CREATE TABLE EBILLER
(
  EBILLER_ID		        NUMBER		    NOT NULL,
  NAME			            VARCHAR2(50)	NOT NULL,
  BILLING_ADDRESS	        VARCHAR2(200)   NOT NULL,
  PHONE			            VARCHAR2(20)	NOT NULL
);


-------------
--PRIMARY KEY
-------------

ALTER TABLE EBILLER ADD CONSTRAINT EBILLER_PK PRIMARY KEY
    (EBILLER_ID) ENABLE;
