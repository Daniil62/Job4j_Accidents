CREATE TABLE IF NOT EXISTS accident_type (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS rule (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE accident (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  text VARCHAR(2000) NOT NULL,
  address VARCHAR(255) NOT NULL,
  type_id INT REFERENCES accident_type(id)
);

CREATE TABLE accident_rule (
  id SERIAL PRIMARY KEY,
  rule_id INT REFERENCES rule(id),
  accident_id INT REFERENCES accident(id)
);