INSERT INTO cuisines (name)
VALUES ('Polish');

INSERT INTO cuisines (name)
VALUES ('Mexican');

INSERT INTO cuisines (name)
VALUES ('Italian');

INSERT INTO desserts (name, price, cuisine_id)
VALUES ('Kremówka', 8.5, (select id from cuisines where name = 'Polish'));

INSERT INTO desserts (name, price, cuisine_id)
VALUES ('Ciepłe lody', 8.0, (select id from cuisines where name = 'Polish'));

INSERT INTO desserts (name, price, cuisine_id)
VALUES ('Śliwki w czekoladzie', 10.0, (select id from cuisines where name = 'Polish'));

INSERT INTO desserts (name, price, cuisine_id)
VALUES ('Chałka', 7.5, (select id from cuisines where name = 'Polish'));

INSERT INTO desserts (name, price, cuisine_id)
VALUES ('Sopapillas', 12.0, (select id from cuisines where name = 'Mexican'));

INSERT INTO desserts (name, price, cuisine_id)
VALUES ('Caramel Flan', 18.0, (select id from cuisines where name = 'Mexican'));

INSERT INTO desserts (name, price, cuisine_id)
VALUES ('Fried Ice Cream', 7.0, (select id from cuisines where name = 'Mexican'));

INSERT INTO desserts (name, price, cuisine_id)
VALUES ('Churros', 15.0, (select id from cuisines where name = 'Mexican'));

INSERT INTO desserts (name, price, cuisine_id)
VALUES ('Torta Barozzi', 12.0, (select id from cuisines where name = 'Italian'));

INSERT INTO desserts (name, price, cuisine_id)
VALUES ('Zuccotto', 15.0, (select id from cuisines where name = 'Italian'));

INSERT INTO desserts (name, price, cuisine_id)
VALUES ('Ricotta Cake', 12.0, (select id from cuisines where name = 'Italian'));

INSERT INTO desserts (name, price, cuisine_id)
VALUES ('Tiramisu', 9.5, (select id from cuisines where name = 'Italian'));

INSERT INTO drinks (name, price)
VALUES ('Coca-cola', 3.5);

INSERT INTO drinks (name, price)
VALUES ('Sprite', 3.5);

INSERT INTO drinks (name, price)
VALUES ('Coffee', 4.5);

INSERT INTO drinks (name, price)
VALUES ('Tea', 4.5);

INSERT INTO main_courses (name, price, cuisine_id)
VALUES ('Bigos', 18.5, (select id from cuisines where name = 'Polish'));

INSERT INTO main_courses (name, price, cuisine_id)
VALUES ('Pierogi', 12.0, (select id from cuisines where name = 'Polish'));

INSERT INTO main_courses (name, price, cuisine_id)
VALUES ('Kotlet Schabowy', 20.0, (select id from cuisines where name = 'Polish'));

INSERT INTO main_courses (name, price, cuisine_id)
VALUES ('Placki Ziemniaczane', 15.5, (select id from cuisines where name = 'Polish'));

INSERT INTO main_courses (name, price, cuisine_id)
VALUES ('Tacos', 15.0, (select id from cuisines where name = 'Mexican'));

INSERT INTO main_courses (name, price, cuisine_id)
VALUES ('Tostadas', 18.0, (select id from cuisines where name = 'Mexican'));

INSERT INTO main_courses (name, price, cuisine_id)
VALUES ('Enchiladas', 22.0, (select id from cuisines where name = 'Mexican'));

INSERT INTO main_courses (name, price, cuisine_id)
VALUES ('Tamales', 16.5, (select id from cuisines where name = 'Mexican'));

INSERT INTO main_courses (name, price, cuisine_id)
VALUES ('Pizza', 32.0, (select id from cuisines where name = 'Italian'));

INSERT INTO main_courses (name, price, cuisine_id)
VALUES ('Bottarga', 25.0, (select id from cuisines where name = 'Italian'));

INSERT INTO main_courses (name, price, cuisine_id)
VALUES ('Lasagna', 28.0, (select id from cuisines where name = 'Italian'));

INSERT INTO main_courses (name, price, cuisine_id)
VALUES ('Polenta', 24.0, (select id from cuisines where name = 'Italian'));