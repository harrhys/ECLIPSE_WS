/*=================================================*/
/*  Database tables for PetStore 1.3_01            */
/*                                                 */
/*    used by SupplierDB schema                    */
/*=================================================*/

drop table SupplierOrderEJBTable;
drop table ContactInfoEJBTable;
drop table AddressEJBTable;
drop table InventoryEJBTable;
drop table LineItemEJBTable;

CREATE TABLE AddressEJBTable (
   PMPrimaryKey NUMBER(22) NOT NULL,
   reverse_address___PMPrimar NUMBER(22),
   streetName1 VARCHAR2(255),
   streetName2 VARCHAR2(255),
   city VARCHAR2(255),
   state VARCHAR2(80),
   zipCode VARCHAR2(25),
   country VARCHAR2(80)
   );

ALTER TABLE AddressEJBTable ADD CONSTRAINT pk_AddressEJBTabl Primary
Key(PMPrimaryKey);

CREATE TABLE ContactInfoEJBTable (
   PMPrimaryKey NUMBER(22) NOT NULL,
   reverse_contactInfo_poId VARCHAR2(255),
   address___PMPrimaryKey NUMBER(22),
   email VARCHAR2(255),
   familyName VARCHAR2(255),
   givenName VARCHAR2(255),
   telephone VARCHAR2(80)
   );
   
ALTER TABLE ContactInfoEJBTable ADD CONSTRAINT pk_ContactInfoEJBTabl
Primary Key (PMPrimaryKey);

ALTER TABLE ContactInfoEJBTable ADD CONSTRAINT FK_AddressEJBTable_1 Foreign Key (address___PMPrimaryKey)REFERENCES AddressEJBTable (PMPrimaryKey);

CREATE TABLE InventoryEJBTable (
  itemId VARCHAR2(80) NOT NULL,
  quantity NUMBER(22) NOT NULL 
  );
  
ALTER TABLE InventoryEJBTable ADD CONSTRAINT pk_InventoryEJBTabl 
  Primary Key (itemId);

  CREATE TABLE LineItemEJBTable (
  PMPrimaryKey NUMBER(22) NOT NULL,
  SupplierOrderEJB__PMPK VARCHAR2(255) NOT NULL,
  categoryId VARCHAR2(80),
  itemId VARCHAR2(80),
  lineItemStatus VARCHAR2(25),
  lineNumber VARCHAR2(80),
  productId VARCHAR2(80),
  quantity NUMBER(22) NOT NULL,
  unitPrice NUMBER(10,2) NOT NULL 
  );
  
ALTER TABLE LineItemEJBTable ADD CONSTRAINT pk_LineItemEJBTabl 
Primary Key (PMPrimaryKey);



CREATE TABLE SupplierOrderEJBTable (
  contactInfo___PMPrimaryKey NUMBER(22),
  poDate NUMBER(22) NOT NULL,
  poId VARCHAR2(255) NOT NULL,
  poStatus VARCHAR2(25) 
  );
  
ALTER TABLE SupplierOrderEJBTable
   ADD CONSTRAINT pk_SupplierOrderEJBTabl Primary Key (poId);

ALTER TABLE SupplierOrderEJBTable ADD CONSTRAINT FK_CONTACTINFOEJBTABLE_1 Foreign Key (contactInfo___PMPrimaryKey )REFERENCES CONTACTINFOEJBTABLE(PMPrimaryKey);

ALTER TABLE LineItemEJBTable ADD CONSTRAINT FK_SupplierOrderEJBTable Foreign Key (SupplierOrderEJB__PMPK) REFERENCES SupplierOrderEJBTable(poId);
      
commit;

/************************/
/***  SupplierDB      ***/
/************************/
insert into InventoryEJBTable (itemId,quantity) values ('EST-1',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-2',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-3',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-4',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-5',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-6',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-7',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-8',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-9',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-10',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-11',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-12',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-13',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-14',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-15',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-16',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-17',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-18',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-19',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-20',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-21',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-22',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-23',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-24',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-25',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-26',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-27',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-28',10000);
insert into InventoryEJBTable (itemId,quantity) values ('EST-29',10000);

commit;

