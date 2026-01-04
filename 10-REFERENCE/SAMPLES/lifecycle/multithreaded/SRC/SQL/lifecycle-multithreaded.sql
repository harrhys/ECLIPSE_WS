-- ========================================================
-- Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
-- ========================================================

-- ========================================================
-- Create LCMLIFECYCLE Table 
-- ========================================================

-- ========================================================
-- Create DDL for table: LCMLIFECYCLE
-- ========================================================
drop table LCMLIFECYCLE;
CREATE TABLE LCMLIFECYCLE (
	serialnumber			INTEGER,
	name				VARCHAR(250),
	marks				DECIMAL(5,2)
);

commit;
