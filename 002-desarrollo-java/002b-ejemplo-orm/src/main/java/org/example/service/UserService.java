package org.example.service;

import org.example.dao.UserDAO;
import org.example.model.User;

import java.util.List;

// ─────────────────────────────────────────────────────────────────────────────
// CAPA DE LÓGICA — Service
//
// El Service contiene las reglas de negocio de la aplicación.
// Su función es decidir QUÉ hacer y bajo QUÉ condiciones, antes de
// delegar el acceso a la BD al DAO.
//
// Separar esta capa del DAO tiene una ventaja clave: si las reglas
// cambian (por ej. agregar un límite de usuarios o un log de auditoría),
// se modifica el Service sin tocar el DAO ni la presentación.
// ─────────────────────────────────────────────────────────────────────────────
public class UserService {

    // El Service conoce al DAO, pero no al revés.
    // Esta dirección de dependencia (Service → DAO) es intencional:
    // cada capa solo conoce a la capa inmediatamente inferior.
    private final UserDAO userDAO = new UserDAO();

    // Registrar un usuario: valida los datos antes de persistir.
    // El DAO no valida nada; esa responsabilidad es del Service.
    public void registrar(String name, boolean active) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        userDAO.save(new User(name, active));
        System.out.println("Usuario registrado: " + name);
    }

    // Desactivar requiere primero buscar si el usuario existe.
    // La decisión de "qué hacer si no existe" es lógica de negocio,
    // no responsabilidad del DAO.
    public void desactivar(Integer id) {
        User user = userDAO.findById(id);
        if (user == null) {
            System.out.println("No se encontró usuario con id=" + id);
            return;
        }
        user.setActive(false);
        userDAO.update(user);
        System.out.println("Usuario desactivado: " + user.getName());
    }

    public void eliminar(Integer id) {
        userDAO.delete(id);
        System.out.println("Usuario con id=" + id + " eliminado");
    }

    // La capa de presentación (Main) recibe la lista y decide cómo mostrarla.
    // El Service solo provee los datos sin formatearlos.
    public List<User> listarTodos() {
        return userDAO.findAll();
    }
}
