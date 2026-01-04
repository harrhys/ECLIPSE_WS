REM ========================================================
REM Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
REM ========================================================

drop table blob_repository;
create table blob_repository (fileName Varchar2(128),
                              mimeType Varchar2(128),
                 	      	  fileSize Number,
                              blobData Blob) 
							  storage (initial 50k next 50K pctincrease 0)
							  lob (blobData) store as (chunk 32k);

commit;
exit;
