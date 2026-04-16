package org.example.data;

import jakarta.persistence.*;

// ─────────────────────────────────────────────────────────────────────────────
// CAPA DE DATOS — Entidad JPA
//
// Una "entidad" es una clase Java que Hibernate mapea a una tabla de la BD.
// Cada instancia de esta clase representa una fila de esa tabla.
// ─────────────────────────────────────────────────────────────────────────────

// @Entity le indica a Hibernate que esta clase representa una tabla en la BD.
// Sin esta anotación Hibernate la ignora por completo.
@Entity

// @Table especifica el nombre exacto de la tabla en la BD.
// Si el nombre de la clase y de la tabla coinciden, esta anotación es opcional.
@Table(name = "users")
public class User {

    // @Id marca este campo como la clave primaria (equivale a PRIMARY KEY en SQL).
    @Id

    // @GeneratedValue delega la generación del ID a la base de datos.
    // IDENTITY usa el AUTO_INCREMENT de MySQL: Hibernate inserta la fila
    // y luego lee el id asignado por la BD.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // @Column mapea este atributo a una columna de la tabla.
    // nullable=false → NOT NULL en SQL.
    // length=45      → VARCHAR(45) en SQL.
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "active", nullable = false)
    private boolean active;

    // Constructor vacío obligatorio para JPA/Hibernate.
    // Hibernate necesita poder crear instancias de esta clase sin argumentos
    // para luego rellenar los campos mediante reflexión al leer filas de la BD.
    public User() {}

    // Constructor de conveniencia para crear usuarios desde el código.
    // El id no se pasa porque lo asigna la BD automáticamente.
    public User(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    // Hibernate también usa los getters y setters para leer y escribir los valores
    // de cada campo durante el mapeo objeto ↔ fila.
    public Integer getId()                { return id; }
    public String getName()               { return name; }
    public boolean isActive()             { return active; }
    public void setName(String name)      { this.name = name; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        return "User{ id=" + id + ", name='" + name + "', active=" + active + " }";
    }
}
