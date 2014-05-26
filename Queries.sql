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


			select CRID as CanCareFor from CanCareFor
				where  CRID='dnscianni' and CGID in (select UName as CGID from SessionTokens where SessionToken='somekey');

select * from Users;
delete from Users 
select * from CanCareFor;
delete from CanCareFor;
select * from QRTokens;
delete from QRTokens;
select * from PillRecord;
select * from SessionTokens;
delete from SessionTokens;
select * from ReminderSchedules;
delete from ReminderSchedules;

drop table CanCareFor;
drop table PillRecord;
drop table QRTokens;
drop table SessionTokens;
drop table ReminderSchedules;
drop table Users;


create table Users
( FName varchar(15) not null CHECK (FName ~ '^[a-zA-Z]+$')
, LName varchar(15) not null CHECK (LName ~ '^[a-zA-Z]+$')
, UName varchar(15) primary key CHECK (UName ~ '^[a-zA-Z]+$')
, Pass  varchar(15) not null CHECK (Pass ~ '^[0-9a-zA-Z]+$')   /* All fields made large to make notes easility, will be changed later */
, PhoneNum  varchar(15) CHECK (PhoneNum ~ '^[0-9]+$')
);

insert into users values ('Doctor', 'Guy', 'docguy','docguy','5555555555');
insert into users values ('Patient', 'Guy', 'patguy','patguy','4444444444');
/*  
insert into users values ('Chris', 'Murphy', 'cdmurphy','chris','6263754326');
insert into users values ('David', 'Scianni', 'dnscianni', 'david');
insert into users values ('Amir', 'Sandoval', 'asandoval', 'amir');
insert into users values ('Alec', 'Shay', 'ashay', 'alec');
insert into users values ('Brian', 'Saia', 'bsaia', 'brian');
insert into users values ('Ted', 'Bear', 'teddybear', 'teddybear');
*/
/*
insert into users values ('Apache', 'http://stackoverflow.com/questions/9893924/error-converting-a-http-post-response-to-json', 'http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/', 'brian');
*/
create table CanCareFor
( CGID varchar(15) 
, CRID varchar(15) 
,foreign key (CRID) references Users(UName) on delete cascade
,foreign key (CGID) references Users(UName) on delete cascade
,primary key (CRID, CGID)
);


insert into CanCareFor values('docguy','patguy');
/*
insert into CanCareFor values('cdmurphy','dnscianni');
insert into CanCareFor values('bsaia','dnscianni');
insert into CanCareFor values('ashay','dnscianni');
insert into CanCareFor values('ashay','asandoval');
insert into CanCareFor values('cdmurphy','asandoval');
insert into CanCareFor values('cdmurphy','teddybear');
insert into CanCareFor values('bsaia','teddybear');
insert into CanCareFor values('ashay','teddybear');
*/
/*			    
			select COUNT(*) as CanCareFor from CanCareFor
				where  CRID in (select UName as CRID from SessionTokens where SessionToken='ptoken')
					and CGID in (select UName as CGID from SessionTokens where SessionToken='somekey')
					
			select * from Users
			where UName in 
				(select CGID as UName from CanCareFor where CRID in 
					(select UName as CRID from SessionTokens where SessionToken='ptoken'));

			select * from CanCareFor
				where CRID in (select UName as CRID from QRTokens where Token='pkey') 
				  and CGID in (select UName as CGID from SessionTokens where SessionToken='somekey')
*/
																

create table SessionTokens
( UName varchar(15)primary key
, SessionToken varchar(256) unique not null
, foreign key (UName) references Users(UName) on delete cascade
);
/* delete from SessionTokens where UName='cdmurphy' */
/* delete from SessionTokens where UName='dnscianni' */
/*
insert into SessionTokens values ('cdmurphy','somekey');
insert into SessionTokens values ('dnscianni','ptoken');
*/

   

create table QRTokens
( UName varchar(15)
, Token varchar(100)  unique not null
, foreign key (UName) references Users(UName)
, Timestamp timestamp
);


/*


insert into QRTokens
select UName, 'pkey' as Token from Users
where UName in (select UName from SessionTokens where SessionToken='ptoken');
*/

create table ReminderSchedules
( UName varchar(15) not null
, name varchar(25) not null check (name <> '')
, minute int not null
, hour int not null
, interval int not null
, foreign key (UName) references Users(UName)
, primary key (UName, name, minute, hour,interval)
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
