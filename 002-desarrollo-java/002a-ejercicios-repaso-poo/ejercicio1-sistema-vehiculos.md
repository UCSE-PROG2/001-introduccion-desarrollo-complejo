# Ejercicio 1 — Sistema de gestión de vehículos

## Descripción

Una empresa de alquiler de vehículos necesita un sistema para gestionar su flota. La flota incluye distintos tipos de vehículos: autos, camionetas y motocicletas.

## Requerimientos

- Todos los vehículos comparten información básica: patente, marca, modelo y año de fabricación.
- Cada vehículo debe poder calcular el costo de alquiler diario, pero la forma de calcularlo varía según el tipo de vehículo (los autos cobran por categoría, las camionetas por capacidad de carga, las motos por cilindrada).
- Todos los vehículos deben poder generar un resumen descriptivo con sus datos principales.
- Algunos vehículos pueden ser eléctricos. Los vehículos eléctricos tienen un comportamiento adicional: informar su autonomía en kilómetros y si necesitan carga.
- El sistema debe poder procesar una lista mixta de vehículos y, por ejemplo, listar solo los eléctricos o calcular el ingreso total del día dado un conjunto de alquileres.
