drop table if exists t_teacher;
create table t_teacher(
id bigint auto_increment primary key,
name varchar(255),
phone varchar(255),
wx_id varchar(255),
memo varchar(255),
create_time datetime,
update_time datetime
);