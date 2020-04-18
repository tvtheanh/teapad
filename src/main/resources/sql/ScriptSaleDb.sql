/* 
 * configuration for all system 
 */
create table config (
	id serial primary key,
	key varchar(50) not null,
	value varchar(100) not null,
	del boolean default false
);

insert into config(key, value)
	values ('name', 'Tea Pad');

select *
from config;



/*
 * provider information list
 */
create table provider (
	id serial primary key,
	name varchar(100) unique,
	del boolean default false
);

insert into provider (name)
	values ('Trung Nguyen');

insert into provider (name)
	values ('Wonderfarm');

insert into provider (name)
	values ('Số Một');

select * from provider;


/*
 * product information list
 */
drop table product cascade;

create table product (
	 id serial primary key,
	 name varchar(100) not null,
	 unit varchar(20),
	 provider_id int references provider(id),
	 del boolean default false
);


select p.id, p.name, p.unit, p.provider_id, v.name as provider_name 
from product p
	join provider v on (p.provider_id=v.id) ;

drop table price;

create table price (
	id serial primary key,
	product_id int references product(id) not null,
	price decimal not null default 0,
	cate varchar(30),
	enabled boolean default true,
	del boolean default false
);

select e.id, e.product_id, p.name, e.cate, e.price
from price e
	join product p on (p.id=e.product_id)
where e.del=false;

insert into price(product_id, cate, price)
	values (1, 'Giá chuẩn', 29500);


/*
 * product number in stock
 */
drop table inventory;
create table inventory (
	id serial primary key,
	product_id int references product(id) not null,
	instock decimal not null default 0,
	trackmonth char(2),
	trackyear char(4),
	del boolean default false
);
	
insert into inventory (product_id, instock, trackmonth, trackyear)
	values (1, 500, '09', '2019');

select * from inventory;


/*
 * customer information
 */
create table customer (
	id serial primary key,
	name varchar(100) unique not null,
	address varchar(100),
	del boolean default false
);

insert into customer (name, address)
	values ('Cà phê Ngon', 'Lê Duẩn, Phường 4, Sóc Trăng');

alter table customer
add column district varchar(20);

alter table customer
add column province varchar(20);

alter table customer
add column contact varchar(30);

alter table customer
add column phone varchar(15);

select * from customer;


/*
 * invoice (with customer and total information)
 */
drop table invoice cascade;
create table invoice (
	id serial primary key,
	customer_id int references customer(id),
	saledate timestamp without time zone,
	total decimal default 0,
	delivered smallint default 0,
	paid smallint default 0,
	del boolean default false
);

insert into invoice (customer_id, saledate)
	values (1, '2019-09-18');

select * from invoice;

UPDATE invoice SET total=total+500000 WHERE id=1;

drop table invoicedetail;
/*
 * invoice detail (with product and quantity)
 */
create table invoicedetail (
	id serial primary key,
	invoice_id int references invoice(id),
	product_id int references product(id),
	product_price decimal,
	quantity decimal,
	amount decimal,
	del boolean default false
);

select * from invoicedetail;

CREATE INDEX idx_invoicedetail_invoiceid
ON invoicedetail(invoice_id);
