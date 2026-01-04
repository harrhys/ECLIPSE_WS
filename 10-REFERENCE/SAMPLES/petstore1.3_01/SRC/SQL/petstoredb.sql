drop table ACCOUNTEJBTABLE;
drop table ADDRESSEJBTABLE;
drop table ITEM_DETAILS;
drop table PRODUCT_DETAILS;
drop table CATEGORY_DETAILS;
drop table CONTACTINFOEJBTABLE;
drop table COUNTEREJBTABLE;
drop table CREDITCARDEJBTABLE;
drop table CUSTOMEREJBTABLE;
drop table ITEM;
drop table PRODUCT;
drop table PROFILEEJBTABLE;
drop table USEREJBTABLE;
drop table CATEGORY;

CREATE TABLE AddressEJBTable 
			(PMPrimaryKey NUMBER(22) NOT NULL ,
			 city VARCHAR2(255),
			 country VARCHAR2(255),
			state VARCHAR2(255),
			streetName1 VARCHAR2(255),
			streetName2 VARCHAR2(255),
			zipCode VARCHAR2(255),
			reverse_address___PMPrimar NUMBER(22));

ALTER TABLE AddressEJBTable ADD CONSTRAINT pk_AddressEJBTabl Primary Key(PMPrimaryKey);

CREATE TABLE CATEGORY (CATID VARCHAR2(10) NOT NULL);

ALTER TABLE CATEGORY ADD CONSTRAINT PK_CATEGORY Primary Key (CATID);

CREATE TABLE CATEGORY_DETAILS(CATID VARCHAR2(10) NOT NULL ,NAME VARCHAR2(80) NOT NULL ,IMAGE VARCHAR2(255),DESCN VARCHAR2(255),LOCALE VARCHAR2(10) NOT NULL);

ALTER TABLE CATEGORY_DETAILS ADD CONSTRAINT PK_CATEGORY_DETAILS Primary Key (CATID,LOCALE);

ALTER TABLE CATEGORY_DETAILS ADD CONSTRAINT FK_CATEGORY_DETAILS_1 Foreign Key (CATID)REFERENCES CATEGORY (CATID);

CREATE TABLE ContactInfoEJBTable(
			PMPrimaryKey NUMBER(22) NOT NULL,
			address___PMPrimaryKey NUMBER(22),
			email VARCHAR2(255),
			familyName VARCHAR2(255),
			givenName VARCHAR2(255),
			telephone VARCHAR2(255),
			reverse_contactInfo___PMPr NUMBER(22)
			);
ALTER TABLE ContactInfoEJBTable ADD CONSTRAINT pk_ContactInfoEJBTabl Primary Key (PMPrimaryKey);

CREATE TABLE CounterEJBTable (counter NUMBER(11) NOT NULL ,name VARCHAR2(255) NOT NULL );
ALTER TABLE CounterEJBTable ADD CONSTRAINT pk_CounterEJBTabl Primary Key (name);

CREATE TABLE CreditCardEJBTable (PMPrimaryKey NUMBER(22)NOT NULL,
       cardNumber VARCHAR2(255),
       cardType VARCHAR2(255),
       expiryDate VARCHAR2(255),
       reverse_creditCard___PMPri NUMBER(22));

ALTER TABLE CreditCardEJBTable ADD CONSTRAINT pk_CreditCardEJBTabl Primary Key (PMPrimaryKey);

CREATE TABLE CustomerEJBTable (account___PMPrimaryKey NUMBER(22),profile___PMPrimaryKey NUMBER(22),userId VARCHAR2(255) NOT NULL );
ALTER TABLE CustomerEJBTable ADD CONSTRAINT pk_CustomerEJBTabl Primary Key (userId);

CREATE TABLE PRODUCT (PRODUCTID VARCHAR2(10) NOT NULL ,
			    CATID VARCHAR2(10) NOT NULL );
