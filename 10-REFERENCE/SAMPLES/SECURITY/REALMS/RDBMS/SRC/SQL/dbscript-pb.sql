CREATE TABLE PUBLIC.user_tbl (
   userid       VARCHAR(10) NOT NULL,
   firstname VARCHAR(10) NOT NULL,
   lastname  VARCHAR(10) NOT NULL,
   passwd    VARCHAR(10) NOT NULL,
   groups    VARCHAR(20) );
COMMIT;
INSERT INTO PUBLIC.user_tbl (userid,firstname,lastname,passwd,groups) VALUES( 'ias','ias','ias','secret','staff');
INSERT INTO PUBLIC.user_tbl (userid,firstname,lastname,passwd,groups) VALUES( 'j2ee','j2ee','j2ee','secret','staff,engineer');
INSERT INTO PUBLIC.user_tbl (userid,firstname,lastname,passwd,groups) VALUES( 'alpha','alpha','alpha','secret','staff,engineer');
INSERT INTO PUBLIC.user_tbl (userid,firstname,lastname,passwd,groups) VALUES( 'beta','beta','beta','secret','staff,engineer');
INSERT INTO PUBLIC.user_tbl (userid,firstname,lastname,passwd,groups) VALUES( 'noel','beta','beta','noel','staff,engineer');

COMMIT;
exit;
