DROP TABLE PRODUCTS IF EXISTS;
CREATE TABLE PRODUCTS (
	ID VARCHAR(25) PRIMARY KEY,
	NAME VARCHAR(50),
	DESCRIPTION VARCHAR(250),
	UNIT_PRICE DECIMAL,
	MANUFACTURER VARCHAR(50),
	CATEGORY VARCHAR(50),
	CONDITION VARCHAR(50),
	UNITS_IN_STOCK BIGINT,
	UNITS_IN_ORDER BIGINT,
	DISCONTINUED BOOLEAN
);

INSERT INTO PRODUCTS VALUES ('P1234', 'iPhone 6s', 
	'Apple iPhone 6s smartphone with 4.00-inch 640x1136 display and 8-megapixel rear camera',
	500,'Apple','Smartphone','New',450,0,false);
INSERT INTO PRODUCTS VALUES ('P1235', 'Dell Inspiron',
	'Dell Inspiron 14-inch Laptop (Black) with 3rd Generation Intel Core processors', 
	700,'Dell','Laptop','New',1000,0,false);
INSERT INTO PRODUCTS VALUES ('P1236', 'Nexus 7', 
	'Google Nexus 7 is the lightest 7 inch tablet With a quad-core Qualcomm Snapdragon? S4 Pro processor',
	300,'Google','Tablet','New',1000,0,false);
	

create table accounts (
	username varchar(10),
	password varchar(32)
);

insert into accounts values ('tea', md5('123'));
insert into accounts values ('ad', md5('ad'));


create table manufacture (
	id varchar(10),
	name varchar(50)
);

insert into manufacture values ('SP001', 'Apple');
insert into manufacture values ('SP002', 'Samsung');
insert into manufacture values ('PC001', 'Dell');
insert into manufacture values ('PC002', 'HP');

select * from products;

-- csdl cho project taisan
create table dmdonvi (
	madonvi VARCHAR(10) primary key,
	tendonvi varchar(30)
);

insert into dmdonvi(madonvi, tendonvi)
	values ('KD028', 'Khoa Khám');

insert into dmdonvi(madonvi, tendonvi)
	values ('KD016', 'Khoa Nhi');

insert into dmdonvi(madonvi, tendonvi)
	values ('KD017', 'Khoa Ngo?i');

insert into dmdonvi(madonvi, tendonvi)
	values ('KD018', 'Khoa GMHS');

insert into dmdonvi(madonvi, tendonvi)
	values ('KD045', 'Khoa Sanh');

CREATE TABLE dmccdc (
	maccdc VARCHAR(10) PRIMARY KEY,
	tenccdc VARCHAR(50),
	donvitinh VARCHAR(10),
	madonvi VARCHAR(10) references dmdonvi(madonvi),
	noisudung VARCHAR(50),
	nuocsanxuat VARCHAR(20),
	namsanxuat VARCHAR(4),
	namsudung VARCHAR(4),
	congsuat VARCHAR(20),
	nguon VARCHAR(50),
	ghichu VARCHAR(50),
	xoa SMALLINT
);

insert into dmccdc (maccdc, tenccdc, donvitinh, madonvi, noisudung, nuocsanxuat, namsanxuat, namsudung, congsuat, nguon)
	values ('PC001', 'Máy tính ð? bàn Dell', 'cái', 'KD016', 'Ph?ng khám Nhi 1', 'Malaysia', '2016', '2017', null, 'BV Ða khoa ð? l?i');

insert into dmccdc (maccdc, tenccdc, donvitinh, madonvi, noisudung, nuocsanxuat, namsanxuat, namsudung, congsuat, nguon)
	values ('PC002', 'Máy tính ð? bàn Dell Vostro', 'cái', 'KD016', 'Ph?ng khám Nhi 1', 'Malaysia', '2016', '2017', null, 'BV Ða khoa ð? l?i');

insert into dmccdc (maccdc, tenccdc, donvitinh, madonvi, noisudung, nuocsanxuat, namsanxuat, namsudung, congsuat, nguon)
	values ('MI001', 'Máy in Canon 2900', 'cái', 'KD016', 'Ph?ng khám Nhi 1', 'Malaysia', '2016', '2017', null, 'BV Ða khoa ð? l?i');

insert into dmccdc (maccdc, tenccdc, donvitinh, madonvi, noisudung, nuocsanxuat, namsanxuat, namsudung, congsuat, nguon)
	SELECT generate_series(1,1000), md5(random()::text), 'cái', 'KD016', 'Ph?ng khám Nhi 1', 'Malaysia', '2016', '2017', null, 'BV Ða khoa ð? l?i';
	
select * from dmccdc;

