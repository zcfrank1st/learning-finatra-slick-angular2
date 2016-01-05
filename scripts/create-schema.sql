
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;

CREATE TABLE posts(
  id UUID PRIMARY KEY NOT NULL,
  title TEXT NOT NULL,
  added BIGINT NOT NULL
);

CREATE TABLE users(
  id UUID PRIMARY KEY NOT NULL,
  username TEXT NOT NULL,
  password TEXT NOT NULL
);

INSERT INTO posts(id, title, added) VALUES ('a4848a9c-541b-453d-9ce5-febb255cb3c5', 'again!', 412351516);
INSERT INTO users(id, username, password) VALUES ('a4848a9c-541b-453d-9ce5-febb25599935', 'stein', 'password');