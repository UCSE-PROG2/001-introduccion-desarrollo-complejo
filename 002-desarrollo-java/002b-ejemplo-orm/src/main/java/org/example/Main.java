package org.example;

import org.example.data.HibernateUtil;
import org.example.service.UserService;

// ─────────────────────────────────────────────────────────────────────────────
// CAPA DE PRESENTACIÓN — Punto de entrada
//
// Main es la única clase que tiene el método main() y por tanto el único
// punto de entrada de la aplicación.
//
// Su única responsabilidad es mostrar información al usuario (en este caso
// por consola) e invocar la capa de lógica (UserService).
// No accede al Repository ni a Hibernate directamente.
//
// Flujo de llamadas:
//   Main → UserService → Repository → Hibernate → MySQL
// ─────────────────────────────────────────────────────────────────────────────
public class Main {

    public static void main(String[] args) {

        // La capa de presentación solo conoce al Service, nunca al Repository.
        UserService service = new UserService();

        // INSERT: registrar inserta tres usuarios en la tabla users.
        System.out.println("=== INSERT ===");
        service.registrar("Ana García",     true);
        service.registrar("Carlos López",   false);
        service.registrar("Laura Martínez", true);

        // SELECT: listarTodos retorna todos los usuarios y los imprimimos con toString().
        System.out.println("\n=== SELECT todos ===");
        service.listarTodos().forEach(System.out::println);

        // UPDATE: desactivar busca el usuario por id, cambia active a false y lo persiste.
        System.out.println("\n=== UPDATE (desactivar id=1) ===");
        service.desactivar(1);
        service.listarTodos().forEach(System.out::println);

        // DELETE: eliminar borra la fila correspondiente al id dado.
        System.out.println("\n=== DELETE (eliminar id=2) ===");
        service.eliminar(2);
        service.listarTodos().forEach(System.out::println);

        // Cierra la SessionFactory y libera las conexiones al finalizar.
        HibernateUtil.shutdown();
    }
}
