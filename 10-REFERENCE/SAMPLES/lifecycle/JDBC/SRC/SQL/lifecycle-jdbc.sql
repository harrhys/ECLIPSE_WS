-- ========================================================
-- Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
-- ========================================================

-- ========================================================
-- Create SAMPLETABLE Table 
-- ========================================================

-- ========================================================
-- Create DDL for table: SAMPLETABLE
-- ========================================================
drop table SAMPLETABLE;
CREATE TABLE SAMPLETABLE (
	serialnumber			INTEGER,
	name				VARCHAR(250),
	marks				DECIMAL(5,2)
);

commit;
