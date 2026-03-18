# Conceptos Previos al Ejercicio

Este documento repasa los conceptos necesarios para encarar el ejercicio de patrones de diseño.

---

## 1. POO en Java

### Creación de clases

Una clase en Java se define con la palabra clave `class`. Los atributos representan el estado del objeto y los métodos su comportamiento. Por convención, los atributos se declaran `private` para proteger el estado interno.

```java
public class Product {
    private String name;
    private double price;
    private String category;

    // Constructor: se ejecuta al crear una instancia
    public Product(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public void printInfo() {
        System.out.println(name + " - $" + price + " (" + category + ")");
    }
}
```

El constructor tiene el mismo nombre que la clase y no declara tipo de retorno. La palabra `this` hace referencia al objeto actual, y se usa para distinguir entre el atributo de la clase y el parámetro del constructor cuando tienen el mismo nombre.

Una clase puede tener múltiples constructores con distintos parámetros (sobrecarga de constructores):

```java
public class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Constructor alternativo con precio por defecto
    public Product(String name) {
        this.name = name;
        this.price = 0.0;
    }
}
```

---

### Instanciación de objetos

Para crear un objeto se usa la palabra clave `new`, que reserva memoria y ejecuta el constructor:

```java
Product laptop = new Product("Laptop", 1500.0, "Electronica");
Product remera = new Product("Remera", 25.0, "Ropa");

laptop.printInfo(); // Laptop - $1500.0 (Electronica)
remera.printInfo(); // Remera - $25.0 (Ropa)
```

Cada objeto es una instancia independiente. Modificar `laptop` no afecta a `remera`.

Es importante distinguir entre la **referencia** y el **objeto**. La variable `laptop` es una referencia que apunta al objeto en memoria. Si se asigna esa referencia a otra variable, ambas apuntan al mismo objeto:

```java
Product a = new Product("Laptop", 1500.0, "Electronica");
Product b = a; // b apunta al mismo objeto que a, no es una copia
```

---

### Herencia

La herencia permite crear una clase nueva basada en una existente, reutilizando y extendiendo su comportamiento. En Java se usa la palabra clave `extends`. Una clase solo puede heredar de una única clase (herencia simple).

```java
// Clase base
public class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() { return price; }
    public String getName() { return name; }

    public String getDescription() {
        return name + " - $" + price;
    }
}

// Subclase: hereda de Product y agrega comportamiento
public class ElectronicProduct extends Product {
    private int warrantyMonths;

    public ElectronicProduct(String name, double price, int warrantyMonths) {
        super(name, price); // llama al constructor de la clase padre
        this.warrantyMonths = warrantyMonths;
    }

    // Sobrescribe el método de la clase padre
    @Override
    public String getDescription() {
        return super.getDescription() + " - Garantía: " + warrantyMonths + " meses";
    }
}
```

La palabra `super` se usa para referirse a la clase padre: `super(...)` llama a su constructor y `super.metodo()` llama a su implementación de un método.

La anotación `@Override` no es obligatoria, pero es una buena práctica: indica explícitamente que el método está sobrescribiendo uno de la clase padre, y el compilador verifica que efectivamente exista ese método.

```java
Product p = new ElectronicProduct("Laptop", 1500.0, 12);
System.out.println(p.getDescription());
// Laptop - $1500.0 - Garantía: 12 meses
```

Nótese que la variable `p` es de tipo `Product`, pero el objeto es un `ElectronicProduct`. Java ejecuta el método de la subclase gracias al **polimorfismo**: el método que se ejecuta se determina por el tipo real del objeto, no por el tipo de la referencia.

---

### Interfaces y clases abstractas

Cuando se quiere definir un contrato que varias clases deben cumplir sin compartir implementación, se usa una **interfaz**:

```java
public interface ShippingStrategy {
    double calculateCost(double weight, double distance);
}

public class TruckShipping implements ShippingStrategy {
    @Override
    public double calculateCost(double weight, double distance) {
        return weight * 3 + distance * 0.5;
    }
}

public class AirShipping implements ShippingStrategy {
    @Override
    public double calculateCost(double weight, double distance) {
        return weight * 10 + distance * 2;
    }
}
```

Una clase puede implementar múltiples interfaces. Si además se quiere compartir código o estado entre subclases, se usa una **clase abstracta**:

```java
public abstract class ProductFactory {
    // Método abstracto: cada subclase debe implementarlo
    public abstract Product createProduct(String name, double price);

    // Método concreto: compartido por todas las subclases
    public void logCreation(Product p) {
        System.out.println("Producto creado: " + p.getName());
    }
}

public class ElectronicaFactory extends ProductFactory {
    @Override
    public Product createProduct(String name, double price) {
        return new ElectronicProduct(name, price, 12);
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

### Getters y setters

Los atributos privados no son accesibles desde fuera de la clase. Para permitir la lectura y escritura controlada se usan métodos públicos llamados **getters** y **setters**:

```java
public class Product {
    private String name;
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) throw new IllegalArgumentException("El precio no puede ser negativo");
        this.price = price;
    }
}
```

El setter puede incluir validaciones antes de asignar el valor, lo que no sería posible si el atributo fuera público. Los IDEs como IntelliJ IDEA y Eclipse pueden generar getters y setters automáticamente.

---

## 2. Principios de Diseño Orientado a Objetos

Estos principios son la base que justifica por qué existen los patrones de diseño. Sin entenderlos, los patrones parecen recetas sin motivo.

### Programar hacia interfaces, no implementaciones

En lugar de depender de una clase concreta, el código debe depender de una abstracción (interfaz o clase abstracta). Esto permite cambiar la implementación sin modificar el código que la usa.

**Mal:** el código queda atado a `PaypalProvider`.
```java
PaypalProvider payment = new PaypalProvider();
payment.processPayment(100.0);
```

**Bien:** el código solo conoce el contrato `PaymentProvider`.
```java
PaymentProvider payment = new PaypalProvider();
payment.processPayment(100.0);
```

La diferencia parece pequeña, pero si mañana se cambia `PaypalProvider` por `MercadoPagoProvider`, en el segundo caso solo se modifica una línea. En sistemas grandes, este principio evita cambios en cascada.

---

### Composición sobre herencia

La herencia crea una relación rígida entre clases. La composición —tener una referencia a otro objeto— es más flexible porque permite cambiar el comportamiento en tiempo de ejecución.

**Con herencia:** para agregar un medio de envío hay que crear una subclase por cada combinación posible.
```
Order
├── OrderWithAirShipping
├── OrderWithTruckShipping
└── OrderWithSeaShipping
```

**Con composición:** `Order` contiene una referencia a `ShippingStrategy` y se puede asignar cualquier implementación:
```java
public class Order {
    private ShippingStrategy shippingStrategy;

    public Order(ShippingStrategy strategy) {
        this.shippingStrategy = strategy;
    }