CREATE TABLE kiemke (
	makiemke SERIAL PRIMARY KEY,
	maccdc VARCHAR(10) references dmccdc(maccdc),
	ngayketoan DATE,
	soluongketoan DECIMAL,
	nguyengiaketoan DECIMAL,
	giatriketoan DECIMAL,
	ngaykiemke DATE,
	soluongkiemke DECIMAL,
	nguyengiakiemke DECIMAL,
	giatrikiemke DECIMAL,
	soluongchenhlech DECIMAL,
	nguyengiachenhlech DECIMAL,
	giatrichenhlech DECIMAL,
	ghichu VARCHAR(50),
	xoa SMALLINT
);

insert into kiemke (maccdc, ngayketoan, soluongketoan, nguyengiaketoan, giatriketoan, 
					ngaykiemke, soluongkiemke, nguyengiakiemke, giatrikiemke)
	values ('PC001', '2019-01-01', 1, 115.8, 100.3, '2019-05-25', 1, 100.1, 99.5);

insert into kiemke (maccdc, ngayketoan, soluongketoan, nguyengiaketoan, giatriketoan, 
					ngaykiemke, soluongkiemke, nguyengiakiemke, giatrikiemke)
	values ('PC002', '2019-01-01', 1, 215.8, 200.3, '2019-05-25', 1, 200.1, 199.5);

CREATE TABLE dieuchuyen (
	madieuchuyen SERIAL PRIMARY KEY,
	maccdc VARCHAR(10) references dmccdc(maccdc),
	giamua DECIMAL,
	chiphivanchuyen DECIMAL,
	chiphichaythu DECIMAL,
	nguyengia DECIMAL,
	tailieukythuat VARCHAR(20),
	ngaygiaonhan DATE,
	makhoagiao VARCHAR(10) references dmdonvi(madonvi),
	makhoanhan VARCHAR(10) references dmdonvi(madonvi),
	ghichu VARCHAR(50),
	xoa SMALLINT
);

insert into dieuchuyen(maccdc, giamua, chiphivanchuyen, chiphichaythu, nguyengia,
			tailieukythuat, ngaygiaonhan, makhoagiao, makhoanhan, ghichu)
	values ('PC001', 100, 2, 5, 100, 'S? hý?ng d?n', '2018-05-20', 'KD016', 'KD017', 'Ph?c v? khám b?nh');

insert into dieuchuyen(maccdc, giamua, chiphivanchuyen, chiphichaythu, nguyengia,
			tailieukythuat, ngaygiaonhan, makhoagiao, makhoanhan, ghichu)
	values ('PC002', 100, 2, 5, 100, 'S? hý?ng d?n', '2018-05-25', 'KD018', 'KD028', 'Ph?c v? khám b?nh');
	
insert into dieuchuyen(maccdc, giamua, chiphivanchuyen, chiphichaythu, nguyengia,
			tailieukythuat, ngaygiaonhan, makhoagiao, makhoanhan, ghichu)
	values ('MI001', 100, 2, 5, 100, 'S? hý?ng d?n', '2018-05-22', 'KD016', 'KD017', 'Ph?c v? khám b?nh');

select * from dieuchuyen;

CREATE TABLE suachua (
	masuachua SERIAL PRIMARY KEY,
	maccdc VARCHAR(10) references dmccdc(maccdc),
	noidungsuachua VARCHAR(100),
	ngaysuachua DATE,
	chiphisuachua DECIMAL,
	ghichu VARCHAR(50),
	xoa SMALLINT
);

insert into suachua(maccdc, noidungsuachua, ngaysuachua, chiphisuachua, ghichu)
	values ('MI001', 'Thay tr?c ép và vô m? bao l?a', '2019-06-10', 200, 'Khoa c?n n? gi?y ð? ngh?');
	
insert into suachua(maccdc, noidungsuachua, ngaysuachua, chiphisuachua, ghichu)
	values ('PC002', 'Thay RAM 512MB', '2019-06-15', 400, 'Khoa c?n n? gi?y ð? ngh?');

insert into suachua(maccdc, noidungsuachua, ngaysuachua, chiphisuachua, ghichu)
	values ('PC001', 'Thay RAM 512MB', '2019-06-15', 400, 'Khoa c?n n? gi?y ð? ngh?');
	
select * from suachua;

SELECT s.masuachua, s.maccdc, c.tenccdc, s.noidungsuachua, 
TO_CHAR(s.ngaysuachua,'DD/MM/YYYY') AS ngaysuachua, s.chiphisuachua, s.ghichu 
FROM suachua s INNER JOIN dmccdc c ON (s.maccdc=c.maccdc);

update dieuchuyen
set xoa = 0;

update dmccdc
set tenccdc = 'Hàng m?i v? chýa có tên'
where tenccdc is null;

select *
from dmccdc
where tenccdc is null;

drop table timesheet;

