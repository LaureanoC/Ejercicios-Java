# Introducción del contenido

Aprenderemos a conectarnos una base de datos en java.

Desarrollaremos 3 métodos.

recuperarTodos() -> recupera la información de todas las personas

recuperarPorParametro() -> simularemos la entrada de un usuario para buscar la persona por id

insertarNuevo() -> simularemos la entrada del usuario agregando una nueva persona en la tabla Persona de la base de datos.

El primer apartado fue proporcionado por la catedra de Java y luego vienen mis notas.


<b>Notas del primer video de introducción JDBC UTN FRRO</b>

<em>Link del video:</em> https://www.youtube.com/watch?v=d8CnoFm0G_s&t=42s

La tabla de contenidos la hice desastrosa asi que cuando pongo "Metodo nombremetodo()" significa que está terminado y que todo el progreso armando el método se encuentra por detrás.
<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [JDBC - Pasos para setear y configurar jdbc](#jdbc---pasos-para-setear-y-configurar-jdbc)
  - [Pasos para preparar la base de datos](#pasos-para-preparar-la-base-de-datos)
  - [En casos de falla](#en-casos-de-falla)
  - [Pasos para setear el conector](#pasos-para-setear-el-conector)
- [Introducción a mis notas](#introducci%C3%B3n-a-mis-notas)
  - [1. Creación del objeto Driver de conexión.](#1-creaci%C3%B3n-del-objeto-driver-de-conexi%C3%B3n)
  - [2. Creamos una conexion](#2-creamos-una-conexion)
  - [3. Creación del Statement y ejecución de la query](#3-creaci%C3%B3n-del-statement-y-ejecuci%C3%B3n-de-la-query)
  - [4. Mapeo de ResultSet a Objeto Java](#4-mapeo-de-resultset-a-objeto-java)
    - [Conexión abierta](#conexi%C3%B3n-abierta)
  - [5. Cerrar conexión](#5-cerrar-conexi%C3%B3n)
  - [Resumen hasta ahora](#resumen-hasta-ahora)
- [Método recuperarTodos()](#m%C3%A9todo-recuperartodos)
- [Manejar los inputs del usuario](#manejar-los-inputs-del-usuario)
  - [No hagas esto](#no-hagas-esto)
- [PreparedStatement](#preparedstatement)
  - [Y entonces utilizo el Statement común?](#y-entonces-utilizo-el-statement-com%C3%BAn)
- [Método recuperarPorParametro()](#m%C3%A9todo-recuperarporparametro)
- [Insertando una nueva persona](#insertando-una-nueva-persona)
  - [No hagas esto](#no-hagas-esto-1)
  - [Haz esto](#haz-esto)
    - [RETURN_GENERATED_KEYS](#return_generated_keys)
- [insertarNuevo()](#insertarnuevo)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->






# JDBC - Pasos para setear y configurar jdbc

## Pasos para preparar la base de datos
* Descargar e instalar mysql o uno de sus forks (mysql 8 o 5.7, mariadb 10.3, percona server 8 o 5.7)
* Crear la base de datos javaTest con un manager o usando el siguiente código

```sql
CREATE DATABASE /*!32312 IF NOT EXISTS*/ `javaTest` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `javaTest`;

DROP TABLE IF EXISTS `persona`;

CREATE TABLE `persona` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tipo_doc` varchar(255) NOT NULL,
  `nro_doc` int NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `apellido` varchar(255) NOT NULL,
  `estaHabilitado` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `persona` VALUES (1,'dni',11111111,'John','Doe',1),(2,'dni',22222222,'Juan','Perez',0);
```

* Crear el usuario para conectarse

```sql
create user java@'%' identified by 'himitsu';

grant select, insert, update, delete on javaTest.* to java@'%';
```

## En casos de falla
En caso que fallara la creación de la db o el usuario puede eliminarlps con el siguiente código y volver a intentarlo.

```sql
DROP DATABASE `javaTest`;
DROP USEr java@'%';
```

## Pasos para setear el conector
1. Descargar Connector/J platform independent y extraer el contenido.
2. Crear el directorio lib dentro del proyecto al mismo nivel de src, eligiendo la opción source folder. Atención no usar folder, usar source folder.
3. Copiar mysql-connector-java-8.0.20.jar dentro de lib/
4. Botón derecho sobre el mysql-connector-java-8.0.20.jar hacer click en Build Path -> Add to build path 
5. Si usa git, agregar el .jar del conector como excepción al .gitignore, (adaptarlo al directorio) por ejemplo en este proyecto

#Whitelist connector/j#
!/ejemplos/Ej4JDBC/lib/*.jar

# Introducción a mis notas

En eclipse crearemos la carpeta lib, donde pondremos nuevas librerias
Descargamos el JConnector de mySQL (puede ser desde el workbench)

Vamos a pegar el archivo JAR en lib.

Si la app va a hacer de consolo y de escritorio -> botón derecho, build path, add to build path.

Nos aparecerá en las referenced libraries

<b>El JConector de Java es el conjunto de librerias disponibles que tenemos para trabajar con la base de datos.</b>

Vamos a tener que <b>importar java.sql.*;</b> Es decir, tooooodos los metodos para trabajar con la base  de datos.

Tenemos que crear el objeto (un conector) para hacer las conexiones con la base de datos.

## 1. Creación del objeto Driver de conexión.

El profe como introducción lo creo en el main.

```java

public static void main(String[] args){

    //Registrar el conector
    try{
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
    }
    catch(InstantiationException | IllegalAccessException | ClassNotFoundException e){
        e.printStackTrace();
    }

    recuperarTodos();
    
    recuperarPorParametro();
    
    insertarNuevo();

}


```
El .newInstance() es porque hay implementaciones en la maquina virtual de java que funciona mal (glitch) y el newInstance soluciona ese problema.

Una vez registrado el Driver de conexión

En un metodo llamado recuperarTodos() que tiene como objetivo recuperar todas las personas de la base de datos de la tabla persona

## 2. Creamos una conexion

Ya con nuestro Driver de conexión creado (nuestro conector encargado de manejar las conexiones a la base de datos) abriremos una nueva conexión para ejecutar las queries.

Y tambien crearemos una lista linkeada de personas para poner a todas las personas.

Lo metemos en un <b>try catch</b> para manejar las excepciones (ejemplo no se conecta)
```java
Connection conn = null;
try{
        //Crear conexión
        conn = DriverManager.getConnection("jdbc:mysql://localhost/javatest","java", "himitsu");
} catch {
    //Resto del código
}
``` 

El string que se encuentra como parámetro se llama <b>Connection String.</b>

Tambien se pudo haber puesto

"jdbc:mysql://localhost/javatest?user=?java&password=himitsu"

<b>El connection string le brinda a la libreria toda la información necesaria para poder conectarse a la base de datos.</b>

("jdbc:mysql://localhost/javatest","java", "himitsu");

<em>Va a usar una conexion jdbc a mysql, le digo donde está el servidor con su puerto localhost:3306 (3306 es el default de mysql), luego le digo el nombre de la base de datos: javatest, por ultimo el nombre de usuario "java" y la contraseña "himitsu".</em>

<b>
Una vez que se ejecuta el DriverManager.getConnection("jdbc:mysql://localhost/javatest","java","himitsu");

Se crea y queda abierta una conexión.

Por lo tanto es importante cerrarla.</b>
 
<em>Las conexiones abiertas a las bases de datos son un ... memory dig?(preguntar al profe) porque se llena la memoria del servidor si nunca se cierra por eso hay que liberarlas (conn.close())</em>

Una vez que se abre la conexión.

## 3. Creación del Statement y ejecución de la query

Las Statements o consultas o queries a bases de datos son utilizadas para insertar,modificar, retornar, eliminar.

Están las Statements comunes y las statement preparadas.

Por ahora comenzaremos con las comunes, creo que las preparadas si mal no recuerdo era para prevenir las inyecciones de SQL.

<b>Creamos una consulta</b>

```java
//Crea consulta
Statement stmt = conn.createStatement();

//Ejecución de query y se guarda un ResultSet
ResultSet rs= stmt.executeQuery("select * from persona");
```
Se pasa la sentencia sql

<b>stmt.executeQuery</b> -> Se ejecuta la query y esperaresultados
<b>stmt.executeUpdate</b> -> Ejecuta una modificación de datos o eliminación devuelve la cantidad de filas que fueron afectadas por la operación

<b>stmt.execute</b> -> no nos devuelve nada, solo un booleano si se ejecutó o no

<b>En un primer momento el ResultSet es una estructura de memoria que es como una tabla y un apuntador que dice cual es el registro actual.</b>

rs al ejecutarse la sentencia está apuntando antes del primer registro posición -1

## 4. Mapeo de ResultSet a Objeto Java

La idea es pasar la estructura de memoria de ResultSet a un objeto nuestro Java.

Al ejecutar rs.next se mueve la posición y apunta al registro 0.

Si existe algo en esa posición devuelve TRUE

Por eso el while(rs.next())

Los ResultSet son solo de lectura y fast forwards.

Tengo acceso a la información del ResultSet a traves de <b>rs.get{tipoDato}></b>
<em>
.getInt
.getString
.getBoolean</em>
.getTimeStamp
.getDouble

```java
while(rs.next()){

Persona p = new Persona();
Documento d = new Documento();
p.setDocumento(d);
	
p.setId(rs.getInt("id"))
d.setNro(rs.getString("nro doc"));
d.setTipo(rs.getString("nombre"));
p.setApellido(rs.getString("apellido"));
p.setHabilitado(rs.getBoolean("estaHabilitado"));
	
personas.add(p);
		
}
```


### Conexión abierta

<b>El objetivo no es mover los ResultSet de un lado a otro por dos grandes motivos.</b>

Primero porque no quiero que toda la aplicación sepa como es la base de datos porque sino cuando cambia algo de la base de datos tengo que cambiar toooda la aplicación.

Segundo porque el ResultSet deja la conexión ABIERTA a la base de datos. No se puede usar el ResultSet si está cerrada.

Como dejo abierto el ResultSet dejo la conexión a la base de datos abierta y desperdicio los recursos del sistema.

<b>Lo mejor es conectarse, recuperar los datos y desconectarse</b>

Por eso al recuperar los datos lo mapeo a objeto java. Al objeto documento le seteo los datos que le corresponden y lo mismo con los de personas.

Luego de haber creado la persona y lo agrego a la LinkedList creada previamente.

## 5. Cerrar conexión

Una vez finalizado el bucle while es momento de cerrar la conexión para liberar los recursos.

Primero cierro el ResulSet,cierro la Statement y por ultimo cierro la conexión.
```java
if(rs!=null){rs.close()}
if(stmt!=null){stmt.close()}

conn.close()
```
## Resumen hasta ahora

Comprendiendo todos los pasos que hicimos podemos decir.

1. Creamos el conector Driver en el main y un metodo llamado recuperarTodos();

2. Creamos una conexión con DriverManager
3. Creamos un Statement o query
4. Le asignamos a un ResultSet el resultado de la query del stmt con .executeQuery(querySQL)
5. Cerramos Resulset, Statement y finalizo con la conexión.


# Método recuperarTodos()

Finalmente nos queda así el metodo, solo nos falta la parte de mostrar por consola pero ese no es el punto de estas notas. El profe había hecho un override en un metodo llamado toString de la clase Persona cosa que cuando lo imprimas sea directamente llamandolo como sysout(instancia de persona) y sale todo el string con los respectivos datos. Era algo así aún no vi el video, es pura suposición.

```java

public static void recuperarTodos(){
    Connection conn = null;
    LinkedList<Persona> personas = new LinkedList<>();

    try{
        // crear conector
        conn = DriverManager.getConnection("jdbc:mysql://localhost/javatest?user=java&password=himitsu");

        //ejecutar la query
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM persona");

        //mapear de resultset a objeto java
        while(rs.next()){
            Persona p = new Persona();
            Documento d = new Documento();
            p.setDocumento(d);

            p.setId(rs.getInt("id"));
            d.setNro(rs.getString("nro doc"));
            d.setTipo(rs.getString("tipo doc"));
            p.setNombre(rs.getString("nombre"));
            p.setApellido(rs.getString("apellido"));
            p.setHabilitado(rs.getBoolean("estaHabilitado"));
        }

        //cerrar recursos
        if(rs!=null){rs.close();}
        if(stmt!=null){stmt.close();}

        conn.close();

        //Podriamos mostrar info obtenida - override toString

        System.out.println(personas)
    }
    catch(SQLException ex){
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
        
    }
}
```

 En el catch lo completamos informando el tipo de error si para conocer cual es el error que nos tira si el servicio por ejemplo de mysql no está funcionando.

# Manejar los inputs del usuario

Muchas veces estaremos buscando algo solicitado por el usuario en los inputs que necesiten acceder a la base de datos.

La idea del metodo recuperar por parametro es simular que el usuario ingresa una entrada y que busque el id de una persona.

## No hagas esto
Antes que nada esto es un <b>error</b> por las posibles inyecciones SQL. Si el usuario pone un apostrofe para cortar nuestra query bye byeeee.

Podría introducir un apostrofe por ejemplo para tirar una tabla de la base de datos. Si es que el usuario tiene el permiso necesario.

<em>Apostrofe para cerrar el primer apostrofe =' " +nom +"'" y luego un comentario con # para que el resto de la sentencia se tome como un comentario </em>
```java
//Nos llega un parametro
String nom = "'; drop table persona #"; 
``` 
 
Si no tengo permiso de usuario para ejecutar la consulta de arriba podría hacer que me devuelva la primer persona que este en la tabla la tabla.
```java
//Info ingresada por el usuario
String nom = "' or true limit 1; # "

Statement stmt2 = conn.createStatement()
ResultSet rs2 = stmt2.executeQuery("select * from persona where nombre ='"+nom+"'");

```
Y podría ser mas dañino por ejemplo una contaseña hasheada. Aquí funcionaría bien ya que nos llega esos dos parametros
```java
//Info ingresada por el usuario
String hashedPass = "dbnclsandcs";
String user = "someone";

ResultSet rs = stmt2.executeQuery("select * from usuario where user='" + user + "' and hashedPass=" + "'");
```
Pero aquí si el usuario tiene malas intenciones podría hacer algo como esto.
```java
//Info ingresada por el usuario
String hashedPass ="dbnasdasdasdndcs" // cualquier valor
String user="admin' #" //usuario administrador + apostrofe + comentar el resto

ResultSet rs = stmt2.executeQuery("select * from usuario where user='" + user + "' and hashedPass=" + "'");

//Quedaria algo así

//select * from usuario where user='admin' # el resto es ignorado

```
Dependiendo del tipo de consulta que realiza podría llegar a tener el control del usuario administrador sin tener una contraseña del usuario admin. Este riesgo de seguridad es "viejo" de más de 20 años y se conoce como <b>SQL INJECTION</b>

El siguiente texto fue proporcionado por chatgpt:

Yo le pregunto: y asi?

Statement stmt2 = conn.createStatement()
ResultSet rs2 = rs2.executeQuery("select * from persona where nombre ='"+nom+"'");

<em>El código que proporcionas tiene un problema de seguridad conocido como SQL Injection. Concatenar directamente valores ingresados por el usuario en una consulta SQL sin una validación o sanitización adecuada puede permitir que un atacante ejecute consultas maliciosas en la base de datos, lo que puede comprometer la integridad y seguridad de los datos.

En lugar de concatenar directamente los valores en la consulta SQL, se recomienda utilizar consultas parametrizadas o preparadas (PreparedStatement) en JDBC. Las consultas parametrizadas separan los datos de la consulta SQL, evitando así la posibilidad de inyección de SQL.</em>


# PreparedStatement

Para evitar las inyecciones de SQL la mayoría de los lenguajes tienen una forma de evitar esto. En Java son las PreparedStatement.

Las PreparedStatement permiten que en lugar de concatenar el parametro dejamos dentro de la query de alguna forma indicado tiene que ser completado por un parametro.

Los conectores que tienen preparedStatement realizan una <b>sanitización de los inputs</b>, es decir, los caracteres peligrosos se van, como por ejemplo el apostrofe o el # o barras invertidaspara evitar que se ejecuten de forma maliciosa.

La <b>sanitización de los inputs</b> se encarga de que los inputs no realicen ningun problema en la base de datos.

El tipo de dato es definido en el set.

stmt2.setInt(1, 2); Para el primer parametro buscame el id 2 por ejemplo


```java

private static void recuperarPorParametro(){
    Connection conn = null;

    try {
        //crear una conexión
        conn = DriverManager.getConnection("jdbc:mysql://localhost/javatest?user=java&password=himitsu");

        // Info ingresada por el usuario
        String hashedPass ="dbnasdasdasdndcs" 
        String user="admin' #" //usuario administrador + apostrofe + comentar el resto

        // Con preparedStatement el input es sanitizado.

        //Preparamos la query
        PreparedStatement stmt2 = conn.prepareStatement("select * from usuario where user=? and hashedPass=?");
        stmt2.setString(1, user);
        stmt2.setString(2, hashedPass);

        //Ejecución de query y resultado
        ResultSet rs = rs.executeQuery();

        // Resto del código
    }
    
}

```

## Y entonces utilizo el Statement común?

Si queremos realizar una consulta común sin parametros no hay problemas con usar el Statement que vimos al inicio.
```sql
Select * from Persona;
```

Pero si se necesitan parámetros es <b>obligatorio</b> el uso del PreparedStatement

# Método recuperarPorParametro()

Recordemos que la idea de este método es aprender a utilizar los inputs de los usuarios -> parametros en las queries;

Con PreparedStatement sanitizamos el input!

```java
private static void recuperarPorParametro(){
    Connection conn = null;
    try {
        //crear una conexión
        conn = 
        DriverManager.getConnection("jdbc:mysql://localhost/javatest","java","himitsu");

        //definir query
        PreparedStatement stmt = conn.prepareStatement("select * from persona where id=?");

        //definir parametros
        stmt.setInt(1,2);

        //Creación de objetos junto a seteo de documento en persona
        Persona p = new Persona();
        Documento d = new Documento();
        p.setDocumento();

        //Ejecutar query y obtener resultados
        ResultSet rs = stmt.executeQuery();

        //Mapear de ResultSet a Objeto java
        if(rs.next()){
            p.setId(rs.getInt("id"));
            d.setNro(rs.getString("nro doc"));
            d.setTipo(rs.getString("tipo doc"));
            p.setNombre(rs.getString("nombre"));
            p.setApellido(rs.getString("apellido"));
            p.setHabilitado(rs.getBoolean("estaHabilitado"));
        }

        //Cerrar recursos

        if(rs!=null){rs.close()}
        if(stmt!=null){stmt.close()}

        conn.close();

        // muestro objeto encontrado por id

    } catch(SQLException ex){
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
    }
}
```
# Insertando una nueva persona

Mismos pasos de siempre.

1. Pedimos la conexión
2. Creamos Statement

La tabla Persona tiene que el id es autoincremental, es decir, la base de datos se encargará de asignarle el valor.

Simularemos el ingreso de una nueva persona. Objeto persona y documento.

El executeUpdate recordemos que devuelve un entero para ver la cantidad de filas fueron afectadas. En este caso sería 1 pues es la agregada.

El id nos da 0 y no es 0.

## No hagas esto

Supongamos que queremos utilizar el id de la persona para utilizarlo en otro lado.

Como es autoincremental no lo elegimos nosotros sino la base de datos, nunca se lo hemos asignados.

Supongamos que estamos insertando una factura y luego con ese id debemos insertar el detalle de la factura (cada linea en otra tabla)

Necesitamos recuperar el id de la factura para poder utilizarlo en el detalle.

Se va a encontrar soluciones donde hacen un select max id from persona para recuperar el ultimo id que se insertó. NO hagamos eso

1. Consume muchos recursos.
2. No sabemos si es realmente el ultimo insert. (mucho volumen de entrada de datos sea pagina web o de escritorio o lo que sea)

o tambien una consulta lastInsertID que devuelve el ultimo id que se inserto desde la ultima conexión.

Para hacerlo bien debería ser dentro de una transacción (insert update delete).

3. Existe otro factor que es el round trip time -> enviar el paquete desde un servidor de aplicación hasta el servidor de base de datos y del servidor de base de datos volver al servidor de app sin tener en cuenta el tiempo de ejecución de la consulta.

Demora de consulta por viaje en la red.

## Haz esto

En lugar de realizar otra consulta los lenguajes de programación tienen una forma de recuperar esta información directamente.

Tienen la PreparedStatement.RETURN_GENERATED_KEYS

### RETURN_GENERATED_KEYS

En el contexto de JDBC (Java Database Connectivity), las "returned generated keys" se refieren a las claves primarias (primary keys) generadas automáticamente por una base de datos después de insertar una nueva fila en una tabla.

# insertarNuevo()

Y así nos quedó

```java
private static void insertarNuevo(){
    // Intanciamos la conexion en null
    Connection conn = null;

    // Simulamos la entrada de estos datos

    Persona p = new Persona();
    Documento d = new Documento();
    documento.setTipo("dni");
    documento.setNro(44444444);
    p.setDocumento(d);
    p.setNombre("Someone");
    p.setApellido("Else");
    p.setHabilitado(true);

    try {
        //crear conexion
        conn =
        DriverManager.getConnection("jdbc:mysql://localhost/javatest","java", "himitsu");

        //definir query
        PreparedStatement pstmt = conn.prepareStatement("insert into persona(tipo doc, nro doc, nombre, apellido, estaHabilitado) values (? ? ? ? ? ?)", PreparedStatement.RETURN_GENERATED_KEYS);
        //Prapara devolver las claves primarias generadas automaticamente

        // seteo los parametros de la query
        pstmt.setString(1, p.getDocumento().getTipo());
        pstmt.setInt(2,p.getDocumento().getTipo());
        pstmt.setString(3, p.getNombre());
        pstmt.setString(4, p.getApellido());
        pstmt.setBoolean(5, p.isHabilitado());

        // ejecución de la query 
        pstmt.executeUpdate();

       

        // obtener el dato de id (clave primaria)
        ResultSet keyResultSet = pstmt.getGeneratedKeys();

        if(keyResultSet == null && keyResultSet.next()){
            int id=keyResultSet.getInt(1);
            System.out.println("ID: " + id);
            p.setId(id);
        }

        //Libero los recursos

        if(keyResultSet != null){keyResultSet.close();}
        if(pstmt != null){ pstmt.close();}

        conn.close();

        //Muestro la persona con el nuevo id

    }
    catch (ex){

    }
}
```











