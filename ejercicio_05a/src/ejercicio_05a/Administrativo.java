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
	
	public double getSueldo() {
		return (this.getSueldoBase() * ((hsExtra * 1.5)+hsMes) / hsMes);
	}
	
	//Constructor utilizado
	public Administrativo(String dni, String nombre, String apellido, String email, float sueldoBase,int hsMes, int hsExtra) {
		super(dni, nombre, apellido, email,sueldoBase);
		setHsExtra(hsExtra);
		setHsMes(hsMes);
	}
	
	//Constructor no utilizado en este ejercicio
	public Administrativo() {
		super();
		Scanner leer = new Scanner(System.in);
		
		System.out.println("Ingresar horas extras");
		setHsExtra(leer.nextInt());
		
		System.out.println("Ingresar horas de mes");
		setHsMes(leer.nextInt());
		
		leer.close();
	}
}
