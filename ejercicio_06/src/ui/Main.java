package ui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

import daos.ProductoDAO;

import java.sql.*;
import entities.*;

public class Main {

    public static void main(String[] args) {
    	
    	Scanner lector = new Scanner(System.in);
    	ProductoDAO.inicializar();
        String op = "";
        while(!op.equals("6")) {
        	
        	System.out.println("[1]. Listar productos (id,name,price)\n[2]. Buscar producto por id(id)\n[3]. Cargar Producto (*)\n[4]. Eliminar Producto (id)\n[5]. Modificar producto (*)\n[6]. Salir");
        	op = lector.nextLine();
        	
        	switch (op) {
        	
        	case "1": listarProductos();
        		break;
        		
        	case "2": mostrarProducto(lector);
        		break;
        		
        	case "3": agregarProducto(lector);
        		break;
        	
        	case "4":
        		break;
        		
        	case "5":
        		break;
        			
        	}
        }
        	
        System.out.println("Fin");
    }
      
private static void agregarProducto(Scanner lector) {
	
	Producto p = new Producto();
	
	System.out.println("Ingrese un ID de Producto");
	p.setName(lector.nextLine());
	System.out.println("Ingrese Descripción del Producto");
	p.setDescription(lector.nextLine());
	System.out.println("Ingrese el precio del Producto");
	p.setPrice(Double.parseDouble(lector.nextLine()));
	System.out.println("Ingrese el stock del Producto");
	p.setStock(Integer.parseInt(lector.nextLine()));
	System.out.println("Ingrese si el envío está incluido [1] Si [2] No");
	p.setShippingIncluded(Boolean.parseBoolean(lector.nextLine()));
	
	ProductoDAO.registrarProducto(p);
	
		
	}

private static void mostrarProducto(Scanner lector) {
		
	 System.out.println("Ingrese un ID de Producto");
	 int id = Integer.parseInt(lector.nextLine());
	 
	 Map <String, Object> map = ProductoDAO.devolverProducto(id);
	 
	 if(map.get("error") != null) {
		 System.out.println(map.get("error"));
	 }
	 else {
		 Producto producto = (Producto) map.get("producto");
		 System.out.println("ID: " + producto.getId() + " Nombre: " + producto.getName() +" Stock: " + producto.getStock() + " Precio: " + producto.getPrice() + " Descripcion: " + producto.getDescription() + " Envío: " + producto.isShippingIncluded());
	 }
}



// Método para listar id, name y price de todos los productos
    private static void listarProductos() {
    	
    	
    	Map<String, Object> map  = ProductoDAO.devolverProductos();
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
 
 
    
    
}
