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
INSERT INTO employee (employeeId, name, password) VALUES (1, 'John Doe', 'password123');
INSERT INTO employee (employeeId, name, password) VALUES (2, 'Jane Smith', 'mypassword');
INSERT INTO employee (employeeId, name, password) VALUES (3, 'Mike Brown', 'securepass');
