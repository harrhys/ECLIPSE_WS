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

create table account
(id varchar(4) constraint pk_account primary key,
balance decimal(10,2));

insert into account
values ('1000', 1000.00);

insert into account
values ('1001', 5000.00);

