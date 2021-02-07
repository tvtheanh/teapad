/* 
 * login accounts 
 */
drop table if exists accounts cascade;

CREATE TABLE accounts (
	username varchar(20) primary key,
	"password" varchar(60),
	rolename varchar(20),
	enabled bool default true
);

insert into accounts(username, "password", rolename)
values ('tea', md5('tea'), 'ROLE_ADMIN')


/* 
 * configuration for system 
 */
drop table if exists config cascade;

create table config (
	id serial primary key,
	key varchar(50) not null,
	value varchar(100) not null,
	del boolean default false
);

insert into config(key, value)
	values ('programmer', 'TheAnh');

select *
from config;



/*
 * provider information list
 */
drop table if exists provider cascade;

create table provider (
	id serial primary key,
	name varchar(100),
	del boolean default false
);

insert into provider (name)
	values ('Trung Nguyên');

insert into provider (name)
	values ('Wonderfarm');

insert into provider (name)
	values ('Number One');

select * from provider;


/*
 * product information list
 */
drop table if exists product cascade;

create table product (
	 id serial primary key,
	 name varchar(100) not null,
	 unit varchar(20),
	 provider_id int references provider(id),
	 weight decimal default 0,
	 del boolean default false
);

select * from product WHERE id=4;

UPDATE product
SET weight=3.8
WHERE id=4;

-- test data
insert into product(name, provider_id, weight)
select 'Sản phẩm ' || md5(random()::text),
	floor(random()*3)+1::integer,
	generate_series(1,1000) ;

drop table if exists price cascade;

create table price (
	id serial primary key,
	product_id int references product(id) not null,
	price decimal not null default 0,
	cate varchar(30),
	enabled boolean default true,
	del boolean default false
);

-- test data
insert into price(product_id, price, cate)
select generate_series(1,1000),
	floor(random()*500000)+100000::integer,
	 'Giá test';

select e.id, e.product_id, p.name, e.cate, e.price
from price e
	join product p on (p.id=e.product_id)
where e.del=false;


/*
 * product number in stock
 */
drop table if exists inventory cascade;

create table inventory (
	id serial primary key,
	product_id int references product(id) not null,
	instock decimal not null default 0,
	trackmonth char(2),
	trackyear char(4),
	del boolean default false
);

select * from inventory;


/*
 * customer information
 */
drop table if exists customer cascade;

create table customer (
	id serial primary key,
	name varchar(100) unique not null,
	address varchar(100),
	district varchar(20),
	province varchar(20),
	contact varchar(30),
	phone varchar(15),
	del boolean default false
);

alter table customer
add column debt decimal default 0;



select * from customer;

-- test data
insert into customer(name, address)
select 'Khách hàng ' || generate_series(1,1000),
	'Địa chỉ' || md5(random()::text);


/*
 * invoice (with customer and total information)
 */
drop table if exists invoice cascade;

create table invoice (
	id serial primary key,
	customer_id int references customer(id),
	saledate DATE NOT NULL DEFAULT CURRENT_DATE,
	weight decimal default 0,
	total decimal default 0,
	discount decimal default 0,
	debt decimal default 0,
	giveaway_id int references giveaway(id),
	delivered smallint default 0,
	paid smallint default 0,
	del boolean default false
);

select * from invoice;


/*
 * invoice detail (with product and quantity)
 */
drop table if exists invoicedetail;

create table invoicedetail (
	id serial primary key,
	invoice_id int references invoice(id),
	product_id int references product(id),
	price_id int references price(id),
	product_price decimal,
	quantity decimal,
	amount decimal,
	del boolean default false
);

CREATE INDEX idx_invoicedetail_invoiceid
ON invoicedetail(invoice_id);

select * from invoicedetail;



-- sync invoice weight
update invoice set weight=0;
update invoice v
set weight=w.weight
from
	(select i.invoice_id, sum(i.quantity * p.weight) as weight
	from invoicedetail i 
		join product p on (i.product_id=p.id)
	where i.del=false
	group by i.invoice_id) w
where v.id=w.invoice_id and v.del=false;

-- sync invoice total
update invoice set total=0;
update invoice v
set total=t.total
from
	(select i.invoice_id, sum(i.quantity * p.price) as total
	from invoicedetail i 
		join price p on (i.price_id=p.id)
	where i.del=false
	group by i.invoice_id) t
where v.id=t.invoice_id and v.del=false;

-- sync customer debt
update customer c
set debt=d.sum_debt
from
	(select i.customer_id, sum(i.debt) as sum_debt
	from invoice i 
	where i.del=false
	group by i.customer_id) d
where c.id=d.customer_id;

select * from customer
where debt>0;


/*
 * employee 
 */
drop table if exists employee cascade;

CREATE TABLE employee (
	employee_id serial NOT null primary key,
	fullname varchar(50),
	title varchar(50),
	del bool NULL DEFAULT false
);



/*
 * giveaway 
 */
drop table if exists giveaway cascade;

CREATE TABLE giveaway (
	id serial NOT null primary key,
	givename varchar(50),
	givecontent text,
	del bool NULL DEFAULT false
);
