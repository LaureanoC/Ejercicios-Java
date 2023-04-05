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
	
	public double getSueldo() {
		return this.getSueldoBase() + (porcenComision*totalVentas/100);
	}
	
	//Constructor utilizado
	public Vendedor(String dni, String nombre, String apellido, String email, float sueldoBase, int porcenComision, int totalVentas) {
		super(dni, nombre, apellido, email,sueldoBase);
		setTotalVentas(totalVentas);
		setPorcenComision(porcenComision);
	}
	
	//Constructor no utilizado en este ejercicio
	public Vendedor() {
		
		super();
		
		Scanner leer = new Scanner(System.in);
		
		System.out.println("Ingresar total de ventas");
		setTotalVentas(leer.nextInt());
		
		System.out.println("Ingresar porcentaje de comision");
		setPorcenComision(leer.nextInt());
		
		leer.close();
	}

	
}