ALTER TABLE PRODUCT ADD CONSTRAINT PK_PRODUCT Primary Key (PRODUCTID);
ALTER TABLE PRODUCT ADD CONSTRAINT FK_PRODUCT_1 Foreign Key (CATID) REFERENCES CATEGORY (CATID);

CREATE TABLE ITEM (ITEMID VARCHAR2(10) NOT NULL ,
			    PRODUCTID VARCHAR2(10) NOT NULL );
   ALTER TABLE ITEM ADD CONSTRAINT PK_ITEM Primary Key (ITEMID);
   ALTER TABLE ITEM ADD CONSTRAINT FK_ITEM_1 Foreign Key (PRODUCTID)REFERENCES PRODUCT (PRODUCTID); 

CREATE TABLE ITEM_DETAILS (
      ITEMID VARCHAR2(10) NOT NULL ,
      LISTPRICE Number(10,2) NOT NULL ,
      UNITCOST Number(10,2) NOT NULL ,
      LOCALE VARCHAR2(10) NOT NULL ,
      IMAGE VARCHAR2(255) NOT NULL ,
      DESCN VARCHAR2(255) NOT NULL ,
      ATTR1 VARCHAR2(80),
      ATTR2 VARCHAR2(80),
      ATTR3 VARCHAR2(80),
      ATTR4 VARCHAR2(80),
      ATTR5 VARCHAR2(80)
   );

ALTER TABLE ITEM_DETAILS ADD CONSTRAINT PK_ITEM_DETAILS Primary Key (ITEMID,LOCALE);
ALTER TABLE ITEM_DETAILS ADD CONSTRAINT FK_ITEM_DETAILS_1 Foreign Key (ITEMID) REFERENCES ITEM (ITEMID);


CREATE TABLE PRODUCT_DETAILS (
      PRODUCTID VARCHAR2(10) NOT NULL ,
      LOCALE VARCHAR2(10) NOT NULL ,
      NAME VARCHAR2(80) NOT NULL ,
      IMAGE VARCHAR2(255),
      DESCN VARCHAR2(255)
      );
ALTER TABLE PRODUCT_DETAILS ADD CONSTRAINT PK_PRODUCT_DETAILS Primary Key (PRODUCTID,LOCALE);
ALTER TABLE PRODUCT_DETAILS ADD CONSTRAINT FK_PRODUCT_DETAILS_1 Foreign Key (PRODUCTID) REFERENCES PRODUCT (PRODUCTID);

CREATE TABLE ProfileEJBTable (
      PMPrimaryKey NUMBER(22) NOT NULL ,
      reverse_profile_userId VARCHAR2(255),
      bannerPreference NUMBER(38) NOT NULL ,
      favoriteCategory VARCHAR2(255),
      myListPreference NUMBER(38) NOT NULL ,
      preferredLanguage VARCHAR2(255)
      );
ALTER TABLE ProfileEJBTable ADD CONSTRAINT pk_ProfileEJBTabl Primary Key (PMPrimaryKey);

CREATE TABLE UserEJBTable (password VARCHAR2(255),userName VARCHAR2(255) NOT NULL);
   ALTER TABLE UserEJBTable ADD CONSTRAINT pk_UserEJBTabl Primary Key (userName);

CREATE TABLE AccountEJBTable
		(PMPrimaryKey NUMBER(22) NOT NULL ,
		reverse_account_userId VARCHAR2(255),
		contactInfo___PMPrimaryKey NUMBER(22),
		creditCard___PMPrimaryKey NUMBER(22),
		status VARCHAR2(255));
ALTER TABLE AccountEJBTable ADD CONSTRAINT pk_AccountEJBTabl Primary Key (PMPrimaryKey);

ALTER TABLE AccountEJBTable ADD CONSTRAINT FK_AccountEJBTabl_1 Foreign Key (contactInfo___PMPrimaryKey)REFERENCES ContactInfoEJBTable(PMPrimaryKey);
ALTER TABLE AccountEJBTable ADD CONSTRAINT FK_AccountEJBTabl_2 Foreign Key (creditCard___PMPrimaryKey)REFERENCES CreditCardEJBTable(PMPrimaryKey);

