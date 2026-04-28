# Ejercicio TP 2 — Sistema de gestión hotelera

## Paso 0 — Repositorio

Antes de escribir una línea de código, creá un repositorio **privado** en GitHub con el nombre `hoteleria-java` y agregá a **maximilianolovera@gmail.com** como colaborador con rol de lectura. La entrega se realiza a través de ese repositorio.

---

## Descripción del sistema

Una cadena hotelera necesita una aplicación de consola para registrar sus hoteles y las habitaciones disponibles en cada uno. La aplicación debe permitir dar de alta nuevos registros, buscar habitaciones aplicando filtros y generar reportes de capacidad y precios agrupados por hotel.

---

## Base de datos

Crear el esquema `hoteleria` en MySQL y ejecutar el siguiente script:

```sql
CREATE SCHEMA IF NOT EXISTS hoteleria;

CREATE TABLE hoteleria.hotel (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre     VARCHAR(100) NOT NULL,
    ciudad     VARCHAR(80)  NOT NULL,
    categoria  INT          NOT NULL,
    telefono   VARCHAR(20)  NOT NULL
);

CREATE TABLE hoteleria.habitacion (
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero             VARCHAR(10)  NOT NULL,
    tipo               VARCHAR(30)  NOT NULL,
    precio_noche       DOUBLE       NOT NULL,
    capacidad_personas INT          NOT NULL,
    hotel_id           BIGINT       NOT NULL,
    FOREIGN KEY (hotel_id) REFERENCES hoteleria.hotel(id)
);

INSERT INTO hoteleria.hotel (nombre, ciudad, categoria, telefono) VALUES
('Sheraton Buenos Aires',      'Buenos Aires',  5, '011-4318-9000'),
('NH Jousten',                 'Buenos Aires',  4, '011-4321-6750'),
('Llao Llao Hotel & Resort',   'Bariloche',     5, '0294-444-8530'),
('Amerian Carlos V',           'Córdoba',       4, '0351-420-7000'),
('Howard Johnson Mendoza',     'Mendoza',       4, '0261-441-6464'),
('Portal del Ángel',           'Salta',         3, '0387-431-0077'),
('Gran Hotel Provincial',      'Mar del Plata', 4, '0223-499-4000'),
('Patios de Cafayate',         'Cafayate',      5, '0387-422-2229'),
('Iguazú Grand Resort & Spa',  'Puerto Iguazú', 5, '03757-498-050'),
('Enjoy Punta del Este',       'Punta del Este',5, '042-491-000');

INSERT INTO hoteleria.habitacion (numero, tipo, precio_noche, capacidad_personas, hotel_id) VALUES
('1201', 'Suite',      85000.00, 4, 1),
('305',  'Doble',      28000.00, 2, 2),
('Lake', 'Suite',     120000.00, 2, 3),
('101',  'Individual', 18000.00, 1, 4),
('212',  'Familiar',   35000.00, 4, 5),
('08',   'Doble',      22000.00, 2, 6),
('PH2',  'Suite',      75000.00, 2, 7),
('DLX',  'Doble',      45000.00, 2, 8),
('C04',  'Familiar',   98000.00, 6, 9),
('PHT',  'Suite',     150000.00, 4, 10);
```

---

## Estructura del proyecto

Crear un proyecto nuevo en **Java con Gradle** respetando la siguiente arquitectura en capas:

```
src/main/java/com/hoteleria/
├── model/
│   ├── Hotel.java
│   └── Habitacion.java
├── data/
│   ├── HibernateUtil.java
│   └── HoteleriaRepository.java
├── service/
│   └── HoteleriaService.java
└── Main.java
```

---

## Clases requeridas

### Entidades (`model/`)

**`Hotel`** — representa un hotel con los campos: nombre, ciudad, categoría (cantidad de estrellas, valor entre 1 y 5) y teléfono. Tiene asociadas una o más habitaciones.

**`Habitacion`** — representa una habitación con los campos: número, tipo (Individual, Doble, Familiar, Suite), precio por noche y capacidad de personas. Cada habitación pertenece a un único hotel.

---

### Capa de datos (`data/`)

**`HibernateUtil`** — administra la conexión a la base de datos. Se configura una única vez al iniciar la aplicación.

**`HoteleriaRepository`** — **debe implementar el patrón Singleton** (una sola instancia durante toda la ejecución). Es la única clase que accede directamente a la base de datos. Debe exponer los siguientes métodos:

- **`guardarHotel`** — persiste un nuevo hotel.
- **`guardarHabitacion`** — persiste una nueva habitación con su hotel asociado.
- **`buscarHotelPorId`** — devuelve el hotel que corresponde al id recibido, o `null` si no existe.
- **`buscarHabitaciones`** — recibe tres filtros opcionales: tipo, precio máximo por noche y capacidad mínima de personas. Cada filtro solo se aplica si fue informado; si los tres están vacíos, devuelve todas las habitaciones.
- **`resumenPorHotel`** — devuelve, agrupado por hotel: cantidad de habitaciones, capacidad total de personas (suma de las capacidades individuales) y precio promedio por noche.

---

### Capa de negocio (`service/`)

**`HoteleriaService`** — **debe implementar el patrón Singleton**. Coordina la lógica de negocio y delega las operaciones de persistencia al repositorio. Debe exponer los siguientes métodos:

- **`registrarHotel`** — recibe los datos del hotel y lo persiste.
- **`registrarHabitacion`** — recibe los datos de la habitación y el id del hotel al que pertenece. Si el hotel no existe, informa el error y no realiza la inserción.
- **`buscarHabitaciones`** — aplica los filtros recibidos y muestra los resultados por pantalla. Si no hay resultados, lo informa.
- **`mostrarResumenPorHotel`** — muestra el resumen por hotel con cantidad de habitaciones, capacidad total de personas y precio promedio por noche.

---

### Menú principal (`Main`)

Presentar un menú repetitivo por consola con las siguientes opciones:

```
=== Sistema de Gestión Hotelera ===
1. Registrar hotel
2. Registrar habitación
3. Buscar habitaciones
4. Ver resumen por hotel
0. Salir
```

- **Opción 1** — solicitar nombre, ciudad, categoría (estrellas) y teléfono.
- **Opción 2** — solicitar número de habitación, tipo, precio por noche, capacidad de personas e ID del hotel.
- **Opción 3** — solicitar tipo, precio máximo por noche y capacidad mínima, aclarando que cada campo es opcional.
- **Opción 4** — no solicita datos; ejecuta y muestra el resumen directamente.

---

## Criterios de evaluación

| Ítem | Descripción |
|------|-------------|
| Repositorio | Repositorio privado creado y colaborador sumado antes de la entrega |
| Estructura | El proyecto compila y respeta la arquitectura en tres capas |
| Entidades | Relación correcta entre `Habitacion` y `Hotel` |
| Singleton | `HoteleriaRepository` y `HoteleriaService` implementan el patrón correctamente |
| Inserción | Ambos métodos persisten correctamente; `registrarHabitacion` valida la existencia del hotel |
| Búsqueda | Los filtros son opcionales: si no se informan, se devuelven todas las habitaciones |
| Agrupamiento | El resumen muestra cantidad de habitaciones, capacidad total y precio promedio por hotel |
| Main | El menú funciona en bucle y solicita los datos indicados en cada opción |
