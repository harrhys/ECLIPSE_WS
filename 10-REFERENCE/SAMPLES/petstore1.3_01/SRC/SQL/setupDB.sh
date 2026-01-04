sqlplus -s system/manager@vortex << ENDSQL
  whenever oserror exit 1
  whenever sqlerror exit 1 
  @createUser.sql
ENDSQL
sqlplus -s petstore/petstore@vortex << ENDSQL
  whenever oserror exit 1
  whenever sqlerror continue 
  @petstoredb.sql
ENDSQL
sqlplus -s opc/opc@vortex << ENDSQL
  whenever oserror exit 1
  whenever sqlerror continue 
  @opcdb.sql
ENDSQL
sqlplus -s supplier/supplier@vortex << ENDSQL
  whenever oserror exit 1
  whenever sqlerror continue 
  @supplierdb.sql
ENDSQL
