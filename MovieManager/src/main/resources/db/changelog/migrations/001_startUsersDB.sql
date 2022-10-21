CREATE TABLE users
(
    id          BIGSERIAL,
    PRIMARY KEY (id),
    first_name  VARCHAR(64),
    second_name VARCHAR(64),
    nickname    VARCHAR(64),
    password    VARCHAR(64)
);