INSERT INTO AddressEJBTable VALUES(1015484450875,'PaloAlto','USA','CA','1234 Anywhere Street','Unit555','94303',1015484453235);
INSERT INTO AddressEJBTable VALUES (1015484455053,'city1','country1','state1','st1','st2','zip1',1015484455052);
INSERT INTO AddressEJBTable VALUES (1015484460463,'city2','country2','state2','st3','st4','zip2',1015484462198);
INSERT INTO AddressEJBTable VALUES (1015484463579,'city3','country3','state3','st5','st6','zip3',1015484463578);

INSERT INTO CATEGORY VALUES ('FISH');
INSERT INTO CATEGORY VALUES ('DOGS');
INSERT INTO CATEGORY VALUES ('REPTILES');
INSERT INTO CATEGORY VALUES ('CATS');
INSERT INTO CATEGORY VALUES ('BIRDS');

INSERT INTO CATEGORY_DETAILS VALUES('FISH','FISH','fish_icon.gif','desc','en_US');
INSERT INTO CATEGORY_DETAILS VALUES('FISH','Fish1','fish_icon.gif','desc','ja_JP');
INSERT INTO CATEGORY_DETAILS VALUES('DOGS','Dogs','dogs_icon.gif','desc','en_US');
INSERT INTO CATEGORY_DETAILS VALUES('DOGS','Dogs1','dogs_icon.gif','desc','ja_JP');
INSERT INTO CATEGORY_DETAILS VALUES('REPTILES','Reptiles','reptiles_icon.gif','desc','en_US');
INSERT INTO CATEGORY_DETAILS VALUES('REPTILES','Reptiles1','reptiles_icon.gif','desc','ja_JP');
INSERT INTO CATEGORY_DETAILS VALUES('CATS','Cats','cats_icon.gif','desc','en_US');
INSERT INTO CATEGORY_DETAILS VALUES('CATS','Cats1','cats_icon.gif','desc','ja_JP');
INSERT INTO CATEGORY_DETAILS VALUES('BIRDS','Birds','birds_icon.gif','desc','en_US');
INSERT INTO CATEGORY_DETAILS VALUES('BIRDS','Birds1','birds_icon.gif','desc','ja_JP');

INSERT INTO ContactInfoEJBTable VALUES (1015484453235,1015484450875,'aaa@bbb.ccc','ABC','XYZ','555-555-5555',1015484450875);
INSERT INTO ContactInfoEJBTable VALUES (1015484455052,000,'yourname1@yourdomain.com','aaa','bbb','555-555-5555',1015484455053);
INSERT INTO ContactInfoEJBTable VALUES (1015484462198,1015484463577,'yourname2@yourdomain.com','aaa','bbb','555-555-5555',1015484460463);
INSERT INTO ContactInfoEJBTable VALUES (1015484463578,000,'yourname2@yourdomain.com','aaa','bbb','555-555-5555',1015484463579);

INSERT INTO CounterEJBTable VALUES (2,'1001');

INSERT INTO CustomerEJBTable VALUES (1015484455051,1015484455055,'j2ee');
INSERT INTO CustomerEJBTable VALUES (1015484463577,1015484463581,'j2ee-ja');

INSERT INTO CreditCardEJBTable VALUES(1015484453689,'123456789','MeowCard','04/04',1015484455051);
INSERT INTO CreditCardEJBTable VALUES (1015484455054,'cardno1','cardtype1','05/05',0000);
INSERT INTO CreditCardEJBTable VALUES (1015484462574,'123456789','VISA','04/04',1015484463577);
INSERT INTO CreditCardEJBTable VALUES (1015484463580,'cardno2','cardtype2','06/06',0000);
INSERT INTO CreditCardEJBTable VALUES (1015527136112,'0100-001-0001','Java(TM) Card','01/2004',1015527136046);

