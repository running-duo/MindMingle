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
alter table t_teacher auto_increment=1;

drop table if exists t_student;
create table t_student(
id bigint auto_increment primary key,
name varchar(255),
phone varchar(255),
wx_id varchar(255),
memo varchar(255),
total_course int,
remain_course int,
course_valid_date datetime,
create_time datetime,
update_time datetime
);
alter table t_student auto_increment=1;