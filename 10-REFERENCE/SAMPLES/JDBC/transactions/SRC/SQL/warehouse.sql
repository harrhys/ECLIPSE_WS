-- ========================================================
--copyright © 2002 Sun Microsystems, Inc. All rights reserved.
-- ========================================================

drop table inventory;


create table inventory  
(product_id varchar(3),  
quantity decimal(10));

insert into inventory  
values ('123', 100);

drop table order_item;

create table order_item  
(order_id varchar(3),  
product_id varchar(3),  
status varchar(8));

insert into order_item  
values ('456', '123', 'open');

commit;
exit;
