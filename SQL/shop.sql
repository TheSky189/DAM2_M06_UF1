-- Crear la base de datos
CREATE DATABASE shop;

-- Usar la base de datos
USE shop;

-- Crear la tabla employee
CREATE TABLE employee (
    employeeId INT PRIMARY KEY,
    name VARCHAR(100),
    password VARCHAR(100)
);

-- Insertar registros de prueba
INSERT INTO employee (employeeId, name, password) VALUES (123, 'Akali', 'akali');
INSERT INTO employee (employeeId, name, password) VALUES (456, 'Zed', 'zed');
INSERT INTO employee (employeeId, name, password) VALUES (789, 'Draven', 'draven');
