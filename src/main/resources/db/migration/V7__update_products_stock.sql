UPDATE products set 
stock = 10
WHERE id IN(1, 2, 3, 4);

UPDATE products
SET preparation_minutes = 20
WHERE id = 4;
