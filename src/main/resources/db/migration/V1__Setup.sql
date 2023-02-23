drop table if exists author cascade;
drop table if exists book cascade;

create table author (
    id bigserial not null,
    first_name varchar(255),
    last_name varchar(255),
    primary key (id)
);

create table book (
    id bigserial not null,
    isbn varchar(255),
    publisher varchar(255),
    title varchar(255),
    author_id BIGINT,
    primary key (id)
);

ALTER TABLE book
    ADD CONSTRAINT book_author_fk FOREIGN KEY (author_id) REFERENCES author (id);