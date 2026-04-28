# Ejercicio TP 1 — Sistema de gestión de una distribuidora de alimentos

## Paso 0 — Repositorio

Antes de escribir una línea de código, creá un repositorio **privado** en GitHub con el nombre `distribuidora-java` y agregá a **maximilianolovera@gmail.com** como colaborador con rol de lectura. La entrega se realiza a través de ese repositorio.

---

## Descripción del sistema

Una distribuidora de alimentos necesita una aplicación de consola para registrar sus proveedores y los productos que cada uno abastece. La aplicación debe permitir dar de alta nuevos registros, buscar productos aplicando filtros y generar reportes de inventario agrupados por proveedor.

---

## Base de datos

Crear el esquema `distribuidora` en MySQL y ejecutar el siguiente script:

```sql
CREATE SCHEMA IF NOT EXISTS distribuidora;

CREATE TABLE distribuidora.proveedor (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    razon_social  VARCHAR(100) NOT NULL,
    cuit          VARCHAR(13)  NOT NULL UNIQUE,
    telefono      VARCHAR(20)  NOT NULL,
    email         VARCHAR(100) NOT NULL
);

CREATE TABLE distribuidora.producto (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre          VARCHAR(100) NOT NULL,
    categoria       VARCHAR(50)  NOT NULL,
    precio_unitario DOUBLE       NOT NULL,
    stock           INT          NOT NULL DEFAULT 0,
    proveedor_id    BIGINT       NOT NULL,
    FOREIGN KEY (proveedor_id) REFERENCES distribuidora.proveedor(id)
);

INSERT INTO distribuidora.proveedor (razon_social, cuit, telefono, email) VALUES
('La Serenísima',         '30-12345678-9', '011-4567-8901', 'contacto@serenisima.com.ar'),
('Arcor S.A.',            '30-98765432-1', '0351-456-7890', 'ventas@arcor.com.ar'),
('Molinos Río de la Plata','30-11111111-2', '011-5678-9012', 'comercial@molinos.com.ar'),
('Mastellone Hnos.',      '30-22222222-3', '011-6789-0123', 'ventas@mastellone.com.ar'),
('Kraft Foods Argentina', '30-33333333-4', '011-7890-1234', 'distribución@kraft.com.ar'),
('Peñaflor S.A.',         '30-44444444-5', '0261-890-1234', 'pedidos@penaflor.com.ar'),
('Cervecería Quilmes',    '30-55555555-6', '011-8901-2345', 'ventas@quilmes.com.ar'),
('Bagley Argentina',      '30-66666666-7', '011-9012-3456', 'contacto@bagley.com.ar'),
('Unilever Argentina',    '30-77777777-8', '011-0123-4567', 'distribuidores@unilever.com.ar'),
('Coca-Cola FEMSA',       '30-88888888-0', '011-1234-5678', 'logistica@cocacola.com.ar');

INSERT INTO distribuidora.producto (nombre, categoria, precio_unitario, stock, proveedor_id) VALUES
('Leche entera 1L',              'Lácteos',     850.00,  200, 1),
('Caramelos masticables x100',   'Confitería',  320.00,  500, 2),
('Harina 000 1kg',               'Harinas',     650.00,  300, 3),
('Manteca 200g',                 'Lácteos',     780.00,  150, 4),
('Mayonesa Hellmann\'s 250g',    'Salsas',     1200.00,   80, 5),
('Vino Toro Rojo 750ml',         'Bebidas',    2500.00,  120, 6),
('Cerveza Quilmes Clásica 1L',   'Bebidas',     950.00,  400, 7),
('Galletitas de agua 200g',      'Galletitas',  450.00,  350, 8),
('Skip Limpieza Profunda 1kg',   'Limpieza',   1800.00,   90, 9),
('Coca-Cola 2.25L',              'Bebidas',     700.00,  600, 10);
```

---

## Estructura del proyecto

