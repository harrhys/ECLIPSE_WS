-- ========================================================
-- Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
-- ========================================================
drop table checking;

create table checking  
(id varchar(3) constraint pk_checking primary key,  
balance decimal(10,2));

insert into checking  
values ('123', 100.00);

drop table saving;

create table saving  
(id varchar(3) constraint pk_saving primary key,  
balance decimal(10,2));

insert into saving  
values ('123', 500.00);

exit;
