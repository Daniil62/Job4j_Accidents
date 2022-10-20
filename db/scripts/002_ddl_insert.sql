INSERT INTO accident_type (name) VALUES('Две машины');
INSERT INTO accident_type (name) VALUES('Машина и человек');
INSERT INTO accident_type (name) VALUES('Машина и велосипед');

INSERT INTO rule (name) VALUES('Статья 1');
INSERT INTO rule (name) VALUES('Статья 2');
INSERT INTO rule (name) VALUES('Статья 3');
INSERT INTO rule (name) VALUES('Статья 4');

INSERT INTO accident (name, text, address, type_id)
  VALUES('Мелкое ДТП', '...', 'ул. ..ская, д.5', 1);
INSERT INTO accident (name, text, address, type_id)
  VALUES('Человек задет на переходе', '...', 'пл. ..ова, д.32', 2);
INSERT INTO accident (name, text, address, type_id)
  VALUES('Велосипед протаранил автомобиль', '...', 'перекресток ул. ..ской и Строителей', 3);
INSERT INTO accident (name, text, address, type_id)
  VALUES('Мелкое ДТП', '...', 'бульвар ..ова, д.11', 1);

INSERT INTO accident_rule (rule_id, accident_id) VALUES(1, 1);
INSERT INTO accident_rule (rule_id, accident_id) VALUES(2, 2);
INSERT INTO accident_rule (rule_id, accident_id) VALUES(2, 3);
INSERT INTO accident_rule (rule_id, accident_id) VALUES(1, 4);
INSERT INTO accident_rule (rule_id, accident_id) VALUES(3, 4);
INSERT INTO accident_rule (rule_id, accident_id) VALUES(4, 2);