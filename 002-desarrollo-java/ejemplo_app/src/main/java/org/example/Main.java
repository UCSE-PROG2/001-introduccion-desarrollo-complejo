package org.example;

import org.example.dao.UserDAO;
import org.example.model.User;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();

        // --- CREAR ---
        System.out.println("=== CREAR ===");
        User u1 = new User("Ana García", true);
        User u2 = new User("Carlos López", false);
        User u3 = new User("Laura Martínez", true);
        dao.save(u1);
        dao.save(u2);
        dao.save(u3);
        System.out.println("Guardado: " + u1);
        System.out.println("Guardado: " + u2);
        System.out.println("Guardado: " + u3);

        // --- LISTAR ---
        System.out.println("\n=== LISTAR TODOS ===");
        List<User> users = dao.findAll();
        users.forEach(System.out::println);

        // --- BUSCAR POR ID ---
        System.out.println("\n=== BUSCAR POR ID ===");
        User encontrado = dao.findById(u1.getId());
        System.out.println("Encontrado: " + encontrado);

        // --- ACTUALIZAR ---
        System.out.println("\n=== ACTUALIZAR ===");
        u1.setActive(false);
        dao.update(u1);
        System.out.println("Después de actualizar: " + dao.findById(u1.getId()));

        // --- ELIMINAR ---
        System.out.println("\n=== ELIMINAR ===");
        dao.delete(u2);
        System.out.println("Eliminado: " + u2);

        // --- LISTA FINAL ---
        System.out.println("\n=== LISTA FINAL ===");
        dao.findAll().forEach(System.out::println);
    }
}
