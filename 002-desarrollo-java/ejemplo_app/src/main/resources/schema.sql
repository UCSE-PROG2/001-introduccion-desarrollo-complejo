-- Paso 1: crear el schema (base de datos)
CREATE SCHEMA IF NOT EXISTS ejemplo_app;

USE ejemplo_app;

-- Paso 2: crear la tabla users
-- Ejecutar este script en MySQL Workbench o HeidiSQL antes de correr la aplicación Java
CREATE TABLE IF NOT EXISTS users (
    id     INT PRIMARY KEY AUTO_INCREMENT,
    name   VARCHAR(45) NOT NULL,
    active BIT NOT NULL
);