INSERT INTO PRODUCT VALUES ('FI-SW-01','FISH');
INSERT INTO PRODUCT VALUES ('FI-SW-02','FISH');
INSERT INTO PRODUCT VALUES ('FI-FW-01','FISH');
INSERT INTO PRODUCT VALUES ('FI-FW-02','FISH');
INSERT INTO PRODUCT VALUES ('K9-BD-01','DOGS');
INSERT INTO PRODUCT VALUES ('K9-PO-02','DOGS');
INSERT INTO PRODUCT VALUES ('K9-DL-01','DOGS');
INSERT INTO PRODUCT VALUES ('K9-RT-01','DOGS');
INSERT INTO PRODUCT VALUES ('K9-RT-02','DOGS');
INSERT INTO PRODUCT VALUES ('K9-CW-01','DOGS');
INSERT INTO PRODUCT VALUES ('RP-SN-01','REPTILES');
INSERT INTO PRODUCT VALUES ('RP-LI-02','REPTILES');
INSERT INTO PRODUCT VALUES ('FL-DSH-01','CATS');
INSERT INTO PRODUCT VALUES ('FL-DLH-02','CATS');
INSERT INTO PRODUCT VALUES ('AV-CB-01','BIRDS');
INSERT INTO PRODUCT VALUES ('AV-SB-02','BIRDS');

INSERT INTO ITEM VALUES ('EST-1','FI-SW-01');

INSERT INTO ITEM VALUES ('EST-2','FI-SW-01'); 
INSERT INTO ITEM VALUES ('EST-3','FI-SW-02');
INSERT INTO ITEM VALUES ('EST-4','FI-FW-01');
INSERT INTO ITEM VALUES ('EST-5','FI-FW-01');

INSERT INTO ITEM VALUES ('EST-6','K9-BD-01');
INSERT INTO ITEM VALUES ('EST-7','K9-BD-01');
INSERT INTO ITEM VALUES ('EST-8','K9-PO-02');
INSERT INTO ITEM VALUES ('EST-9','K9-DL-01');

INSERT INTO ITEM VALUES ('EST-10','K9-DL-01');
INSERT INTO ITEM VALUES ('EST-11','RP-SN-01');
INSERT INTO ITEM VALUES ('EST-12','RP-SN-01');
INSERT INTO ITEM VALUES ('EST-13','RP-LI-02');
INSERT INTO ITEM VALUES ('EST-14','FL-DSH-01');

INSERT INTO ITEM VALUES ('EST-15','FL-DSH-01');
INSERT INTO ITEM VALUES ('EST-16','FL-DLH-02');
INSERT INTO ITEM VALUES ('EST-17','FL-DLH-02');
INSERT INTO ITEM VALUES ('EST-18','AV-CB-01');
INSERT INTO ITEM VALUES ('EST-19','AV-SB-02');

INSERT INTO ITEM VALUES ('EST-20','FI-FW-02');
INSERT INTO ITEM VALUES ('EST-21','FI-FW-02');
INSERT INTO ITEM VALUES ('EST-22','K9-RT-02');
INSERT INTO ITEM VALUES ('EST-23','K9-RT-02');

INSERT INTO ITEM VALUES ('EST-24','K9-RT-02');
INSERT INTO ITEM VALUES ('EST-25','K9-RT-02');
INSERT INTO ITEM VALUES ('EST-26','K9-CW-01');
INSERT INTO ITEM VALUES ('EST-27','K9-CW-01');
INSERT INTO ITEM VALUES ('EST-28','K9-RT-01');

INSERT INTO ITEM_DETAILS VALUES('EST-1',16.50,10.00,'en_US','fish3.gif','Fresh Water fish from Japan','Large','Cuddly','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-1',1951.00,1551.00,'ja_JP','fish3.gif','who knows','att1','att2','att3','att4','att5');

