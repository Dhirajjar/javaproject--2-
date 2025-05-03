CREATE DATABASE airline_db;

USE airline_db;

CREATE TABLE flight (
    id INT PRIMARY KEY AUTO_INCREMENT,
    flight_no VARCHAR(20),
    source VARCHAR(50),
    destination VARCHAR(50),
    departure_time VARCHAR(20),
    arrival_time VARCHAR(20),
    price DOUBLE
);

CREATE TABLE customers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    passport_no VARCHAR(50),
    nationality VARCHAR(50),
    flight_no VARCHAR(20)
);
