drop user dukesbank cascade;

commit;
create user dukesbank identified by dukesbank;
grant connect, resource to dukesbank;

commit;
exit;
