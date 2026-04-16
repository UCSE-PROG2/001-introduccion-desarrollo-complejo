package org.example.dao;

import org.example.model.User;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

// ─────────────────────────────────────────────────────────────────────────────
// CAPA DE DATOS — DAO (Data Access Object)
//
// El DAO concentra todas las operaciones de acceso a la base de datos
// para una entidad. El resto de la aplicación (Service, Main) no escribe
// SQL ni conoce a Hibernate: solo llama a los métodos de este DAO.
//
// Si en el futuro se cambia la BD o el ORM, solo se modifica este archivo.
// ─────────────────────────────────────────────────────────────────────────────
public class UserDAO {

    // ── INSERT ────────────────────────────────────────────────────────────────
    public void save(User user) {
        // try-with-resources garantiza que la Session se cierre al terminar,
        // incluso si ocurre una excepción. Equivale a llamar session.close()
        // en un bloque finally.
        try (Session session = HibernateUtil.getSession()) {

            // Las operaciones que modifican datos (INSERT, UPDATE, DELETE)
            // deben ejecutarse dentro de una transacción.
            // Si algo falla antes del commit(), los cambios se descartan.
            session.beginTransaction();

            // persist() toma un objeto nuevo (sin id) y genera un INSERT.
            // Después del commit, Hibernate rellena el campo id con el valor
            // asignado por AUTO_INCREMENT.
            session.persist(user);

            session.getTransaction().commit();
        }
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────
    public void update(User user) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            // merge() se usa para actualizar un objeto que ya tiene id pero
            // que fue modificado fuera de una sesión activa ("detached").
            // Hibernate genera un UPDATE con los valores actuales del objeto.
            session.merge(user);

            session.getTransaction().commit();
        }
    }

    // ── DELETE ────────────────────────────────────────────────────────────────
    public void delete(Integer id) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            // Para eliminar, Hibernate necesita un objeto "managed" (gestionado
            // por la sesión actual). Por eso primero lo buscamos con get()
            // y luego lo pasamos a delete().
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }

            session.getTransaction().commit();
        }
    }

    // ── SELECT por id ─────────────────────────────────────────────────────────
    public User findById(Integer id) {
        try (Session session = HibernateUtil.getSession()) {

            // Las consultas de solo lectura no necesitan transacción explícita.
            // session.get() genera un SELECT WHERE id = ? y retorna null
            // si no existe ninguna fila con ese id.
            return session.get(User.class, id);
        }
    }

    // ── SELECT todos ──────────────────────────────────────────────────────────
    public List<User> findAll() {
        try (Session session = HibernateUtil.getSession()) {

            // createQuery usa JPQL (Java Persistence Query Language), no SQL.
            // "FROM User" equivale a "SELECT * FROM users" pero opera sobre
            // la clase Java User, no directamente sobre la tabla.
            // Hibernate traduce esto al SQL específico del motor configurado.
            return session.createQuery("FROM User", User.class).getResultList();
        }
    }
}
