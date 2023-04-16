package ui;

import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

import entities.Producto;
import logic.ControladorCRUD;

public class Menu {

	static ControladorCRUD c = new ControladorCRUD();

	public static void main(String[] args) {

		Scanner lector = new Scanner(System.in);

		String op = "";
		while (!op.equals("6")) {

			System.out.println(
					"[1]. Listar todos los productos (id,name,price)\n[2]. Buscar producto por id(id)\n[3]. Cargar Producto (*)\n[4]. Eliminar Producto (id)\n[5]. Modificar producto (*)\n[6]. Salir");
			op = lector.nextLine();

			switch (op) {

			case "1":
				mostrarTodosLosProductos(c);
				break;

			case "2":
				mostrarProducto(c, lector);
				break;

			case "3":
				ingresarProducto(c, lector);
				break;

			case "4": borrarProducto(c,lector);
				break;

			case "5":
				break;

			}
		}

		System.out.println("Fin");
	}

	private static void mostrarTodosLosProductos(ControladorCRUD c) {

		Map<String, Object> map = c.recuperarTodosLosProductos();

		if (map.get("error") != null) {
			System.out.println(map.get("error"));
		} else {

			// casteo para que sea linkedlist ya que el map decia STRING, OBJECT

			LinkedList<Producto> productos = (LinkedList<Producto>) map.get("productos");

			String listado = "";

			for (Producto producto : productos) {

				listado += "ID: " + producto.getId() + " Nombre: " + producto.getName() + " Precio: "
						+ producto.getPrice() + "\n\n";
			}

			System.out.println(listado);
		}

	}

	private static void mostrarProducto(ControladorCRUD c, Scanner lector) {

		System.out.println("Ingrese un ID de Producto");
		int id = Integer.parseInt(lector.nextLine());

		Producto p = new Producto();
		p.setId(id);

		Map<String, Object> map = c.recuperarProducto(p);

		if (map.get("error") != null) {
			System.out.println(map.get("error"));
		} else {
			Producto producto = (Producto) map.get("producto");
			System.out.println("ID: " + producto.getId() + " Nombre: " + producto.getName() + " Stock: "
					+ producto.getStock() + " Precio: " + producto.getPrice() + " Descripcion: "
					+ producto.getDescription() + " Envío: " + producto.isShippingIncluded());
		}
	}
	
	private static void ingresarProducto(ControladorCRUD c, Scanner lector) {
		
		Producto p = new Producto();
		
		System.out.println("Ingresar nombre del Producto");
		p.setName(lector.nextLine());
		
		System.out.println("Ingresar precio del Producto");
		p.setPrice(Double.parseDouble(lector.nextLine()));
		
		System.out.println("Ingresar stock del Producto");
		p.setStock(Integer.parseInt(lector.nextLine()));
		
		System.out.println("Ingresar descripcion del Producto");
		p.setDescription(lector.nextLine());
		
		System.out.println("Ingresar si el envío del producto está incluido");
		p.setShippingIncluded(Boolean.parseBoolean(lector.nextLine()));
		
		Map<String,Object> mapa = c.registrarProducto(p);
		
		if (mapa.get("error") != null) {
			System.out.println(mapa.get("error"));
		} else {
			Producto producto = (Producto) mapa.get("producto");
			
			System.out.println("PRODUCTO REGISTRADO CON ID: " + producto.getId());
		}
		

	}
	
	private static void borrarProducto(ControladorCRUD c, Scanner lector) {

		System.out.println("Ingrese un ID de Producto");
		int id = Integer.parseInt(lector.nextLine());

		Producto p = new Producto();
		p.setId(id);

		Map<String, Object> map = c.eliminarProducto(p);

		if (map.get("error") != null) {
			System.out.println(map.get("error"));
		} else {
			Producto producto = (Producto) map.get("producto");
			System.out.println("PRODUCTO ELIMINADO CON ID: " + producto.getId());
		}
	}

}
