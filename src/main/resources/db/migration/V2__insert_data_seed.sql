INSERT INTO restaurant_tables(number, capacity)
VALUES(1, 4), (20, 4), (35, 5), (3, 8), (2, 10), (14, 8);

INSERT INTO categories_products(name)
VALUES ('Bebidas'), ('Sobremesas'), ('Salgados'), ('Hamburguers'),
('Pizzas');

INSERT INTO products(category_id, name, description, price, preparation_minutes, created_at)
SELECT category.id, 'X-Bacon', 'Hamburguer com bacon e queijo cheddar', 26.99, 25, now()
FROM categories_products category WHERE category.name = 'Hamburguers';

INSERT INTO products(category_id, name, description, price, preparation_minutes, created_at)
SELECT category.id, 'Pizza Calabresa', 'Pizza com calabresa e cebola', 40.50, 35, now()
FROM categories_products category WHERE category.name = 'Pizzas';

INSERT INTO products(category_id, name, description, price, preparation_minutes, created_at)
SELECT category.id, 'Suco de Laranja', 'Suco de laranja 250ml', 10.00, 10, now()
FROM categories_products category WHERE category.name = 'Bebidas';

INSERT INTO products(category_id, name, description, price, created_at)
SELECT category.id, 'Coxinha', 'Coxinha de Frango', 8.00, now()
FROM categories_products category WHERE category.name = 'Salgados';
