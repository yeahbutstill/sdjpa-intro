drop table if exists book;
drop table if exists book_seq;
drop table if exists author;
drop table if exists author_seq;

create table book (
    id bigint not null,
    author_id bigint,
    isbn varchar(255),
    publisher varchar(255),
    title varchar(255),
    primary key (id)
) engine=InnoDB;

create table book_seq (
    next_val bigint
) engine=InnoDB;

create table author (
    id bigint not null,
    first_name varchar(255),
    last_name varchar(255),
    primary key (id)
) engine=InnoDB;

create table author_seq (
    next_val bigint
) engine=InnoDB;


insert into book_seq values (1);
insert into author_seq values (1);