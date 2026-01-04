--- Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
drop table product;

create table product
(
  description varchar(255),
  price double precision not null,
  productid varchar(255) constraint pk_product primary key
);

commit;
