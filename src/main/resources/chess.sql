create
database testchess

CREATE TABLE move
(
    id     INT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    source VARCHAR(2) NOT NULL,
    target VARCHAR(2) NOT NULL
);
