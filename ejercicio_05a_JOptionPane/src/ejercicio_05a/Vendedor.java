package ejercicio_05a;

import javax.swing.JOptionPane;

public class Vendedor extends Empleado{
	
	private int porcenComision;
	private int totalVentas;
	
	public int getPorcenComision() {
		return porcenComision;
	}
	
	public void setPorcenComision(int porcenComision) {
		this.porcenComision = porcenComision;
	}
	
	public int getTotalVentas() {
		return totalVentas;
	}
	
	public void setTotalVentas(int totalVentas) {
		this.totalVentas = totalVentas;
	}
	
	public double getSueldo() {
		return this.getSueldoBase() + (porcenComision*totalVentas/100);
	}
	
	//Constructor por parámetros
	public Vendedor(String dni, String nombre, String apellido, String email, float sueldoBase, int porcenComision, int totalVentas) {
		
		super(dni, nombre, apellido, email,sueldoBase);
		setTotalVentas(totalVentas);
		setPorcenComision(porcenComision);
	}
	
	//Constructor JOptionPane
	public Vendedor() {
	
		super();
		setTotalVentas(Integer.parseInt(JOptionPane.showInputDialog("Ingrese el total de ventas")));
		setPorcenComision(Integer.parseInt(JOptionPane.showInputDialog("Ingrese el porcentaje de comisión")));
			
	}

	
}
