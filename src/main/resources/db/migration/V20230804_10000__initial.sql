create table authors (
    author_id       SERIAL PRIMARY KEY,
    author_name     VARCHAR NOT NULL
);

create table publishers (
    publisher_id        SERIAL PRIMARY KEY,
    publisher_name      VARCHAR NOT NULL
);

create table books (
    book_id                 SERIAL PRIMARY KEY,
    title                   VARCHAR NOT NULL,
    author_id               INT NOT NULL,
    average_rating          VARCHAR NOT NULL,
    isbn                    VARCHAR NOT NULL,
    isbn13                  VARCHAR NOT NULL,
    language_code           VARCHAR NOT NULL,
    num_pages               VARCHAR NOT NULL,
    ratings_count           VARCHAR NOT NULL,
    text_reviews_count      VARCHAR NOT NULL,
    publication_date        TIMESTAMP NOT NULL,
    publisher_id            INT NOT NULL,

    FOREIGN KEY (author_id) REFERENCES authors (author_id),
    FOREIGN KEY (publisher_id) REFERENCES publishers (publisher_id)
);


create index idx_books_title on books(title);
grant select, insert, update on table authors to ${application-user};
grant select, insert, update on table publishers to ${application-user};
grant select, insert, update on table books to ${application-user};