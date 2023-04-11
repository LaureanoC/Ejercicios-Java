package ejercicio_05a;

import javax.swing.JOptionPane;

public class Empleado {

	private String dni;
	private String nombre;
	private String apellido;
	private String email;
	private double sueldoBase;
	
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
	
	public void setSueldoBase(double d) {
		this.sueldoBase = d;
	}
	
	// Constructor por parámetros
	public Empleado(String dni, String nombre, String apellido, String email, float sueldoBase) {
		setDni(dni);
		setNombre(nombre);
		setApellido(apellido);
		setEmail(email);
		setSueldoBase(sueldoBase);
	}
	
	//Constructor JOptionPane
	public Empleado() {
		
		setDni(JOptionPane.showInputDialog("Ingrese su DNI"));
		setNombre(JOptionPane.showInputDialog("Ingrese su nombre"));
		setApellido(JOptionPane.showInputDialog("Ingrese su apellido"));
		setEmail(JOptionPane.showInputDialog("Ingrese su email"));
		setSueldoBase(Double.parseDouble(JOptionPane.showInputDialog("Ingrese el sueldo base")));
	}
}
