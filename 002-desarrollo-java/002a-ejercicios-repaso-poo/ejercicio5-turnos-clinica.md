# Ejercicio 5 — Sistema de turnos de una clínica

## Descripción

Una clínica médica necesita gestionar sus turnos, que pueden darse de distintas modalidades según las necesidades del paciente.

## Requerimientos

- Todos los turnos tienen información común: paciente, médico, especialidad y fecha y hora del turno.
- Todos los turnos deben poder generar un comprobante de reserva con los datos del paciente, el médico y la fecha.
- La clínica ofrece tres modalidades: turno presencial (en consultorio), teleconsulta (videollamada) y visita domiciliaria. El costo de cada turno se calcula de forma diferente: el turno presencial tiene un arancel base por especialidad, la teleconsulta aplica un descuento porcentual sobre el arancel base, la visita domiciliaria suma al arancel base un adicional por kilómetros de distancia al domicilio del paciente.
- Algunas modalidades permiten cancelación con reembolso. Las que lo soporten deben definir el porcentaje de reembolso aplicable y ejecutar la lógica de cancelación (que devuelve el monto a reintegrar). La visita domiciliaria no admite cancelación una vez confirmada.
- El sistema debe poder procesar una lista mixta de turnos del día, calcular la facturación total y determinar cuántos turnos admiten cancelación con reembolso.
