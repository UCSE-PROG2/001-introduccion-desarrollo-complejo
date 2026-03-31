# Ejercicio 3 — Sistema de notificaciones de una aplicación

## Descripción

Una aplicación móvil necesita enviar notificaciones a sus usuarios a través de distintos canales: email, SMS y notificación push.

## Requerimientos

- Todos los canales de notificación tienen un destinatario y un mensaje, y deben poder enviarse.
- La forma de enviar la notificación varía según el canal: el email incluye asunto y dirección de correo, el SMS usa un número de teléfono con límite de caracteres, la notificación push usa un token de dispositivo y puede incluir un ícono.
- Todos los canales deben poder generar un resumen del envío con la información relevante del mensaje y el destinatario.
- Algunos canales soportan reenvío automático en caso de fallo. Los que lo soporten deben definir cuántos reintentos realizan y ejecutar la lógica de reintento.
- El sistema debe poder gestionar una cola mixta de notificaciones pendientes, procesarlas en orden y calcular cuántas soportan reintento.
