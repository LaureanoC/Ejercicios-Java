package ui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.sql.*;
import entities.*;

public class Main {

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
        	
        	case "1": listarProductos();
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
 
    public static Map<String, Object> devolverProductos() {
    	
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
    		
    		// Asignamos la lista de productos al map y le ponemos mensaje null
    		mapa.put("productos", productos);
    		mapa.put("error", null);
    		
    		//Liberamos los recursos y cerramos la conexón
    		if(rs!=null){rs.close();}
    		if(st!=null){st.close();}

    		conn.close();
    		
    	}
    	catch (SQLException ex) {
    		String mensaje = "SQLException: " + ex.getMessage() + " SQLState: " + ex.getSQLState() + "VendorError: " + ex.getErrorCode();
    		mapa.put("productos", null);
    		mapa.put("error", mensaje);
    		return mapa;
    	}
    	
    	return mapa;
    }
    
    
}
