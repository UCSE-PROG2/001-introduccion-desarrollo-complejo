# Ejercicio 4 — Sistema de cuentas bancarias

## Descripción

Un banco necesita modelar los distintos tipos de cuentas que ofrece a sus clientes para calcular intereses y gestionar operaciones financieras básicas.

## Requerimientos

- Todos los tipos de cuenta comparten información básica: número de cuenta, titular y saldo actual.
- Todas las cuentas deben poder generar un resumen con sus datos principales y el saldo disponible.
- El banco ofrece tres tipos de cuenta: caja de ahorro, cuenta corriente y cuenta de inversión. El cálculo de intereses mensuales es diferente para cada tipo: la caja de ahorro aplica una tasa fija sobre el saldo, la cuenta corriente aplica una tasa menor pero suma un bono por operaciones realizadas en el mes, la cuenta de inversión aplica una tasa variable según el plazo de permanencia del dinero.
- Algunas cuentas permiten realizar transferencias a terceros. Las que lo soporten deben definir un límite de transferencia diario y ejecutar la lógica de transferencia (validando que no se supere el límite ni el saldo disponible). La cuenta de inversión no permite transferencias directas.
- El sistema debe poder gestionar una lista mixta de cuentas, calcular el total de intereses que el banco debe abonar en el mes y listar solo las cuentas que soportan transferencias.