create table timesheet (
	acc_month varchar(2),
	acc_year varchar(4),
	employee_id integer references employee(employee_id),
	del boolean default false,
	D01 int,
	D02 int,
	D03 int,
	D04 int,
	D05 int,
	D06 int,
	D07 int,
	D08 int,
	D09 int,
	D10 int,
	D11 int,
	D12 int,
	D13 int,
	D14 int,
	D15 int,
	D16 int,
	D17 int,
	D18 int,
	D19 int,
	D20 int,
	D21 int,
	D22 int,
	D23 int,
	D24 int,
	D25 int,
	D26 int,
	D27 int,
	D28 int,
	D29 int,
	D30 int,
	D31 int
);

insert into timesheet(acc_month, acc_year, employee_id)
values ('11', '2019', 1);

insert into timesheet(acc_month, acc_year, employee_id)
values ('11', '2019', 2);

select * from timesheet;

create table status (
	value int primary key,
	display varchar(7),
	description varchar(50)
);

insert into status (value, display, description)
values (0, '', '');

insert into status (value, display, description)
values (1, 'Ô', '?m, ði?u dý?ng');

insert into status (value, display, description)
values (2, 'Cô', 'Con ?m');

insert into status (value, display, description)
values (3, 'T', 'Tai n?n');

insert into status (value, display, description)
values (4, 'TS', 'Thai s?n');

insert into status (value, display, description)
values (5, 'CN', 'Ch? nh?t');

insert into status (value, display, description)
values (6, 'NL', 'Ngh? l?');

insert into status (value, display, description)
values (7, 'NB', 'Ngh? bù');

insert into status (value, display, description)
values (8, '1/2K', 'Ngh? n?a ngày không lýõng');

insert into status (value, display, description)
values (9, 'K', 'Ngh? không lýõng');

insert into status (value, display, description)
values (10, 'N', 'Ng?ng vi?c');

insert into status (value, display, description)
values (11, 'P', 'Ngh? phép');

insert into status (value, display, description)
values (12, '1/2P', 'Ngh? n?a ngày tính phép');

insert into status (value, display, description)
values (13, 'NN', 'Làm n?a ngày công');

select * from status;

select * from timesheet;

drop table employee;

create table employee (
	employee_id SERIAL primary key,
	fullname varchar(50),
	title varchar(50),
	del boolean default false
);

insert into employee (employee_id, fullname, title)
values (1, 'Nguy?n Vãn Nguy?n Vãn AAA', 'Giám Ð?c');

insert into employee (employee_id, fullname, title)
values (2, 'Nguy?n Vãn Nguy?n Vãn BBB', 'Nhân viên');


select row_number() over(order by e.employee_id) as stt, 
	e.fullname as hoten, e.title as chucvu,
	(select display from status s where s.value=t.D01) as ngay01,
	(select display from status s where s.value=t.D02) as ngay02,
	(select display from status s where s.value=t.D03) as ngay03,
	(select display from status s where s.value=t.D04) as ngay04,
	(select display from status s where s.value=t.D05) as ngay05,
	(select display from status s where s.value=t.D06) as ngay06,
	(select display from status s where s.value=t.D07) as ngay07,
	(select display from status s where s.value=t.D08) as ngay08,
	(select display from status s where s.value=t.D09) as ngay09,
	(select display from status s where s.value=t.D10) as ngay10,
	(select display from status s where s.value=t.D11) as ngay11,
	(select display from status s where s.value=t.D12) as ngay12,
	(select display from status s where s.value=t.D13) as ngay13,
	(select display from status s where s.value=t.D14) as ngay14,
	(select display from status s where s.value=t.D15) as ngay15,
	(select display from status s where s.value=t.D16) as ngay16,
	(select display from status s where s.value=t.D17) as ngay17,
	(select display from status s where s.value=t.D18) as ngay18,
	(select display from status s where s.value=t.D19) as ngay19,
	(select display from status s where s.value=t.D20) as ngay20,
	(select display from status s where s.value=t.D21) as ngay21,
	(select display from status s where s.value=t.D22) as ngay22,
	(select display from status s where s.value=t.D23) as ngay23,
	(select display from status s where s.value=t.D24) as ngay24,
	(select display from status s where s.value=t.D25) as ngay25,
	(select display from status s where s.value=t.D26) as ngay26,
	(select display from status s where s.value=t.D27) as ngay27,
	(select display from status s where s.value=t.D28) as ngay28,
	(select display from status s where s.value=t.D29) as ngay29,
	(select display from status s where s.value=t.D30) as ngay30,
	(select display from status s where s.value=t.D31) as ngay31
from timesheet t
	join employee e on (t.employee_id=e.employee_id)
order by stt;


create table accounts (
	username varchar primary key,
	password varchar,
	rolename varchar,
	enabled boolean
);

insert into accounts(username, password, rolename, enabled)
values ('admin', md5('admin'), 'ROLE_ADMIN', true);

insert into accounts(username, password, rolename, enabled)
values ('tea', md5('tea'), 'ROLE_USER', true);

select * from accounts;

