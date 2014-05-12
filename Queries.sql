-- Database: d86fo56ie0kqjt

-- DROP DATABASE d86fo56ie0kqjt;

CREATE DATABASE d86fo56ie0kqjt
  WITH OWNER = yndbtfxmnwkcgi
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.UTF-8'
       LC_CTYPE = 'en_US.UTF-8'
       CONNECTION LIMIT = -1;
GRANT ALL ON DATABASE d86fo56ie0kqjt TO yndbtfxmnwkcgi;
REVOKE ALL ON DATABASE d86fo56ie0kqjt FROM public;

select * from Users;
select * from CanCareFor;
select * from QRToken;
select * from PillRecord;
select * from SessionKeys;

drop table CanCareFor;
drop table PillRecord;
drop table QRToken;
drop table SessionKeys;
drop table Users;
create table Users
( FName varchar(15) not null
, LName varchar(15) not null
, UName varchar(15) primary key
, Pass  varchar(15) not null
);
insert into users values ('Chris', 'Murphy', 'cdmurphy','chris');
insert into users values ('David', 'Scianni', 'dnscianni', 'david');
insert into users values ('Amir', 'Sandoval', 'asandoval', 'amir');
insert into users values ('Alec', 'Shay', 'ashay', 'alec');
insert into users values ('Brian', 'Saia', 'bsaia', 'brian');

create table CanCareFor
( CRID varchar(15) 
, CGID varchar(15) 
,foreign key (CRID) references Users(UName) on delete cascade
,foreign key (CGID) references Users(UName) on delete cascade
,primary key (CRID, CGID)
);

create table SessionKeys
( UName varchar(15)primary key
, SessionKey varchar(256) unique
, foreign key (UName) references Users(UName) on delete cascade
);

/*insert into SessionKeys values ('cdmurphy','CoolKeyBro');*/

create table QRToken
( UName varchar(15)
, Token varchar(100)
, foreign key (UName) references Users(UName)
, Timestamp timestamp
);

create table PillRecord
( UName varchar(15)
, alert_time varchar(30) not null
, log_time varchar(30) not null
, message varchar(250) 
, action_taken varchar(250)
);
insert into PillRecord values ('cdmurphy','Mon May 12 00:31:00 GMT 2014', 'Mon May 12 00:36:00 GMT 2014', 'Take the Blue pill','Red Pill Taken');
insert into PillRecord values ('dnscianni','Mon May 12 00:32:00 GMT 2014','Mon May 12 00:37:00 GMT 2014',  'Take the Red pill','Blue Pill Taken');
