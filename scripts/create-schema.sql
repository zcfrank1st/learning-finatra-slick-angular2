DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id          UUID PRIMARY KEY NOT NULL,
  username    TEXT             NOT NULL,
  password    TEXT             NOT NULL,
  permissions TEXT             NOT NULL
);

CREATE TABLE posts (
  id      UUID PRIMARY KEY NOT NULL,
  title   TEXT             NOT NULL,
  added   BIGINT           NOT NULL,
  user_id UUID             NOT NULL,
  CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

INSERT INTO users (id, username, password, permissions) VALUES
  ('9b235810-f8b9-4833-8031-93e47fe51b01', 'stein', 'password', 'EDIT_DATASETS,SUPER');

INSERT INTO posts (id, title, added, user_id) VALUES
  ('a4848a9c-541b-453d-9ce5-febb255cb3c5', 'again!', 412351516, '9b235810-f8b9-4833-8031-93e47fe51b01'),
  ('c0c13fd2-6a5c-459e-81b9-711fa37ce955', 'again!', 412351516, '9b235810-f8b9-4833-8031-93e47fe51b01');
