-- Hibernate crea la tabla automáticamente gracias a hbm2ddl.auto=create-drop.
-- Este script es solo de referencia para entender la estructura de la tabla.

CREATE SCHEMA IF NOT EXISTS ejemplo_orm;

USE ejemplo_orm;

CREATE TABLE IF NOT EXISTS users (
    id     INT PRIMARY KEY AUTO_INCREMENT,
    name   VARCHAR(45) NOT NULL,
    active BIT NOT NULL
);
