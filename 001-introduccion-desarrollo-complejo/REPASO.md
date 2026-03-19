# Conceptos Previos al Ejercicio

Este documento repasa los conceptos necesarios para encarar el ejercicio de patrones de diseño.

---

## POO en Java

### Creación de clases

Una clase en Java se define con la palabra clave `class`. Los atributos representan el estado del objeto y los métodos su comportamiento. Por convención, los atributos se declaran `private` para proteger el estado interno.

```java
public class Producto {
    private String nombre;
    private double precio;
    private String categoria;

    // Constructor: se ejecuta al crear una instancia
    public Producto(String nombre, double precio, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    public void imprimirInfo() {
        System.out.println(nombre + " - $" + precio + " (" + categoria + ")");
    }
}
```

El constructor tiene el mismo nombre que la clase y no declara tipo de retorno. La palabra `this` hace referencia al objeto actual, y se usa para distinguir entre el atributo de la clase y el parámetro del constructor cuando tienen el mismo nombre.

Una clase puede tener múltiples constructores con distintos parámetros (sobrecarga de constructores):

```java
public class Producto {
    private String nombre;
    private double precio;

    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    // Constructor alternativo con precio por defecto
    public Producto(String nombre) {
        this.nombre = nombre;
        this.precio = 0.0;
    }
}
```

---

### Instanciación de objetos

Para crear un objeto se usa la palabra clave `new`, que reserva memoria y ejecuta el constructor:

```java
Producto laptop = new Producto("Laptop", 1500.0, "Electronica");
Producto remera = new Producto("Remera", 25.0, "Ropa");

laptop.imprimirInfo(); // Laptop - $1500.0 (Electronica)
remera.imprimirInfo(); // Remera - $25.0 (Ropa)
```

Cada objeto es una instancia independiente. Modificar `laptop` no afecta a `remera`.

Es importante distinguir entre la **referencia** y el **objeto**. La variable `laptop` es una referencia que apunta al objeto en memoria. Si se asigna esa referencia a otra variable, ambas apuntan al mismo objeto:

```java
Producto a = new Producto("Laptop", 1500.0, "Electronica");
Producto b = a; // b apunta al mismo objeto que a, no es una copia
```

---

### Getters y setters

Los atributos privados no son accesibles desde fuera de la clase. Para permitir la lectura y escritura controlada se usan métodos públicos llamados **getters** y **setters**:

```java
public class Producto {
    private String nombre;
    private double precio;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio < 0) throw new IllegalArgumentException("El precio no puede ser negativo");
        this.precio = precio;
    }
}
```

El setter puede incluir validaciones antes de asignar el valor, lo que no sería posible si el atributo fuera público. Los IDEs como IntelliJ IDEA y Eclipse pueden generar getters y setters automáticamente.

---

### Herencia

La herencia permite crear una clase nueva basada en una existente, reutilizando y extendiendo su comportamiento. En Java se usa la palabra clave `extends`. Una clase solo puede heredar de una única clase (herencia simple).

```java
// Clase base
public class Producto {
    private String nombre;
    private double precio;

    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public double getPrecio() { return precio; }
    public String getNombre() { return nombre; }

    public String getDescripcion() {
        return nombre + " - $" + precio;
    }
}

// Subclase: hereda de Producto y agrega comportamiento
public class ProductoElectronico extends Producto {
    private int mesesGarantia;

    public ProductoElectronico(String nombre, double precio, int mesesGarantia) {
        super(nombre, precio); // llama al constructor de la clase padre
        this.mesesGarantia = mesesGarantia;
    }

    // Sobrescribe el método de la clase padre
    @Override
    public String getDescripcion() {
        return super.getDescripcion() + " - Garantía: " + mesesGarantia + " meses";
    }
}
```

La palabra `super` se usa para referirse a la clase padre: `super(...)` llama a su constructor y `super.metodo()` llama a su implementación de un método.

La anotación `@Override` no es obligatoria, pero es una buena práctica: indica explícitamente que el método está sobrescribiendo uno de la clase padre, y el compilador verifica que efectivamente exista ese método.

---

### Polimorfismo

El polimorfismo permite tratar objetos de distintos tipos de manera uniforme a través de un tipo común (clase padre o interfaz). El método que se ejecuta se determina por el **tipo real del objeto** en tiempo de ejecución, no por el tipo de la referencia.

```java
Producto p = new ProductoElectronico("Laptop", 1500.0, 12);
System.out.println(p.getDescripcion());
// Laptop - $1500.0 - Garantía: 12 meses  ← ejecuta la versión de ProductoElectronico
```

Esto se vuelve muy poderoso cuando se trabaja con listas de objetos heterogéneos:

```java
List<Producto> productos = new ArrayList<>();
productos.add(new Producto("Remera", 25.0));
productos.add(new ProductoElectronico("Laptop", 1500.0, 12));
productos.add(new ProductoElectronico("Celular", 800.0, 6));

for (Producto p : productos) {
    System.out.println(p.getDescripcion()); // cada objeto ejecuta su propia versión
}
```

El código que itera la lista no necesita saber qué tipo concreto tiene cada elemento: simplemente llama al método y Java se encarga de ejecutar la implementación correcta.

