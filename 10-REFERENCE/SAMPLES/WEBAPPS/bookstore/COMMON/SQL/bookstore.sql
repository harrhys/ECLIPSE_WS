DROP TABLE books;

CREATE TABLE books
(id VARCHAR(8) 
CONSTRAINT pk_books PRIMARY KEY,
surname VARCHAR(24),
first_name VARCHAR(24),
title VARCHAR(96),
price FLOAT,
yr INT,
description VARCHAR(30),
inventory INT);


DELETE FROM books;

INSERT INTO books VALUES('201', 'Duke', '',
 'My Early Years: Growing up on *7',
 10.75, 1995, 'What a cool book.', 20);

INSERT INTO books VALUES('202', 'Jeeves', '',
 'Web Servers for Fun and Profit', 10.75,
 2000, 'What a cool book.', 20);

INSERT INTO books VALUES('203', 'Masterson', 'Webster',
 'Web Components for Web Developers',
 17.75, 2000, 'What a cool book.', 20);

INSERT INTO books VALUES('205', 'Novation', 'Kevin',
 'From Oak to Java: The Revolution of a Language',
 10.75, 1998, 'What a cool book.', 20);

INSERT INTO books VALUES('206', 'Gosling', 'James',
 'Java Intermediate Bytecodes', 10.75,
 2000, 'What a cool book.', 20);

INSERT INTO books VALUES('207', 'Thrilled', 'Ben',
 'The Green Project: Programming for Consumer Devices',
 10.75, 1998, 'What a cool book', 20);

INSERT INTO books VALUES('208', 'Tru', 'Itzal',
 'Duke: A Biography of the Java Evangelist',
 10.75, 2001, 'What a cool book.', 20);

commit;
