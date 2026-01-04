-- ============================================================
-- Copyright  2002 Sun Microsystems, Inc. All rights reserved.
-- =============================================================

drop table blob_repository;
create table blob_repository (fileName Varchar2(128),
                              mimeType Varchar2(128),
                 	      fileSize Number,
                              blobData Blob(102400));

commit;
