CREATE TABLE movies
(
    id           BIGSERIAL,
    PRIMARY KEY (id),
    name         VARCHAR(32),
    genre        VARCHAR(32),
    year         INT,
    created_on   DATE,
    updated_on   DATE,
    watched      BOOLEAN
);