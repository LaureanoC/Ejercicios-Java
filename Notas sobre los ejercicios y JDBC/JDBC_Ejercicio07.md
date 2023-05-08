# Base de datos

Copiemos y ejecutemos el siguiente SQL.

```sql
CREATE DATABASE  IF NOT EXISTS `java`;
USE `java`;

--
-- Table structure for table `persona`
--

DROP TABLE IF EXISTS `persona`;
CREATE TABLE `persona` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo_doc` varchar(10) NOT NULL,
  `nro_doc` varchar(45) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `apellido` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `habilitado` tinyint(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `persona`
--

INSERT INTO `persona` VALUES (1,'dni','42950004','Laureano Neyén','Chaves','laureano@gmail.com','2478515151',1,'qwerty'),(3,'dni','77777777','Pesca','Ditto','pescadito@gmail.com','2478515152',0,'qwerty'),(4,'dni','42950003','Elsa','Pay','zapallito@gmail.com','2478515253',1,'zapa');

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
CREATE TABLE `rol` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `rol`
--


INSERT INTO `rol` VALUES (1,'admin'),(2,'user');

--
-- Table structure for table `rol_persona`
--

DROP TABLE IF EXISTS `rol_persona`;
CREATE TABLE `rol_persona` (
  `id_persona` int NOT NULL,
  `id_rol` int NOT NULL,
  PRIMARY KEY (`id_persona`,`id_rol`),
  KEY `rol_persona_rol_fk` (`id_rol`),
  CONSTRAINT `rol_persona_persona_fk` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id`),
  CONSTRAINT `rol_persona_rol_fk` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `rol_persona`
--

INSERT INTO `rol_persona` VALUES (1,1),(3,1),(1,2),(3,2),(4,2);
```

Creación del usuario

```sql
create user javaej07@'%' identified by 'laureano';
grant select, insert, update, delete on java.* to javaej07@'%';
```

# DbConnector

Esta clase se encargará de abrir una conexión y también de cerrar.

```java
package data;

import java.sql.*;

public class DbConnector {

	private static DbConnector instancia;

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String host = "localhost";
	private String port = "3306";
	private String user = "javaej07";
	private String password = "laureano";
	private String db = "java";
	private int conectados = 0;
	private Connection conn = null;

	private DbConnector() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static DbConnector getInstancia() {
		if (instancia == null) {
			instancia = new DbConnector();
		}
		return instancia;
	}

	public Connection getConn() {
		try {
			if (conn == null || conn.isClosed()) {
				conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db, user, password);
				conectados = 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conectados++;
		return conn;
	}

	public void releaseConn() {
		conectados--;
		try {
			if (conectados <= 0) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
```

# Ejercicio 07

El ejercicio 07 tenía un código base ya hecho y solo faltaba completar las funcionalidades de search, new, edit y delete.

Apliqué los conceptos aprendidos en el ejercicio 06 + la importancia del update para obtener el id, modificar los datos de la persona, eliminar todas las relaciones correspondientes y luego insertarlas.

En el delete hay que tener un orden de uso.

Primero borro los registros de la relacion rol_persona y luego la persona. 

En este ejercicio el objeto persona también tenia al objeto Documento y un hashmap que contenía el id del rol y el objeto Rol.

La dificultad también fue de comprender como realizar múltiples insert y para ello utilicé un for de hashmap.keySet() -> "Para cada clave del hashmap quiero que me ejecutes una query"

Hay que preguntar si es una mala práctica poner todas las statements juntas o tengo que crear otros métodos para que sea mejor. 
