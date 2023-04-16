<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [Notas del ejercicio 06 - JDBC](#notas-del-ejercicio-06---jdbc)
- [Incorporación del JConnector](#incorporaci%C3%B3n-del-jconnector)
- [Creación de base de datos](#creaci%C3%B3n-de-base-de-datos)
- [Creación de usuario](#creaci%C3%B3n-de-usuario)
- [Driver y packages](#driver-y-packages)
- [Menú principal](#men%C3%BA-principal)
- [Método devolverProductos()](#m%C3%A9todo-devolverproductos)
  - [Hashmap](#hashmap)
  - [Métodos utilizados para un HashMap](#m%C3%A9todos-utilizados-para-un-hashmap)
  - [Retornando un HashMap](#retornando-un-hashmap)
  - [Mostrando los datos](#mostrando-los-datos)
  - [listarProductos()](#listarproductos)
- [Método mostrarProducto()](#m%C3%A9todo-mostrarproducto)
  - [devolverProducto(int id)](#devolverproductoint-id)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# Notas del ejercicio 06 - JDBC

A continuación veremos como resolví el ejercicio 6a

**Ejercicio 6a**

Resolver:
1. Crear una clase Product que contenga los siguientes atributos: id:int, name:String, description:String, price:double, stock: int, shippingIncluded: boolean
2. Crear la base de datos javaMarket con la tabla Product para almacenar objetos de la clase Product con id autoincremental y los demás atributos según corresponda.
3. Cargar al menos 3 registros en la base de datos para realizar el desarrollo.

Crear una app que se conecte a la DB y permita:
1. list. Listar todos los productos indicando: id, name y price.
2. search. Mostrar los datos completos de un Product. El usuario debe ingresar un id y la app debe mostrar todos los datos de ese articulo.
3. new. Cargar nuevos Products (sin id) e insertarlos en la tabla. Durante la inserción debe recuperar el id generado en la BD y mostrarlo al usuario (no puede realizar un nuevo select)
4. delete. Eliminar un Product. El usuario debe ingresar un id y la app debe eliminar el registro
5. update. Modificar un Product. El usuario debe ingresar un id y la app debe mostrar los datos actuales y preguntar los nuevos datos. Finalmente debe aplicar el cambio en la base de datos.

Restricciones:
* Todo el manejo de la base de datos debe hacerse en una clase que no sea el programa principal ni la clase Product.
* La clase Product no puede contener código para leer o escribir datos de/a la interfaz, ni de o hacia la DB.
* Cada operación debe realizarse en un método (puede invocar a otros si desea reusar el código).
* Cada método solo puede recibir y/o devolver objetos Product o una colección (array, ArrayList, LinkedList, etc) de objetos Product. Por ejemplo el listar no necesita recibir nada y devuelve una colección de Product, el mostrar recibe un bbjeto Product con el id y devuelve uno completo.
* El archivo .jar del conector a la DB debe ubicarse localmente en el proyecto.

Sugerencias:
* No preocuparse por validaciones.
* Hacer un menu simple que itere y pregunte por las opciones requeridas (agregar una opción para salir)
* Primero cree la clase lógica, luego el menú sin realizar acciones y finalmente desarrollar las acciones una a una por el orden indicado, ya que se han listado en orden de dificultad.


# Incorporación del JConnector

Recordemos que tenemos que utilizar el JConnector proporcionado por mysql. Buscamos en la carpeta JConnector el .jar. Esta carpeta se encuentra el la ruta de instalación, en mi caso la default.

Creamos la carpeta lib en nuestro proyecto de Java, y le pegamos el .jar

Como es solo para escritorios o web -> click derecho add to path

# Creación de base de datos

Dejaré aquí el sql que realicé para crear la base de datos con su tabla producto ya cargada.

```sql
CREATE DATABASE  IF NOT EXISTS `javamarket`

USE `javamarket`;

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(60) NOT NULL,
  `product_description` varchar(255) NOT NULL,
  `product_price` double NOT NULL,
  `product_stock` int NOT NULL,
  `product_shipping_included` tinyint NOT NULL,
  PRIMARY KEY (`id_product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
--
-- Dumping data for table `product`
--
INSERT INTO `product` VALUES 
(1,'Peluche de Luffy','Peluche 100% Algodón muy suave al tacto',1500,5,0),
(2,'Camiseta de KOI','Camiseta del equipo competitivo de e-sports KOI hecha de nylon',3500,10,1),
(3,'Gorro de Teemo','Gorro hecho de algodón del personaje Teemo de League of Legends',2500,15,1);
```

# Creación de usuario

Creamos un usuario en mySQL para que pueda acceder a la base de datos con los permisos de select, insert, update y delete

```sql
create user javamarketuser@'%' identified by 'laureano';
grant select, insert, update, delete on javamarket.* to javamarketuser@'%';
```

Para ver la información de todos tus usuarios creados desde una instancia root

```sql
SELECT * FROM mysql.user;
```

Para ver las contraseñas que estan hasheadas podemos utilizar PASSWORD() o SHA1() en mysql



# Driver y packages

Inicialización del Driver en el main para poder realizar las conexiones correspondientes.

En un **paquete** vamos a poner todas las entidades que manejarán la **lógica del negocio** y en otro pondremos nuestra **ui** con su main

Recordemos que debemos importar <b> importar java.sql.*</b> y tambien el paquete con las **entidades**

<em>A mi me salió error y lo solucioné borrando module_java</em>

```java
package ui;

import java.util.LinkedList;
import java.sql.*;
import entities.*;

public class Main {

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } 
        catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Veo  si hago un menú o simplemente llamo los 5 metodos que me pide el enunciado

    }
}
```

En el paquete de **entidades** vamos a crear una clase llamada product con sus respectivos atributos, setters y getters.

```java
package entities;

public class Producto {

	private int id;
	private String name;
	private String description;
	private double price;
	private int stock;
	private boolean shippingIncluded;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public boolean isShippingIncluded() {
		return shippingIncluded;
	}
	public void setShippingIncluded(boolean shippingIncluded) {
		this.shippingIncluded = shippingIncluded;
	}
}
```

# Menú principal

Crearemos un menú principal para que luego el test de si funciona cada uno sea chill.

Importaremos la clase **Scanner** y lo resolveremos con un switch y un while.

A medida que vayamos creando los metodos lo iremos poniendo en su respectivo número.

```java

    public static void main(String[] args) {
    	
    	Scanner lector = new Scanner(System.in);
    	
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } 
        catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
  
        String op = "";
        while(!op.equals("6")) {
        	
        	System.out.println("[1]. Listar productos (id,name,price)\n[2]. Buscar producto por id(id)\n[3]. Cargar Producto (*)\n[4]. Eliminar Producto (id)\n[5]. Modificar producto (*)\n[6]. Salir");
        	op = lector.nextLine();
        	
        	switch (op) {
        	
        	case "1":
        		break;
        		
        	case "2":
        		break;
        		
        	case "3":
        		break;
        	
        	case "4":
        		break;
        		
        	case "5":
        		break;
        			
        	}
        }
        System.out.println("Fin");
    }
```

# Método devolverProductos()

Estaba desarrollando este método y recorde al profe en la clase de diseño de sistemas que nosotros al realizar los diagramas de secuencia siguiendo un patrón MVC obviavamos la idea de que el controlador debía crear un nuevo objeto contenedor para asignar la entidad y el mensaje -> simplemente retornabamos (mensaje, entidad).

Cuando el controlador le devuelve a la UI que puede que sea un mensaje de error o una entidad lo que se crea es un contenedor para ya que **solo se puede devolver 1 solo objeto**

En java no se permite algo como un 'return Object entidad, String mensaje'

Por lo que quiero implementar **algo similar**, para ello encontre una estructura de datos llamada hashmap.


## Hashmap

ChatGPT Texto

HashMap es una clase en Java que implementa la interfaz Map, que representa una colección de pares clave-valor, donde cada clave es única y se utiliza para acceder a su valor asociado. HashMap se basa en una tabla de dispersión (hash table) para implementar esta funcionalidad.

1. Las claves y los valores en un HashMap pueden ser de cualquier tipo de objeto, pero las claves deben ser únicas. 

2. HashMap no mantiene un orden específico de los elementos. Pero se puede implementar.

3. HashMap permite null como valor y también permite una sola clave null

4. HashMap proporciona un acceso rápido a los valores a través de las claves utilizando el concepto de hash code. Esto hace que las operaciones de búsqueda, inserción y eliminación sean eficientes en términos de tiempo promedio de ejecución, en promedio O(1) para la mayoría de las operaciones.

5. HashMap no es sincronizado, lo que significa que no es seguro para su uso en hilos de ejecución concurrentes. Si necesitas utilizar HashMap en un entorno multi-hilo, debes sincronizar su acceso manualmente o utilizar una implementación sincronizada, como Hashtable o ConcurrentHashMap.

*La notación Big O es una notación matemática que nos sirve para poner nota a la velocidad de procesamiento de un algoritmo atendiendo a cómo se comporta conforme aumenta el tamaño del trabajo a procesar, por lo que nos sirve para clasificar la eficacia de los mismos.*
Link: https://es.wikipedia.org/wiki/Complejidad_temporal

## Métodos utilizados para un HashMap

En un HashMap en Java, se pueden utilizar varios métodos para realizar operaciones de inserción, búsqueda, eliminación y otros manipulaciones de datos. Algunos de los métodos más comunes utilizados en un HashMap son los siguientes:

1. -> .put(K key, V value): Este método se utiliza para insertar un par clave-valor en el HashMap. Toma dos argumentos, la clave (key) y el valor (value) a ser insertados en el mapa.

2. -> .get(Object key): Este método se utiliza para obtener el valor asociado a una clave específica en el HashMap. Toma un argumento, la clave (key) para la cual se desea obtener el valor correspondiente, y devuelve el valor asociado a esa clave, o null si la clave no está presente en el mapa.

3. -> .remove(Object key): Este método se utiliza para eliminar un par clave-valor del HashMap basado en la clave proporcionada. Toma un argumento, la clave (key) del par clave-valor que se desea eliminar, y devuelve el valor asociado a esa clave antes de eliminarla, o null si la clave no está presente en el mapa.

4. -> .containsKey(Object key): Este método se utiliza para verificar si el HashMap contiene una clave específica. Toma un argumento, la clave (key) que se desea verificar, y devuelve true si la clave está presente en el mapa, o false en caso contrario.

5. -> .size(): Este método se utiliza para obtener el número de pares clave-valor en el HashMap. No toma argumentos y devuelve un entero que representa el número de elementos en el mapa.

6. -> .isEmpty(): Este método se utiliza para verificar si el HashMap está vacío, es decir, si no contiene ningún par clave-valor. No toma argumentos y devuelve true si el mapa está vacío, o false en caso contrario.

7. -> .clear(): Este método se utiliza para eliminar todos los pares clave-valor del HashMap, dejándolo vacío. No toma argumentos y no devuelve ningún valor.

## Retornando un HashMap

Bueno ahora sabiendo lo que es un HashMap vamos a crear nuestro método devolverProductos()

Sabemos que va a retornar un hashmap por lo que el manejo de como se mostrará la información se encontrará dentro del case. Eso lo haremos al final.

Como siempre. Creamos una conexión, un Statement, ejecutamos el Statement y nos devuelve un ResultSet. Al ResultSet lo iteramos y vamos creando por cada iteración un objeto java que en este caso es el objeto Producto y luego seteamos los atributos necesarios con sus setters y como parámetros le enviamos rs.getTipoDato(nombre columna).

Así logramos setear todos los atributos necesarios al Producto y por último lo agregamos a la linked list creada previamente con el Producto ya seteado.

En la siguiente iteración ocurre lo mismo hasta que no haya mas registros.

Ya finalizando agregamos al mapa la clave-valor "productos" - Producto y "error" - null

La misma idea si ocurre un error, seteamos el mensaje "error" - mensaje y "productos" - null

**No olvidemos liberar los recursos y cerrar la conexión**

**En ambas situaciones devolvemos un solo objeto**

```java
// Retornará un objeto Map que contiene un String y un Object
public static Map<String, Object> devolverProductos() {
    	
      // Creamos un objeto mapa del tipo Map y le asignamos un nuevo hashmap
    	Map<String, Object> mapa = new HashMap<>();
    	LinkedList<Producto> productos = new LinkedList<>();
    	Connection conn = null;
    	
    	try {
    		//crear conexión
    		conn = DriverManager.getConnection("jdbc:mysql://localhost/javamarket", "javamarketuser","laureano");
    		
    		//creación y ejecución
    		PreparedStatement st = conn.prepareStatement("select * from product");
    		ResultSet rs = st.executeQuery();
    		
    		//mapear resultset a objeto java
    		
    		while(rs.next()) {
    			Producto p = new Producto();
    			
    			p.setId(rs.getInt("product_id"));
    			p.setName(rs.getString("product_name"));
    			p.setPrice(rs.getDouble("product_price"));
    		
    			productos.add(p);
    		}
    		
    		// Asignamos la lista de productos al map
    		mapa.put("productos", productos);
        mapa.put("error", null)

        //Liberamos los recursos y cerramos la conexón
    		if(rs!=null){rs.close();}
    		if(st!=null){st.close();}

    		conn.close();
    	}
    	catch (SQLException ex) {
    		String mensaje = "SQLException: " + ex.getMessage() + " SQLState: " + ex.getSQLState() + "VendorError: " + ex.getErrorCode();

        // Si ocurre algún error de conexión por ejemplo
        //Asignamos al listado como null y un mensaje de error
    		mapa.put("productos", null);
    		mapa.put("error", mensaje);
    		return mapa;
    	}
    	
      // Devolvemos el mapa
    	return mapa;
    }
```

## Mostrando los datos

Para probar el funcionamiento del código anterior en nuestro **main** en el apartado del switch correspondiendo con el 1.

Primero lo probé con el servicio de mysql funcionando y salió correctamente el mensaje por consola "Imprimo todos los datos de la linked list"

Luego detuve el servicio de mysql y también funcionó como debía mostrandome el mensaje de error.

Para quien no sepa detener el servicio, en windows haz lo siguiente.

Presiona la tecla windows, escribe servicios, y busca el que diga mysql, click derecho -> detener.

Así lograremos que al abrir la conexión no logre conectarse ocasionando un error.

No se olviden de luego probarlo si quieren que este funcionando deben iniciarlo nuevamente, click derecho -> iniciar.

Entonces, si la opción elegida es la 1 crearemos una variable local que almacenará un objeto Map con el valor que retorna deolverProductos();

```java
switch (op) {
        	
        	case "1": 
        			Map map = devolverProductos();
        			if(map.get("error") != null) {
        				System.out.println(map.get("error"));
        			}
        			else {
        				System.out.println("Imprimo todos los datos de la linked list");
        			}
```

Ahora desarrollaremos como mostrar la información.

Previamente meteremos el apartado de mostrar la info en un método porque sino cuando completemos los 4 métodos restantes el main será un choclo de información visual.

Por lo que encerraremos todo el código de mostrar la información en listarProductos();

```java
case "1": listarProductos(); 
  break;

case "2":
  break; 
// . . .
```

## listarProductos()

Modificaremos el siguiente código para lograr la correcta visualización de los datos.

Basicamente si el mensaje de error no se encuentra nulo, muestro el mensaje. Si el mensaje de error se encuentra nulo es porque existe una linked list con todos los productos
```java
 private static void listarProductos() {	
	 
	 Map map = devolverProductos();
		if(map.get("error") != null) {
			System.out.println(map.get("error"));
		}
		else {
			System.out.println("Imprimo todos los datos de la linked list");

      for(/*para cada producto en productos*/){
        
      }

		}	
	}
  ```

  Desarrollando el código he decidido lo siguiente.

  Primero, cuando el metodo listarProductos() es llamado se creará un Objeto Map que contendrá como clave valor (String, Object)

  A mi se me ocurrio que debía ser Object porque a veces lo que retorna es un String.

  Quizá otra solución que no sea un hashmap es crear un objeto arbitrario donde por un lado se setee el iterable y por el otro el mensaje. y no tendría que poner Object y creo que ni realizar el casteo.

  > El casting (o casteo) en Java se refiere a la conversión explícita de un valor de un tipo de dato a otro tipo de dato compatible. En otras palabras, el casting permite cambiar el tipo de dato de una variable de un tipo a otro, siempre y cuando la conversión sea válida según las reglas de tipos de datos de Java.
  Es importante tener en cuenta que el casting puede causar la pérdida de datos o la truncación de valores si no se realiza correctamente. Por lo tanto, es necesario tener cuidado al realizar casting en Java y asegurarse de que la conversión sea válida y segura en cada caso.

  Una vez casteado es asignado a una linked list llamada productos, luego con un foreach voy pidiendo los datos y agregandolo a una lista vacía declarada previamente.

  Y así finaliza este método

  ```java
  // Método para listar id, name y price de todos los productos
    private static void listarProductos() {	
   	 
    	Map<String, Object> map  = devolverProductos();

   		if(map.get("error") != null) {
   			System.out.println(map.get("error"));
   		}
   		else {
   			
        // casteo para que sea linkedlist ya que el map decia STRING, OBJECT
   			LinkedList<Producto> productos = (LinkedList<Producto>) map.get("productos");
   			
   			String listado = "";
   			
   			for(Producto producto : productos){
   				
   				listado += "ID: " + producto.getId() + " Nombre: " + producto.getName() + " Precio: " + producto.getPrice() + "\n\n";
   			}
        
   			System.out.println(listado);
   		}	
   	}
```
# Método mostrarProducto()

Esta vez nos anticiparemos para encerrar el código que mostrará el main.

Crearemos dos metodos.
1. mostrarProducto()
2. devolverProducto(int id)

Con la idea de que el primero es para mostrar la información como el caso anterior.

Y en segundo es el encargado de abrir una conexión, recuperar el dato y devolverle el map con el objeto Producto o String.

Debemos pasarle el lector xq así funciona...

Así nos queda el main

```java
case "2": mostrarProducto(lector);
        break;
```

Nuestro metodo mostrarProducto queda definido así.
Sencillo de interpretar -> Le pregunta un ID -> ese ID es mandado al metodo devolverProducto -> Puede devolver un mensaje de error o un Producto

Si el mensaje de error NO es nulo -> Imprime el mensaje de error
Si el mensaje de error es nulo -> Imprime los datos del objeto

```java
private static void mostrarProducto(Scanner lector) {
		
	System.out.println("Ingrese un ID de Producto");
	int id = Integer.parseInt(lector.nextLine());
	 
	Map <String, Object> map = devolverProducto(id);
	 
	if(map.get("error") != null) {
		 System.out.println(map.get("error"));
	}
	else {
		Producto producto = (Producto) map.get("producto");
		System.out.println("ID: " + producto.getId() + " Nombre: " + producto.getName() +" Stock: " + producto.getStock() + " Precio: " + producto.getPrice() + " Descripcion: " + producto.getDescription() + " Envío: " + producto.isShippingIncluded());
	}
}
```

Una vez pensado este método nos iremos a desarrollar devolverProducto(id);



## devolverProducto(int id)

Este método será realizado con PreparedStatement pero no es realmente necesario ya que un entero nunca va a tener caracteres especiales por lo que no habría que preocuparse. Habría que preguntar si es buena práctica.

Este método será el encargado de realizar una conexión, setear el objeto y devolver el objeto Map.

Creo objeto map<String,Object>
Connection conn = null

Dentro de un try 
1. Abro conexión
2. Creo un PreparedStatement para asignar comodamente el parámetro utilizado
3. Asigno el parametro con un setInt()
4. Le asigno el resultset obtenido de la query
5. Creo un objeto Producto
6. Lo relleno con los setters y pidiendole los datos al resultset
7. Cierro la conexión
8. Agrego el Producto al mapa y el mensaje como null

Dentro del catch
1. Asigno el valor a mensaje
2. Agrego el mensaje al mapa y el producto como null


Por ultimo se retorna el mapa con sus respectivos valores. Y al probarlo vemos que funciona correctamente con el código anterior

```java
private static Map<String, Object> devolverProducto(int id) {
	
	Map<String, Object> mapa = new HashMap();
	Connection conn = null;
	
	try {
		//abro conexión
		conn = DriverManager.getConnection("jdbc:mysql://localhost/javamarket","javamarketuser","laureano");
		
		//Creo statement y ejecuto query
		PreparedStatement st = conn.prepareStatement("select * from product where product_id=?");
		
		//Defino el parámetro
		st.setInt(1,id);
		
		//Ejecuto query y obtengo resultset
		ResultSet rs = st.executeQuery();
		
		//Mapeo, puedo utilizar if si es solo un valor, con el while da lo mismo
		
		Producto p = new Producto(); // Lo pongo afuera ya que es 1
		
		if(rs.next()) {
			p.setId(rs.getInt("product_id"));
			p.setName(rs.getString("product_name"));
			p.setPrice(rs.getDouble("product_price"));
			p.setStock(rs.getInt("product_stock"));
			p.setDescription(rs.getString("product_description"));
			p.setShippingIncluded(rs.getBoolean("product_shipping_included"));
		}
		
		// Liberar recursos
		
		if(rs!=null) {rs.close();}
		if(st!=null) {st.close();}
		
		conn.close();
			
		// lo agrego al mapa
		mapa.put("producto", p);
		
	}
	catch(SQLException ex) {
		String mensaje = "SQLException: " + ex.getMessage() + " SQLState: " + ex.getSQLState() + "VendorError: " + ex.getErrorCode();
		mapa.put("producto", null);
		mapa.put("error", mensaje);
		return mapa;
	}
	
	return mapa;
}
```

