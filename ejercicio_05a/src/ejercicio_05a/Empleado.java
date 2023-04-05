package ejercicio_05a;

import java.util.Scanner;

public class Empleado {

	private String dni;
	private String nombre;
	private String apellido;
	private String email;
	private float sueldoBase;
	
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
	
	public float getSueldoBase() {
		return sueldoBase;
	}
	
	public void setSueldoBase(float sueldoBase) {
		this.sueldoBase = sueldoBase;
	}
	
	
	
	// Constructor utilizado
	public Empleado(String dni, String nombre, String apellido, String email, float sueldoBase) {
		setDni(dni);
		setNombre(nombre);
		setApellido(apellido);
		setEmail(email);
		setSueldoBase(sueldoBase);
	}
	
	
	
	//Constructor no utilizado en este ejercicio
	public Empleado() {
		
		Scanner leer = new Scanner(System.in);
		
		System.out.print("Ingrese su DNI: ");
		setDni(leer.nextLine());
		
		System.out.print("Ingrese su nombre: ");
		setNombre(leer.nextLine());
		
		System.out.print("Ingrese su apellido: ");
		setApellido(leer.nextLine());
		
		System.out.print("Ingrese su email: ");
		setEmail(leer.nextLine());
		
		System.out.print("Ingrese su sueldo base: ");
		setSueldoBase(leer.nextFloat());
		
		leer.close();
	}


	


}
