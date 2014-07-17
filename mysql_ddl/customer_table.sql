CREATE DATABASE CustomerDB;
Use CustomerDB;
CREATE TABLE Customer (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
first_name VARCHAR(30),
last_name VARCHAR(30),
email_id VARCHAR(50), 
phone_number VARCHAR(15));