INSERT INTO ITEM_DETAILS VALUES('EST-2',16.50,10.00,'en_US','fish3.gif','Fresh Water fish from Japan','Small','att2','att3','att4','att5'); 
INSERT INTO ITEM_DETAILS VALUES('EST-2',1114.00,1003.00,'ja_JP','fish3.gif','Fresh Water fish from Japan','Small','att2','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-3',18.50,12.00,'en_US','fish4.gif','Salt Water fish from Australia','Toothless','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-3',1351.00,1231.00,'ja_JP','fish4.gif','who knows','att1','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-4',18.50,12.00,'en_US','fish3.gif','Fresh Water fish from Japan','Spotted','Mean','att3','att4','att5');

INSERT INTO ITEM_DETAILS VALUES('EST-4',1323.00,1251.00,'ja_JP','fish3.gif','Fresh Water fish from Japan','Spotted','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-5',18.50,12.00,'en_US','fish3.gif','Fresh Water fish from Japan','Spotless','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-5',1250.00,1100.00,'ja_JP','fish3.gif','Fresh Water fish from Japan','Spotless','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-6',18.50,12.00,'en_US','dog2.gif','Friendly dog from England','Male Adult','Mean','att3','att4','att5');

INSERT INTO ITEM_DETAILS VALUES('EST-6',1450.00,1200.00,'ja_JP','dog2.gif','Friendly dog from England','Male Adult','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-7',18.50,12.00,'en_US','dog2.gif','Friendly dog from England','Female Puppy','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-7',1150.00,1011.00,'ja_JP','dog2.gif','Friendly dog from England','Female Puppy','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-8',18.50,12.00,'en_US','dog6.gif','Cute dog from France','Male Puppy','Mean','att3','att4','att5');

INSERT INTO ITEM_DETAILS VALUES('EST-8',1220.00,1110.00,'ja_JP','dog6.gif','Cute dog from France','Male Puppy','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-9',18.50,12.00,'en_US','dog5.gif','Great dog for a Fire Station','Spotless Male Puppy','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-9',1440.00,1410.00,'ja_JP','dog5.gif','Great dog for a Fire Station','Spotless Male Puppy','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-10',18.50,12.00,'en_US','dog5.gif','Great dog for a Fire Station','Spotted Adult Female','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-10',1830.00,1610.00,'ja_JP','dog5.gif','Great dog for a Fire Station','Spotted Adult Female','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-11',18.50,12.00,'en_US','lizard3.gif','More Bark than bite','Venomless','Mean','att3','att4','att5');

INSERT INTO ITEM_DETAILS VALUES('EST-11',1770.00,1330.00,'ja_JP','lizard3.gif','More Bark than bite','Venomless','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-12',18.50,12.00,'en_US','lizard3.gif','Doubles as a watch dog','Rattleless','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-12',1550.00,1330.00,'ja_JP','lizard3.gif','Doubles as a watch dog','Rattleless','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-13',12.50,11.10,'en_US','lizard2.gif','Friendly green friend','Green Adult','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-13',1850.00,1200.00,'ja_JP','lizard2.gif','Friendly green friend','Green Adult','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-14',58.50,12.00,'en_US','cat3.gif','Great for reducing mouse populations','Tailless','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-14',5840.00,3200.00,'ja_JP','cat3.gif','Great for reducing mouse populations','Tailless','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-15',23.50,12.00,'en_US','cat3.gif','Great for reducing mouse populations','With tail','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-15',2340.00,1951.00,'ja_JP','cat3.gif','Great for reducing mouse populations','With tail','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-16',93.50,12.00,'en_US','cat1.gif','Friendly house cat,doubles as a princess','Adult Female','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-16',9350.00,8100.00,'ja_JP','cat1.gif','Friendly house cat, doubles as a princess','Adult Female','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-17',93.50,12.00,'en_US','cat1.gif','Friendly house cat,doubles as a prince','Adult Male','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-17',9350.00,6100.00,'ja_JP','cat1.gif','Friendly house cat,doubles as a prince','Adult Male','Mean','att3','att4','att5');

