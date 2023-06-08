create table users (
    user_id         SERIAL PRIMARY KEY,
    user_name       VARCHAR NOT NULL
);

create table favourite_books_join_table (
     user_id        integer NOT NULL,
     book_id        integer NOT NULL,

     CONSTRAINT favourite_book_id PRIMARY KEY (user_id, book_id),
     FOREIGN KEY (user_id) REFERENCES users (user_id),
     FOREIGN KEY (book_id) REFERENCES books (book_id)
);
