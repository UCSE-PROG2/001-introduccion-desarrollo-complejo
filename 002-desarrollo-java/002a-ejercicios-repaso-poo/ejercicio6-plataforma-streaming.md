# Ejercicio 6 — Plataforma de streaming de contenidos

## Descripción

Una plataforma de streaming necesita modelar el catálogo de contenidos que ofrece a sus suscriptores.

## Requerimientos

- Todos los contenidos tienen información básica: título, año de lanzamiento y clasificación por género.
- Todos los contenidos deben poder mostrar su ficha con los datos principales y la duración total.
- El catálogo incluye tres tipos de contenido: películas, series y podcasts. La duración total se calcula de forma distinta para cada uno: una película tiene una duración fija en minutos, una serie tiene una cantidad de temporadas con episodios de duración promedio, un podcast tiene una cantidad de episodios con duración individual variable (se calcula el promedio).
- Algunos contenidos pueden descargarse para verlos sin conexión. Los que lo soporten deben informar el tamaño estimado del archivo en MB y ejecutar la lógica de descarga (que muestra un mensaje con el título y el tamaño). Las series no están disponibles para descarga por su volumen de datos.
- El sistema debe poder recorrer el catálogo completo, calcular las horas totales de contenido disponible y listar únicamente los contenidos que se pueden descargar.
