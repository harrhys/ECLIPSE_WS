-- ========================================================
-- Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
-- ========================================================

-- ========================================================
-- Create account Table and Data
-- ========================================================

-- ========================================================
-- Create DDL for table: account 
-- ========================================================
drop table account;

CREATE TABLE account (
id varchar(3) constraint pk_account primary key,
balance decimal(10,2),
max_withdrawal decimal(10,2)
);

insert into account
values ('001', 100.00, 300.00);

insert into account
values ('002', 500.00, 300.00);

commit;

