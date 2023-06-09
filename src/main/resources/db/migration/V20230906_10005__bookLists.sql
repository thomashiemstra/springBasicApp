create table book_lists (
    book_list_id        SERIAL PRIMARY KEY,
    book_list_name     VARCHAR NOT NULL,
    user_id             INT NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

create table book_list_join_table (
        book_list_id        integer NOT NULL,
        book_id             integer NOT NULL,

        CONSTRAINT book_list_id PRIMARY KEY (book_list_id, book_id),
        FOREIGN KEY (book_list_id) REFERENCES book_lists (book_list_id),
        FOREIGN KEY (book_id) REFERENCES books (book_id)
);



grant select, insert, update on table book_lists to ${application-user};
grant select, insert, update on table book_list_join_table to ${application-user};