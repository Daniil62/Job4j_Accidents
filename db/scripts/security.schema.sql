CREATE TABLE IF NOT EXISTS users (
  username VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  enabled boolean default true,
  PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS authorities (
  username VARCHAR NOT NULL,
  authority VARCHAR NOT NULL,
  FOREIGN KEY (username) REFERENCES users(username)
);