CREATE TABLE IF NOT EXISTS authorities (
  id SERIAL PRIMARY KEY,
  authority VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
  id SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL unique,
  password VARCHAR NOT NULL,
  enabled BOOLEAN DEFAULT TRUE,
  authority_id INT NOT NULL REFERENCES authorities(id)
);

INSERT INTO authorities (authority) VALUES ('ROLE_USER');
INSERT INTO authorities (authority) VALUES ('ROLE_ADMIN');

INSERT INTO users (username, enabled, password, authority_id)
VALUES ('root', true, '$2a$10$foW4g4IH/uvbyxLrNLMn2OUthT8yU1.ZSzL50u/mm2u6Tx6gfv17e',
(SELECT id FROM authorities WHERE authority = 'ROLE_ADMIN'));