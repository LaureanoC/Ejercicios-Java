package ejercicio_05a;

public class Principal_05_JOptionPane {

	public static void main(String[] args) {
		
/*Enunciado
  *Cargar una lista de empleados (máximo 20) preguntado en cada uno si son administrativos o 
  *vendedores y cargar todos los datos respectivos.
  *Luego listar los empleados indicando: dni, nombre, apellido y sueldo.
*/	
		Empresa e = new Empresa();
		e.cargarEmpleados();
		e.getEmpleados();
	}
	
}


