-- ========================================================
-- Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
-- ========================================================

-- ========================================================
-- Create Greeting Table and Data
-- ========================================================

-- ========================================================
-- Create DDL for table: Greeting
-- ========================================================
drop table Greeting;
CREATE TABLE Greeting (
		timeStamp			varchar(25) PRIMARY KEY,
		name				varchar(50),
		message				varchar(50)
);

commit;
exit;
