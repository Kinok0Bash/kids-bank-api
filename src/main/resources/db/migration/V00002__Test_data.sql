-- Вставка пользователей (10 родителей и 10 детей)
INSERT INTO "users" ("id", "lastname", "name", "father_name", "login", "password", "birth_date", "city", "role", "child") VALUES
    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Иванов', 'Артем', 'Иванович', 'ivanov_child', 'password11', '2005-01-01', 'Москва', 'CHILD', null),
    ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'Петров', 'Игорь', 'Петрович', 'petrov_child', 'password12', '2007-02-01', 'Санкт-Петербург', 'CHILD', null),
    ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'Сидоров', 'Денис', 'Сидорович', 'sidorov_child', 'password13', '2008-03-01', 'Новосибирск', 'CHILD', null),
    ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Смирнов', 'Евгений', 'Алексеевич', 'smirnov_child', 'password14', '2010-04-01', 'Екатеринбург', 'CHILD', null),
    ('ffffffff-ffff-ffff-ffff-ffffffffffff', 'Кузнецов', 'Максим', 'Николаевич', 'kuznetsov_child', 'password15', '2006-05-01', 'Казань', 'CHILD', null),
    ('11111111-aaaa-aaaa-aaaa-111111111111', 'Федоров', 'Глеб', 'Федорович', 'fedorov_child', 'password16', '2004-06-01', 'Челябинск', 'CHILD', null),
    ('22222222-bbbb-bbbb-bbbb-222222222222', 'Михайлов', 'Олег', 'Михайлович', 'mikhailov_child', 'password17', '2009-07-01', 'Самара', 'CHILD', null),
    ('33333333-cccc-cccc-cccc-333333333333', 'Алексеев', 'Антон', 'Александрович', 'alexeev_child', 'password18', '2011-08-01', 'Ростов-на-Дону', 'CHILD', null),
    ('44444444-dddd-dddd-dddd-444444444444', 'Попов', 'Никита', 'Дмитриевич', 'popov_child', 'password19', '2003-09-01', 'Уфа', 'CHILD', null),
    ('55555555-eeee-eeee-eeee-555555555555', 'Васильев', 'Владислав', 'Васильевич', 'vasiliev_child', 'password20', '2002-10-01', 'Красноярск', 'CHILD', null),
    ('11111111-1111-1111-1111-111111111111', 'Иванов', 'Иван', 'Иванович', 'ivanov_parent', 'password1', '1980-01-01', 'Москва', 'PARENT', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
    ('22222222-2222-2222-2222-222222222222', 'Петров', 'Петр', 'Петрович', 'petrov_parent', 'password2', '1975-02-01', 'Санкт-Петербург', 'PARENT', 'cccccccc-cccc-cccc-cccc-cccccccccccc'),
    ('33333333-3333-3333-3333-333333333333', 'Сидоров', 'Сидор', 'Сидорович', 'sidorov_parent', 'password3', '1985-03-01', 'Новосибирск', 'PARENT', 'dddddddd-dddd-dddd-dddd-dddddddddddd'),
    ('44444444-4444-4444-4444-444444444444', 'Смирнов', 'Алексей', 'Алексеевич', 'smirnov_parent', 'password4', '1978-04-01', 'Екатеринбург', 'PARENT', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'),
    ('55555555-5555-5555-5555-555555555555', 'Кузнецов', 'Николай', 'Николаевич', 'kuznetsov_parent', 'password5', '1982-05-01', 'Казань', 'PARENT', 'ffffffff-ffff-ffff-ffff-ffffffffffff'),
    ('66666666-6666-6666-6666-666666666666', 'Федоров', 'Федор', 'Федорович', 'fedorov_parent', 'password6', '1986-06-01', 'Челябинск', 'PARENT', '11111111-aaaa-aaaa-aaaa-111111111111'),
    ('77777777-7777-7777-7777-777777777777', 'Михайлов', 'Михаил', 'Михайлович', 'mikhailov_parent', 'password7', '1983-07-01', 'Самара', 'PARENT', '22222222-bbbb-bbbb-bbbb-222222222222'),
    ('88888888-8888-8888-8888-888888888888', 'Алексеев', 'Александр', 'Александрович', 'alexeev_parent', 'password8', '1987-08-01', 'Ростов-на-Дону', 'PARENT', '33333333-cccc-cccc-cccc-333333333333'),
    ('99999999-9999-9999-9999-999999999999', 'Попов', 'Дмитрий', 'Дмитриевич', 'popov_parent', 'password9', '1984-09-01', 'Уфа', 'PARENT', '44444444-dddd-dddd-dddd-444444444444'),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Васильев', 'Василий', 'Васильевич', 'vasiliev_parent', 'password10', '1979-10-01', 'Красноярск', 'PARENT', '55555555-eeee-eeee-eeee-555555555555');

-- Вставка данных в таблицу счетов
INSERT INTO "accounts" ("id", "user", "balance") VALUES
    ('11111111-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111', 10000),
    ('22222222-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222222', 9000),
    ('33333333-3333-3333-3333-333333333333', '33333333-3333-3333-3333-333333333333', 8000),
    ('44444444-4444-4444-4444-444444444444', '44444444-4444-4444-4444-444444444444', 7000),
    ('55555555-5555-5555-5555-555555555555', '55555555-5555-5555-5555-555555555555', 6000),
    ('66666666-6666-6666-6666-666666666666', '66666666-6666-6666-6666-666666666666', 11000),
    ('77777777-7777-7777-7777-777777777777', '77777777-7777-7777-7777-777777777777', 12000),
    ('88888888-8888-8888-8888-888888888888', '88888888-8888-8888-8888-888888888888', 5000),
    ('99999999-9999-9999-9999-999999999999', '99999999-9999-9999-9999-999999999999', 13000),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 15000),
    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 4000),
    ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 3000),
    ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 2000),
    ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 9000),
    ('ffffffff-ffff-ffff-ffff-ffffffffffff', 'ffffffff-ffff-ffff-ffff-ffffffffffff', 8000),
    ('11111111-aaaa-aaaa-aaaa-111111111111', '11111111-aaaa-aaaa-aaaa-111111111111', 7000),
    ('22222222-bbbb-bbbb-bbbb-222222222222', '22222222-bbbb-bbbb-bbbb-222222222222', 6000),
    ('33333333-cccc-cccc-cccc-333333333333', '33333333-cccc-cccc-cccc-333333333333', 5000),
    ('44444444-dddd-dddd-dddd-444444444444', '44444444-dddd-dddd-dddd-444444444444', 4000),
    ('55555555-eeee-eeee-eeee-555555555555', '55555555-eeee-eeee-eeee-555555555555', 10000);