INSERT INTO ITEM_DETAILS VALUES('EST-18',193.50,92.00,'en_US','bird4.gif','Great companion for up to 75 years','Adult Male','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-18',19350.00,12000.00,'ja_JP','bird4.gif','Great companion for up to 75 years','Adult Male','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-19',15.50,2.00,'en_US','bird1.gif','Great stress reliever','Adult Male','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-19',1540.00,1200.00,'ja_JP','bird1.gif','Great stress reliever','Adult Male','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-20',5.50,2.00,'en_US','fish2.gif','Fresh Water fish from China','Adult Male','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-20',550.00,440.00,'ja_JP','fish2.gif','Fresh Water fish from China','Adult Male','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-21',5.29,1.00,'en_US','fish2.gif','Fresh Water fish from China','Adult Female','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-21',549.00,400.00,'ja_JP','fish2.gif','Fresh Water fish from China','Adult Female','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-22',135.50,100.00,'en_US','dog5.gif','Great hunting dog','Adult Male','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-22',13550.00,10000.00,'ja_JP','dog5.gif','Great hunting dog','Adult Male','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-23',145.49,100.00,'en_US','dog5.gif','Great hunting dog','Adult Female','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-23',14549.00,10000.00,'ja_JP','dog5.gif','Great hunting dog','Adult Female','Mean','att3','att4','att5');

INSERT INTO ITEM_DETAILS VALUES('EST-24',255.50,92.00,'en_US','dog5.gif','Great addition to a family','Male Puppy','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-24',25550.00,19200.00,'ja_JP','dog5.gif','Great addition to a family','Male Puppy','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-25',325.29,90.00,'en_US','dog5.gif','Great hunting dog','Female Puppy','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-25',32529.00,12100.00,'ja_JP','dog5.gif','Great hunting dog','Female Puppy','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-26',125.50,92.00,'en_US','dog4.gif','Little yapper','Adult Male','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-26',12550.00,19200.00,'ja_JP','dog4.gif','Little yapper','Adult Male','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-27',155.29,90.00,'en_US','dog4.gif','Great companion dog','Adult Female','Mean','att3','att4','att5');

INSERT INTO ITEM_DETAILS VALUES('EST-27',15519.00,14111.00,'ja_JP','dog4.gif','Great companion dog','Adult Female','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-28',155.29,90.00,'en_US','dog1.gif','Great family dog','Adult Female','Mean','att3','att4','att5');
INSERT INTO ITEM_DETAILS VALUES('EST-28',15529.00,10900.00,'ja_JP','dog1.gif','Great family dog','Adult Female','Mean','att3','att4','att5');


INSERT INTO PRODUCT_DETAILS VALUES('FI-SW-01','en_US','Angelfish','fish1.jpg','Salt Water fish from Australia');

INSERT INTO PRODUCT_DETAILS VALUES ('FI-SW-01','ja_JP','nippan','fish1.jpg','who knows');
INSERT INTO PRODUCT_DETAILS VALUES ('FI-SW-02','en_US','Tiger Shark','fish4.gif','Salt Water fish from Australia');
INSERT INTO PRODUCT_DETAILS VALUES ('FI-SW-02','ja_JP','dontknow','fish4.gif','who knows');
INSERT INTO PRODUCT_DETAILS VALUES ('FI-FW-01','en_US','Koi','fish3.gif','Fresh Water fish from Japan');
INSERT INTO PRODUCT_DETAILS VALUES ('FI-FW-01','ja_JP','notsure','fish3.gif','not sure');
INSERT INTO PRODUCT_DETAILS VALUES ('FI-FW-02','en_US','Goldfish','fish2.gif','Fresh Water fish from China');

INSERT INTO PRODUCT_DETAILS VALUES ('FI-FW-02','ja_JP','notsure','fish2.gif','who knows');
INSERT INTO PRODUCT_DETAILS VALUES ('K9-BD-01','en_US','Bulldog','dog2.gif','Friendly dog from England');
INSERT INTO PRODUCT_DETAILS VALUES ('K9-BD-01','ja_JP','notsure','dog2.gif','who knows');
INSERT INTO PRODUCT_DETAILS VALUES ('K9-PO-02','en_US','Poodle','dog6.gif','Cute dog from France');
INSERT INTO PRODUCT_DETAILS VALUES ('K9-PO-02','ja_JP','notsure','dog6.gif','who knows');

