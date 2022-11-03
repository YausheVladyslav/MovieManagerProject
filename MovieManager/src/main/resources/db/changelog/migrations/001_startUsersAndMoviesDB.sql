CREATE TABLE users
(
    user_id     BIGSERIAL,
    PRIMARY KEY (user_id),
    first_name  VARCHAR(64),
    second_name VARCHAR(64),
    nickname    VARCHAR(64),
    password    VARCHAR(64)
);

CREATE TABLE movies
(
    id         BIGSERIAL,
    primary key (id),
    user_id    BIGINT REFERENCES users,
    name       VARCHAR(255),
    genre      VARCHAR(255),
    year       INT,
    watched    BOOLEAN,
    created_on DATE,
    updated_on DATE
);
