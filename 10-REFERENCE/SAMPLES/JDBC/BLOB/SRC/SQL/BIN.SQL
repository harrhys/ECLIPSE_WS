--=============================================================
-- Copyright  2002 Sun Microsystems, Inc. All rights reserved.
--=============================================================

drop table bin_repository;
create table bin_repository (fileName Varchar2(128),
                             mimeType Varchar2(128),
                 	     fileSize Number,
                             binData  Long Raw);

commit;
exit;
