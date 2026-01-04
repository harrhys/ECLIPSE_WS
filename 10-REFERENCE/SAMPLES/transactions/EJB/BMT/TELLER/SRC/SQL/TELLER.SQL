-- ========================================================
-- Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
-- ========================================================

drop table checking;

create table checking  
(id varchar(3) constraint pk_checking primary key,  
balance decimal(10,2));

insert into checking  
values ('123', 500.00);

drop table cash_in_machine;

create table cash_in_machine  
(amount decimal(10,2),  
time_stamp date);

insert into cash_in_machine  
values (10000.00, current_date);

exit;
