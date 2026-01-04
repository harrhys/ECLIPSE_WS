REM ========================================================
REM Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
REM ========================================================

REM ========================================================
REM Create Greeting Table and Data
REM ========================================================

REM ========================================================
REM Create DDL for table: Greeting
REM ========================================================
drop table Greeting;
CREATE TABLE Greeting (
		timeStamp				varchar(25) PRIMARY KEY,
		name					varchar(50),
		message				varchar(50)
);
exit;
