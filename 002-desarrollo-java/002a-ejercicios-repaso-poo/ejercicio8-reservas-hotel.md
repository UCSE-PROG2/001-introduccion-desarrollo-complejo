# Ejercicio 8 — Sistema de reservas de hotel

## Descripción

Un hotel necesita un sistema para gestionar sus habitaciones, calcular precios por noche y ofrecer servicios adicionales según el tipo de habitación.

## Requerimientos

- Todas las habitaciones tienen un número, el piso en el que se encuentran y una descripción general.
- Existen tres tipos de habitación: simple, doble y suite.
  - Las habitaciones simples tienen un precio base fijo por noche.
  - Las habitaciones dobles tienen un precio base más un adicional según la cantidad de huéspedes (máximo 2).
  - Las suites tienen un precio base elevado más un porcentaje adicional según la temporada (alta, media o baja).
- Todas las habitaciones deben poder mostrar su información completa incluyendo el precio por noche calculado.
- Algunos tipos de habitación permiten agregar servicios personalizados (por ejemplo, desayuno incluido, decoración especial, traslado desde aeropuerto). Las habitaciones que lo soporten deben listar los extras disponibles y permitir agregar uno al momento de la reserva.
- El sistema debe poder procesar una lista mixta de habitaciones: calcular la facturación total para una estadía de N noches y listar cuáles admiten personalización.
