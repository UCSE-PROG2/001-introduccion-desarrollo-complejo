# Ejercicio 7 — Sistema de pedidos de e-commerce

## Descripción

Una tienda online necesita modelar los pedidos que recibe para calcular costos de envío y gestionar coberturas adicionales.

## Requerimientos

- Todos los pedidos tienen un número de pedido, el nombre del cliente, la fecha de creación y el peso total en kilogramos.
- Existen tres tipos de pedido: nacional, internacional y express.
  - Los pedidos nacionales calculan el costo de envío según el peso y la zona de destino (zona A, B o C con tarifas distintas).
  - Los pedidos internacionales calculan el costo de envío según el peso más un recargo por país de destino.
  - Los pedidos express calculan el costo de envío con una tarifa fija más un recargo por kilogramo, independientemente del destino.
- Todos los pedidos deben poder mostrar un resumen con los datos del pedido y el costo de envío calculado.
- Algunos tipos de pedido pueden ser asegurados. Los pedidos que soporten seguro deben informar el valor de la prima y permitir activar el seguro.
- El sistema debe poder procesar una lista mixta de pedidos: calcular el costo total de envíos del día y listar cuáles pueden ser asegurados.
