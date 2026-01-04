select 'drop table if exists "' || tablename || '" cascade;' 
from pg_tables
 where schemaname = 'public';

drop table trip cascade ;

drop table trip_one cascade;

drop table trip_two cascade;

drop table trip_three cascade ;

drop table trip_four cascade;

drop table vehicle cascade ;

drop table vehicle_one cascade;

drop table vehicle_two cascade;

drop table vehicle_three cascade ;

drop table vehicle_four cascade;

drop table vehicle_trip cascade ;

drop table vehicle_trip_one cascade;

drop table vehicle_trip_two cascade;

drop table vehicle_trip_three cascade ;

drop table vehicle_trip_four cascade;

drop table vehicle_trip_map cascade ;

drop table users cascade;

drop table user_address cascade ;

drop table test cascade;

drop table trip_two_many cascade;

drop table vehicle_two_many cascade;

drop table base_user cascade;

drop table special_user cascade;

drop table hibernate_unique_key cascade;

drop sequence tt_seq;

drop sequence vv_seq;


drop sequence hibernate_sequence;




