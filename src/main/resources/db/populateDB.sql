DELETE FROM user_roles;
DELETE FROM plans;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100),
  ('ROLE_ADMIN', 101),
  ('ROLE_USER', 101);

INSERT INTO plans (date_time, plan,  user_id)
VALUES ('2019-05-30 10:00:00', 'Eating', 100),
  ('2019-05-30 13:00:00', 'Going to the gym',  100),
  ('2019-05-30 20:00:00', 'Reading',  100),
  ('2019-05-31 10:00:00', 'Work A',  100),
  ('2019-05-31 13:00:00', 'Move to another work',  100),
  ('2019-05-31 20:00:00', 'Work B',  100),
  ('2019-06-01 14:00:00', 'Admin test',  101),
  ('2019-06-01 21:00:00', 'Admin bug fixes', 101);