---

### Interfaces y clases abstractas

Cuando se quiere definir un contrato que varias clases deben cumplir sin compartir implementación, se usa una **interfaz**:

```java
public interface EstrategiaEnvio {
    double calcularCosto(double peso, double distancia);
}

public class EnvioCamion implements EstrategiaEnvio {
    @Override
    public double calcularCosto(double peso, double distancia) {
        return peso * 3 + distancia * 0.5;
    }
}

public class EnvioAereo implements EstrategiaEnvio {
    @Override
    public double calcularCosto(double peso, double distancia) {
        return peso * 10 + distancia * 2;
    }
}
```

Una clase puede implementar múltiples interfaces. Si además se quiere compartir código o estado entre subclases, se usa una **clase abstracta**:

```java
public abstract class FabricaProducto {
    // Método abstracto: cada subclase debe implementarlo
    public abstract Producto crearProducto(String nombre, double precio);

    // Método concreto: compartido por todas las subclases
    public void registrarCreacion(Producto p) {
        System.out.println("Producto creado: " + p.getNombre());
    }
}

public class FabricaElectronica extends FabricaProducto {
    @Override
    public Producto crearProducto(String nombre, double precio) {
        return new ProductoElectronico(nombre, precio, 12);
    }
}
```

Una clase abstracta no se puede instanciar directamente. Solo se pueden instanciar sus subclases concretas.

| | Interfaz | Clase abstracta |
|---|---|---|
| Atributos de instancia | No | Sí |
| Constructor | No | Sí |
| Implementación de métodos | Solo `default` (Java 8+) | Sí |
| Herencia múltiple | Sí | No |

Regla práctica: usá **interfaz** cuando solo querés definir un contrato. Usá **clase abstracta** cuando querés compartir código o estado entre subclases.

---

### Miembros estáticos

Los miembros declarados con `static` pertenecen a la **clase**, no a una instancia particular. Existe una sola copia compartida por todos los objetos.

```java
public class Contador {
    private static int total = 0; // compartido por todas las instancias
    private int id;

    public Contador() {
        total++;
        this.id = total;
    }

    public static int getTotal() { return total; }
    public int getId() { return id; }
}

Contador c1 = new Contador();
Contador c2 = new Contador();
System.out.println(Contador.getTotal()); // 2 — se llama sobre la clase, no sobre un objeto
```

Los métodos estáticos se invocan sobre la clase directamente (`Contador.getTotal()`) y no pueden acceder a atributos de instancia porque no tienen `this`.

Este mecanismo es la base del patrón **Singleton**: se usa un atributo estático para guardar la única instancia de la clase y un método estático para acceder a ella.

---

### Colecciones

Java provee estructuras de datos en el paquete `java.util`. Las más usadas son `ArrayList` y `HashMap`.

**`ArrayList`:** lista ordenada que puede crecer dinámicamente.

```java
import java.util.ArrayList;
import java.util.List;

List<Producto> carrito = new ArrayList<>();
carrito.add(new Producto("Laptop", 1500.0));
carrito.add(new Producto("Mouse", 30.0));

System.out.println(carrito.size()); // 2

for (Producto p : carrito) {
    System.out.println(p.getNombre());
}

carrito.remove(0); // elimina el primer elemento
```

**`HashMap`:** mapa de clave-valor, útil para buscar objetos por identificador.

```java
import java.util.HashMap;
import java.util.Map;

Map<String, Producto> catalogo = new HashMap<>();
catalogo.put("laptop-001", new Producto("Laptop", 1500.0));
catalogo.put("mouse-001", new Producto("Mouse", 30.0));

Producto p = catalogo.get("laptop-001"); // acceso en tiempo constante
System.out.println(p.getNombre()); // Laptop

catalogo.containsKey("tablet-001"); // false
```

También se pueden combinar ambas estructuras. Por ejemplo, un mapa donde cada clave agrupa una lista de productos (útil para representar categorías):

```java
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

Map<String, List<Producto>> categorias = new HashMap<>();

// Agregar productos a cada categoría
categorias.put("electronica", new ArrayList<>());
categorias.get("electronica").add(new Producto("Laptop", 1500.0));
categorias.get("electronica").add(new Producto("Celular", 800.0));

categorias.put("ropa", new ArrayList<>());
categorias.get("ropa").add(new Producto("Remera", 25.0));

// Recorrer todas las categorías y sus productos
for (Map.Entry<String, List<Producto>> entrada : categorias.entrySet()) { // entrada: un par clave-valor del mapa (ej: "electronica" → [Laptop, Celular])
    System.out.println("Categoría: " + entrada.getKey());                  // getKey(): la clave String (ej: "electronica")
    for (Producto p : entrada.getValue()) {                                 // getValue(): la List<Producto> asociada a esa clave
        System.out.println("  - " + p.getNombre());                        // p: cada Producto dentro de esa lista
    }
}
```

La convención es declarar la variable con el tipo de la interfaz (`List`, `Map`) y asignar la implementación concreta (`ArrayList`, `HashMap`). Así, si más adelante se necesita cambiar la implementación, solo se modifica una línea.
