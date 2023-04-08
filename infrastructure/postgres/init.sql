CREATE DATABASE books;
CREATE USER admin WITH PASSWORD 'admin';
CREATE USER books WITH PASSWORD 'books';
GRANT ALL PRIVILEGES ON DATABASE "books" to admin;
ALTER DATABASE "books" OWNER to admin;