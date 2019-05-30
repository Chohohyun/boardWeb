create table admin(
    admin_no int,
    admin_id varchar2(20) not null unique,
    pwd varchar2(20) not null,
    primary key(admin_no)
)
insert into admin values(1,'abc','abc123');
insert into admin values(2,'xyz','xyz123');

commit

select count(*) from admin where admin_id='abc' and pwd='abc1234';
