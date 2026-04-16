package org.example.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

// ─────────────────────────────────────────────────────────────────────────────
// CAPA DE DATOS — Configuración de Hibernate (patrón Singleton)
//
// SessionFactory es el objeto central de Hibernate: lee la configuración,
// establece el pool de conexiones y sabe cómo mapear cada entidad.
// Crearlo es costoso (puede tardar varios segundos), por eso se hace
// una sola vez y se reutiliza durante toda la vida de la aplicación.
// ─────────────────────────────────────────────────────────────────────────────
public class HibernateUtil {

    // El campo es static: existe uno solo para toda la clase, no uno por instancia.
    // Es final: una vez asignado no se puede reemplazar.
    private static final SessionFactory sessionFactory;

    // El bloque static{} se ejecuta una única vez cuando la JVM carga esta clase.
    // Es el lugar correcto para inicializar recursos costosos y compartidos.
    static {
        try {
            // Configuration().configure() lee el archivo hibernate.cfg.xml
            // que está en src/main/resources. Desde ahí obtiene la URL de la BD,
            // usuario, contraseña, dialecto y las clases entidad registradas.
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            // Si falla la conexión o la configuración está mal, detenemos
            // la aplicación de inmediato con un mensaje claro.
            throw new RuntimeException("Error al inicializar Hibernate", e);
        }
    }

    // Abre y retorna una nueva Session.
    // Una Session representa una única "conversación" con la base de datos:
    // se usa para ejecutar operaciones (INSERT, SELECT, UPDATE, DELETE)
    // y luego se cierra. No es thread-safe, por eso se abre una por operación.
    public static Session getSession() {
        return sessionFactory.openSession();
    }

    // Libera todos los recursos de la SessionFactory al cerrar la aplicación.
    // Importante llamarlo al final para cerrar el pool de conexiones limpiamente.
    public static void shutdown() {
        sessionFactory.close();
    }
}