INSERT INTO PRODUCT_DETAILS VALUES ('K9-DL-01','en_US','Dalmation','dog5.gif','Great dog for a Fire Station');
INSERT INTO PRODUCT_DETAILS VALUES ('K9-DL-01','ja_JP','notsure','dog5.gif','who knows');
INSERT INTO PRODUCT_DETAILS VALUES ('K9-RT-01','en_US','Golden Retriever','dog1.gif','Great family dog');
INSERT INTO PRODUCT_DETAILS VALUES ('K9-RT-01','ja_JP','notsure','dog1.gif','who knows');
INSERT INTO PRODUCT_DETAILS VALUES ('K9-RT-02','en_US','Labrador Retriever','dog5.gif','Great hunting dog');

INSERT INTO PRODUCT_DETAILS VALUES ('K9-RT-02','ja_JP','notsure','dog5.gif','who knows');
INSERT INTO PRODUCT_DETAILS VALUES ('K9-CW-01','en_US','Chihuahua','dog4.gif','Great companion dog');
INSERT INTO PRODUCT_DETAILS VALUES ('K9-CW-01','ja_JP','notsure','dog4.gif','who knows');
INSERT INTO PRODUCT_DETAILS VALUES ('RP-SN-01','en_US','Rattlesnake','lizard3.gif','Doubles as a watch dog');
INSERT INTO PRODUCT_DETAILS VALUES ('RP-SN-01','ja_JP','notsure','lizard3.gif','who knows');

INSERT INTO PRODUCT_DETAILS VALUES ('RP-LI-02','en_US','Iguana','lizard2.gif','Friendly green friend');
INSERT INTO PRODUCT_DETAILS VALUES ('RP-LI-02','ja_JP','notsure','lizard2.gif','who knows');
INSERT INTO PRODUCT_DETAILS VALUES ('FL-DSH-01','en_US','Manx','cat3.gif','Great for reducing mouse populations');
INSERT INTO PRODUCT_DETAILS VALUES ('FL-DSH-01','ja_JP','notsure','cat3.gif','who knows');
INSERT INTO PRODUCT_DETAILS VALUES ('FL-DLH-02','en_US','Persian','cat1.gif','Friendly house cat, doubles as a princess');

INSERT INTO PRODUCT_DETAILS VALUES ('FL-DLH-02','ja_JP','notsure','cat1.gif','who knows');
INSERT INTO PRODUCT_DETAILS VALUES ('AV-CB-01','en_US','Amazon Parrot','bird4.gif','Great companion for up to 75 years');
INSERT INTO PRODUCT_DETAILS VALUES ('AV-CB-01','ja_JP','notsure','bird4.gif','who knows');
INSERT INTO PRODUCT_DETAILS VALUES ('AV-SB-02','en_US','Finch','bird1.gif','Great stress reliever');
INSERT INTO PRODUCT_DETAILS VALUES ('AV-SB-02','ja_JP','notsure','bird1.gif','who knows');

INSERT INTO ProfileEJBTable VALUES (1015484455055,'j2ee',0,'REPTILES',1,'en_US');
INSERT INTO ProfileEJBTable VALUES (1015484463581,'j2ee-ja',0,'REPTILES',1,'ja_JP');

INSERT INTO UserEJBTable VALUES('j2ee','j2ee');
INSERT INTO UserEJBTable VALUES('j2ee','j2ee-ja');

INSERT INTO AccountEJBTable VALUES(1015484455051,'j2ee',1015484453235,1015484453689,'active');
INSERT INTO AccountEJBTable VALUES(1015484463577,'j2ee-ja',1015484462198,1015484462574,'active');

commit;
