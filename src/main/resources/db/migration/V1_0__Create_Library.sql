DROP TABLE IF EXISTS Copy;
DROP TABLE IF EXISTS Book;
DROP TABLE IF EXISTS Copy_Status;

CREATE TABLE Copy_Status
(
    id INTEGER PRIMARY KEY,
    status VARCHAR(9) NOT NULL
);

CREATE TABLE Book
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(50) NOT NULL,
    isbn VARCHAR(13) NOT NULL
);

CREATE TABLE Copy
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    book_id INTEGER NOT NULL ,
    status_id INTEGER NOT NULL,
    FOREIGN KEY (book_id) REFERENCES Book(id),
    FOREIGN KEY (status_id) REFERENCES Copy_Status(id)
);

INSERT INTO Copy_Status
VALUES (1, 'Available'),
       (2, 'On loan');

INSERT INTO Book(title, author, isbn)
VALUES ('Java is brilliant!', 'Graham Mansfield', '1234567890123'),
       ('Spring Boot for beginners', 'Luke Moonwalker', '3210987654321');

INSERT INTO Copy(book_id, status_id)
VALUES (1, 1),
       (1, 1),
       (1, 1),
       (2, 1),
       (2, 1);