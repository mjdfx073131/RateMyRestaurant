drop sequence Rateid_counter;

drop table Promote;
drop table Favorite;
drop table Rate;

drop table Voucher;
drop table Restaurant;
drop table Customer;
drop table Administrator;

drop table Star;
drop table Price;
drop table Food_Type;

ALTER SESSION SET nls_date_language='american';

create table Star(
    Star_level integer primary key,
    Description varchar2(100)
);
insert into Star values(1, 'The food is bad and the service is awful. This restaurant is not worth visiting.');
insert into Star values(2, 'The food is ok and the service is ok. I am not likely to recommend this restaurant to my friends.');
insert into Star values(3, 'The food is so so and the service is passable. This restaurant is just above average.');
insert into Star values(4, 'The food is delicious and the service is awesome. This restaurant has certainly caught my attention.');
insert into Star values(5, 'The food is very delicious and the service is spectacular. What a breathtaking restaurant!');
select * from Star;


create table Price(
    Price_level integer primary key,
    Description varchar2(20)
);
insert into Price values(1,'Cheap');
insert into Price values(2,'Medium');
insert into Price values(3,'Expensive');
select * from Price;


create table Food_Type(
    Type_name varchar2(20) primary key,
    Description varchar2(300)
);
insert into Food_type values('Japanese cuisine','Japanese cuisine encompasses the regional and traditional foods of Japan, which have developed through centuries of political, economic, and social changes.');
insert into Food_type values('Korean cuisine','Korean cuisine is the customary cooking traditions and practices of the culinary arts of Korea. It is largely based on rice, vegetables, and meats.');
insert into Food_type values('Chinese cuisine','Chinese cuisine is mainly described by color, smell and taste, as well as the meaning, appearance and nutrition of the food. Cooking should be appraised with respect to the ingredients used, knifework, cooking time and seasoning.');
insert into Food_type values('Western cuisine', 'Western cuisine, or alternatively European cuisine, is a generalised term collectively referring to the cuisines of Europe, as well as non-indigenous cuisines of the Americas, Oceania, and Southern Africa.');
insert into Food_type values('Mexican cuisine','Mexican cuisine began about 9,000 years ago, when agricultural communities such as the Maya formed, domesticating maize, creating the standard process of corn nixtamalization, and establishing their foodways.');
select * from Food_Type;


create table Voucher(
    Vid integer primary key,
    Description varchar2(20),
    Start_date date,
    Expire_date date
);
insert into Voucher values(1, '10% off for all food', to_date('01-MAY-2019', 'DD-MON-YYYY'), to_date('31-AUG-2019', 'DD-MON-YYYY'));
insert into Voucher values(2, '20% off for all food', to_date('01-MAY-2019', 'DD-MON-YYYY'), to_date('31-AUG-2019', 'DD-MON-YYYY'));
insert into Voucher values(3, '30% off for all food', to_date('01-MAY-2019', 'DD-MON-YYYY'), to_date('31-AUG-2019', 'DD-MON-YYYY'));
insert into Voucher values(4, '40% off for all food', to_date('01-MAY-2019', 'DD-MON-YYYY'), to_date('31-May-2019', 'DD-MON-YYYY'));
insert into Voucher values(5, '50% off for all food', to_date('01-MAY-2019', 'DD-MON-YYYY'), to_date('10-June-2019', 'DD-MON-YYYY'));
select * from Voucher;


create table Restaurant(
    Rid integer primary key,
    Name varchar2(30) not null,
    Star_level integer not null,
    Price_level integer not null,
    Type_Name varchar2(20) not null,
    Distance integer not null,
    Phone_num varchar2(20),
    Website varchar2(50),
    Address varchar2(50),
    foreign key (Star_level) references Star,
    foreign key (Price_level) references Price,
    foreign key (Type_Name) references Food_type
);
insert into Restaurant values(1,'Sushi Mura', 3, 1, 'Japanese cuisine', 9, '(604) 428-1145', 'www.mura.com', '6485 Oak St, Vancouver');
insert into Restaurant values(2,'Raisu', 4, 2, 'Japanese cuisine', 9, '(604) 620-1564', 'www.raisu.ca', '2340 W 4th Ave, Vancouver');
insert into Restaurant values(3,'Running Chicken', 3, 3, 'Korean cuisine', 1, '(604) 336-1006', 'www.running.com', '207/208, 5728 University Blvd, Vancouver');
insert into Restaurant values(4,'Tastes Chinese Bistro', 3, 1, 'Chinese cuisine', 5, '(604) 228-9870', 'www.tastes.com', '2158 Western Pkwy, Vancouver');
insert into Restaurant values(5,'Botanist', 4, 2, 'Western cuisine', 14, '(604) 695-5500', 'www.botanistrestaurant.com/', '1038 Canada Pl, Vancouver');
insert into Restaurant values(6,'La Brass', 4, 3, 'Western cuisine', 5, '(604) 222-1980', 'www.labrass.co/', '4473 W 10th Ave, Vancouver');
insert into Restaurant values(7,'HaiDiLao Hot Pot', 4, 1, 'Chinese cuisine', 18, '(604) 370-6665', 'www.haidilao.com','5890 No 3 Rd Room 200, Richmond');
insert into Restaurant values(8,'New Chong Qing Restaurant', 3, 2, 'Chinese cuisine', 9, '(604) 266-9988', 'www.newchongqing.com', '2044 W 41st Ave, Vancouver');
insert into Restaurant values(9,'Mukja', 3, 3, 'Korean cuisine', 7, '(604) 565-5006', 'www.azymeal.com/menu/mukjacorp','3211 W Broadway, Vancouver');
insert into Restaurant values(10,'My Home cuisine', 2, 1, 'Chinese cuisine', 1, '(604) 221-7482', 'www.homecuisine.com', 'Food Court, 5728 University Blvd, Vancouver');
select * from Restaurant;


