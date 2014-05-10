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
select * from fauxPillRecord;

drop table CanCareFor;
drop table fauxPillRecord;
drop table QRToken;
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


create table QRToken
( UName varchar(15)
, Token varchar(100)
, foreign key (UName) references Users(UName)
);

create table fauxPillRecord
( UName varchar(15)
, SuperSecretPersonalReminderMessage varchar(200)
, foreign key (UName) references Users(UName)
);
insert into fauxPillRecord values ('cdmurphy', 'Take the Blue pill');
insert into fauxPillRecord values ('dnscianni', 'Take the Red pill');
