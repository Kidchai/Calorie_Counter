DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2022-10-21 08:00', 'Завтрак пользователя', 400),
       (100000, '2022-10-21 14:15', 'Обед пользователя', 800),
       (100000, '2022-10-21 19:10', 'Ужин пользователя', 800),
       (100001, '2022-10-24 09:00', 'Завтрак администратора', 400),
       (100001, '2022-10-24 14:30', 'Обед администратора', 650),
       (100001, '2022-10-24 20:00', 'Ужин администратора', 1000);
