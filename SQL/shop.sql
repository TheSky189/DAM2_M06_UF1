-- Crear la base de datos
CREATE DATABASE shop;

-- Usar la base de datos
USE shop;

-- Crear la tabla employee
CREATE TABLE employee (
    employeeId INT PRIMARY KEY,
    password VARCHAR(100)
);

-- Insertar registros de prueba
INSERT INTO employee (employeeId, password) VALUES (123, 'akali');
INSERT INTO employee (employeeId, password) VALUES (456, 'zed');
INSERT INTO employee (employeeId, password) VALUES (789, 'draven');