    public double calculateShipping(double weight, double distance) {
        return shippingStrategy.calculateCost(weight, distance);
    }
}
```

Agregar un nuevo medio de envío no requiere modificar `Order`, solo crear una nueva clase que implemente `ShippingStrategy`.

---

### Principio Open/Closed

> *"Las clases deben estar abiertas a la extensión pero cerradas a la modificación."*

Una clase está **cerrada a la modificación** cuando su código existente no necesita cambiar para agregar nueva funcionalidad. Está **abierta a la extensión** cuando se puede ampliar su comportamiento agregando nuevas clases.

**Ejemplo:** si el cálculo de envío está hardcodeado con `if/else`, agregar un nuevo tipo obliga a modificar la clase existente:
```java
// Viola Open/Closed
public double calculateShipping(String type, double weight) {
    if (type.equals("avion")) return weight * 10;
    else if (type.equals("camion")) return weight * 3;
    // Para agregar "barco" hay que editar este método
}
```

Con Strategy, agregar `SeaShipping` no toca ninguna clase existente:
```java
public class SeaShipping implements ShippingStrategy {
    public double calculateCost(double weight, double distance) {
        return weight * 1.5 + distance * 0.2;
    }
}
```

---

### Principio de Responsabilidad Única

> *"Una clase debe tener una sola razón para cambiar."*

Si una clase maneja tanto la lógica de negocio como la creación de objetos y la persistencia, cualquier cambio en cualquiera de esas áreas la afecta. Separar responsabilidades hace el código más fácil de mantener y de testear.

En el ejercicio:
- `ProductFactory` tiene una sola responsabilidad: crear productos.
- `ShippingStrategy` tiene una sola responsabilidad: calcular el costo de envío.
- `Order.Builder` tiene una sola responsabilidad: construir una orden válida.

---

### Qué son los patrones de diseño

Un patrón de diseño es una solución probada a un problema recurrente de diseño. No es código que se copia directamente, sino una guía conceptual que se adapta al contexto particular.

Se clasifican en tres grupos:

| Tipo | Propósito | Ejemplos en este ejercicio |
|------|-----------|---------------------------|
| **Creacionales** | Cómo se crean los objetos | Singleton, Builder, Factory Method |
| **Estructurales** | Cómo se componen las clases | Bridge |
| **De comportamiento** | Cómo se comunican los objetos | Strategy |

El sitio [Refactoring Guru](https://refactoring.guru/es/design-patterns) tiene explicaciones y ejemplos en Java para cada patrón.

---

## 3. Arquitectura en Capas

### Qué es la separación de concerns

"Concern" es cualquier responsabilidad o preocupación del sistema: mostrar información, aplicar reglas de negocio, guardar datos. La separación de concerns propone que cada parte del código se ocupe de una sola de estas responsabilidades.

Cuando una sola clase hace todo —muestra por pantalla, calcula precios y guarda en memoria— cualquier cambio pequeño puede romper funcionalidad no relacionada. Las capas son la forma estructural de aplicar este principio.

### Las tres capas

```
presentacion/   →   logica/   →   datos/
```

Las dependencias fluyen en una sola dirección: la presentación conoce la lógica, la lógica conoce los datos. Nunca al revés.

| Capa | Responsabilidad | Qué va ahí |
|------|----------------|------------|
| **Presentación** | Interactuar con el usuario | Leer input, mostrar resultados, clase `Main` |
| **Lógica** | Reglas de negocio y patrones | Factories, strategies, builders, cálculos |
| **Datos** | Representar y almacenar información | Clases de dominio (`Product`, `Order`), repositorios en memoria |

### Ejemplo de flujo entre capas

```java
// presentacion/Main.java
ShoppingCart cart = ShoppingCart.getInstance();           // lógica
cart.addProduct(new ElectronicaFactory().createProduct("Laptop", 1500)); // lógica + datos

ShippingStrategy envio = new TruckShipping();            // lógica
Order order = new Order.Builder()
    .withCart(cart)
    .withShippingStrategy(envio)
    .build();                                            // lógica

System.out.println("Total: " + order.getTotal());       // presentación
```

`Main` solo coordina: llama a la lógica, recibe resultados y los muestra. No calcula nada ni conoce los detalles de implementación de cada patrón.

### Por qué importa en este ejercicio

Aunque los datos están en memoria y no hay una base de datos real, la separación en capas sigue siendo válida. Prepara el código para que en el futuro se pueda:

- Cambiar la presentación de consola a una interfaz web sin tocar la lógica.
- Reemplazar el almacenamiento en memoria por una base de datos sin tocar la presentación.
- Testear la lógica de negocio de forma aislada, sin depender de cómo se muestran los datos.
