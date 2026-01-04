-- create tables for online banking app
-- also seeds next_id tables w. initial values

DROP TABLE customer_account_xref;
DROP TABLE tx;
DROP TABLE customer;
DROP TABLE account;
DROP TABLE next_tx_id;
DROP TABLE next_customer_id;
DROP TABLE next_account_id;


CREATE TABLE account
   (account_id VARCHAR(8) 
       CONSTRAINT pk_account PRIMARY KEY,
    type VARCHAR(24),
    description VARCHAR(30),
    balance NUMERIC(10,2),
    credit_line NUMERIC(10,2),
    begin_balance NUMERIC(10,2),
    begin_balance_time_stamp TIMESTAMP);

CREATE TABLE customer
   (customer_id VARCHAR(8) 
       CONSTRAINT pk_customer PRIMARY KEY,
    last_name VARCHAR(30),
    first_name VARCHAR(30),
    middle_initial VARCHAR(1),
    street VARCHAR(40),
    city VARCHAR(40),
    state VARCHAR(2),
    zip VARCHAR(5),
    phone VARCHAR(16),
    email VARCHAR(30));

CREATE TABLE tx 
   (tx_id VARCHAR(8) 
       CONSTRAINT pk_tx PRIMARY KEY,
    account_id VARCHAR(8),
    time_stamp TIMESTAMP,
    amount NUMERIC(10,2),
    balance NUMERIC(10,2),
    description VARCHAR(30));

CREATE TABLE customer_account_xref
   (customer_id VARCHAR(8), 
    account_id VARCHAR(8));


CREATE TABLE next_account_id
   (id INTEGER);

CREATE TABLE next_customer_id
   (id INTEGER);

CREATE TABLE next_tx_id
   (id INTEGER); 

INSERT INTO next_account_id
   VALUES (5050);

INSERT INTO next_customer_id
   VALUES (150);

INSERT INTO next_tx_id
   VALUES (1);



EXIT;
