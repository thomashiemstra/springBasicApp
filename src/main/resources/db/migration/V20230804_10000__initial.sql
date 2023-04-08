create table books
(
    id          uuid primary key,
    name        text not null
);

create index idx_books_name on books(name);
grant select, insert, update on table books to ${application-user};