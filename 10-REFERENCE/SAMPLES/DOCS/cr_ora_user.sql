
set echo on;
drop user &&USER cascade;
create user &&USER identified by &&PASSWORD 
default tablespace &&TABLESPACE temporary tablespace TEMP
quota unlimited on &&TABLESPACE 
quota 0 on system ;
grant connect,resource to &&USER;

alter user &&USER quota unlimited on &TABLESPACE;
