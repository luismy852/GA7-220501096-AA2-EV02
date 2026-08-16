-- Proyecto: PlanPet
-- Script de creacion de la tabla plan para el modulo AA2-EV02
CREATE DATABASE IF NOT EXISTS planpet;
USE planpet;

CREATE TABLE IF NOT EXISTS plan (
    id_plan     INT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(80) NOT NULL,
    descripcion VARCHAR(255),
    precio      DECIMAL(10,2) NOT NULL,
    id_company  INT NOT NULL DEFAULT 1
);

INSERT INTO plan (nombre, descripcion, precio, id_company) VALUES
('Plan Basico', 'Consulta general y vacunacion anual', 30000.00, 1),
('Plan Premium', 'Consulta, vacunacion y cirugias menores', 60000.00, 1);