Crear un proyecto nuevo en **Java con Gradle** respetando la siguiente arquitectura en capas:

```
src/main/java/com/distribuidora/
├── model/
│   ├── Proveedor.java
│   └── Producto.java
├── data/
│   ├── HibernateUtil.java
│   └── DistribuidoraRepository.java
├── service/
│   └── DistribuidoraService.java
└── Main.java
```

---

## Clases requeridas

### Entidades (`model/`)

**`Proveedor`** — representa un proveedor con los campos: razón social, CUIT, teléfono y email. Tiene asociados uno o más productos.

**`Producto`** — representa un producto con los campos: nombre, categoría, precio unitario y stock. Cada producto pertenece a un único proveedor.

---

### Capa de datos (`data/`)

**`HibernateUtil`** — administra la conexión a la base de datos. Se configura una única vez al iniciar la aplicación.

**`DistribuidoraRepository`** — **debe implementar el patrón Singleton** (una sola instancia durante toda la ejecución). Es la única clase que accede directamente a la base de datos. Debe exponer los siguientes métodos:

- **`guardarProveedor`** — persiste un nuevo proveedor.
- **`guardarProducto`** — persiste un nuevo producto con su proveedor asociado.
- **`buscarProveedorPorId`** — devuelve el proveedor que corresponde al id recibido, o `null` si no existe.
- **`buscarProductos`** — recibe tres filtros opcionales: categoría, precio máximo y stock mínimo. Cada filtro solo se aplica si fue informado; si los tres están vacíos, devuelve todos los productos.
- **`resumenPorProveedor`** — devuelve, agrupado por proveedor: cantidad de productos, stock total y valor total del inventario (stock × precio unitario).

---

### Capa de negocio (`service/`)

**`DistribuidoraService`** — **debe implementar el patrón Singleton**. Coordina la lógica de negocio y delega las operaciones de persistencia al repositorio. Debe exponer los siguientes métodos:

- **`registrarProveedor`** — recibe los datos del proveedor y lo persiste.
- **`registrarProducto`** — recibe los datos del producto y el id del proveedor al que pertenece. Si el proveedor no existe, informa el error y no realiza la inserción.
- **`buscarProductos`** — aplica los filtros recibidos y muestra los resultados por pantalla. Si no hay resultados, lo informa.
- **`mostrarResumenPorProveedor`** — muestra el resumen de inventario por proveedor con cantidad de productos, stock total y valor total.

---

### Menú principal (`Main`)

Presentar un menú repetitivo por consola con las siguientes opciones:

```
=== Distribuidora de Alimentos ===
1. Registrar proveedor
2. Registrar producto
3. Buscar productos
4. Ver resumen por proveedor
0. Salir
```

- **Opción 1** — solicitar razón social, CUIT, teléfono y email.
- **Opción 2** — solicitar nombre, categoría, precio unitario, stock e ID del proveedor.
- **Opción 3** — solicitar categoría, precio máximo y stock mínimo, aclarando que cada campo es opcional.
- **Opción 4** — no solicita datos; ejecuta y muestra el resumen directamente.

---

## Criterios de evaluación

| Ítem | Descripción |
|------|-------------|
| Repositorio | Repositorio privado creado y colaborador sumado antes de la entrega |
| Estructura | El proyecto compila y respeta la arquitectura en tres capas |
| Entidades | Relación correcta entre `Producto` y `Proveedor` |
| Singleton | `DistribuidoraRepository` y `DistribuidoraService` implementan el patrón correctamente |
| Inserción | Ambos métodos persisten correctamente; `registrarProducto` valida la existencia del proveedor |
| Búsqueda | Los filtros son opcionales: si no se informan, se devuelven todos los productos |
| Agrupamiento | El resumen muestra cantidad, stock total y valor total por proveedor |
| Main | El menú funciona en bucle y solicita los datos indicados en cada opción |
