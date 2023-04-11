package ejercicio_05a;

import javax.swing.JOptionPane;

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
	
	//Constructor por par�metros
	public Administrativo(String dni, String nombre, String apellido, String email, float sueldoBase,int hsMes, int hsExtra) {
		super(dni, nombre, apellido, email,sueldoBase);
		setHsExtra(hsExtra);
		setHsMes(hsMes);
	}
	
	//Constructor JOptionPane
	public Administrativo() {
		
		super();
		setHsMes(Integer.parseInt(JOptionPane.showInputDialog("Ingrese las horas del mes")));
		setHsExtra(Integer.parseInt(JOptionPane.showInputDialog("Ingrese las horas extras")));
	}
}
