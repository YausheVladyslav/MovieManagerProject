CREATE TABLE users
(
    user_id     BIGSERIAL,
    PRIMARY KEY (user_id),
    first_name  VARCHAR(64),
    second_name VARCHAR(64),
    nickname    VARCHAR(64),
    password    VARCHAR(64),
    question    VARCHAR,
    answer      VARCHAR(128)
);

CREATE TABLE movies
(
    id         BIGSERIAL,
    primary key (id),
    user_id    BIGINT REFERENCES users,
    name       VARCHAR(64),
    genre      VARCHAR(64),
    year       INT,
    watched    BOOLEAN,
    created_on DATE,
    updated_on DATE,
    rating     INT
);
