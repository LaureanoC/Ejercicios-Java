package ejercicio_05a;

import java.util.Scanner;

public class Administrativo extends Empleado {

	private int hsExtra;
	private int hsMes;
	
	public int getHsExtra() {
		return hsExtra;
	}

	public void setHsExtra(int hsExtra) {
		this.hsExtra = hsExtra;
	}

	public int getHsMes() {
		return hsMes;
	}

	public void setHsMes(int hsMes) {
		this.hsMes = hsMes;
	}
	
	@Override
	public double getSueldo() {
		return (this.getSueldoBase() * ((hsExtra * 1.5)+hsMes) / hsMes);
	}
	
	//Constructor Administrativo por parámetros
	public Administrativo(String dni, String nombre, String apellido, String email, double sueldoBase,int hsMes, int hsExtra) {
		super(dni, nombre, apellido, email,sueldoBase);
		setHsExtra(hsExtra);
		setHsMes(hsMes);
	}
	
	//Constructor Administrativo con Scanner
	public Administrativo(Scanner leer) {
		super(leer);
		
		System.out.println("Ingresar horas extras");
		setHsExtra(Integer.parseInt(leer.nextLine()));
		
		System.out.println("Ingresar horas de mes");
		setHsMes(Integer.parseInt(leer.nextLine()));
	}
}
