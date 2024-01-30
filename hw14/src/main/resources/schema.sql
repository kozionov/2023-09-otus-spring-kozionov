create sequence authors_id_seq start with 1 increment by 1
create sequence books_id_seq start with 1 increment by 1
create sequence comments_id_seq start with 1 increment by 1
create sequence genres_id_seq start with 1 increment by 1
create table authors (id bigint not null, first_name varchar(255) not null, last_name varchar(255), primary key (id))
create table books (author_id bigint, id bigint not null, title varchar(500) not null, description varchar(1000) not null, primary key (id))
create table books_genres (book_id bigint not null, genre_id bigint not null, primary key (book_id, genre_id))
create table comments (book_id bigint not null, id bigint not null, text varchar(1000) not null, primary key (id))
create table genres (genre_id bigint not null, name varchar(500) not null unique, primary key (genre_id))
alter table if exists books add constraint FKfjixh2vym2cvfj3ufxj91jem7 foreign key (author_id) references authors
alter table if exists books_genres add constraint FKgkat05y2cec3tcpl6ur250sd0 foreign key (genre_id) references genres
alter table if exists books_genres add constraint FKlv42b6uemg63q27om39jjbt9o foreign key (book_id) references books