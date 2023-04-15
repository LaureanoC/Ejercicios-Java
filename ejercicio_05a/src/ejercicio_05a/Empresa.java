package ejercicio_05a;

import java.util.Scanner;

public class Empresa {
	
	Empleado[] empleados = new Empleado[20];
	
	
	public void getEmpleados() {
		
		// Listar los empleados indicando: dni, nombre, apellido y sueldo.
		
		String lista = "";
		for(Empleado empleado : empleados) {
			
				if(empleado != null) {
					lista = lista + "DNI: " + empleado.getDni() + " Nombre: " + empleado.getNombre() + " Apellido: " + empleado.getApellido() + " Sueldo: " + empleado.getSueldo() + "\n\n";
				} 
				else {
					break;
				}
		}
		
		System.out.println(lista);
	}
	
	public void cargarEmpleados() {
		
		Scanner lector = new Scanner(System.in);
		
		// Cargar 20 empleados o cargar como min 20 empleados
		String eleccion;
		int i=0;
		while (i<20) {
			
			//Sin validación
			System.out.println("Agregar [a]dministrativo o [v]endedor -- [s]alir");
			eleccion = (lector.nextLine().toLowerCase());
			

			if(eleccion.equals("a")) {
				Administrativo a = new Administrativo(lector);
				this.empleados[i] = a;
			}
			
			if (eleccion.equals("v")) {
				Vendedor v = new Vendedor(lector);
				this.empleados[i] = v;
			}
			
			if(eleccion.equals("s")) {
				i = 20;
			}
			
			i++;
		}
		
		lector.close();
	}
	
	
}