package ejercicio_05a;

import java.util.Scanner;

// abstract porque nunca vamos a instanciar empleado

public abstract class Empleado { 

	private String dni;
	private String nombre;
	private String apellido;
	private String email;
	private double sueldoBase;
	
	//Las clases concretas no puden implementar metodos abstractos. 
	//Metodos concretos de las clases abstractas

	public String getDni() {
		return dni;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public double getSueldoBase() { 
		return sueldoBase;
	}
	
	public void setSueldoBase(double sueldoBase) {
		this.sueldoBase = sueldoBase;
	}
	
	//abstract para que pueda ejecutar administrativo y vendedor getSueldo()
	
	//Java siempre le hace caso al objeto, como el array es de Empleado lo busca en empleado y no en el objeto que tiene adentro
	// En otros lenguaje
	
	public abstract double getSueldo(); 
	
	// Constructor Empleado por par�metros
	public Empleado(String dni, String nombre, String apellido, String email, double sueldoBase) {
		setDni(dni);
		setNombre(nombre);
		setApellido(apellido);
		setEmail(email);
		setSueldoBase(sueldoBase);
	}
	
	
	
	//Constructor Empleado con Scanner
	public Empleado(Scanner leer) {
		
		System.out.print("Ingrese su DNI: ");
		setDni(leer.nextLine());
		
		System.out.print("Ingrese su nombre: ");
		setNombre(leer.nextLine());
		
		System.out.print("Ingrese su apellido: ");
		setApellido(leer.nextLine());
		
		System.out.print("Ingrese su email: ");
		setEmail(leer.nextLine());
		
	
		System.out.print("Ingrese su sueldo base: ");
		setSueldoBase(Double.parseDouble(leer.nextLine()));
		
	}

}
