USE sakila;
SHOW TABLES;
DESCRIBE film;
SELECT * FROM film;

-- selected columns and rows
SELECT title, rating, length FROM film;

-- distinct list of rating, get available rating
SELECT DISTINCT rating FROM film ORDER BY rating;

SELECT title, rating, length FROM film WHERE rating = "R";

-- more by or criteria
SELECT title, rating, length FROM film WHERE rating = "R" OR length < 75;

-- multible criteria

SELECT title, rating, length FROM film WHERE rating = "R" AND length < 75;

-- another criteria for OR
SELECT title, rating, length FROM film WHERE rating IN ("R", "NC-17");

-- Not In criteria
SELECT title, rating, length FROM film WHERE rating NOT IN ("R", "NC-17");

-- Sorting
SELECT title, rating, length FROM film WHERE rating NOT IN ("R", "NC-17") ORDER BY length DESC;

-- TOP 5 result
SELECT title, rating, length FROM film WHERE rating NOT IN ("R", "NC-17") ORDER BY length DESC LIMIT 5;