-- Вставка категорий магазинов
INSERT INTO "shop_categories" ("id", "name") VALUES
    (1, 'Одежда'),
    (2, 'Продукты'),
    (3, 'Электроника'),
    (4, 'Игрушки'),
    (5, 'Книги'),
    (6, 'Мебель');

-- Вставка магазинов
INSERT INTO "shops" ("name", "category") VALUES
    ('H&M', 1),               -- Одежда
    ('Zara', 1),              -- Одежда
    ('Лента', 2),             -- Продукты
    ('Перекресток', 2),       -- Продукты
    ('DNS', 3),               -- Электроника
    ('Эльдорадо', 3),         -- Электроника
    ('Детский мир', 4),       -- Игрушки
    ('Hamleys', 4),           -- Игрушки
    ('Читай-город', 5),       -- Книги
    ('Лабиринт', 5),          -- Книги
    ('IKEA', 6),              -- Мебель
    ('Hoff', 6),              -- Мебель
    ('Adidas', 1),            -- Одежда
    ('Ашан', 2),              -- Продукты
    ('М.Видео', 3);           -- Электроника

-- Вставка лимитов по категориям
INSERT INTO "categories_limit" ("category", "child") VALUES
    (1, 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
    (2, 'cccccccc-cccc-cccc-cccc-cccccccccccc'),
    (3, 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
    (4, 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
    (5, 'cccccccc-cccc-cccc-cccc-cccccccccccc'),
    (6, 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb');

-- Вставка транзакций
INSERT INTO "transactions" ("id", "from", "to", "time", "sum") VALUES
    ('10101010-1010-1010-1010-101010101010', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '1', '2024-12-08 12:00:00+00', 1000),
    ('20202020-2020-2020-2020-202020202020', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '7', '2024-12-25 13:30:00+00', 500),
    ('30303030-3030-3030-3030-303030303030', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '5', '2024-12-19 14:00:00+00', 1500),
    ('40404040-4040-4040-4040-404040404040', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '3', '2024-12-10 16:30:00+00', 2000),
    ('50505050-5050-5050-5050-505050505050', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '2', '2024-12-05 17:00:00+00', 2500),
    ('60606060-6060-6060-6060-606060606060', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '4', '2024-12-15 18:20:00+00', 3000),
    ('70707070-7070-7070-7070-707070707070', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '5', '2024-11-22 19:00:00+00', 3500),
    ('80808080-8080-8080-8080-808080808080', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '5', '2024-11-13 20:30:00+00', 4000),
    ('90909090-9090-9090-9090-909090909090', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '1', '2024-11-08 21:00:00+00', 4500),
    ('10101010-1010-1010-1010-111111111111', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '1', '2024-11-02 23:30:00+00', 5000),
    ('11101010-1010-1010-1010-101010101010', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '1', '2024-12-08 12:00:00+00', 1000),
    ('21202020-2020-2020-2020-202020202020', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '1', '2024-12-25 13:30:00+00', 500),
    ('31303030-3030-3030-3030-303030303030', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '2', '2024-12-19 14:00:00+00', 1500),
    ('41404040-4040-4040-4040-404040404040', '22222222-bbbb-bbbb-bbbb-222222222222', '3', '2024-12-10 16:30:00+00', 2000),
    ('51505050-5050-5050-5050-505050505050', '22222222-bbbb-bbbb-bbbb-222222222222', '2', '2024-12-05 17:00:00+00', 2500),
    ('61606060-6060-6060-6060-606060606060', '22222222-bbbb-bbbb-bbbb-222222222222', '4', '2024-12-15 18:20:00+00', 3000),
    ('71707070-7070-7070-7070-707070707070', '22222222-bbbb-bbbb-bbbb-222222222222', '5', '2024-11-22 19:00:00+00', 3500),
    ('81808080-8080-8080-8080-808080808080', '22222222-bbbb-bbbb-bbbb-222222222222', '5', '2024-11-13 20:30:00+00', 4000),
    ('91909090-9090-9090-9090-909090909090', '22222222-bbbb-bbbb-bbbb-222222222222', '1', '2024-11-08 21:00:00+00', 4500),
    ('12101010-1010-1010-1010-111111111111', '22222222-bbbb-bbbb-bbbb-222222222222', '1', '2024-11-02 23:30:00+00', 5000)
