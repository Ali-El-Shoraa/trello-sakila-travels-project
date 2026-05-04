select * from city order by city_id;
select city_id,city ,country from city c
INNER JOIN country o
on c.country_id=o.country_id
order by c.city_id ;
SELECT * FROM city WHERE city_id BETWEEN 10 AND 50;
SELECT * FROM city WHERE city LIKE 'A%';
SELECT * FROM city WHERE city_id IN (1, 10, 100);
SELECT * FROM city WHERE country_id NOT IN (1, 2, 3)
order by country_id;
SELECT country_id, COUNT(city_id) AS total_cities
FROM city
GROUP BY country_id
HAVING COUNT(city_id) > 10;
SELECT city FROM city WHERE country_id = ANY (SELECT country_id FROM country WHERE country LIKE 'A%');
DELETE FROM country WHERE country_id = 7;
SELECT * FROM city WHERE city_id IN (1, 10, 100);
SELECT * FROM city WHERE city_id BETWEEN 10 AND 50;
SELECT * FROM city WHERE city_id NOT IN (1, 2, 3);
SELECT c.city, a.address, a.district 
FROM city c 
INNER JOIN address a ON c.city_id = a.city_id
where a.district='California';
DELETE FROM country WHERE country_id = 5;


'