create table Customer(
    Cid varchar2(20) primary key,
    Password varchar2(20),
    Name varchar2(30),
    Phone_num varchar2(20)
);
insert into Customer values('frankyf', 'qwer', 'Franky Fan', '778-123-456');
insert into Customer values('risaw', 'asdf', 'Risa Watanabe', '778-284-456');
insert into Customer values('stevenx', 'zxcv', 'Steven Xiao', '778-123-656');
insert into Customer values('Jackyc', 'wert', 'Jacky Chen', '778-243-954');
insert into Customer values('Jayc', 'sdfg', 'Jay Chow', '778-643-246');
select * from Customer;


create table Administrator(
    Cid varchar2(20) primary key,
    Password varchar2(20),
    Admin_level integer
);
insert into Administrator values('franky', 'franky', 3);
insert into Administrator values('jepson', 'jepson', 3);
insert into Administrator values('kerry', 'kerry', 3);
insert into Administrator values('will', 'will', 3);
select * from Administrator;


create table Promote(
    Rid integer,
    Vid integer,
    primary key(Rid, Vid),
    foreign key (Rid) references Restaurant on delete cascade,
    foreign key (Vid) references Voucher on delete cascade
);
insert into Promote values(1, 1);
insert into Promote values(1, 2);
insert into Promote values(1, 3);
insert into Promote values(1, 4);
insert into Promote values(1, 5);
insert into Promote values(2, 1);
insert into Promote values(2, 2);
insert into Promote values(3, 1);
insert into Promote values(4, 2);
insert into Promote values(5, 3);
insert into Promote values(6, 1);
insert into Promote values(7, 2);
insert into Promote values(8, 3);
insert into Promote values(9, 4);
select * from Promote;


create table Favorite(
    Cid varchar2(20),
    Rid integer,
    primary key (Cid, Rid),
    foreign key (Cid) REFERENCES Customer on delete cascade,
    foreign key (Rid) REFERENCES Restaurant on delete cascade
);
insert into Favorite values('frankyf', 1);
insert into Favorite values('frankyf', 2);
insert into Favorite values('frankyf', 3);
insert into Favorite values('risaw', 4);
insert into Favorite values('stevenx', 5);
select * from Favorite;

create sequence Rateid_counter;
create table Rate(
    Rateid integer primary key,
    Cid varchar2(20),
    Rid integer,
    Score integer,
    Rate_comment varchar2(100),
    Rate_date date,
    foreign key (Cid) references Customer on delete cascade,
    foreign key (Rid) references Restaurant on delete cascade
);
insert into Rate values(Rateid_counter.nextval, 'frankyf', 1, 3, 'The price is not cheap, but the food is really nice!',to_date('11-MAR-2018', 'DD-MON-YYYY'));
insert into Rate values(Rateid_counter.nextval, 'risaw', 1, 4, 'One of the best Restaurant', to_date('11-JAN-2018', 'DD-MON-YYYY'));
insert into Rate values(Rateid_counter.nextval, 'stevenx', 1, 4, 'You should definitely try!', to_date('11-FEB-2018', 'DD-MON-YYYY'));
insert into Rate values(Rateid_counter.nextval, 'frankyf', 2, 2, 'Not bad',to_date('21-OCT-2017', 'DD-MON-YYYY'));
insert into Rate values(Rateid_counter.nextval, 'frankyf', 3, 1, 'Very terrible', to_date('16-JAN-2018', 'DD-MON-YYYY'));
select * from Rate;

commit;
