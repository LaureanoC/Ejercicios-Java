package ejercicio_05a;

import java.util.Scanner;

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
	
	@Override
	public double getSueldo() {
		return this.getSueldoBase() + (porcenComision*totalVentas/100);
	}
	
	//Constructor Vendedor por parámetros
	public Vendedor(String dni, String nombre, String apellido, String email, double sueldoBase, int porcenComision, int totalVentas) {
		super(dni, nombre, apellido, email,sueldoBase);
		setTotalVentas(totalVentas);
		setPorcenComision(porcenComision);
	}
	
	//Constructor Vendedor con Scanner
	public Vendedor(Scanner leer) {
		
		super(leer);
		
		System.out.println("Ingresar total de ventas: ");
		setTotalVentas(Integer.parseInt(leer.nextLine()));
		
		System.out.println("Ingresar porcentaje de comision: ");
		setPorcenComision(Integer.parseInt(leer.nextLine()));
		
	}

	
}
