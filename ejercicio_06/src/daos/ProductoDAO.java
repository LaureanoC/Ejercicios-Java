package daos;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import entities.Producto;

public class ProductoDAO {

	public static Map<String, Object> devolverProductos() {

		Map<String, Object> mapa = new HashMap<>();
		LinkedList<Producto> productos = new LinkedList<>();
		Connection conn = null;

		try {
			// crear conexión
			conn = DriverManager.getConnection("jdbc:mysql://localhost/javamarket", "javamarketuser", "laureano");

			// creación y ejecución
			PreparedStatement st = conn.prepareStatement("select * from product");
			ResultSet rs = st.executeQuery();

			// mapear resultset a objeto java

			while (rs.next()) {
				Producto p = new Producto();

				p.setId(rs.getInt("product_id"));
				p.setName(rs.getString("product_name"));
				p.setPrice(rs.getDouble("product_price"));

				productos.add(p);
			}

			// Asignamos la lista de productos al map y le ponemos mensaje null
			mapa.put("productos", productos);
			mapa.put("error", null);

			// Liberamos los recursos y cerramos la conexón
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}

			conn.close();

		} catch (SQLException ex) {
			String mensaje = "SQLException: " + ex.getMessage() + " SQLState: " + ex.getSQLState() + "VendorError: "
					+ ex.getErrorCode();
			mapa.put("productos", null);
			mapa.put("error", mensaje);
			return mapa;
		}

		return mapa;
	}

	public static Map<String, Object> devolverProducto(int id) {

		Map<String, Object> mapa = new HashMap();
		Connection conn = null;

		try {
			// abro conexión
			conn = DriverManager.getConnection("jdbc:mysql://localhost/javamarket", "javamarketuser", "laureano");

			// Creo statement y ejecuto query
			PreparedStatement st = conn.prepareStatement("select * from product where product_id=?");

			// Defino el parámetro
			st.setInt(1, id);

			// Ejecuto query y obtengo resultset
			ResultSet rs = st.executeQuery();

			// Mapeo, puedo utilizar if si es solo un valor, con el while da lo mismo

			Producto p = new Producto(); // Lo pongo afuera ya que es 1

			if (rs.next()) {
				p.setId(rs.getInt("product_id"));
				p.setName(rs.getString("product_name"));
				p.setPrice(rs.getDouble("product_price"));
				p.setStock(rs.getInt("product_stock"));
				p.setDescription(rs.getString("product_description"));
				p.setShippingIncluded(rs.getBoolean("product_shipping_included"));
			}

			// Liberar recursos

			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}

			conn.close();

			// lo agrego al mapa
			mapa.put("producto", p);

		} catch (SQLException ex) {
			String mensaje = "SQLException: " + ex.getMessage() + " SQLState: " + ex.getSQLState() + "VendorError: "
					+ ex.getErrorCode();
			mapa.put("producto", null);
			mapa.put("error", mensaje);
			return mapa;
		}

		return mapa;
	}
	
	public static void registrarProducto(Producto p) {
		
		
		Connection conn = null;
		
		try {
			
			// abrir conexión
			conn = DriverManager.getConnection("jdbc:mysql://localhost/javamarket","javamarketuser","laureano");
			
			Statement st = conn.createStatement();
			
		}
		catch (SQLException ex) {
			String mensaje = "SQLException: " + ex.getMessage() + " SQLState: " + ex.getSQLState() + "VendorError: "
					+ ex.getErrorCode();
			
			return null
		}
		
	}
	
	public static void inicializar() {
		   
		try {
	            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	        } 
	        catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	}

}
