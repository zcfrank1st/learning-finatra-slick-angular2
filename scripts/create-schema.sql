
DROP TABLE IF EXISTS posts;

CREATE TABLE posts(
  id UUID PRIMARY KEY NOT NULL,
  title TEXT NOT NULL,
  added BIGINT NOT NULL
);

INSERT INTO posts(id, title, added) VALUES ('a4848a9c-541b-453d-9ce5-febb255cb3c5', 'again!', 412351516)