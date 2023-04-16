package logic;

import java.util.HashMap;
import java.util.Map;

import data.ProductoDAO;
import entities.Producto;

public class ControladorCRUD {

	private static ProductoDAO pdao = new ProductoDAO();

	public static Map<String, Object> recuperarTodosLosProductos() {

		Map<String, Object> mapa = pdao.devolverTodosLosProductos();
		return mapa;
	}

	public Map<String, Object> recuperarProducto(Producto p) {
		
		Map<String, Object> mapa = pdao.devolverProducto(p);
		return mapa;
	}
	
	public Map<String, Object> registrarProducto(Producto p){
		Map<String, Object> mapa = pdao.insertarProducto(p);
		return mapa;
		
	}
	
	public Map<String, Object> eliminarProducto(Producto p){
		Map<String, Object> mapa = pdao.destruirProducto(p);
		return mapa;
		
	}
	
	
	
		
}
