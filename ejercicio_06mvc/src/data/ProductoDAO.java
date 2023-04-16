package data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.sql.*;

import entities.Producto;

public class ProductoDAO {

	public ProductoDAO() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Map<String, Object> devolverTodosLosProductos() {

		Map<String, Object> mapa = new HashMap();
		LinkedList<Producto> productos = new LinkedList<Producto>();

		Connection conn = null;
		try {

			// abrir conexión
			conn = DriverManager.getConnection("jdbc:mysql://localhost/javamarket", "javamarketuser", "laureano");

			// creo statement
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select product_id, product_name, product_price from product");

			// mapeo resultset a objeto java y agrego a productos

			while (rs.next()) {
				Producto p = new Producto();

				p.setId(rs.getInt("product_id"));
				p.setName(rs.getString("product_name"));
				p.setPrice(rs.getDouble("product_price"));

				productos.add(p);
			}

			// cerrar conexión

			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}

			conn.close();

			mapa.put("productos", productos);
			mapa.put("error", null);

		} catch (SQLException ex) {
			String mensaje = "SQLException: " + ex.getMessage() + " SQLState: " + ex.getSQLState() + "VendorError: "
					+ ex.getErrorCode();
			mapa.put("error", mensaje);
			mapa.put("producto", null);
		}

		return mapa;

	}

	public Map<String, Object> devolverProducto(Producto p) {

		Connection conn = null;
		Map<String, Object> mapa = new HashMap();

		try {
			// abro conexión
			conn = DriverManager.getConnection("jdbc:mysql://localhost/javamarket", "javamarketuser", "laureano");

			// preparo la query
			PreparedStatement st = conn.prepareStatement("select * from product where product_id=?");

			// defino los parametros
			st.setInt(1, p.getId());

			// ejecuto y guardo resultado
			ResultSet rs = st.executeQuery();

			// puedo utilizar if porque es solo 1, con while da igual
			if (rs.next()) {

				p.setName(rs.getString("product_name"));
				p.setDescription(rs.getString("product_description"));
				p.setPrice(rs.getDouble("product_price"));
				p.setStock(rs.getInt("product_stock"));
				p.setShippingIncluded(rs.getBoolean("product_shipping_included"));
			}

			mapa.put("producto", p);
			mapa.put("error", null);

		} catch (SQLException ex) {
			String mensaje = "SQLException: " + ex.getMessage() + "SQLState: " + ex.getSQLState() + "VendorError: "
					+ ex.getErrorCode();
			mapa.put("error", mensaje);
			mapa.put("producto", null);
		}

		return mapa;

	}

	public Map<String, Object> insertarProducto(Producto p) {

		Connection conn = null;
		Map<String, Object> mapa = new HashMap();

		try {
			// abro conexión
			conn = DriverManager.getConnection("jdbc:mysql://localhost/javamarket", "javamarketuser", "laureano");

			// Preparo la Statement con su PreparedStatement.RETURN_GENERATED_KEYS
			// La clave primaria es autogenerada por la base de datos
			PreparedStatement st = conn.prepareStatement(
					"insert into product(product_name, product_price, product_stock, product_description, product_shipping_included) values (?,?,?,?,?)",
					PreparedStatement.RETURN_GENERATED_KEYS);

			// Defino lo parámetros requeridos en la query
			st.setString(1, p.getName());
			st.setDouble(2, p.getPrice());
			st.setInt(3, p.getStock());
			st.setString(4, p.getDescription());
			st.setBoolean(5, p.isShippingIncluded());

			// Ejecuto la modificación de tablas
			st.executeUpdate();

			// Ahora busco la clave primaria generada

			ResultSet rskey = st.getGeneratedKeys();

			// Si se encuentra algo en la primera posición entrá
			if (rskey.next()) {
				// Asigno el id generado
				p.setId(rskey.getInt(1));
			}

			// Libero los recursos

			if (rskey != null) {
				rskey.close();
			}
			if (st != null) {
				st.close();
			}

			conn.close();
			
			// Agrego el producto al map
			
			mapa.put("producto", p);
			mapa.put("error", null);

		} catch (SQLException ex) {
			String mensaje = "SQLException: " + ex.getMessage() + "SQLState: " + ex.getSQLState() + "VendorError: "
					+ ex.getErrorCode();
			mapa.put("error", mensaje);
			mapa.put("producto", null);
		}

		return mapa;

	}
	
	
	
	
	public Map<String, Object> destruirProducto(Producto p) {

		Connection conn = null;
		Map<String, Object> mapa = new HashMap();

		try {
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost/javamarket", "javamarketuser", "laureano");

			PreparedStatement st = conn.prepareStatement("delete from product where product_id=? ");
			
			st.setInt(1, p.getId());
			
			st.executeUpdate();

			mapa.put("producto", p);
			mapa.put("error", null);

		} catch (SQLException ex) {
			String mensaje = "SQLException: " + ex.getMessage() + "SQLState: " + ex.getSQLState() + "VendorError: "
					+ ex.getErrorCode();
			mapa.put("error", mensaje);
			mapa.put("producto", null);
		}

		return mapa;

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
