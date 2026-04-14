package org.example;

import org.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    // Datos de conexión a la base de datos
    // Asegurarse de que el schema "ejemplo_app" exista antes de ejecutar
    private static final String URL  = "jdbc:mysql://localhost:3306/ejemplo_app?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "123456";

    public static void main(String[] args) {

        // DriverManager.getConnection() establece la conexión con la BD.
        // El bloque try-with-resources cierra la conexión automáticamente al terminar.
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {

            // INSERT
            System.out.println("=== INSERT ===");
            insertar(conn, "Ana García",     true);
            insertar(conn, "Carlos López",   false);
            insertar(conn, "Laura Martínez", true);

            // SELECT
            System.out.println("\n=== SELECT todos ===");
            listar(conn).forEach(System.out::println);

            // UPDATE
            System.out.println("\n=== UPDATE (desactivar id=1) ===");
            actualizar(conn, 1, false);
            listar(conn).forEach(System.out::println);

            // DELETE
            System.out.println("\n=== DELETE (eliminar id=2) ===");
            eliminar(conn, 2);
            listar(conn).forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // INSERT: se usa PreparedStatement para parametrizar la consulta.
    // Los "?" son placeholders que se reemplazan con setString/setBoolean.
    // Esto previene ataques de SQL Injection.
    static void insertar(Connection conn, String name, boolean active) throws SQLException {
        String sql = "INSERT INTO users (name, active) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);    // reemplaza el primer "?"
        stmt.setBoolean(2, active); // reemplaza el segundo "?"
        int filas = stmt.executeUpdate(); // executeUpdate() se usa para INSERT, UPDATE y DELETE
        System.out.println(filas + " fila insertada: " + name);
    }

    // UPDATE: mismo mecanismo que INSERT.
    // executeUpdate() devuelve la cantidad de filas afectadas.
    static void actualizar(Connection conn, int id, boolean active) throws SQLException {
        String sql = "UPDATE users SET active = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setBoolean(1, active);
        stmt.setInt(2, id);
        int filas = stmt.executeUpdate();
        System.out.println(filas + " fila actualizada");
    }

    // DELETE: igual que UPDATE, se filtra por id con un placeholder.
    static void eliminar(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        int filas = stmt.executeUpdate();
        System.out.println(filas + " fila eliminada");
    }

    // SELECT: se usa executeQuery() (en lugar de executeUpdate()) porque devuelve resultados.
    // ResultSet es un cursor que recorre las filas devueltas una por una con rs.next().
    static List<User> listar(Connection conn) throws SQLException {
        List<User> usuarios = new ArrayList<>();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users");
        while (rs.next()) {
            // rs.getString(), rs.getInt(), rs.getBoolean() leen el valor de cada columna
            usuarios.add(new User(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getBoolean("active")
            ));
        }
        return usuarios;
    }
}
